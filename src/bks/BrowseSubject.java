package bks;

public class BrowseSubject implements MenuAction {

	// Executes when this action is chosen in a menu
	public boolean execute(){
		// Get a local instance of the db and renderer
		DBConnection db = Application.GetDB();
		Render render = Application.GetRenderer();
		String subjects[];
		
		try{
			// Try to read all unique subjects from the db
			QueryData results[] = db.read(
				"books", 
				new String[]{
					"unique subject"
				}, 
				null
			);
			
			// Prepare the subjects list
			subjects = new String[results.length];
			
			// Map the returned data to the subjects list
			for(int i = 0; i < results.length; i++){
				subjects[i] = results[i].getData()[0];
			}
		} catch(Exception e){
			// If there is an issue during reading/mapping, throw an error and exit this action
			render.error("Could not retrieve subject list.");
			return true;
		}
		
		// Build the menu which will asks for a subject from the user
		char subjectChoices[] = new char[subjects.length];
		MenuAction actions[] = new MenuAction[subjects.length];
		for(int i = 0; i < subjectChoices.length; i++){
			// Build the char choices for the menu to select a subject
			String buf = Integer.toString(i + 1);
			subjectChoices[i] = buf.charAt(0);
			
			// Build the actions taken when a subject is chosen
			actions[i] = new SearchSubject(subjects[i]);
		}
		
		// Instantiate the menu
		Menu subjectMenu = new Menu("Choose a subject", subjectChoices, subjects, actions);
		
		// While we are still browsing, continue to do so
		boolean browse = true;
		while(browse){
			// Get a choice from the subject menu
			char choice = subjectMenu.getMenuChoice();
			
			// Determine if we are still browsing based on the return of the choice's action
			browse = subjectMenu.action(choice);
		
		}
		return true;
	}
	
}
