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
	
	public void render(){
		Render render = Application.GetRenderer();
		
		for(int i = 0; i < this.labels.length; i++){
			render.notice(this.labels[i], this.values[i]);
		}
	}
	
}
