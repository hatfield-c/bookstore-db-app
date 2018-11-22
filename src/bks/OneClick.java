package bks;

public class OneClick implements MenuAction {

	// Executes when this action is chosen in a menu
	public boolean execute(){
		// Instantiate prerequisite data objects
		Render render = Application.GetRenderer();
		Cart cart = new Cart();
		
		// Get the products from the cart
		Product products[] = cart.getProducts();
		
		// If there are no products, throw an error and return
		if(products.length < 1){
			render.error("No products in the cart!");
			return true;
		}
		
		// Map the the products to more detailed book objects
		Book books[] = new Book[products.length];
		
		for(int i = 0; i < products.length; i++){
			books[i] = new Book(products[i]);
		}
		
		// Get the db
		DBConnection db = Application.GetDB();
		
		String address[] = new String[6];
		try{
			// Try to read the member table for the address
			QueryData results[] = db.read(
				"members", 
				new String[]{
					"fname",
					"lname",
					"address",
					"city",
					"state",
					"zip"
				}, 
				new Condition(
					new String[] { "userid" },
					new String[] { Login.CurrentUserId() }
				)
			);
			
			// If no user account was found, something is wrong
			if(results.length < 0)
				throw new Exception("User account not found!");
			
			// Map the results to the address tring array
			String data[] = results[0].getData();
			address[0] = data[2];
			address[1] = data[3];
			address[2] = data[4];
			address[3] = data[5];
			address[4] = data[0];
			address[5] = data[1];
			
		}catch(Exception e){
			// If there was a problem, throw an error and exit
			render.error("Could not submit order.");
			return true;
		}
		
		// Instantiate the order and invoice it
		Order order = new Order(address, address, books);
		order.invoice();
		
		return true;
	}
	
}
