package com.sap.repositories;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.sap.entities.Product;
import com.sap.exceptions.ProductException;
import com.sap.exceptions.EntityNotFoundException;

public class ProductRepository extends Repository {

	public Product getProductByField(String fieldName, String fieldValue) throws SQLException {
		ResultSet rs = null;
		PreparedStatement ps;
		ps = connection.prepareStatement("select * from product where " + fieldName + " = ?");
		ps.setString(1, fieldValue);
		rs = ps.executeQuery();
		if (!rs.next())
			throw new EntityNotFoundException("There is no product with " + fieldName + " = " + fieldValue);
		return createProductFromResultSet(rs);
	}

	public void addNewProduct(Product product) throws SQLException {
		PreparedStatement ps = connection
				.prepareStatement("insert into product(id,name,quantity,minimalPrice,actualPrice) values(?,?,?,?,?)");
		ps.setInt(1, getNextId("product"));
		ps.setString(2, product.getName());
		ps.setDouble(3, product.getQuantity());
		ps.setDouble(4, product.getMinimalPrice());
		ps.setDouble(5, product.getActualPrice());
		ps.executeUpdate();
		ps.close();
	}

	public void deleteProduct(Integer id) throws SQLException {
		getProductByField("id", id.toString());
		Statement statement = connection.createStatement();
		statement.executeUpdate("delete from product where id = " + id.toString());
	}

	public void updateField(Integer id, String fieldName, String fieldValue) throws SQLException {
		getProductByField("id", id.toString());
		updateFieldOfEntity("product", id, fieldName, fieldValue);
	}

	public List<Product> getAllProducts() throws SQLException {
		List<Product> products = new ArrayList<Product>();
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery("select * from product");
		while (rs.next()) {
			products.add(createProductFromResultSet(rs));
		}
		return products;
	}

	public List<Product> getAllBlackFridayProducts() throws SQLException {
		List<Product> products = new ArrayList<Product>();
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery("select * from product where blackFriday = 1");
		while (rs.next()) {
			products.add(createProductFromResultSet(rs));
		}
		return products;
	}

	public List<Product> getAllExistingProducts() throws SQLException {
		List<Product> products = new ArrayList<Product>();
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery("select * from product where quantity != 0");
		while (rs.next()) {
			products.add(createProductFromResultSet(rs));
		}
		return products;
	}

	public void declareProductForBlackFriday(Integer id, Double blackFridayPercentage)
			throws SQLException, ProductException {
		Product product = getProductByField("id", id.toString());
		Double wantedPrice = product.getActualPrice() - (product.getActualPrice() * (blackFridayPercentage / 100));
		if (product.isBlackFriday())
			throw new ProductException("The product already participates in black friday");
		if (product.getMinimalPrice() > wantedPrice)
			throw new ProductException(
					"The actual price cannot go lower than the minimal. Choose another sale percentage.");
		updateField(id, "blackFridayPercentage", blackFridayPercentage.toString());
		updateField(id, "blackFriday", "1");
	}

	public void addProductToBlackFriday(Integer id, Double blackFridayPercentage)
			throws SQLException, ProductException {
		declareProductForBlackFriday(id, blackFridayPercentage);
		Double productPrice = getProductByField("id", id.toString()).getActualPrice();
		Double wantedPrice = productPrice - (productPrice * blackFridayPercentage / 100);
		updateField(id, "actualPrice", wantedPrice.toString());
	}

	public void removeDeclaredProductFromBlackFriday(Integer id) throws SQLException, ProductException {
		Product product = getProductByField("id", id.toString());
		if (!product.isBlackFriday())
			throw new ProductException("The product does not participate in black Friday");
		updateField(id, "blackFriday", "0");
		updateField(id, "blackFridayPercentage", "0.00");
	}

	public void removeProductFromBlackFriday(Integer id) throws SQLException, ProductException {
		Product product = getProductByField("id", id.toString());
		removeDeclaredProductFromBlackFriday(id);
		Double wantedPrice = product.getActualPrice() * 100 / (100 - product.getBlackFridayPercentage());
		updateField(id, "actualPrice", wantedPrice.toString());
	}

	public void startBlackFriday() throws SQLException {
		List<Product> blackFridayProducts = getAllBlackFridayProducts();
		for (Product p : blackFridayProducts) {
			Double wantedPrice = p.getActualPrice() - (p.getActualPrice() * (p.getBlackFridayPercentage() / 100));
			updateField(p.getId(), "actualPrice", wantedPrice.toString());
		}
	}

	public void stopBlackFriday() throws SQLException {
		List<Product> blackFridayProducts = getAllBlackFridayProducts();
		for (Product p : blackFridayProducts) {
			Double wantedPrice = p.getActualPrice() * 100 / (100 - p.getBlackFridayPercentage());
			updateField(p.getId(), "actualPrice", wantedPrice.toString());
			updateField(p.getId(), "blackFriday", "0");
			updateField(p.getId(), "blackFridayPercentage", "0,00");
		}
	}

	public void purchase(Integer id, Integer userId, Integer quantity) throws SQLException, ClassNotFoundException, ProductException {
		Connection con;
		Class.forName("com.mysql.jdbc.Driver");
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/task", "root", "root");
		con.setAutoCommit(false);
		Product p = getProductByField("id", id.toString());
		if(p.getQuantity() < quantity) throw new ProductException("You are trying to buy more than the store has.");
		try {
			PreparedStatement ps1 = con.prepareStatement("update product set quantity = ? where id = ?");
			ps1.setInt(1, p.getQuantity() - quantity);
			ps1.setInt(2, id);
			ps1.executeUpdate();
			PreparedStatement ps2 = con.prepareStatement("insert into sale(id,userId,productName,value) values(?,?,?,?)");
			ps2.setInt(1, getNextId("sale"));
			ps2.setInt(2, userId);
			ps2.setString(3, p.getName());
			ps2.setDouble(4, p.getActualPrice() * quantity);
			ps2.executeUpdate();
			con.commit();
		} catch (SQLException e) {
			con.rollback();
		} finally {
			con.setAutoCommit(true);
		}
	}

	public static Product createProductFromResultSet(ResultSet rs) throws SQLException {
		Product product = new Product();
		product.setName(rs.getString("name"));
		product.setActualPrice(rs.getDouble("actualPrice"));
		product.setBlackFriday(rs.getBoolean("blackFriday"));
		product.setMinimalPrice(rs.getDouble("minimalPrice"));
		product.setBlackFridayPercentage(rs.getDouble("blackFridayPercentage"));
		product.setId(rs.getInt("id"));
		product.setQuantity(rs.getInt("quantity"));
		return product;
	}

}
