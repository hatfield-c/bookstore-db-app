package bks;

public class Condition {

	public String con[];
	
	Condition(){
		this("", "");
	}
	
	Condition(String field, String value){
		this.con = new String[2];
		this.con[0] = field;
		this.con[1] = value;
	}
	
}
