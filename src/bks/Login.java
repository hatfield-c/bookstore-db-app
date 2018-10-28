package bks;

public class Login implements MenuAction {
	
	private Render render;
	
	Login(){
		this.render = Application.GetRenderer();
	}
	
	public boolean execute(){
		boolean connected = true;
		
		Menu memberMenu = new Menu(
			"Type your option",
			new char[] { 
				'1',
				'2',
				'3',
				'4',
				'5',
				'6',
				'7',
				'8'
			},
			new String[] { 
				"Browse by Subject",
				"Search by Author/Title/Subject",
				"View/Edit Shopping Cart",
				"Check Order Status",
				"Check Out",
				"One Click Check Out",
				"View/Edit Personal Information",
				"Logout"
			},
			new MenuAction[] {
					new BrowseSubject(),
					new Search(),
					new CartMenu(),
					new OrderStatus(),
					new Checkout(),
					new OneClick(),
					new AccountMenu(),
					new Quit()
			}
		);

		while(connected){
			this.render.memberSplash();
			
			char choice = memberMenu.getMenuChoice();
			connected = memberMenu.action(choice);
		}
		
		return true;
	}

}
