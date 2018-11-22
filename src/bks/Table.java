package bks;

// structure to hold a two column table of data
public class Table {

	// Internal data for the table
	private String labels[];
	private String values[];
	
	// Instantiate an empty table
	Table(){
		this(new String[] {}, new String[] {});
	}
	
	// General table constructor - if the number of labels doesn't match the
	// number of values, then set this table to empty.
	Table(String labels[], String values[]){
		if(labels.length != values.length){
			this.labels = new String[] {};
			this.values = new String[] {};
		} else {
			this.labels = labels;
			this.values = values;
		}
	}
	
	// Build this table based on a book object supplied
	Table(Book book){
		this.labels = new String[] {
			"Author",
			"Title",
			"ISBN",
			"Price",
			"Subject"
		};
		
		this.values = new String[] {
			book.getAuthor(),
			book.getTitle(),
			book.getIsbn(),
			Double.toString(book.getPrice()),
			book.getSubject()
		};
	}
	
	
	// Render this table
	public void render(){
		Render render = Application.GetRenderer();
		
		for(int i = 0; i < this.labels.length; i++){
			render.notice(this.labels[i], this.values[i]);
		}
	}
	
}
