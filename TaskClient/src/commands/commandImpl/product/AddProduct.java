package commands.commandImpl.product;

import java.io.IOException;

public class AddProduct extends AbstractProductCommand {

	public AddProduct(String sessionToken) {
		super(sessionToken, "add product");
	}

	@Override
	public String execute() throws IOException{
		System.out.println("Enter product name:");
		String name = br.readLine();
		System.out.println("Enter product quantity:");
		String quantity = br.readLine();
		System.out.println("Enter product minimal price:");
		String minimalPrice = br.readLine();
		System.out.println("Enter product actual price:");
		String actualPrice = br.readLine();
		return jh.getValue(pr.addNew(name, quantity, minimalPrice, 
				actualPrice, sessionToken), "message");
	}

}
