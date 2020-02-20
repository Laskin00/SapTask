package commands.commandImpl.product;

import java.io.IOException;

public class DeclareProductForNextBlackFriday extends AbstractProductCommand {

	public DeclareProductForNextBlackFriday(String sessionToken) {
		super(sessionToken, "declare product for next black friday");
	}

	@Override
	public String execute() throws IOException {
		System.out.println("Enter product id:");
		Integer id = Integer.parseInt(br.readLine());
		System.out.println("Enter the percentage you want to lower the price with:");
		String percentage = br.readLine();
		return jh.getValue(pr.declareForBlackFriday(id, percentage, sessionToken), "message");
	}

}
