package com.sap.entities;

public class Customer extends User {

	private float balance;
	public Customer(String name, String password, String email, int id) {
		super(name, password, email, Role.CUSTOMER,id);
		// TODO Auto-generated constructor stub
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
