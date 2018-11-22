package bks;

// Controller for the account menu action
public class AccountMenu implements MenuAction {
	
	// Internal menu for deciding on this menu's own action
	private Menu accountInfo = new Menu(
			"What do you want to do",
			new char[] {
				'1',
				'2'
			},
			new String[] {
				"View Account Info",
				"Edit Account Info"
			},
			new MenuAction[] {
					new ViewAccount(),
					new EditAccount()
			}
	);

	// Executes when this action is chosen in a menu
	public boolean execute(){
		// Get the menu choice
		char choice = this.accountInfo.getMenuChoice();
		
		// Take action based on the choice, and return the result
		return this.accountInfo.action(choice); 
	}
	
}
