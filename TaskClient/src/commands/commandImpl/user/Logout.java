package commands.commandImpl.user;

import java.io.IOException;

public class Logout extends AbstractUserCommand {

	public Logout(String sessionToken) {
		super(sessionToken, "logout");
	}

	@Override
	public String execute() throws IOException {
		return jh.getValue(ur.logout(sessionToken), "message");
	}

}
