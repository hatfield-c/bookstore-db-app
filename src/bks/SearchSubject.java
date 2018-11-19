package bks;

public class SearchSubject implements MenuAction {

	private String subject;
	
	SearchSubject(String subject){
		this.subject = subject;
	}
	
	public boolean execute(){
		Render render = Application.GetRenderer();
		Search searcher = new Search();
		Book books[] = searcher.searchBooks("subject", this.subject, false);
		
		int count = books.length;
		System.out.println(
			count +
			" book" +
			(count != 1 ? "s" : "") +
			" found!"
		);
		
		Menu browseMenu = SearchAction.BrowseChoices;
		Menu searchMenu = SearchAction.SearchChoices;
		browseMenu.subMenu = true;
		searchMenu.subMenu = true;
		
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
				char choice = browseMenu.getMenuChoice();
				continueBrowse = browseMenu.action(choice);
			
				if(!continueBrowse){
					return false;
				}
			}
		}
		
		if(books.length > 0){
			render.notice("Info", "End of search results.");
		}
		
		char choice = searchMenu.getMenuChoice();
		return searchMenu.action(choice);
	}
	
}
