package bks;

import java.util.Scanner;

// Renderer for the entire application - all (or at least most) of output is done
// through the renderer
public class Render {
	
	Render(){
		
	}
	
	// Wait for the user to press the enter button
	public static void WaitForUser(){
		System.out.println("Press ENTER to continue...");
		Scanner in = new Scanner(System.in);
		in.nextLine();
	}
	
	// Output a newline
	public static void NewLine(){
		System.out.println("");
	}
	
	// Render a menu that is passed into the renderer
	public void Menu(Menu menu){
		// If this is a submenu, then render the menu without whitespace
		if(menu.isSubmenu()){
			this.renderMenu(menu, "");
		} else {
			this.renderMenu(menu, "                    ");
		}
	}
	
	// Renders the menu, with the specified margin
	private void renderMenu(Menu menu, String margin){
		// Get the options and the titles for the menu
		char options[] = menu.getOptions();
		String titles[] = menu.getTitles();
		
		// Output each menu option, with its associated title
		for(int i = 0; i < options.length; i++){
			System.out.println(margin + options[i] + ". " + titles[i]);
		}
	}
	
	// Renders a heading with a title
	public void heading(String title){
		System.out.println(" ------------ "+ title + " ------------");
	}
	
	// Renders a prompt
	public void prompt(String msg){
		System.out.print(msg + ": ");
	}
	
	// Render an error message
	public void error(String msg){
		this.notice("ERROR", msg);
	}
	
	// Render a success message
	public void success(String msg){
		this.notice("SUCCESS", msg);
	}
	
	// Renders a notice, with an associated title and message
	public void notice(String title, String msg){
		System.out.println("[" + title + "]: " +  msg);
	}
	
	// Renders a delimiter for menus and tables
	public void delim(){
		System.out.println("--------------------------------------------------------------------------------");
	}
	
	// Renders a list of order entries
	public void orderList(OrderEntry orders[]){
		// Formatting width for text
		int cellWidth = 25; 
		
		// If there aren't any orders, do nothing
		if(orders.length < 1)
			return;
		
		System.out.println("Orders placed by " + orders[0].getName());
		this.delim();
		
		System.out.format(
			"%-" + cellWidth + "s%-" + cellWidth + "s%-" + cellWidth + "s\n" , 
			"ORDER NO",
			"RECEIVED DATE",
			"SHIPPED DATE"
		);
		
		this.delim();
		
		// Output each order entry data
		for(int i = 0; i < orders.length; i++){
			System.out.format(
				"%-" + cellWidth + "s%-" + cellWidth + "s%-" + cellWidth + "s\n" , 
				orders[i].getOrderNo(),
				orders[i].getRecDate(),
				orders[i].getShipDate()
			);
		}
		
		this.delim();
		
	}
	
	// Renders an invoice for an order object
	public void invoice(Order order){
		// Get the order number and the addresses from the order object
		int addressLength = 41;
		String orderNo = order.getOrderNo();
		String shipAddress[] = order.getShipAddress();
		String billAddress[] = order.getBillAddress();
		
		// Output all of the formatted data from the order
		System.out.println("                  Details for Order no." + orderNo);
		
		System.out.format(
			"%-" + addressLength + "s%-" + addressLength + "s\n", 
			"Shipping Address",
			"Billing Address"
		);
		
		System.out.format(
			"%-" + addressLength + "s%-" + addressLength + "s\n", 
			"Name: " + shipAddress[4] + " " + shipAddress[5],
			"Name: " + billAddress[4] + " " + billAddress[5]
		);
		
		System.out.format(
			"%-" + addressLength + "s%-" + addressLength + "s\n", 
			"Address: " + shipAddress[0],
			"Address: " + billAddress[0]
		);
		
		System.out.format(
			"%-" + addressLength + "s%-" + addressLength + "s\n", 
			shipAddress[1],
			billAddress[1]
		);
		
		System.out.format(
			"%-" + addressLength + "s%-" + addressLength + "s\n", 
			shipAddress[2] + " " + shipAddress[3],
			billAddress[2] + " " + billAddress[3]
		);
		
		// Render the product table
		this.delim();
		this.productTable(order.getBooks());
		this.delim();
		
		// Wait for the user
		WaitForUser();
	}
	
	// Renders the cart with the given books
	public void cart(Book books[]){
		System.out.println("Current Cart Contents:");
		this.productTable(books);
		System.out.println();
	}
	
	// Renders a table of products, with their quantities, prices, and total
	public void productTable(Book books[]){
		// Cell withds for the formatted info in the table
		int isbnLength = 11;
		int titleLength = 45;
		int priceLength = 8;
		int qtyLength = 6;
		int totalLength = 8;
		
		// Output the formatted data titles for the product table
		System.out.format(
			"%-" + isbnLength + "s%-" + titleLength + "s%-" + priceLength + "s%-" + qtyLength + "s%-" + totalLength + "s\n", 
			"ISBN",
			"Title",
			"Price",
			"Qty",
			"Total"
		);
		this.delim();
		
		// Out for the formatted data for each product
		double totalCost = 0;
		for(int i = 0 ; i < books.length; i++){
			System.out.format(
				"%-" + isbnLength + "s%-" + titleLength + "s%-" + priceLength + ".2f%-" + qtyLength + "d%-" + totalLength + ".2f\n", 
				books[i].getIsbn(),
				books[i].getTitle(),
				books[i].getPrice(),
				books[i].getQty(),
				books[i].getQty() * books[i].getPrice()
			);
			totalCost = totalCost + (books[i].getQty() * books[i].getPrice());
		}
		
		// Output the total
		this.delim();
		System.out.format(
			"%-" + (isbnLength + titleLength + priceLength + qtyLength) + "s%-" + totalLength + ".2f\n", 
			"Total = ",
			totalCost
		);
		
	}
	
	// Renders the splash screen for the login menu
	public void loginSplash(){
		System.out.println(
			"**************************************************************\n" +
			"***                                                        ***\n" +
			"***            Welcome to the Online Bookstore             ***\n" +
			"***                     (made by hati)                     ***\n" +
			"***                                                        ***\n" +
			"**************************************************************\n"
		);
	}
	
	// Renders the splash screen for the member menu
	public void memberSplash(){
		System.out.println(
			"**************************************************************\n" +
			"***                                                        ***\n" +
			"***                    Online Bookstore:                   ***\n" +
			"***                       Member Menu                      ***\n" +
			"***                                                        ***\n" +
			"**************************************************************\n"
		);
	}

}
