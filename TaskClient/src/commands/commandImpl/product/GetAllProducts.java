package commands.commandImpl.product;

import java.io.IOException;

public class GetAllProducts extends AbstractProductIndex {

	public GetAllProducts() {
		super("", "get all products");
	}

	@Override
	public String execute() throws IOException {
		printProducts(pr.index());
		return "";
	}

}
