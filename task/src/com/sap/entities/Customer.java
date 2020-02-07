package com.sap.entities;

public class Customer extends User {

	private float balance;
	
	public Customer(String name, String password, String email) {
		super(name, password, email, Role.CUSTOMER);
	}
	
	public float getBalance() {
		return balance;
	}
	public void setBalance(float balance) {
		this.balance = balance;
	}
	
	/*
	 public void buy(){
	 }
	 */

}
