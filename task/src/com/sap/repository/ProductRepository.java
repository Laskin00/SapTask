package com.sap.repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.sap.entities.Product;
import com.sap.exceptions.EntityNotFoundException;
import com.sap.exceptions.BlackFridayException;

public class ProductRepository extends Repository {

	public Product getProductByField(String fieldName, String fieldValue) throws SQLException{
		ResultSet rs = null;
		PreparedStatement ps;
		ps = connection.prepareStatement("select * from product where " + fieldName + " = ?");
		ps.setString(1, fieldValue);
		rs = ps.executeQuery();
		if(!rs.next()) throw new EntityNotFoundException("There is no product with " + fieldName + " = " + fieldValue);
		return createProductFromResultSet(rs);
	}
	
	public void addProduct(Product product) throws SQLException{
		PreparedStatement ps = 
				connection.prepareStatement("insert into product(id,name,quantity,minimalPrice,actualPrice) values(?,?,?,?,?)");
		ps.setInt(1,getNextId("product"));
		ps.setString(2, product.getName());
		ps.setDouble(3, product.getQuantity());
		ps.setDouble(4, product.getMinimalPrice());
		ps.setDouble(5,product.getActualPrice());
		ps.executeUpdate();
		ps.close();
	}
	
	public void deleteProduct(Integer id) throws SQLException{
		getProductByField("id", id.toString());
		Statement statement = connection.createStatement();
		statement.executeUpdate("delete from product where id = " + id.toString());
	}
	
	public void updateField(Integer id, String fieldName, String fieldValue) throws SQLException{
		super.updateFieldOfEntity("product", id, fieldName, fieldValue);
	}
	
	public List<Product> getAllProducts() throws SQLException{
		List<Product> products = new ArrayList<Product>();
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery("select * from product");
		while(rs.next()) {
			products.add(createProductFromResultSet(rs));
		}
		return products;
	}
	
	public List<Product> getAllBlackFridayProducts() throws SQLException{
		List<Product> products = new ArrayList<Product>();
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery("select * from product where blackFriday = 1");
		while(rs.next()) {
			products.add(createProductFromResultSet(rs));
		}
		return products;
	}
	
	public void addProductToBlackFriday(Integer id, Double blackFridayPercentage) throws SQLException, BlackFridayException{
		Product product  = getProductByField("id", id.toString());
		Double wantedPrice = product.getActualPrice() - (product.getActualPrice() * (blackFridayPercentage/100));
		if(product.isBlackFriday()) throw new BlackFridayException("The product already participates in black friday");
		if(product.getMinimalPrice() > wantedPrice)
			throw new BlackFridayException("The actual price cannot go lower than the minimal. Choose another sale percentage.");
		updateField(id,"blackFridayPercentage", blackFridayPercentage.toString());
		updateField(id,"blackFriday", "1");
		updateField(id,"priceInSale", wantedPrice.toString());
	}
	
	public void removeProductFromBlackFriday(Integer id) throws SQLException, BlackFridayException{
		Product product = getProductByField("id", id.toString());
		if(!product.isBlackFriday()) throw new BlackFridayException("The product does not participate in black Friday");
		updateField(id,"priceInSale", product.getActualPrice().toString());
		updateField(id,"blackFriday", "0");
	}

	public Product createProductFromResultSet(ResultSet rs) throws SQLException{
		Product product = new Product();
		product.setName(rs.getString("name"));
		product.setActualPrice(rs.getDouble("actualPrice"));
		product.setBlackFriday(rs.getBoolean("blackFriday"));
		product.setMinimalPrice(rs.getDouble("minimalPrice"));
		product.setBlackFridayPercentage(rs.getDouble("blackFridayPercentage"));
		product.setId(rs.getInt("id"));
		product.setPriceInSale(rs.getDouble("priceInSale"));
		product.setQuantity(rs.getInt("quantity"));
		return product;
	}
	
	

}
