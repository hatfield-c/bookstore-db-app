package bks;

public class EditAccount implements MenuAction {

	public boolean execute() {
		Form accountForm = new Form(
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
		
		String responses[] = accountForm.response();
		Render render = Application.GetRenderer();
		DBConnection db = Application.GetDB();
		boolean success = false;
		
		render.notice("CONNECTING", "Attempting to save new user data...");
		
		try{
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
					"userid",
					"password",
					"creditcardtype",
					"creditcardnumber"
				},
				responses,
				new Condition( new String[] { "userid" }, new String[] { Login.CurrentUserId() })
			);
		}catch(Exception e){
			render.error("Error during account edit - could not apply changes.");
			return true;
		}
		
		if(!success){
			render.error("Error during account edit - could not apply changes.");
			return true;
		}
		
		render.success("Account edit successful!");
		Login.ChangeUser(responses[8]);
		Render.WaitForUser();
		
		return true;
	}

}
