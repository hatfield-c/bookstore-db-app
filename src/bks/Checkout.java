package bks;

public class Checkout implements MenuAction {

	public boolean execute(){
		Render render = Application.GetRenderer();
		Cart cart = new Cart();
		Product products[] = cart.getProducts();
		
		if(products.length < 1){
			render.error("No products in the cart!");
			return true;
		}
		
		Book books[] = new Book[products.length];
		
		for(int i = 0; i < products.length; i++){
			books[i] = new Book(products[i]);
		}
		
		render.cart(books);
		
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
		
		String response[] = proceedPrompt.response();
		
		if(!response[0].equals("Y")){
			return true;
		}
		
		DBConnection db = Application.GetDB();
		
		String shipAddress[] = new String[6];
		String billAddress[] = new String[6];
		try{
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
			
			if(results.length < 0)
				throw new Exception("User account not found!");
			
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
	
		response = newAddressPrompt.response();
		if(response[0].equals("Y")){
			response = newAddress.response();
			shipAddress[0] = response[2];
			shipAddress[1] = response[3];
			shipAddress[2] = response[4];
			shipAddress[3] = response[5];
			shipAddress[4] = response[0];
			shipAddress[5] = response[1];
		} else {
			shipAddress = billAddress;
		}
		
		response = newCreditPrompt.response();
		if(response[0].equals("Y")){
			response = newCredit.response();
		}
		
		Order order = new Order(shipAddress, billAddress, books);
		order.invoice();
		return true;
	}
	
}
