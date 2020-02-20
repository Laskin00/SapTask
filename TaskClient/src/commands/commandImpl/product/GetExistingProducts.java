package commands.commandImpl.product;

import java.io.IOException;

public class GetExistingProducts extends AbstractProductIndex {

	public GetExistingProducts() {
		super("", "get all existing products");
	}

	@Override
	public String execute() throws IOException {
		printProducts(pr.existingIndex());
		return "";
	}

}
