package bks;

public class OrderStatus implements MenuAction {

	public boolean execute(){
		Render render = Application.GetRenderer();	
		DBConnection db = Application.GetDB();
		
		String name[];
		OrderEntry orderEntries[];
		try{
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
			
			if(results.length < 1)
				throw new Exception("User account not found!");
			
			String data[] = results[0].getData();
			name = new String[] { data[0], data[1] };
			
			Condition orderTableCondition = new Condition(
					new String[] { "userid" },
					new String[] { Login.CurrentUserId() }
			);
			orderTableCondition.setOrder("ORDER BY ono");
			
			results = db.read(
				"orders", 
				new String[]{
					"ono",
					"received",
					"shipped"
				}, 
				orderTableCondition
			);
			
			if(results.length < 1){
				render.error("No orders for the current user. Please place an order first, and then try again.");
				return true;
			}
				
			orderEntries = new OrderEntry[results.length];
			for(int i = 0; i < orderEntries.length; i++){
				data = results[i].getData();
				orderEntries[i] = new OrderEntry(data[0], name[0] + " " + name[1], data[1], data[2]);
			}
		}catch(Exception e){
			render.error("Could not display orders from the database.");
			return true;
		}
		
		render.orderList(orderEntries);
		OrderEntry chosenOrder = this.chooseOrder(orderEntries);
		Book books[];
		String billAddress[] = new String[6];
		String shipAddress[] = new String[6];
		
		try{
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
			
			if(results.length < 1)
				throw new Exception("User account not found!");
			
			String data[] = results[0].getData();
			billAddress[0] = data[0];
			billAddress[1] = data[1];
			billAddress[2] = data[2];
			billAddress[3] = data[3];
			billAddress[4] = name[0];
			billAddress[5] = name[1];
			
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
			
			if(results.length < 1)
				throw new Exception("Order chosen not found in the database.");
			
			data = results[0].getData();
			shipAddress[0] = data[0];
			shipAddress[1] = data[1];
			shipAddress[2] = data[2];
			shipAddress[3] = data[3];
			shipAddress[4] = name[0];
			shipAddress[5] = name[1];
			
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
			
			if(results.length < 1)
				throw new Exception("No products found for order!");
			
			books = new Book[results.length];
			for(int i = 0; i < books.length; i++){
				data = results[i].getData();
				Product product = new Product(data[0], Integer.parseInt(data[1]));
				books[i] = new Book(product);
			}
		}catch(Exception e){
			render.error("Could not display selected order from the database.");
			return true;
		}
		
		Order order = new Order(shipAddress, billAddress, books);
		order.setOrderNo(chosenOrder.getOrderNo());
		render.invoice(order);
		
		return true;
	}
	
	private OrderEntry chooseOrder(OrderEntry orderEntries[]){
		OrderEntry chosenOrder = null;
		String response;
		String responses[];
		Render render = Application.GetRenderer();
		
		boolean validResponse = false;
		while(!validResponse){
			Form form = new Form("str", "Enter the Order number to display its details, or (q) to quit");
			responses = form.response();
			response = responses[0];
			
			if(response.equals("q")){
				return null;
			}
			
			if(this.orderPos(response, orderEntries) > -1){
				validResponse = true;
				chosenOrder = orderEntries[this.orderPos(response, orderEntries)];
			} else {
				render.error("Invalid entry");
			}
		}
		
		return chosenOrder;
	}
	
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
