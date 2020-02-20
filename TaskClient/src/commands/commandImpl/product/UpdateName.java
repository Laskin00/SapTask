package commands.commandImpl.product;

import java.io.IOException;

public class UpdateName extends AbstractProductCommand {

	public UpdateName(String sessionToken) {
		super(sessionToken, "update name");
		// TODO Auto-generated constructor stub
	}

	@Override
	public String execute() throws IOException {
		System.out.println("Enter product id:");
		Integer id = Integer.parseInt(br.readLine());
		System.out.println("Enter the new name:");
		String name = br.readLine();
		return jh.getValue(pr.updateName(id, sessionToken, name), "message");
	}

}
