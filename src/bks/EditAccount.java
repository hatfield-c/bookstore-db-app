package bks;

public class EditAccount implements MenuAction {

	public boolean execute() {
		// Build the form for getting new account data from the user
		Form accountForm = new Form(
			new String[]{"str", "str", "str", "str", "str", "int", "str", "str", "str", "str"},
			new String[]{ 
				"First Name",
				"Last Name",
				"Address",
				"City",
				"State",
				"ZIP",
				"Phone",
				"Email",
				"Card Type",
				"Card Number"
			}
		);
		
		// Get the new user data from the user
		String responses[] = accountForm.response();
		
		// Get the DB and the renderer from the application, and init our success boolean value
		Render render = Application.GetRenderer();
		DBConnection db = Application.GetDB();
		boolean success = false;
		
		// Render a notice to the user that we are trying to save the new data
		render.notice("CONNECTING", "Attempting to save new user data...");
		
		try{
			// Try to update members data, using the user's responses
			success = db.update(
				"members",
				new String[] {
					"fname",
					"lname",
					"address",
					"city",
					"state",
					"zip",
					"phone",
					"email",
					"creditcardtype",
					"creditcardnumber"
				},
				responses,
				new Condition( new String[] { "userid" }, new String[] { Login.CurrentUserId() })
			);
		}catch(Exception e){
			// If there was a problem, throw an error and exit
			render.error("Error during account edit - could not apply changes.");
			return true;
		}
		
		// If we didn't succeed, throw an error and exit
		if(!success){
			render.error("Error during account edit - could not apply changes.");
			return true;
		}
		
		// We've succeeded, so render a success message and wait for a user
		render.success("Account edit successful!");
		Render.WaitForUser();
		
		return true;
	}

}
