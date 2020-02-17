package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import commands.ProductCommands;
import commands.UserCommands;
import helpers.JsonHelper;
import requests.ProductRequest;
import requests.SaleRequest;
import requests.UserRequest;
import commands.SaleCommands;

public class Main {
	public static void main(String[] args) {
		String sessionToken = null;
		List<String> availableCommands = new ArrayList<String>();
		availableCommands.add("login");
		availableCommands.add("register");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		boolean blackFridayActive = false;
			try {
				 blackFridayActive = ProductRequest.blackFridayStatus();
			}catch(IOException e) {
				System.out.println(e.getMessage());
			}
			printCommands(availableCommands);
			while(sessionToken == null) {
				try {
					String command = br.readLine();
					if(!availableCommands.contains(command)) {
						System.out.println("Choose a valid command !");
						System.out.println("Do you want to see the available commands again ?");
						System.out.println("yes");
						System.out.println("no");
						if(br.readLine().equals("yes")) printCommands(availableCommands);
					}else {
						switch(command) {
							case "login":
								String message = UserCommands.login();
								if((sessionToken = JsonHelper.getValue(message, "sessionToken")) != null) {
									if(UserRequest.getRoleBySessionToken(sessionToken).equals("ADMIN")) {
										availableCommands.addAll(initAdminCommands());
										if(blackFridayActive) availableCommands.addAll(initBlackFridayCommands());
									}else {
										availableCommands.addAll(initCustomerCommands());
									}
									availableCommands.remove("login");
									availableCommands.remove("register");
									availableCommands.addAll(initSharedCommandsForLoggedUsers());
									System.out.println("You Have logged in successfully");
								}else {
									System.out.println(JsonHelper.getValue(message, "message"));
								}
							break;
							case "register":
								System.out.println(UserCommands.register());
								printCommands(availableCommands);
							break;
						}
					}
				}catch(IOException e) {
					System.out.println(e.getMessage());
				}
			}
			printCommands(availableCommands);
			while(true) {
				try {
					String command = br.readLine();
					if(availableCommands.contains(command)) {
						switch(command) {
							case "add new product":
								System.out.println(ProductCommands.addProduct(sessionToken));
								break;
							case "update product quantity":
								System.out.println(ProductCommands.updateQuantity(sessionToken));
								break;
							case "update product minimal price":
								System.out.println(ProductCommands.updateMinimalPrice(sessionToken));
								break;
							case "update product actual price":
								System.out.println(ProductCommands.updateActualPrice(sessionToken));
								break;
							case "update product name":
								System.out.println(ProductCommands.updateName(sessionToken));
								break;
							case "start black friday":
								availableCommands.addAll(initBlackFridayCommands());
								System.out.println(ProductCommands.startBlackFriday(sessionToken));
								break;
							case "stop black friday":
								availableCommands.removeAll(initBlackFridayCommands());
								System.out.println(ProductCommands.stopBlackFriday(sessionToken));
								break;
							case "declare product for next black friday":
								System.out.println(ProductCommands.declareProductForNextBlackFriday(sessionToken));
								break;
							case "add product to black friday":
								System.out.println(ProductCommands.addProductToBlackFriday(sessionToken));
								break;
							case "remove declared product from next black friday":
								System.out.println(ProductCommands.removeDeclaredFromBlackFriday(sessionToken));
								break;
							case "remove product from black friday":
								System.out.println(ProductCommands.removeFromBlackFriday(sessionToken));
								break;
							case "get sales for a period of time":
								String result = SaleCommands.getSalesForPeriod(sessionToken);
								if(JsonHelper.getValue(result, "value") != null) {
									System.out.println(JsonHelper.getValue(result, "value"));
								}else {
									System.out.println(JsonHelper.getValue(result, "message"));
								}
								break;
							case "purchase product":
								System.out.println(ProductCommands.purchase(sessionToken));
								break;
							case "get all products":
								ProductCommands.getAllProducts();
								break;
							case "get all existing products":
								ProductCommands.getAllExistingProducts();
								break;
							case "get all black friday products":
								ProductCommands.getAllBlackFridayProducts();
								break;
							case "logout":
								System.out.println(UserCommands.logout(sessionToken));
								return;
						}
					}else {
						System.out.println("Choose a valid command !");
						System.out.println("Do you want to see the available commands again ?");
						System.out.println("yes");
						System.out.println("no");
						if(br.readLine().equals("yes")) printCommands(availableCommands);
					}
				}catch(IOException e) {
					System.out.println(e.getMessage());
				}
					
			}
	}
	
	public static List<String> initAdminCommands(){
		List<String> adminCommands = new ArrayList<String>();
		adminCommands.add("add new product");
		adminCommands.add("update product quantity");
		adminCommands.add("update product minimal price");
		adminCommands.add("update product actual price");
		adminCommands.add("update product name");
		adminCommands.add("start black friday");
		adminCommands.add("stop black friday");
		adminCommands.add("declare product for next black friday");
		adminCommands.add("remove declared product from next black friday");
		adminCommands.add("get sales for a period of time");
		return adminCommands;
		
	}
	
	public static List<String> initBlackFridayCommands(){
		List<String> blackFridayCommands = new ArrayList<String>();
		blackFridayCommands.add("add product to black friday");
		blackFridayCommands.add("remove product from black friday");
		return blackFridayCommands;
		
	}
	
	public static List<String> initCustomerCommands(){
		List<String> customerCommands = new ArrayList<String>();
		customerCommands.add("purchase product");
		return customerCommands;
		
	}
	
	public static List<String> initSharedCommandsForLoggedUsers(){
		List<String> sharedCommands = new ArrayList<String>();
		sharedCommands.add("logout");
		sharedCommands.add("get all products");
		sharedCommands.add("get all existing products");
		sharedCommands.add("get all black friday products");
		return sharedCommands;
		
	}
	
	public static void printCommands(List<String> commands) {
		System.out.println("You can choose between one of the following commands:");
		int i = 1;
		for(String s : commands) {
			System.out.println(i + " " + s);
			i++;
		}
	}

}
