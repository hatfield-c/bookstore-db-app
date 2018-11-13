package bks;

import java.util.Scanner;

public class Form {

	private String types[];
	private String prompts[];
	
	Form(String type, String prompt){
		this(new String[] {type}, new String[] {prompt});
	}
	
	Form(String types[], String prompts[]){
		this.types = types;
		this.prompts = prompts;
		
		if(this.types.length != this.prompts.length){
			this.types = null;
			this.prompts = null;
		}
	}
	
	public String[] response(){
		Render render = Application.GetRenderer();
		Scanner scanner = new Scanner(System.in);
		String response[] = new String[this.prompts.length];
		
		for(int i = 0; i < prompts.length; i++){
			String buff;
			render.prompt(prompts[i]);
			buff = scanner.nextLine();
			buff = buff.trim();
			
			if(buff.length() < 1){
				i--;
				continue;
			}
			
			if(!this.validate(types[i], buff)){
				render.error("Invalid form entry, must be of type '" + types[i] + "'");
				i--;
				continue;
			}
			
			response[i] = buff;
		}
		
		Render.NewLine();
		
		return response;
	}
	
	private boolean validate(String type, String str){
		if(str == null)
			return false;
		
		switch(type){
		case "str":
			if(str.length() < 1)
				break;
			
			return true;
			
		case "char":
			if(str.length() != 1)
				break;
			
			return true;
			
		case "int":
			if(str.matches("\\d+"))
				return true;
			
			break;
			
		case "float":
			if(str.matches("([0-9]*)\\.([0-9]*)") || str.matches("\\d+"))
				return true;
			
			break;
		}
		
		return false;
	}
	
}
