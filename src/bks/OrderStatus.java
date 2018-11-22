package bks;

// Controller for the order status action in the member menu
public class OrderStatus implements MenuAction {

	// Executes when this action is chosen in a menu
	public boolean execute(){
		// Get the renderer and the db from the application
		Render render = Application.GetRenderer();	
		DBConnection db = Application.GetDB();
		
		// The user's name, and the list of order entries
		String name[];
		OrderEntry orderEntries[];
		try{
			// Read the user's name from the DB
			QueryData results[] = db.read(
				"members", 
				new String[]{
					"fname",
					"lname",
				}, 
				new Condition(
					new String[] { "userid" },
					new String[] { Login.CurrentUserId() }
				)
			);
			
			// If no results, there is a serious problem
			if(results.length < 1)
				throw new Exception("User account not found!");
			
			// Get the data from the first result returned
			String data[] = results[0].getData();
			
			// Build the name from the data
			name = new String[] { data[0], data[1] };
			
			// Condition object for finding orders
			Condition orderTableCondition = new Condition(
					new String[] { "userid" },
					new String[] { Login.CurrentUserId() }
			);
			orderTableCondition.setOrder("ORDER BY ono");
			
			// Read the orders from the db
			results = db.read(
				"orders", 
				new String[]{
					"ono",
					"received",
					"shipped"
				}, 
				orderTableCondition
			);
			
			// If there were no results, the current user has not placed an order yet. Throw an error
			// and return to the member menu
			if(results.length < 1){
				render.error("No orders for the current user. Please place an order first, and then try again.");
				return true;
			}
			
			// Instantiate the list of entries based on the results we got back
			orderEntries = new OrderEntry[results.length];
			for(int i = 0; i < orderEntries.length; i++){
				data = results[i].getData();
				orderEntries[i] = new OrderEntry(data[0], name[0] + " " + name[1], data[1], data[2]);
			}
		}catch(Exception e){
			// If there was a problem, throw an error and return
			render.error("Could not display orders from the database.");
			return true;
		}
		
		// Render the list of order entries
		render.orderList(orderEntries);
		
		// Get the user to choose an order from the list
		OrderEntry chosenOrder = this.chooseOrder(orderEntries);
		
		// Instantiate the base data for chosen order
		Book books[];
		String billAddress[] = new String[6];
		String shipAddress[] = new String[6];
		
		try{
			// Read the billing address from the member table
			QueryData results[] = db.read(
				"members", 
				new String[] { 
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
			
			// If there aren't any user accounts found, there was a problem
			if(results.length < 1)
				throw new Exception("User account not found!");
			
			// Get the data from the member record
			String data[] = results[0].getData();
			
			// Map the data from the record to the billing address
			billAddress[0] = data[0];
			billAddress[1] = data[1];
			billAddress[2] = data[2];
			billAddress[3] = data[3];
			billAddress[4] = name[0];
			billAddress[5] = name[1];
			
			// Read the shipping the address for the order 
			results = db.read(
				"orders", 
				new String[] {
					"shipAddress",
					"shipCity",
					"shipState",
					"shipZip"
				}, 
				new Condition(
					new String[] { "ono" },
					new String[] { chosenOrder.getOrderNo() }
				)
			);
			
			// If we couldn't find the associated, chosen order, there was a major problem
			if(results.length < 1)
				throw new Exception("Order chosen not found in the database.");
			
			// Map the result data into the shipping address
			data = results[0].getData();
			shipAddress[0] = data[0];
			shipAddress[1] = data[1];
			shipAddress[2] = data[2];
			shipAddress[3] = data[3];
			shipAddress[4] = name[0];
			shipAddress[5] = name[1];
			
			// Read the products from the order
			results = db.read(
				"odetails", 
				new String[] {
					"isbn",
					"qty"
				}, 
				new Condition(
					new String[]{ "ono" },
					new String[]{ chosenOrder.getOrderNo() }
				)
			);
			
			// If there were no products associated with the order, there was a major problem
			if(results.length < 1)
				throw new Exception("No products found for order!");
			
			// Instantiate the list of books for the order, based on the results
			books = new Book[results.length];
			for(int i = 0; i < books.length; i++){
				// Get the data for the current record
				data = results[i].getData();
				
				// Build the product for that record
				Product product = new Product(data[0], Integer.parseInt(data[1]));
				
				// Build the book based on the product
				books[i] = new Book(product);
			}
		}catch(Exception e){
			// If there was a problem, throw an error and exit
			render.error("Could not display selected order from the database.");
			return true;
		}
		
		// Build the order based on the addresses and the list of books
		Order order = new Order(shipAddress, billAddress, books);
		
		// Set the order number based on what we read
		order.setOrderNo(chosenOrder.getOrderNo());
		
		// Render the invoice for the order
		render.invoice(order);
		
		return true;
	}
	
	// Chooses an order entry from a list of order entries
	private OrderEntry chooseOrder(OrderEntry orderEntries[]){
		// Instantiate the base data
		OrderEntry chosenOrder = null;
		String response;
		String responses[];
		Render render = Application.GetRenderer();
		
		// If we haven't got a valid response, then keep asking for some
		boolean validResponse = false;
		while(!validResponse){
			// The single entry form that gets the order number, or q to quit
			Form form = new Form("str", "Enter the Order number to display its details, or (q) to quit");
			
			// Get the response from the user
			responses = form.response();
			response = responses[0];
			
			// If we want to quit, then return null
			if(response.equals("q")){
				return null;
			}
			
			// If the response references a valid order entry, then select that entry. Otherwise,
			// render an error, and get another response
			if(this.orderPos(response, orderEntries) > -1){
				validResponse = true;
				chosenOrder = orderEntries[this.orderPos(response, orderEntries)];
			} else {
				render.error("Invalid entry");
			}
		}
		
		// Return the selected order
		return chosenOrder;
	}
	
	// Find the position of the order entry with orderNo, and returns it. If
	// the order entry is not in the list, return -1
	private int orderPos(String orderNo, OrderEntry orderEntries[]){
		for(int i = 0; i < orderEntries.length; i++){
			String orderCheck = orderEntries[i].getOrderNo();
			if(orderCheck.equals(orderNo)){
				return i;
			}
		}
		
		return -1;
	}
	
}
