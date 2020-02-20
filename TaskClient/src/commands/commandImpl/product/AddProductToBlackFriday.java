package commands.commandImpl.product;

import java.io.IOException;

public class AddProductToBlackFriday extends AbstractProductCommand {

	public AddProductToBlackFriday(String sessionToken) {
		super(sessionToken, "add product to black friday");
	}

	@Override
	public String execute() throws IOException {
		System.out.println("Enter product id:");
		Integer id = Integer.parseInt(br.readLine());
		System.out.println("Enter the percentage you want to lower the price with:");
		String percentage = br.readLine();
		return jh.getValue(pr.addToBlackFriday(id, percentage, sessionToken), "message");
	}

}
