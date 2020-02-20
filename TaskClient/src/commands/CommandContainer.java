package commands;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import commands.commandImpl.product.AddProduct;
import commands.commandImpl.product.AddProductToBlackFriday;
import commands.commandImpl.product.DeclareProductForNextBlackFriday;
import commands.commandImpl.product.DeleteProduct;
import commands.commandImpl.product.GetAllProducts;
import commands.commandImpl.product.GetBlackFridayProducts;
import commands.commandImpl.product.GetExistingProducts;
import commands.commandImpl.product.PurchaseProduct;
import commands.commandImpl.product.RemoveDeclaredProductFromBlackFriday;
import commands.commandImpl.product.RemoveProductFromBlackFriday;
import commands.commandImpl.product.StartBlackFriday;
import commands.commandImpl.product.StopBlackFriday;
import commands.commandImpl.product.UpdateActualPrice;
import commands.commandImpl.product.UpdateMinimalPrice;
import commands.commandImpl.product.UpdateName;
import commands.commandImpl.product.UpdateProductQuantity;
import commands.commandImpl.sale.GetSalesForPeriodOfTime;

public class CommandContainer {
	List<Command> commands = new ArrayList<Command>();
	
	public boolean contains(String commandName) {
		return commands.stream().anyMatch(c -> c.getName().equals(commandName));
	}
	
	public void addAdminCommands(String sessionToken) {
		commands.add(new AddProduct(sessionToken));
		commands.add(new AddProductToBlackFriday(sessionToken));
		commands.add(new DeclareProductForNextBlackFriday(sessionToken));
		commands.add(new RemoveDeclaredProductFromBlackFriday(sessionToken));
		commands.add(new RemoveProductFromBlackFriday(sessionToken));
		commands.add(new StartBlackFriday(sessionToken));
		commands.add(new StopBlackFriday(sessionToken));
		commands.add(new UpdateActualPrice(sessionToken));
		commands.add(new UpdateMinimalPrice(sessionToken));
		commands.add(new UpdateName(sessionToken));
		commands.add(new GetSalesForPeriodOfTime(sessionToken));
		commands.add(new UpdateProductQuantity(sessionToken));
		commands.add(new DeleteProduct(sessionToken));
		addSharedCommands();
	}
	
	public void addCustomerCommands(String sessionToken) {
		commands.add(new PurchaseProduct(sessionToken));
		addSharedCommands();
	}
	
	public void addSharedCommands() {
		commands.add(new GetAllProducts());
		commands.add(new GetBlackFridayProducts());
		commands.add(new GetExistingProducts());
	}
	
	public void printCommands() {
		for(Command c : commands) {
			System.out.println(c.getName());
		}
		System.out.println("logout");
		System.out.println("display commands");
	}
	
	public String executeCommand(String commandName) throws IOException{
		if(!contains(commandName)) {
			printCommands();
			return "Choose a command from the list above.";
		}
		Command c = getCommand(commandName);
		return c.execute();
	}
	
	public Command getCommand(String commandName) {
		for(Command c : commands) {
			if(c.getName().equals(commandName)) return c;
		}
		return null;
	}
}
