package main;

import java.io.IOException;
import java.sql.Timestamp;

import helpers.JsonHelper;
import requests.ProductRequest;
import requests.SaleRequest;
import requests.UserRequest;

public class Main {
	public static void main(String[] args) {
		try {
			//USER RELATED
			//get user by id
			System.out.println(UserRequest.getUserById(2));
			//register
			System.out.println(UserRequest.register("", "", "sasds@abv.bg", "admin"));
			//login
			String sessionToken = UserRequest.login("test@abv.bg", "123123");
			System.out.println(sessionToken);
			sessionToken = JsonHelper.getValue(sessionToken, "sessionToken");
			//logout
			//System.out.println(UserRequest.logout(sessionToken));
			
			//PRODUCT RELATED
			//add new product
			System.out.println(ProductRequest.addNew("Laptop", "2", "100", "200", sessionToken));
			System.out.println(ProductRequest.updateQuantity(1, sessionToken, "10"));
			System.out.println(ProductRequest.updateMinimalPrice(1, sessionToken, "100"));
			System.out.println(ProductRequest.updateActualPrice(1, sessionToken, "200"));
			System.out.println(ProductRequest.startBlackFriday(sessionToken));
			System.out.println(ProductRequest.stopBlackFriday(sessionToken));
			System.out.println(ProductRequest.updateName(1, sessionToken, "PC"));
			System.out.println(ProductRequest.delete(1, sessionToken));
			System.out.println(ProductRequest.get(2));
			System.out.println(ProductRequest.index());
			System.out.println(ProductRequest.blackFridayIndex());
			System.out.println(ProductRequest.existingIndex());
			System.out.println(ProductRequest.declareForBlackFriday(2, "20", sessionToken));
			System.out.println(ProductRequest.removeDeclaredFromBlackFriday(2, sessionToken));
			System.out.println(ProductRequest.purchase(2, "1", sessionToken));
			
			//SALE RELATED
			System.out.println(SaleRequest.getSalesForPeriod(Timestamp.valueOf("2020-02-16 14:48:05.123"),
					Timestamp.valueOf("2020-02-16 17:40:05.123"), sessionToken));
			
		}catch(IOException e) {
			System.out.println(e.getMessage());
		}
	}

}
