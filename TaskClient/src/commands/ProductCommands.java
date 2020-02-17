package commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import helpers.JsonHelper;
import requests.ProductRequest;

public class ProductCommands {

	private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in)); 

	public static String addProduct(String sessionToken) throws IOException{
		System.out.println("Enter product name:");
		String name = br.readLine();
		System.out.println("Enter product quantity:");
		String quantity = br.readLine();
		System.out.println("Enter product minimal price:");
		String minimalPrice = br.readLine();
		System.out.println("Enter product actual price:");
		String actualPrice = br.readLine();
		return JsonHelper.getValue(ProductRequest.addNew(name, quantity, minimalPrice, 
				actualPrice, sessionToken), "message");
	}
	
	public static String updateQuantity(String sessionToken) throws IOException{
		System.out.println("Enter product id:");
		Integer id = Integer.parseInt(br.readLine());
		System.out.println("Enter the quantity you want to add:");
		String quantity = br.readLine();
		return JsonHelper.getValue(ProductRequest.updateQuantity(id, sessionToken, quantity), "message");
	}
	
	public static String updateMinimalPrice(String sessionToken) throws IOException{
		System.out.println("Enter product id:");
		Integer id = Integer.parseInt(br.readLine());
		System.out.println("Enter the new minimal price:");
		String minimalPrice = br.readLine();
		return JsonHelper.getValue(ProductRequest.updateMinimalPrice(id, sessionToken, minimalPrice), "message");
	}
	
	public static String updateActualPrice(String sessionToken) throws IOException{
		System.out.println("Enter product id:");
		Integer id = Integer.parseInt(br.readLine());
		System.out.println("Enter the new actual price:");
		String actualPrice = br.readLine();
		return JsonHelper.getValue(ProductRequest.updateActualPrice(id, sessionToken, actualPrice), "message");
	}
	
	public static String updateName(String sessionToken) throws IOException{
		System.out.println("Enter product id:");
		Integer id = Integer.parseInt(br.readLine());
		System.out.println("Enter the new name:");
		String name = br.readLine();
		return JsonHelper.getValue(ProductRequest.updateName(id, sessionToken, name), "message");
	}
	
	public static String startBlackFriday(String sessionToken) throws IOException{
		return JsonHelper.getValue(ProductRequest.startBlackFriday(sessionToken), "message");
	}
	
	public static String stopBlackFriday(String sessionToken) throws IOException{
		return JsonHelper.getValue(ProductRequest.stopBlackFriday(sessionToken), "message");
	}
	
	public static String declareProductForNextBlackFriday(String sessionToken) throws IOException{
		System.out.println("Enter product id:");
		Integer id = Integer.parseInt(br.readLine());
		System.out.println("Enter the percentage you want to lower the price with:");
		String percentage = br.readLine();
		return JsonHelper.getValue(ProductRequest.declareForBlackFriday(id, percentage, sessionToken), "message");
	}
	
	public static String addProductToBlackFriday(String sessionToken) throws IOException{
		System.out.println("Enter product id:");
		Integer id = Integer.parseInt(br.readLine());
		System.out.println("Enter the percentage you want to lower the price with:");
		String percentage = br.readLine();
		return JsonHelper.getValue(ProductRequest.addToBlackFriday(id, percentage, sessionToken), "message");
	}
	
	public static String removeDeclaredFromBlackFriday(String sessionToken) throws IOException{
		System.out.println("Enter product id:");
		Integer id = Integer.parseInt(br.readLine());
		return JsonHelper.getValue(ProductRequest.removeDeclaredFromBlackFriday(id, sessionToken), "message");
	}
	
	public static String removeFromBlackFriday(String sessionToken) throws IOException{
		System.out.println("Enter product id:");
		Integer id = Integer.parseInt(br.readLine());
		return JsonHelper.getValue(ProductRequest.removeFromBlackFriday(id, sessionToken), "message");
	}
	
	public static String purchase(String sessionToken) throws IOException {
		System.out.println("Enter product id:");
		Integer id = Integer.parseInt(br.readLine());
		System.out.println("Enter product quantity:");
		String quantity = br.readLine();
		return JsonHelper.getValue(ProductRequest.purchase(id, quantity, sessionToken), "message");
	}
	
	public static void getAllProducts() throws IOException{
		printProducts(ProductRequest.index());
	}
	public static void getAllExistingProducts() throws IOException{
		printProducts(ProductRequest.existingIndex());
	}
	public static void getAllBlackFridayProducts() throws IOException{
		printProducts(ProductRequest.blackFridayIndex());
	}
	public static void printProduct(String productInJson) throws IOException{
		String id = JsonHelper.getValue(productInJson, "id");
		String name = JsonHelper.getValue(productInJson, "name");
		String quantity = JsonHelper.getValue(productInJson, "quantity");
		String minimalPrice = JsonHelper.getValue(productInJson, "minimalPrice");
		String actualPrice = JsonHelper.getValue(productInJson, "actualPrice");
		System.out.println("id: " + id + "\tname: " + name + "\tquantity: " + quantity + "\tactual price: " + 
		actualPrice + "\tminimal price: " + minimalPrice);
	}
	public static void printProducts(String productsInJson) throws IOException{
		if(productsInJson.equals("[]")) {
			System.out.println("There are no products in this category.");
			return;
		}
		String[] products = productsInJson.replace("[", "").replace("]","").split("},");
		for(String s : products) {
			if(s.charAt(s.length() - 1)!= '}')
				s = s.concat("}");
			printProduct(s);
		}
	}
}
/*
case "get sales for a period of time":
break;
*/