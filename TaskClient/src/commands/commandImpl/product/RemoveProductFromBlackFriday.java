package commands.commandImpl.product;

import java.io.IOException;

public class RemoveProductFromBlackFriday extends AbstractProductCommand {

	public RemoveProductFromBlackFriday(String sessionToken) {
		super(sessionToken, "remove product from black friday");
	}

	@Override
	public String execute() throws IOException {
		System.out.println("Enter product id:");
		Integer id = Integer.parseInt(br.readLine());
		return jh.getValue(pr.removeFromBlackFriday(id, sessionToken), "message");
	}

}
