package bks;

public class Book {

	private String isbn;
	private String author;
	private String title;
	private String subject;
	private double price;
	private int qty;
	
	Book(Product product){
		this.isbn = product.getIsbn();
		this.qty = product.getQty();
		
		DBConnection db = Application.GetDB();
		
		try{
			QueryData data[] = db.read(
				"books", 
				new String[] { "author", "title", "price", "subject" }, 
				new Condition(
					new String[] { "isbn" },
					new String[] { this.isbn }
				)	
			);
			
			if(data.length < 0)
				throw new Exception("Product not found.");
			
			String buffer[] = data[0].getData();
			this.author = buffer[0];
			this.title = buffer[1];
			this.price = Double.parseDouble(buffer[2]);
			this.subject = buffer[3];
			
		} catch(Exception e){
			this.isbn = "";
			this.author = "";
			this.title = "";
			this.subject = "";
			this.price = 0;
			this.qty = 0;
		}
	}
	
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
