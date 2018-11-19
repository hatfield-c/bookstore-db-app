package bks;

public class Search {
	
	public Book[] searchBooks(String constraint, String value, boolean wildcard){
		DBConnection db = Application.GetDB();
		Book books[];
		
		try{
			QueryData data[] = db.read(
				"books", 
				new String[] {
					"isbn"
				}, 
				new Condition(new String[] { constraint }, new String[] { value }, wildcard)
			);
			
			books = new Book[data.length];
			
			for(int i = 0; i < data.length; i++){
				Product buffer = new Product(data[i].getData()[0], 0);
				books[i] = new Book(buffer);
			}
		} catch(Exception e){
			books = new Book[0];
		}
		
		return books;
	}

}
