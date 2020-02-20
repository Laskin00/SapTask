package commands.commandImpl.product;

import java.io.IOException;

public class RemoveDeclaredProductFromBlackFriday extends AbstractProductCommand {

	public RemoveDeclaredProductFromBlackFriday(String sessionToken) {
		super(sessionToken, "remove declared product from black friday");
	}

	@Override
	public String execute() throws IOException {
		System.out.println("Enter product id:");
		Integer id = Integer.parseInt(br.readLine());
		return jh.getValue(pr.removeDeclaredFromBlackFriday(id, sessionToken), "message");
	}

}
