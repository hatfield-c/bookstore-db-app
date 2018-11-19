package bks;

public class BrowseSubject implements MenuAction {

	public boolean execute(){
		DBConnection db = Application.GetDB();
		Render render = Application.GetRenderer();
		String subjects[];
		
		try{
			QueryData results[] = db.read(
				"books", 
				new String[]{
					"unique subject"
				}, 
				null
			);
			
			subjects = new String[results.length];
			
			for(int i = 0; i < results.length; i++){
				subjects[i] = results[i].getData()[0];
			}
		} catch(Exception e){
			render.error("Could not retrieve subject list.");
			return true;
		}
		
		char subjectChoices[] = new char[subjects.length];
		MenuAction actions[] = new MenuAction[subjects.length];
		for(int i = 0; i < subjectChoices.length; i++){
			String buf = Integer.toString(i + 1);
			subjectChoices[i] = buf.charAt(0);
			
			actions[i] = new SearchSubject(subjects[i]);
		}
		
		Menu subjectMenu = new Menu("Choose a subject", subjectChoices, subjects, actions);
		
		boolean browse = true;
		while(browse){
			char choice = subjectMenu.getMenuChoice();
			browse = subjectMenu.action(choice);
		
		}
		return true;
	}
	
}
