package bks;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

// Data structure to hold order information, and to submit an order after it has been built
public class Order {
	
	// Internal data for the order
	private String shipAddress[];
	private String billAddress[];
	private Book books[];
	private String orderNo;
	
	// An order requires a shipping/billing address, and a list of books to buy
	Order(String shipAddress[], String billAddress[], Book books[]){
		this.books = books;
		this.shipAddress = shipAddress;
		this.billAddress = billAddress;
		this.orderNo = "0";
	}
	
	// After the order has been built, the order is invoiced, and submitted to the db
	public void invoice(){
		// Get the db and the renderer
		DBConnection db = Application.GetDB();
		Render render = Application.GetRenderer();
		
		// Get the current data/time, and format it for our database
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MMM/yy");
		LocalDate localDate = LocalDate.now();
		String date = dateFormatter.format(localDate);
		
		try {
			// Ordering is considered a special condition, thus it has no constructor
			Condition countCondition = new Condition();
			countCondition.setOrder("ORDER BY ono");
			
			// We read all the orders from the db, ascending order by the order number
			QueryData results[] = db.read(
				"orders",
				new String[] {
					"ono"
				}, 
				countCondition
			);
			
			// If we got results from the db, then we get our new order number based on the order number
			// of the very last order
			if(results.length > 0){
				String lastData[] = results[results.length - 1].getData();
				int newOrderNo = Integer.parseInt(lastData[0]) + 1;
				this.orderNo = Integer.toString(newOrderNo);
			} else {
				// If we got no results, assume this is the first order, and move forward
				this.orderNo = "1";
			}

			// Insert the order data into the database - the shipping address is stored in the orders table,
			// and the member's address is used as the billing address
			db.insert(
				"orders", 	
				new String[] {
					Login.CurrentUserId(),
					this.orderNo,
					date,
					null,
					this.shipAddress[0],
					this.shipAddress[1],
					this.shipAddress[2],
					this.shipAddress[3]
				}
			);
			
			// Insert each of the products into the associated order details table
			for(int i = 0; i < this.books.length; i++){
				db.insert(
					"odetails", 
					new String[] {
						this.orderNo,
						books[i].getIsbn(),
						Integer.toString(books[i].getQty()),
						Double.toString(books[i].getPrice())
					}
				);
			}
			
			// Dump the cart
			Cart cart = new Cart();
			cart.dumpCart();
			
			// Render a success message
			render.success("Successfully submitted order!");
			render.invoice(this);
		} catch(Exception e){
			// If there was problem, throw an error message
			render.error("Error during order processing.");
		}
	}
	
	/***
	 *    Getters and setters for the order
	 */
	
	public String getOrderNo(){
		return this.orderNo;
	}
	
	public void setOrderNo(String orderNo){
		this.orderNo = orderNo;
	}
	
	public String[] getShipAddress() {
		return shipAddress;
	}

	public void setShipAddress(String[] shipAddress) {
		this.shipAddress = shipAddress;
	}

	public String[] getBillAddress() {
		return billAddress;
	}

	public void setBillAddress(String[] billAddress) {
		this.billAddress = billAddress;
	}

	public Book[] getBooks() {
		return books;
	}

	public void setBooks(Book[] books) {
		this.books = books;
	}

}
