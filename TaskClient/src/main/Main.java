package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import commands.CommandContainer;
import commands.commandImpl.user.GetRole;
import commands.commandImpl.user.Login;
import commands.commandImpl.user.Logout;
import commands.commandImpl.user.Register;
import helpers.JsonHelper;

public class Main {
	public static void main(String[] args) {
		while(true) {
			String sessionToken = null;
			String role = null;
			CommandContainer commandContainer = new CommandContainer();
			JsonHelper jh = new JsonHelper();
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			
			System.out.println("Welcome to task client !");
			while(sessionToken == null) {
				System.out.println("You can choose from the following commands:");
				System.out.println("login");
				System.out.println("register");
				try {
					switch(br.readLine()){
						case "login":
							String response = new Login().execute();
							if(jh.getValue(response, "sessionToken") == null) {
								System.out.println(jh.getValue(response, "message"));
							}else {
								sessionToken = jh.getValue(response, "sessionToken");
								role = new GetRole(sessionToken).execute();
							}
						break;
						case "register" :
							System.out.println(new Register().execute());
						break;
					}
				}catch(IOException e) {
					System.out.println(e.getMessage());
				}
			}
			if(role.equals("ADMIN")) {
				commandContainer.addAdminCommands(sessionToken);
			}else {
				commandContainer.addCustomerCommands(sessionToken);
			}
			System.out.println("You can choose from the following commands: ");
			commandContainer.printCommands();
			
			while(true) {
				try {
					String command = br.readLine();
					if(command.equals("display commands")) commandContainer.printCommands();
					if(command.equals("logout")) {
						System.out.println(new Logout(sessionToken).execute());
						break;
					}else {
						if(!command.equals("display commands")) {
							System.out.println(commandContainer.executeCommand(command));
						}
					}
					
				}catch(IOException e) {
					System.out.println(e.getMessage());
				}
			}
		}	
	}

}
