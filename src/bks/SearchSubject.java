package bks;

// Controller for searching for a given subject
public class SearchSubject implements MenuAction {

	// The subject we are searching for
	private String subject;
	
	// We instantiate with the subject we are searching for
	SearchSubject(String subject){
		this.subject = subject;
	}
	
	// Executes when this action is chosen in a menu
	public boolean execute(){
		// Get the renderer from the application, and instantiate a searcher
		Render render = Application.GetRenderer();
		Search searcher = new Search();
		
		// Get the list of books in the chosen subject
		Book books[] = searcher.searchBooks("subject", this.subject, false);
		
		// Get number of books found, and render it
		int count = books.length;
		System.out.println(
			count +
			" book" +
			(count != 1 ? "s" : "") +
			" found!"
		);
		
		// Instantiate the browse and search menus, based on the browse and search menus used 
		// in the SearchAction controller
		Menu browseMenu = SearchAction.BrowseChoices;
		Menu searchMenu = SearchAction.SearchChoices;
		browseMenu.subMenu = true;
		searchMenu.subMenu = true;
		
		// Iterate through the list of books in increments of 2
		for(int i = 0; i < books.length; i+=2){
			boolean continueBrowse;
			
			// Render the heading for this increment sets
			render.heading("Books " + (i + 1) + "-" + (i + 2));
			
			// Render the books in this increment set
			for(
				int j = i; 
				j < ( i + 1 == books.length ? i + 1 : i + 2); 
				j++
			){
				// Instantiates a table object based on the book data
				Table bookData = new Table(books[j]);
				
				// Render the book table for this entry
				bookData.render();
				Render.NewLine();
			}
			
			// If we aren't at the end of the list of books, then get a choice from the
			// browse menu
			if(i + 1 != books.length && i + 2 != books.length){
				char choice = browseMenu.getMenuChoice();
				continueBrowse = browseMenu.action(choice);
			
				if(!continueBrowse){
					return false;
				}
			}
		}
		
		// If we are the end of list, and there were books in the list, render a notice message to the user
		if(books.length > 0){
			render.notice("Info", "End of search results.");
		}
		
		// Gets a choice from the final search menu, and return the result of the action taken
		char choice = searchMenu.getMenuChoice();
		return searchMenu.action(choice);
	}
	
}
