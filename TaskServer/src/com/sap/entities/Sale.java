package com.sap.entities;

public class Sale extends AbstractEntity{
	private double value;

	public Sale() {
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

}
