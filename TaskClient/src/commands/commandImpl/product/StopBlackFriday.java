package commands.commandImpl.product;

import java.io.IOException;

public class StopBlackFriday extends AbstractProductCommand {

	public StopBlackFriday(String sessionToken) {
		super(sessionToken, "stop black friday");
	}

	@Override
	public String execute() throws IOException {
		return jh.getValue(pr.stopBlackFriday(sessionToken), "message");
	}

}
