package bks;

public class Product {

	private String isbn;
	private int qty;
	
	Product(){
		this("", 0);
	}
	
	Product(String isbn, int qty){
		this.isbn = isbn;
		this.qty = qty;
	}
	
	public String getIsbn(){
		return this.isbn;
	}
	
	public int getQty(){
		return this.qty;
	}
	
}
