package com.sap.repository;
import java.sql.*;

public class UserRepository {
	private Connection conn;
	private Statement stm;
	
	public UserRepository() {
		try {
			conn = DriverManager.getConnection(
			        "jdbc:mysql://localhost:3306/task",
			        "root", "root");
			System.out.println("Connection successful!");
		} catch (SQLException e) {
			System.out.println("Connection failed !!!");
			e.printStackTrace();
		}
	}
	
	public String getUser(Integer id) {
		String result = "";
		try {
			stm = conn.createStatement();
			ResultSet rset = stm.executeQuery("select * from user where id = 1");
			result = rset.getString("name");
		} catch (SQLException e) {
			System.out.println("Statement failed");
			e.printStackTrace();
		}
		System.out.println(result);
		return result;
		
	}
	
}
