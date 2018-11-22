package bks;


// Controller for logging a user in
public class Login implements MenuAction {
	
	private Render render;
	private String memberPrompt = "Type your option";
	
	// Menu for after a member has logged in
	private Menu memberMenu = new Menu(
		this.memberPrompt,
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
				new SearchMenu(),
				new CartMenu(),
				new OrderStatus(),
				new Checkout(),
				new OneClick(),
				new AccountMenu(),
				new Quit()
		}
	);
	
	// Login messages
	private String loginFailure = "Error during authentication. Returning to main menu...";
	private String invalidCred = "Invalid credentials. Returning to main menu...";
	private String loginSuccess = "Log in successful!";
	
	// Static userid for the current user of this application instance
	private static String userid = null;
	
	Login(){
		this.render = Application.GetRenderer();
	}
	
	// Executes when this action is chosen in a menu
	public boolean execute(){
		boolean connected = true;
		
		// Get the db from the application
		DBConnection db = Application.GetDB();
		
		// Build the login form for the user
		Form loginForm = new Form(new String[]{ "str", "str" }, new String[]{ "userID", "password" });
		
		// Get the user's login response
		String response[] = loginForm.response();
		QueryData data[];
		
		try{
			// Try to read the user's data from the database
			 data = db.read(
					"members", 
					new String[]{ "userid", "password" },  
					new Condition(new String[] { "userid" }, new String[] { response[0] })
			);
		} catch(Exception e){
			// If there was a problem, render an error and return back to the application menu
			this.render.error(this.loginFailure);
			return true;
		}
		
		// There should be exactly one user account returned - if not, there was a problem. Return to the application menu
		if(data.length != 1){
			this.render.error(this.loginFailure);
			return true;
		}
		
		// Get the user's password
		String userData[] = data[0].getData();
		
		// If the password provided by the user doesn't match the password in the database,
		// throw an error and return to the application menu
		if(!userData[1].equals(response[1])){
			this.render.error(this.invalidCred);
			return true;
		}
		
		// Output notice and success messages
		this.render.notice("CONNECTING","Attempting to log in...");
		this.render.success(this.loginSuccess);
		
		// Set the current id to the logged in user, and wait for the user to acknowledge
		Login.userid = userData[0];
		
		Render.WaitForUser();

		// While we are connected, continue to be connected as a logged in user
		while(connected){
			// Render the member splash screen
			this.render.memberSplash();
			
			// Output the user id and the prompt
			String newPrompt = "[USER: " + CurrentUserId() + "]\n" + this.memberPrompt;
			this.memberMenu.setPrompt(newPrompt);
			
			// Get a choice from the user for the menu
			char choice = this.memberMenu.getMenuChoice();
			
			// Determine if we're still connected based on the choice we make
			connected = this.memberMenu.action(choice);
		}
		
		return true;
	}
	
	// Static method to get the current logged in user for the entire application
	public static String CurrentUserId(){
		return Login.userid;
	}

}
