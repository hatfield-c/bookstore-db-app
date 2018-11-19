package bks;

public class SearchMenu implements MenuAction{

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
	
	SearchMenu(){
		this.searchMenu.subMenu = true;
	}
	
	public boolean execute(){
		Render render = Application.GetRenderer();
		
		boolean running = true;
		
		while(running){
			char choice = this.searchMenu.getMenuChoice();
			running = this.searchMenu.action(choice);
		
		}
		return true;
	}
	
}
