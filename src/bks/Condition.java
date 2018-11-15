package bks;

public class Condition {

	private String fields[];
	private String values[];
	private int size;
	
	Condition(){
		this(new String[] { }, new String[] { });
	}
	
	Condition(String fields[], String values[]){
		if(fields.length != values.length){
			this.fields = new String[] {};
			this.values = new String[] {};
			this.size = 0;
		} else {
			this.fields = fields;
			this.values = values;
			this.size = this.fields.length;
		}
	}
	
	public String field(int i){
		if(i > this.size || this.size == 0)
			return "";
		
		return this.fields[i];
	}
	
	public String value(int i){
		if(i > this.size || this.size == 0)
			return "";
		
		return this.values[i];
	}
	
	public int getSize(){
		return this.size;
	}
	
}
