package bks;

public class CartAdd implements MenuAction {

	// Executes when this action is chosen in a menu
	public boolean execute(){
		// Form to get data needed to add product to the cart
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
		
		// Parses the response
		String response[] = cartForm.response();
		Product potentialProduct = new Product(response[0], Integer.parseInt(response[1]));
		
		// Try to add the product to the cart
		Cart cart = new Cart();
		cart.addProduct(potentialProduct);
		
		return true;
	}
	
}
