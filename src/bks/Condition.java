package bks;

public class Condition {

	private String fields[];
	private String values[];
	private String order;
	private int size;
	public boolean wildcard;
	public boolean unique = false;
	
	Condition(){
		this(new String[] { }, new String[] { });
	}
	
	Condition(String fields[], String values[]){
		this(fields, values, false);
	}
	
	Condition(String fields[], String values[], boolean wildcard){
		if(fields.length != values.length){
			this.fields = new String[] {};
			this.values = new String[] {};
			this.size = 0;
			this.wildcard = false;
		} else {
			this.fields = fields;
			this.values = values;
			this.size = this.fields.length;
			this.wildcard = wildcard;
		}
		this.order = "";
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
	
	public String getOrder(){
		return this.order;
	}
	
	public void setOrder(String order){
		this.order = order;
	}
	
}
