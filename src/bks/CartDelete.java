package bks;

public class CartDelete implements MenuAction {
	
	// Executes when this action is chosen in a menu
	public boolean execute(){
		Cart cart = new Cart();

		// Form to get data needed to delete product from the cart
		Form form = new Form("str", "Enter ISBN of item");
		String response[] = form.response();
		
		// Try to delete the product
		cart.deleteProduct(new Product(response[0], 0));
		
		return true;
	}
	
}
