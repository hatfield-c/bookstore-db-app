package bks;

public class CartAdd implements MenuAction {

	public boolean execute(){
		Form cartForm = new Form(
			new String[]{
				"str",
				"int"
			},
			new String[] {
				"Enter the ISBN",
				"Enter the quantity"
			}
		);
		
		String response[] = cartForm.response();
		Product potentialProduct = new Product(response[0], Integer.parseInt(response[1]));
		Cart cart = new Cart();
		cart.addProduct(potentialProduct);
		
		return true;
	}
	
}
