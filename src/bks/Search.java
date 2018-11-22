package bks;

// Class for searching the products in the data base
public class Search {
	
	public Book[] searchBooks(String constraint, String value, boolean wildcard){
		// Get the db from the application
		DBConnection db = Application.GetDB();
		Book books[];
		
		try{
			// Read the isbn for books who match the condition given
			QueryData data[] = db.read(
				"books", 
				new String[] {
					"isbn"
				}, 
				new Condition(new String[] { constraint }, new String[] { value }, wildcard)
			);
			
			// Map the books read into the list of books 
			books = new Book[data.length];
			
			for(int i = 0; i < data.length; i++){
				Product buffer = new Product(data[i].getData()[0], 0);
				books[i] = new Book(buffer);
			}
		} catch(Exception e){
			// If there was a problem, set the list of books to an empty list
			books = new Book[0];
		}
		
		// Return the list of the books
		return books;
	}

}
