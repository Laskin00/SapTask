package com.sap.entities;

import org.jasypt.util.password.StrongPasswordEncryptor;

public class User extends AbstractEntity{
	
	private String name;
	private String password;
	private String email;
	private Role role;
	private String sessionToken;
	
	public User() {
		
	}
	public User(String name, String password, String email, Role role) {
		this.name = name;
		this.email = email;
		this.role = role;
		this.password = password;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	
	public String getSessionToken() {
		return sessionToken;
	}
	public void setSessionToken(String sessionToken) {
		this.sessionToken = sessionToken;
	}
	
	public void encryptPassword() {
		StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
		this.password = passwordEncryptor.encryptPassword(this.password);
	}
	
	public boolean authenticatePassword(String password) {
		StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
		return passwordEncryptor.checkPassword(password, this.password);
		
	}

}
