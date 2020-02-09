package com.sap.repository;
import java.sql.*;

import com.sap.entities.Role;
import com.sap.entities.User;
import com.sap.exceptions.EntityNotFoundException;

public class UserRepository extends Repository{

	public User getUserByField(String fieldName, String fieldValue) throws SQLException{
		ResultSet rs = null;
		PreparedStatement ps;
		ps = connection.prepareStatement("select * from user where " + fieldName + " = ?");
		ps.setString(1, fieldValue);
		rs = ps.executeQuery();
		if(!rs.next()) throw new EntityNotFoundException("There is no user with " + fieldName + " = " + fieldValue);
		return createUserFromResultSet(rs);
	}
	
	public void updateField(Integer id, String fieldName, String fieldValue) throws SQLException{
		getUserByField("id", id.toString());
		super.updateFieldOfEntity("user", id, fieldName, fieldValue);
	}

	public void addUser(User user) throws SQLException{
		PreparedStatement ps;
		ps = connection.prepareStatement("insert into user(id,name,password,email,role) values(?,?,?,?,?)");
		ps.setInt(1,getNextId("user"));
		ps.setString(2, user.getName());
		ps.setString(3, user.getPassword());
		ps.setString(4, user.getEmail());
		ps.setString(5,user.getRole().toString());
		ps.executeUpdate();
		ps.close();	
	}
	
	public User createUserFromResultSet(ResultSet rs) throws SQLException{
		User user = new User();
			if(rs.first()) {
				user.setEmail(rs.getString("email"));
				user.setName(rs.getString("name"));
				user.setId(rs.getInt("id"));
				user.setPassword(rs.getString("password"));
				user.setSessionToken(rs.getString("sessionToken"));
				user.setRole(Role.valueOf(rs.getString("role")));
				rs.close();
			}else {
				throw new EntityNotFoundException();
			}
		return user;
	}
	
	
}
