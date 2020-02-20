package commands.commandImpl.product;

import java.io.IOException;

public class GetBlackFridayProducts extends AbstractProductIndex {

	public GetBlackFridayProducts() {
		super("", "get all blackfriday products");
	}

	@Override
	public String execute() throws IOException {
		printProducts(pr.blackFridayIndex());
		return "";
	}

}
