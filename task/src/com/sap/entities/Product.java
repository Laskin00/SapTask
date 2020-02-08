package com.sap.entities;

public class Product {

	private int id;
	private String name;
	private int quantity;
	private double minimalPrice;
	private double actualPrice;
	private double priceInSale;
	private double blackFridaySalePercentage;
	private boolean blackFriday;
	
	public Product() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getMinimalPrice() {
		return minimalPrice;
	}

	public void setMinimalPrice(double minimalPrice) {
		this.minimalPrice = minimalPrice;
	}

	public double getActualPrice() {
		return actualPrice;
	}

	public void setActualPrice(double actualPrice) {
		this.actualPrice = actualPrice;
	}

	public double getPriceInSale() {
		return priceInSale;
	}

	public void setPriceInSale(double priceInSale) {
		this.priceInSale = priceInSale;
	}

	public double getBlackFridaySalePercentage() {
		return blackFridaySalePercentage;
	}

	public void setBlackFridaySalePercentage(double blackFridaySalePercentage) {
		this.blackFridaySalePercentage = blackFridaySalePercentage;
	}

	public boolean isBlackFriday() {
		return blackFriday;
	}

	public void setBlackFriday(boolean blackFriday) {
		this.blackFriday = blackFriday;
	}
	
}
