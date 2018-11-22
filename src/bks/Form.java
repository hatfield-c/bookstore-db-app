package bks;

import java.util.Scanner;

// Handles displaying and getting form data from the user
public class Form {

	// Internal data for the form - the types for each entry, and the prompt for each entry
	private String types[];
	private String prompts[];
	
	// Special constructor for a single prompt form
	Form(String type, String prompt){
		this(new String[] {type}, new String[] {prompt});
	}
	
	// Normal constructor for a form with multiple prompts
	Form(String types[], String prompts[]){
		this.types = types;
		this.prompts = prompts;
		
		// If the number of types doesn't match the number of prompts, set them both to null
		if(this.types.length != this.prompts.length){
			this.types = null;
			this.prompts = null;
		}
	}
	
	// Method to get responses from the user for the form - returns the responses as an array of strings
	public String[] response(){
		// Initialize necessary data structures for getting the response
		Render render = Application.GetRenderer();
		Scanner scanner = new Scanner(System.in);
		String response[] = new String[this.prompts.length];
		
		// For each prompt, we get a response
		for(int i = 0; i < prompts.length; i++){
			String buff;
			// Render the prompts
			render.prompt(prompts[i]);
			
			// Get a user response
			buff = scanner.nextLine();
			buff = buff.trim();
			
			// If an empty response, reset and ask again
			if(buff.length() < 1){
				i--;
				continue;
			}
			
			// If the response isn't valid, output an error, reset, and ask again
			if(!this.validate(types[i], buff)){
				render.error("Invalid form entry, must be of type '" + types[i] + "'");
				i--;
				continue;
			}
			
			// If its valid, copy the given response into the array, and proceed to the next prompt
			response[i] = buff;
		}
		
		// Print a new line, and return the response
		Render.NewLine();
		
		return response;
	}
	
	// Validates a response, ensuring it is the proper response type
	private boolean validate(String type, String str){
		// If the string being validated is null, the response is invalid
		if(str == null)
			return false;
		
		// check the string based on its type
		switch(type){
		case "str":
			// If a string, if its not a full string, it isn't valid
			if(str.length() < 1)
				break;
			
			return true;
			
		case "char":
			// If char, its length should equal exactly 1
			if(str.length() != 1)
				break;
			
			return true;
			
		case "int":
			// Check if the string matches an integer via regular expression
			if(str.matches("\\d+"))
				return true;
			
			break;
			
		case "float":
			// Check if the string matches a float via regular expression
			if(str.matches("([0-9]*)\\.([0-9]*)") || str.matches("\\d+"))
				return true;
			
			break;
		}
		
		return false;
	}
	
}
