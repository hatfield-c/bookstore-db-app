package bks;

public class CartDelete implements MenuAction {
	
	public boolean execute(){
		DBConnection db = Application.GetDB();
		Cart cart = new Cart();
		
		Form form = new Form("str", "Enter ISBN of item");
		String response[] = form.response();
		cart.deleteProduct(new Product(response[0], 0));
		
		return true;
	}
	
}
