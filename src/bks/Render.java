package bks;

import java.util.Scanner;

public class Render {
	
	Render(){
		
	}
	
	public static void WaitForUser(){
		System.out.println("Press ENTER to continue...");
		Scanner in = new Scanner(System.in);
		in.nextLine();
	}
	
	public static void NewLine(){
		System.out.println("");
	}
	
	public void Menu(Menu menu){
		if(menu.isSubmenu()){
			this.renderMenu(menu, "");
		} else {
			this.renderMenu(menu, "                    ");
		}
	}
	
	private void renderMenu(Menu menu, String margin){
		char options[] = menu.getOptions();
		String titles[] = menu.getTitles();
		
		for(int i = 0; i < options.length; i++){
			System.out.println(margin + options[i] + ". " + titles[i]);
		}
	}
	
	public void heading(String title){
		System.out.println(" ------------ "+ title + " ------------");
	}
	
	public void prompt(String msg){
		System.out.print(msg + ": ");
	}
	
	public void error(String msg){
		this.notice("ERROR", msg);
	}
	
	public void success(String msg){
		this.notice("SUCCESS", msg);
	}
	
	public void notice(String title, String msg){
		System.out.println("[" + title + "]: " +  msg);
	}
	
	public void delim(){
		System.out.println("--------------------------------------------------------------------------------");
	}
	
	public void orderList(OrderEntry orders[]){
		int cellWidth = 25; 
		
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
		
		for(int i = 0; i < orders.length; i++){
			System.out.format(
				"%-" + cellWidth + "s%" + cellWidth + "s%" + cellWidth + "s\n" , 
				orders[i].getOrderNo(),
				orders[i].getRecDate(),
				orders[i].getShipDate()
			);
		}
		
		this.delim();
		
	}
	
	public void invoice(Order order){
		int addressLength = 41;
		String orderNo = order.getOrderNo();
		String shipAddress[] = order.getShipAddress();
		String billAddress[] = order.getBillAddress();
		
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
		
		this.delim();
		this.productTable(order.getBooks());
		this.delim();
		WaitForUser();
	}
	
	public void cart(Book books[]){
		System.out.println("Current Cart Contents:");
		this.productTable(books);
		System.out.println();
	}
	
	public void productTable(Book books[]){
		int isbnLength = 11;
		int titleLength = 45;
		int priceLength = 8;
		int qtyLength = 6;
		int totalLength = 8;
		
		System.out.format(
			"%-" + isbnLength + "s%-" + titleLength + "s%-" + priceLength + "s%-" + qtyLength + "s%-" + totalLength + "s\n", 
			"ISBN",
			"Title",
			"Price",
			"Qty",
			"Total"
		);
		this.delim();
		
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
		
		this.delim();
		System.out.format(
			"%-" + (isbnLength + titleLength + priceLength + qtyLength) + "s%-" + totalLength + ".2f\n", 
			"Total = ",
			totalCost
		);
		
	}
	
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
