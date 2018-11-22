package bks;

// Controller for when a search has been performed, and the user must interact with its data
public class SearchAction implements MenuAction {

	// Menu for after all search items have been browsed through
	public static Menu SearchChoices = new Menu(
		"Enter choice",
		new char[] {
			'a',
			's',
			'q'
		},
		new String[] {
			"Add a product to the cart",
			"Search again",
			"Return to member menu"
		},
		new MenuAction[] {
			new CartAdd(),
			new Repeat(),
			new Quit()
		}
	);
	
	// Menu for interacting with search items while browsing through them
	public static Menu BrowseChoices = new Menu(
		"Enter choice",
		new char[] {
			'a',
			'n',
			'q'
		},
		new String[] {
			"Add a product to the cart",
			"Continue browsing search results",
			"Return to member menu"
		},
		new MenuAction[] {
			new CartAdd(),
			new Repeat(),
			new Quit()
		}
	);
	
	// The attribute searched for
	private String attribute;
	
	// Instantiates the search action with an attribute, and sets both its menus to submenus
	SearchAction(String action){
		this.attribute = action;
		SearchChoices.subMenu = true;
		BrowseChoices.subMenu = true;
	}
	
	// Executes when this action is chosen in a menu
	public boolean execute(){
		// Get the renderer
		Render render = Application.GetRenderer();
		
		// Form for getting the value of the attribute we are searching for
		Form form = new Form(
			"str",
			"Enter " + this.attribute + " or part of the " + this.attribute
		);
		
		// Get the user response to the form
		String response[] = form.response();
		
		// Instantiate the searcher
		Search searcher = new Search();
		
		// Search for books which match the value of the attribute we are searching for, and store them in an array
		Book books[] = searcher.searchBooks(
			this.attribute, 
			response[0], 
			true
		);
		
		// Get the number of products we found, and render
		int count = books.length;
		System.out.println(
			count +
			" book" +
			(count != 1 ? "s" : "") +
			" found!"
		);
		
		// Iterate through the set of books, in increments of two
		for(int i = 0; i < books.length; i+=2){
			boolean continueBrowse;
			
			// Output the heading for the increment set we are on
			render.heading("Books " + (i + 1) + "-" + (i + 2));
			
			// Render the two books, checking if we are on the last record or the last two records
			for(
				int j = i; 
				j < ( i + 1 == books.length ? i + 1 : i + 2); 
				j++
			){
				// Instantiates the table holding the book data
				Table bookData = new Table(books[j]);
				
				// Render the bookdata table
				bookData.render();
				Render.NewLine();
			}
			
			// If we aren't on the last two iterations, then render the browse menu
			if(i + 1 != books.length && i + 2 != books.length){
				char choice = BrowseChoices.getMenuChoice();
				
				// Determine if we are continuing to browse based on the action taken
				continueBrowse = BrowseChoices.action(choice);
			
				if(!continueBrowse){
					return false;
				}
			}
		}
		
		// If we're at the end of the book list, and there were books in the list, render an info message
		// letting the user know
		if(books.length > 0){
			render.notice("Info", "End of search results.");
		}
		
		// Get a choice from the final search menu, and take an action based on that choice, returning the result to
		// menu this action was called from
		char choice = SearchChoices.getMenuChoice();
		return SearchChoices.action(choice);
	}
	
}
