package bks;

public class Checkout implements MenuAction {

	// Executes when this action is chosen in a menu
	public boolean execute(){
		// Instantiate preliminary data structures
		Render render = Application.GetRenderer();
		Cart cart = new Cart();
		Product products[] = cart.getProducts();
		
		// If there are no products in the cart, throw an error and exit
		if(products.length < 1){
			render.error("No products in the cart!");
			return true;
		}
		
		// Build the list of books from the products in the cart
		Book books[] = new Book[products.length];
		for(int i = 0; i < products.length; i++){
			books[i] = new Book(products[i]);
		}
		
		// Render the cart
		render.cart(books);
		
		// Instantiate forms used to determine if the user wants to use new shipping data, and forms to get that data
		Form proceedPrompt = new Form("char", "Proceed to checkout (Y/N)");
		Form newAddressPrompt = new Form("char", "Do you want to use a different shipping address (Y/N)");
		Form newAddress = new Form(
			new String[] {
				"str",
				"str",
				"str",
				"str",
				"str",
				"str"
			},
			new String[] {
				"First name",
				"Last name",
				"Street",
				"City",
				"State",
				"ZIP"
				
			}
		);
		Form newCreditPrompt = new Form("char", "Do you want to use a different credit card number (Y/N)");
		Form newCredit = new Form("str", "Enter card number");
		
		// Determine if the user wants to proceed or not
		String response[] = proceedPrompt.response();
		if(!response[0].equals("Y")){
			return true;
		}
		
		// Get the DB
		DBConnection db = Application.GetDB();
		
		// Prepare the shipping and billing address for the order
		String shipAddress[] = new String[6];
		String billAddress[] = new String[6];
		try{
			// Try to read member address data to get the billing address
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
			
			// If no results, then the user account doesn't, meaning a major error has occured
			if(results.length < 0)
				throw new Exception("User account not found!");
			
			// Map the results to the billing address
			String data[] = results[0].getData();
			billAddress[0] = data[2];
			billAddress[1] = data[3];
			billAddress[2] = data[4];
			billAddress[3] = data[5];
			billAddress[4] = data[0];
			billAddress[5] = data[1];
			
		}catch(Exception e){
			render.error("Could not submit order.");
			return true;
		}
	
		// Ask the user if they want to use a different shipping address
		response = newAddressPrompt.response();
		if(response[0].equals("Y")){
			// If so, then get their new address, and map it to the shipping address array
			response = newAddress.response();
			shipAddress[0] = response[2];
			shipAddress[1] = response[3];
			shipAddress[2] = response[4];
			shipAddress[3] = response[5];
			shipAddress[4] = response[0];
			shipAddress[5] = response[1];
		} else {
			// Otherwise, use the billing address as the shipping address
			shipAddress = billAddress;
		}
		
		// Ask if the user wants to use a new credit card or not
		response = newCreditPrompt.response();
		if(response[0].equals("Y")){
			response = newCredit.response();
		}
		
		// Instantiate the order
		Order order = new Order(shipAddress, billAddress, books);
		
		// Invoice the order, then return to the calling menu
		order.invoice();
		return true;
	}
	
}
