package commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import helpers.JsonHelper;

public abstract class AbstractCommand implements Command{

	private String name;
	protected JsonHelper jh = new JsonHelper();
	protected BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	protected String sessionToken;
	
	public AbstractCommand(String sessionToken, String name) {
		this.name = name;
		this.sessionToken = sessionToken;
	}

	public String getName() {
		return name;
	}

	public abstract String execute() throws IOException;


}
