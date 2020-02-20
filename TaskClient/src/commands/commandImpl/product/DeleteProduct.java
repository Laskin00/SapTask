package commands.commandImpl.product;

import java.io.IOException;

public class DeleteProduct extends AbstractProductCommand {

	public DeleteProduct(String sessionToken) {
		super(sessionToken, "delete product");
	}

	@Override
	public String execute() throws IOException {
		System.out.println("Enter the id of the product you want to delete:");
		Integer id = Integer.parseInt(br.readLine());
		return jh.getValue(pr.delete(id, sessionToken), "message");
	}

}
