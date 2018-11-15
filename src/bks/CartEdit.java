package bks;

public class CartEdit implements MenuAction {

	public boolean execute(){
		DBConnection db = Application.GetDB();
		Cart cart = new Cart();
		
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
		
		String responses[] = form.response();
		cart.editProduct(new Product(responses[0], Integer.parseInt(responses[1])));
		
		return true;
	}
	
}
