package bks;

public class Register implements MenuAction {
	
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
	
	private String registerError = "Could not register. UserID may already be taken, or invalid data was given. Returning to main menu...";
	
	Register(){
		
	}

	public boolean execute(){
		String responses[] = this.registerForm.response();
		
		Render render = Application.GetRenderer();
		DBConnection db = Application.GetDB();
		boolean success = false;
		
		try{
			success = db.insert("members", responses);
		}catch(Exception e){
			render.error(this.registerError);
			return true;
		}
		
		if(!success){
			render.error(this.registerError);
			return true;
		}
		
		return true;
	}
	
}
