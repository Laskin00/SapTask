package commands.commandImpl.user;

import java.io.IOException;

public class Register extends AbstractUserCommand {

	public Register() {
		super("", "register");
	}

	@Override
	public String execute() throws IOException {
		System.out.println("Enter your name:");
		String name =  br.readLine();
		System.out.println("Enter your email:");
		String email = br.readLine();
		System.out.println("Enter your password:");
		String password = br.readLine();
		System.out.println("Enter your role:");
		String role = br.readLine();
		return ur.register(name, email, password, role);
	}

}
