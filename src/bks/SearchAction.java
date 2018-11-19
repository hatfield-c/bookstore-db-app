package bks;

public class SearchAction implements MenuAction {

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
	
	private String attribute;
	
	SearchAction(String action){
		this.attribute = action;
		SearchChoices.subMenu = true;
		BrowseChoices.subMenu = true;
	}
	
	public boolean execute(){
		Render render = Application.GetRenderer();
		Form form = new Form(
			"str",
			"Enter " + this.attribute + " or part of the " + this.attribute
		);
		
		String response[] = form.response();
		Search searcher = new Search();
		Book books[] = searcher.searchBooks(
			this.attribute, 
			response[0], 
			true
		);
		
		int count = books.length;
		System.out.println(
			count +
			" book" +
			(count != 1 ? "s" : "") +
			" found!"
		);
		
		for(int i = 0; i < books.length; i+=2){
			boolean continueBrowse;
			
			render.heading("Books " + (i + 1) + "-" + (i + 2));
			
			for(
				int j = i; 
				j < ( i + 1 == books.length ? i + 1 : i + 2); 
				j++
			){
				Table bookData = new Table(books[j]);
				bookData.render();
				Render.NewLine();
			}
			
			
			if(i + 1 != books.length && i + 2 != books.length){
				char choice = BrowseChoices.getMenuChoice();
				continueBrowse = BrowseChoices.action(choice);
			
				if(!continueBrowse){
					return false;
				}
			}
		}
		
		if(books.length > 0){
			render.notice("Info", "End of search results.");
		}
		
		char choice = SearchChoices.getMenuChoice();
		return SearchChoices.action(choice);
	}
	
}
