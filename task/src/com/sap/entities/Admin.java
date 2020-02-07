package com.sap.entities;

public class Admin extends User {

	public Admin(String name, String password, String email) {
		super(name, password, email, Role.ADMIN);
	}
	
	/*public discountItem() {
		
	}*/

}
