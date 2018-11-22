package bks;

// Controller for registering a new user
public class Register implements MenuAction {
	
	// Form for getting the new user's data
	private Form registerForm = new Form(
		new String[]{"str", "str", "str", "str", "str", "int", "str", "str", "str", "str", "str", "str"},
		new String[]{ 
			"First Name",
			"Last Name",
			"Address",
			"City",
			"State",
			"ZIP",
			"Phone",
			"Email",
			"UserID",
			"Password",
			"Card Type",
			"Card Number"
		}
	);
	
	// Register error message
	private String registerError = "Could not register. UserID may already be taken, or invalid data was given. Returning to main menu...";

	// Executes when this action is chosen in a menu
	public boolean execute(){
		// Get the responses from the user for the new user data
		String responses[] = this.registerForm.response();
		
		// Get the renderer and the db
		Render render = Application.GetRenderer();
		DBConnection db = Application.GetDB();
		boolean success = false;
	
		try{
			// Try to insert the new user data into members table
			success = db.insert("members", responses);
		}catch(Exception e){
			// If there was a problem, throw an error and return
			render.error(this.registerError);
			return true;
		}
		
		// If we weren't successful, throw an error and return
		if(!success){
			render.error(this.registerError);
			return true;
		}
		
		// We've succeeded - throw an error message, and wait for the user
		render.success("Registration successful!");
		Render.WaitForUser();
		
		return true;
	}
	
}
