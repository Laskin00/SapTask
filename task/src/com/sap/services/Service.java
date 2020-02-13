package com.sap.services;

import java.sql.SQLException;

import com.sap.entities.Role;
import com.sap.repositories.ProductRepository;
import com.sap.repositories.SaleRepository;
import com.sap.repositories.UserRepository;

public class Service {

	protected UserRepository userRepository = new UserRepository();
	protected ProductRepository productRepository = new ProductRepository();
	protected SaleRepository salesRepository = new SaleRepository();
	
	public boolean isAdmin(String sessionToken) throws SQLException {
		if(sessionToken == null || userRepository.getUserByField("sessionToken", sessionToken).getRole() != Role.ADMIN) return false;
		return true;
	}

}
