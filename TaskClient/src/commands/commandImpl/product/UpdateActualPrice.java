package commands.commandImpl.product;

import java.io.IOException;


public class UpdateActualPrice extends AbstractProductCommand {

	public UpdateActualPrice(String sessionToken) {
		super(sessionToken, "update actual price");
		// TODO Auto-generated constructor stub
	}

	@Override
	public String execute() throws IOException {
		System.out.println("Enter product id:");
		Integer id = Integer.parseInt(br.readLine());
		System.out.println("Enter the new actual price:");
		String actualPrice = br.readLine();
		return jh.getValue(pr.updateActualPrice(id, sessionToken, actualPrice), "message");
	}

}
