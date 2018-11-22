package bks;

// Data object to hold book data
public class Book {

	// Internal book data
	private String isbn;
	private String author;
	private String title;
	private String subject;
	private double price;
	private int qty;
	
	// A book is instantiated from a more primitive product object
	Book(Product product){
		this.isbn = product.getIsbn();
		this.qty = product.getQty();
		
		// Get a local instance of the db
		DBConnection db = Application.GetDB();
		
		try{
			// Try to read book referenced by the product from the db
			QueryData data[] = db.read(
				"books", 
				new String[] { "author", "title", "price", "subject" }, 
				new Condition(
					new String[] { "isbn" },
					new String[] { this.isbn }
				)	
			);
			
			// If no results, the book/product does not exist in the db
			if(data.length < 0)
				throw new Exception("Product not found.");
			
			// Map data from the result object to this book object
			String buffer[] = data[0].getData();
			this.author = buffer[0];
			this.title = buffer[1];
			this.price = Double.parseDouble(buffer[2]);
			this.subject = buffer[3];
			
		} catch(Exception e){
			// If there was an error during reading/mapping, set everything to empty and end instantiation
			this.isbn = "";
			this.author = "";
			this.title = "";
			this.subject = "";
			this.price = 0;
			this.qty = 0;
		}
	}
	
	/***
	 *    Getters and setters for the book objects
	 */
	
	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getTitle(){
		return this.title;
	}
	
	public void setTitle(String title){
		this.title = title;
	}
	
	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}
	
}
