package bks;

public class OneClick implements MenuAction {

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
		
		DBConnection db = Application.GetDB();
		
		String address[] = new String[6];
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
			address[0] = data[2];
			address[1] = data[3];
			address[2] = data[4];
			address[3] = data[5];
			address[4] = data[0];
			address[5] = data[1];
			
		}catch(Exception e){
			render.error("Could not submit order.");
			return true;
		}
		
		Order order = new Order(address, address, books);
		order.invoice();
		
		return true;
	}
	
}
