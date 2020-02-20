package commands.commandImpl.product;

import java.io.IOException;

public class StartBlackFriday extends AbstractProductCommand {

	public StartBlackFriday(String sessionToken) {
		super(sessionToken, "start blackfriday");
		// TODO Auto-generated constructor stub
	}

	@Override
	public String execute() throws IOException {
		return jh.getValue(pr.startBlackFriday(sessionToken), "message");
	}

}
