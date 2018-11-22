package bks;

public class CartEdit implements MenuAction {

	// Executes when this action is chosen in a menu
	public boolean execute(){
		Cart cart = new Cart();
		
		// Form to get data needed to edit product in the cart
		Form form = new Form(
			new String[] {
				"str",
				"int"
			},
			new String[] {
				"Enter ISBN of item",
				"Enter new quantity"
			}
		);
		
		// Get the response from the user, and try to edit the product with the info they gave
		String responses[] = form.response();
		cart.editProduct(new Product(responses[0], Integer.parseInt(responses[1])));
		
		return true;
	}
	
}
