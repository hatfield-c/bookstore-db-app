package bks;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Order {
	
	private String shipAddress[];
	private String billAddress[];
	private Book books[];
	private String orderNo;
	
	Order(String shipAddress[], String billAddress[], Book books[]){
		this.books = books;
		this.shipAddress = shipAddress;
		this.billAddress = billAddress;
		this.orderNo = "0";
	}
	
	public void invoice(){
		DBConnection db = Application.GetDB();
		Render render = Application.GetRenderer();
		
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MMM/yy");
		LocalDate localDate = LocalDate.now();
		String date = dateFormatter.format(localDate);
		
		try {
			Condition countCondition = new Condition();
			countCondition.setOrder("ORDER BY ono");
			QueryData results[] = db.read(
				"orders",
				new String[] {
					"ono"
				}, 
				countCondition
			);
			
			if(results.length > 0){
				String lastData[] = results[results.length - 1].getData();
				int newOrderNo = Integer.parseInt(lastData[0]) + 1;
				this.orderNo = Integer.toString(newOrderNo);
			} else {
				this.orderNo = "1";
			}

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
			
			Cart cart = new Cart();
			cart.dumpCart();
			render.success("Successfully submitted order!");
			render.invoice(this);
		} catch(Exception e){
			render.error("Error during order processing.");
		}
	}
	
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
