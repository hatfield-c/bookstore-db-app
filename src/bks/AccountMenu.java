package bks;

public class AccountMenu implements MenuAction {
	
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

	public boolean execute(){
		char choice = this.accountInfo.getMenuChoice();
		return this.accountInfo.action(choice); 
	}
	
}
