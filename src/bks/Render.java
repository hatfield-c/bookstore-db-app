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
