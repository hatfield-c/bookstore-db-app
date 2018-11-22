package bks;

// Data structure for SQL statement conditions
// Maps fields to values in where statements, and holds ordering info as well
// as a wild card flag for pattern matching
public class Condition {

	// Internal data
	private String fields[];
	private String values[];
	private String order;
	private int size;
	public boolean wildcard;
	public boolean unique = false;
	
	// Empty condition - useful for more strict definitions of conditions
	Condition(){
		this(new String[] { }, new String[] { });
	}
	
	// Constructor for a basic where condition - with fields matching to their associated values
	Condition(String fields[], String values[]){
		this(fields, values, false);
	}
	
	// Constructor for advanced where condition, which includes support for a wild card for pattern matching
	Condition(String fields[], String values[], boolean wildcard){
		// If there aren't enough fields to match the values, then zero the condition out
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
	
	// Returns the field at position i
	public String field(int i){
		if(i > this.size || this.size == 0)
			return "";
		
		return this.fields[i];
	}
	
	// Returns the value at position i
	public String value(int i){
		if(i > this.size || this.size == 0)
			return "";
		
		return this.values[i];
	}
	
	// Gets the size (number of fields/values) in the condition
	public int getSize(){
		return this.size;
	}
	
	// Get the ordering SQL option
	public String getOrder(){
		return this.order;
	}
	
	// Set the ordering SQL option
	public void setOrder(String order){
		this.order = order;
	}
	
}
