package bks;

// Controller to determine a kind of search to perform
public class SearchMenu implements MenuAction{

	// Menu for choosing the kind of search
	private Menu searchMenu = new Menu(
		"Type your option",
		new char[] {
			'1',
			'2',
			'3'
		},
		new String[] {
			"Search by Author",
			"Search by Title",
			"Return to main menu"
		},
		new MenuAction[] {
			new SearchAction("author"),
			new SearchAction("title"),
			new Quit()
		}
	);
	
	// Set the search menu to a submenu
	SearchMenu(){
		this.searchMenu.subMenu = true;
	}
	
	// Executes when this action is chosen in a menu
	public boolean execute(){
		// Get the renderer from the application
		Render render = Application.GetRenderer();
		
		// While we are running this menu, continue getting menu choices from the user
		boolean running = true;
		
		while(running){
			char choice = this.searchMenu.getMenuChoice();
			running = this.searchMenu.action(choice);
		
		}
		return true;
	}
	
}
