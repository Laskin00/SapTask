package com.sap.entities;

import org.jasypt.util.password.StrongPasswordEncryptor;

public class User {
	
	private Integer id;
	private String name;
	private String password;
	private String email;
	private Role role;
	
	public User() {
		
	}
	public User(String name, String password, String email, Role role, int id) {
		this.name = name;
		this.email = email;
		this.role = role;
		this.id = id;
		encryptPassword(password);
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
		encryptPassword(password);
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
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	private void encryptPassword(String password) {
		StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
		this.password = passwordEncryptor.encryptPassword(password);
	}
	
	public boolean authenticatePassword(String password) {
		StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
		return passwordEncryptor.checkPassword(password, this.password);
	}
	
	public String toJson() {;
		return "{ \n\t\"id\": \"" + id.toString() + "\", \n\t\"name\": \"" + name + "\",\n\t\"email\": \"" + 
		email + "\",\n\t\"password\": \"" + password + "\",\n\t\"role\": \"" + role.toString() + "\"\n}";
	}
	

}
