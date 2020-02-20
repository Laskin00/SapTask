package commands.commandImpl.product;

import java.io.IOException;


public class UpdateProductQuantity extends AbstractProductCommand {

	public UpdateProductQuantity(String sessionToken) {
		super(sessionToken, "update product quantity");
		// TODO Auto-generated constructor stub
	}

	@Override
	public String execute() throws IOException {
		System.out.println("Enter product id:");
		Integer id = Integer.parseInt(br.readLine());
		System.out.println("Enter the quantity you want to add:");
		String quantity = br.readLine();
		return jh.getValue(pr.updateQuantity(id, sessionToken, quantity), "message");
	}

}
