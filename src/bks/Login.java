package bks;

public class Login implements MenuAction {
	
	private Render render;
	private Menu memberMenu = new Menu(
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
	private String loginFailure = "Error during authentication. Returning to main menu...";
	private String invalidCred = "Invalid credentials. Returning to main menu...";
	private String loginSuccess = "Loggin in...";
	
	Login(){
		this.render = Application.GetRenderer();
	}
	
	public boolean execute(){
		boolean connected = true;
		
		DBConnection db = Application.GetDB();
		
		Form loginForm = new Form(new String[]{ "str", "str" }, new String[]{ "userID", "password" });
		String response[] = loginForm.response();
		QueryData data[];
		
		try{
			 data = db.read(
					"members", 
					new String[]{ "userid", "password" },  
					new Condition("userid", response[0])
			);
		} catch(Exception e){
			this.render.error(this.loginFailure);
			return true;
		}
		
		if(data.length != 1){
			this.render.error(this.loginFailure);
			return true;
		}
		
		String userData[] = data[0].getData();
		
		if(!userData[1].equals(response[1])){
			this.render.error(this.invalidCred);
			return true;
		}
		
		this.render.success(this.loginSuccess);

		while(connected){
			this.render.memberSplash();
			
			char choice = this.memberMenu.getMenuChoice();
			connected = this.memberMenu.action(choice);
		}
		
		return true;
	}

}
