package commands.commandImpl.product;

import java.io.IOException;

public class UpdateMinimalPrice extends AbstractProductCommand {

	public UpdateMinimalPrice(String sessionToken) {
		super(sessionToken, "update minimal price");
		// TODO Auto-generated constructor stub
	}

	@Override
	public String execute() throws IOException {
		System.out.println("Enter product id:");
		Integer id = Integer.parseInt(br.readLine());
		System.out.println("Enter the new minimal price:");
		String minimalPrice = br.readLine();
		return jh.getValue(pr.updateMinimalPrice(id, sessionToken, minimalPrice), "message");
	}

}
