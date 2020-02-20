package commands.commandImpl.user;

import java.io.IOException;

public class Login extends AbstractUserCommand {

	public Login() {
		super("", "login");
	}

	@Override
	public String execute() throws IOException {
		System.out.println("Enter your email:");
		String email = br.readLine();
		System.out.println("Enter your password:");
		String password = br.readLine();
		return ur.login(email,password);
	}

}
