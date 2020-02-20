package commands.commandImpl.product;

import java.io.IOException;

public class PurchaseProduct extends AbstractProductCommand {

	public PurchaseProduct(String sessionToken) {
		super(sessionToken, "purchase product");
		
	}

	@Override
	public String execute() throws IOException {
		System.out.println("Enter product id:");
		Integer id = Integer.parseInt(br.readLine());
		System.out.println("Enter product quantity:");
		String quantity = br.readLine();
		return jh.getValue(pr.purchase(id, quantity, sessionToken), "message");
	}

}
