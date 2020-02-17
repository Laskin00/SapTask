package commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import helpers.JsonHelper;
import requests.UserRequest;

public class UserCommands {

	private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in)); 
		
	public static String register() throws IOException{
		System.out.println("Enter your name:");
		String name =  br.readLine();
		System.out.println("Enter your email:");
		String email = br.readLine();
		System.out.println("Enter your password:");
		String password = br.readLine();
		System.out.println("Enter your role:");
		String role = br.readLine();
		return UserRequest.register(name, email, password, role);
	}
	public static String login() throws IOException {
		System.out.println("Enter your email:");
		String email = br.readLine();
		System.out.println("Enter your password:");
		String password = br.readLine();
		return UserRequest.login(email,password);
	}
	public static String logout(String sessionToken) throws IOException{
		return JsonHelper.getValue(UserRequest.logout(sessionToken), "message");
	}

}
