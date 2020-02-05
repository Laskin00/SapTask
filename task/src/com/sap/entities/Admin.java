package com.sap.entities;

public class Admin extends User {

	public Admin(String name, String password, String email, int id) {
		super(name, password, email, Role.ADMIN, id);
	}
	
	/*public discountItem() {
		
	}*/

}
