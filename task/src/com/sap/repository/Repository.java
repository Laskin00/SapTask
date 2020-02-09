package com.sap.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public abstract class Repository {
	protected Connection connection;
	
	public Repository(){
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(
				        "jdbc:mysql://localhost:3306/task",
				        "root", "root");

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	public Integer getNextId(String entity) throws SQLException{
		PreparedStatement ps;
		ps = connection.prepareStatement("select max(id) as maxId from " + entity);
		ResultSet rs = ps.executeQuery();
		if(rs.next())	return rs.getInt("maxId") + 1;
		return 1;

	}
	
	
	public void updateFieldOfEntity(String entity,Integer id, String fieldName, String fieldValue) throws SQLException{
		PreparedStatement ps;
		ps = connection.prepareStatement("update " + entity + " set " + fieldName + " = ? where id = ?");
		ps.setString(1, fieldValue);
		ps.setInt(2,id);
		ps.executeUpdate();
	}
	
}
