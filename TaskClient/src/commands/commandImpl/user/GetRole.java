package commands.commandImpl.user;

import java.io.IOException;

public class GetRole extends AbstractUserCommand {

	public GetRole(String sessionToken) {
		super(sessionToken, "get role");
	}

	@Override
	public String execute() throws IOException {
		return ur.getRoleBySessionToken(sessionToken);
	}

}
