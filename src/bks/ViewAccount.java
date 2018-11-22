package bks;

// Controller for viewing info for the logged in user
public class ViewAccount implements MenuAction {

	// Executes when this action is chosen in a menu
	public boolean execute() {
		// Get the renderer, the db, and the userid
		Render render = Application.GetRenderer();
		DBConnection db = Application.GetDB();
		String userId = Login.CurrentUserId();
		
		try {
			// Read the data for the current user from the database
			QueryData data[] = db.read(
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
					"userid",
					"password",
					"creditcardtype",
					"creditcardnumber"
				}, 
				new Condition(new String[] { "userid" }, new String[] { userId })
			);
			
			// If no user account was returned, there was a major problem
			if(data.length != 1){
				throw new Exception("No user account found!");
			}
			
			// Create a table to hold the user's data
			Table dataTable = new Table(
				new String[] {
					"First Name",
					"Last Name",
					"Address",
					"City",
					"State",
					"Zip",
					"Phone",
					"Email",
					"UserID",
					"Password",
					"Card Type",
					"Card Number"
				},
				data[0].getData()		
			);
			
			// Render the heading, the table with the user's data
			render.heading("Account Info");
			dataTable.render();
			System.out.println("");
			
			// Wait for the user
			Render.WaitForUser();
			
		} catch (Exception e){
			// If there was a problem, render an error message and return
			render.error("There was an error retrieving account information. Returning to the menu.");
			return true;
		}
		
		return true;
	}

}
