package com.sap.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Repository {
	private Connection connection;
	private Statement statement;
	private PreparedStatement preparedStatement;
	
	public Repository() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(
			        "jdbc:mysql://localhost:3306/task",
			        "root", "root");
			statement = connection.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void initPreparedStatement(String query) {
		try {
			preparedStatement = connection.prepareStatement(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void setPreparedStatement(int position, String value) {
		try {
			preparedStatement.setString(position, value);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public ResultSet executeStatement(String query) {
		ResultSet rs = null;
		try {
			rs =  statement.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	public ResultSet executePreparedStatement() {
		ResultSet rs = null;
		try {
			rs = preparedStatement.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
}
