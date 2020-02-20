package commands.commandImpl.user;

import java.io.IOException;

import commands.AbstractCommand;
import requests.UserRequest;

public abstract class AbstractUserCommand extends AbstractCommand {

	protected UserRequest ur = new UserRequest();
	
	public AbstractUserCommand(String sessionToken, String name) {
		super(sessionToken, name);
	}

	public abstract String execute() throws IOException;

}
