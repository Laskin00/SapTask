package com.sap.repository;
import java.sql.*;

import com.sap.entities.Role;
import com.sap.entities.User;
import com.sap.exceptions.EmailConstraintException;
import com.sap.exceptions.EntityNotFoundException;

public class UserRepository extends Repository{

	
	public User getUserById(Integer id) {
		initPreparedStatement("select * from user where id = ?");
		setPreparedStatement(1,id.toString());
		return createUserFromResultSet(executePreparedStatement());
	}
	
	public User getUserByEmail(String email) {
		initPreparedStatement("select * from user where email = ?");
		setPreparedStatement(1,email);
		return createUserFromResultSet(executePreparedStatement());
	}
	
	public User getUserBySessionToken(String sessionToken) {
		initPreparedStatement("select * from user where sessionToken = ?");
		setPreparedStatement(1,sessionToken);
		return createUserFromResultSet(executePreparedStatement());
	}
	
	public void registerUser(User user) {
		try {
			getUserByEmail(user.getEmail());
			throw new EmailConstraintException("The email is already used.");
		}catch(EntityNotFoundException e) {
			initPreparedStatement("insert into user(id,name,password,email,role) values(?,?,?,?,?)");
			setPreparedStatement(1,getNextId().toString());
			setPreparedStatement(2, user.getName());
			setPreparedStatement(3, user.getPassword());
			setPreparedStatement(4, user.getEmail());
			setPreparedStatement(5,user.getRole().toString());
			executePreparedUpdate();
		}
		
	}
	
	public void updateSessionToken(Integer id, String sessionToken) {
		initPreparedStatement("update user set sessionToken = ? where id = ?");
		setPreparedStatement(1, sessionToken);
		setPreparedStatement(2, id.toString());
		executePreparedUpdate();
	}
	
	public Integer getNextId() {
		ResultSet rs = executeStatement("select max(id) from user");
		try {
			rs.next();
			return rs.getInt(1) + 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 1;
	}
	
	public User createUserFromResultSet(ResultSet rs) {
		User user = new User();
		try {
			if(rs.first()) {
				user.setEmail(rs.getString("email"));
				user.setName(rs.getString("name"));
				user.setId(Integer.parseInt(rs.getString("id")));
				user.setPassword(rs.getString("password"));
				user.setRole(Role.valueOf(rs.getString("role")));
			}else {
				throw new EntityNotFoundException();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}
	
	
}
