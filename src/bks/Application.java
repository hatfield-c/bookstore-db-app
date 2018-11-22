package bks;
import java.util.Scanner;

// Application object for booting and running the application
public class Application {

	// Internal renderer and database connection - shared by all actions throughout
	// the application
	private static Render render;
	private static DBConnection db;
	
	// Application must be loaded with a renderer and a database connection
	Application(Render render, DBConnection db){
		Application.render = render;
		Application.db = db;
	}
	
	// Method which begins running the application after instantiation
	public void run(){
		boolean running = true;
		
		// Login menu for the application
		Menu appMenu = new Menu(
			"Type a response",  
			new char[] { 
				'1', 
				'2', 
				'q' 
			}, 
			new String[] { 
				"Member Login", 
				"New Member Registration", 
				"Quit" 
			},
			new MenuAction[] { 
				new Login(), 
				new Register(), 
				new Quit() 
			}
		);
		
		// While the application is running, continue asking for input
		while(running){
			Application.render.loginSplash();
			
			char choice = appMenu.getMenuChoice();
			running = appMenu.action(choice);
		}
	}
	
	
	// Static method to get the renderer from within the application
	public static Render GetRenderer(){
		return Application.render;
	}
	
	// Static method to get the renderer from within the application
	public static DBConnection GetDB(){
		return Application.db;
	}
	
}
