package bks;
import java.util.Scanner;

public class Application {

	private static Render render;
	
	Application(Render render){
		Application.render = render;
	}
	
	public void run(){
		boolean running = true;
		
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
		
		while(running){
			Application.render.loginSplash();
			
			char choice = appMenu.getMenuChoice();
			running = appMenu.action(choice);
		}
	}
	
	public static Render GetRenderer(){
		return Application.render;
	}
	
}
