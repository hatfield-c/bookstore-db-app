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
	
	public void cart(Book books[]){
		int isbnLength = 11;
		int titleLength = 45;
		int priceLength = 8;
		int qtyLength = 6;
		int totalLength = 8;
		
		System.out.println("Current Cart Contents:");
		System.out.format(
			"%-" + isbnLength + "s%-" + titleLength + "s%-" + priceLength + "s%-" + qtyLength + "s%-" + totalLength + "s\n", 
			"ISBN",
			"Title",
			"Price",
			"Qty",
			"Total"
		);
		System.out.println("--------------------------------------------------------------------------------");
		
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
		
		System.out.println("--------------------------------------------------------------------------------");
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
