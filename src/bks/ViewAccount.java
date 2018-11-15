package bks;

public class ViewAccount implements MenuAction {

	public boolean execute() {
		Render render = Application.GetRenderer();
		DBConnection db = Application.GetDB();
		String userId = Login.CurrentUserId();
		
		try {
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
			
			if(data.length != 1){
				throw new Exception();
			}
			
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
			
			render.heading("Account Info");
			dataTable.render();
			System.out.println("");
			Render.WaitForUser();
			
		} catch (Exception e){
			render.error("There was an error retrieving account information. Returning to the menu.");
			return true;
		}
		
		return true;
	}

}
