package commands.commandImpl.sale;

import java.io.IOException;

import commands.AbstractCommand;
import requests.SaleRequest;

public abstract class AbstractSaleCommand extends AbstractCommand {

	protected SaleRequest sr = new SaleRequest();
	
	public AbstractSaleCommand(String sessionToken, String name) {
		super(sessionToken, name);
	}

	public abstract String execute() throws IOException;

}
