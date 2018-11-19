package bks;

public class Table {

	private String labels[];
	private String values[];
	
	Table(){
		this(new String[] {}, new String[] {});
	}
	
	Table(String labels[], String values[]){
		if(labels.length != values.length){
			this.labels = new String[] {};
			this.values = new String[] {};
		} else {
			this.labels = labels;
			this.values = values;
		}
	}
	
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
	
	public void render(){
		Render render = Application.GetRenderer();
		
		for(int i = 0; i < this.labels.length; i++){
			render.notice(this.labels[i], this.values[i]);
		}
	}
	
}
