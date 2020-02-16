package com.sap.entities;

public class Product extends AbstractEntity{

	private String name;
	private Integer quantity;
	private Double minimalPrice;
	private Double actualPrice;
	private Double priceInSale;
	private Double blackFridayPercentage;
	private boolean blackFriday;
	
	public Product() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Double getMinimalPrice() {
		return minimalPrice;
	}

	public void setMinimalPrice(Double minimalPrice) {
		this.minimalPrice = minimalPrice;
	}

	public Double getActualPrice() {
		return actualPrice;
	}

	public void setActualPrice(double actualPrice) {
		this.actualPrice = actualPrice;
	}

	public Double getPriceInSale() {
		return priceInSale;
	}

	public void setPriceInSale(Double priceInSale) {
		this.priceInSale = priceInSale;
	}

	public Double getBlackFridayPercentage() {
		return blackFridayPercentage;
	}

	public void setBlackFridayPercentage(double blackFridayPercentage) {
		this.blackFridayPercentage = blackFridayPercentage;
	}

	public boolean isBlackFriday() {
		return blackFriday;
	}

	public void setBlackFriday(boolean blackFriday) {
		this.blackFriday = blackFriday;
	}
	
}
