package bks;

public class SearchAction implements MenuAction {

	private Menu searchChoices = new Menu(
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
	
	private Menu browseChoices = new Menu(
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
	
	private String action;
	
	SearchAction(String action){
		this.action = action;
		this.searchChoices.subMenu = true;
		this.browseChoices.subMenu = true;
	}
	
	public boolean execute(){
		Render render = Application.GetRenderer();
		Form form = new Form(
			"str",
			"Enter " + this.action + " or part of the " + this.action
		);
		
		String response[] = form.response();
		Search searcher = new Search();
		Book books[] = searcher.searchBooks(
			this.action, 
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
				Table bookData = new Table(
					new String[] {
						"Author",
						"Title",
						"ISBN",
						"Price",
						"Subject"
					},
					new String[] {
						books[j].getAuthor(),
						books[j].getTitle(),
						books[j].getIsbn(),
						Double.toString(books[j].getPrice()),
						books[j].getSubject()
					}
				);
			
				bookData.render();
				Render.NewLine();
				
			}
			
			
			if(i + 1 != books.length && i + 2 != books.length){
				char choice = this.browseChoices.getMenuChoice();
				continueBrowse = this.browseChoices.action(choice);
			
				if(!continueBrowse){
					return false;
				}
			}
		}
		
		if(books.length > 0){
			render.notice("Info", "End of search results.");
		}
		
		char choice = this.searchChoices.getMenuChoice();
		return this.searchChoices.action(choice);
	}
	
}
