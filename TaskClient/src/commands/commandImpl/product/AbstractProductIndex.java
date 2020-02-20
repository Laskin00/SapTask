package commands.commandImpl.product;

import java.io.IOException;

public abstract class AbstractProductIndex extends AbstractProductCommand {

	public AbstractProductIndex(String sessionToken, String name) {
		super(sessionToken, name);
		// TODO Auto-generated constructor stub
	}

	public abstract String execute() throws IOException;

	public void printProduct(String productInJson) throws IOException{
		String id = jh.getValue(productInJson, "id");
		String name = jh.getValue(productInJson, "name");
		String quantity = jh.getValue(productInJson, "quantity");
		String minimalPrice = jh.getValue(productInJson, "minimalPrice");
		String actualPrice = jh.getValue(productInJson, "actualPrice");
		System.out.println("id: " + id + "\tname: " + name + "\tquantity: " + quantity + "\tactual price: " + 
		actualPrice + "\tminimal price: " + minimalPrice);
	}
	public void printProducts(String productsInJson) throws IOException{
		if(productsInJson.equals("[]")) {
			System.out.println("There are no products in this category.");
			return;
		}
		String[] products = productsInJson.replace("[", "").replace("]","").split("},");
		for(String s : products) {
			if(s.charAt(s.length() - 1)!= '}')
				s = s.concat("}");
			printProduct(s);
		}
	}
}
