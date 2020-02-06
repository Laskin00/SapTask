package com.sap.repository;
import java.sql.*;

import com.sap.entities.Role;
import com.sap.entities.User;
import com.sap.exceptions.UserNotFoundException;

public class UserRepository extends Repository{

	
	public User getUserById(Integer id) {
		initPreparedStatement("select * from user where id = ?");
		setPreparedStatement(1,id.toString());;
		return createUserFromResultSet(executePreparedStatement());
	}
	
	public User getUserByEmail(String email) {
		initPreparedStatement("select * from user where email = ?");
		setPreparedStatement(1,email);;
		return createUserFromResultSet(executePreparedStatement());
	}
	
	public User createUserFromResultSet(ResultSet rs) {
		User user = new User();
		try {
			if(rs.next()) {
				user.setEmail(rs.getString("email"));
				user.setName(rs.getString("name"));
				user.setId(Integer.decode(rs.getString("id")));
				user.setPassword(rs.getString("password"));
				user.setRole(Role.valueOf(rs.getString("role")));
			}else {
				throw new UserNotFoundException();
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}
	
	
}
