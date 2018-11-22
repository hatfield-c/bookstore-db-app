package bks;

// Data structure to hold product data. Serves as a quick abstraction, used in
// other data structures
public class Product {

	// Internal data for the product
	private String isbn;
	private int qty;
	
	// An empty product - debugging
	Product(){
		this("", 0);
	}
	
	// Basic constructor for the product
	Product(String isbn, int qty){
		this.isbn = isbn;
		this.qty = qty;
	}
	
	/***
	 *    Getters and setters for the book objects
	 */
	
	public String getIsbn(){
		return this.isbn;
	}
	
	public int getQty(){
		return this.qty;
	}
	
}
