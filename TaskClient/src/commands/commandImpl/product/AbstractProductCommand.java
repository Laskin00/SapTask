package commands.commandImpl.product;

import java.io.IOException;

import commands.AbstractCommand;
import requests.ProductRequest;

public abstract class AbstractProductCommand extends AbstractCommand {
	protected ProductRequest pr = new ProductRequest();
	
	public AbstractProductCommand(String sessionToken, String name) {
		super(sessionToken, name);
	}

	public abstract String execute() throws IOException;
	
}
