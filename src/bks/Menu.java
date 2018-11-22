package bks;

import java.util.Scanner;

// Data structure to handle the various menu and submenus used throughout the application
public class Menu {
	
	// Internal data for the menu
	private String prompt;
	private char options[];
	private String titles[];
	private MenuAction actions[];
	boolean subMenu;
	
	// Each menu must be instantiated with a prompt, the possible options, the titles for each option, and the action
	// to be taken for each option
	Menu(String prompt, char options[], String titles[], MenuAction actions[]){
		this.prompt = prompt;
		this.options = options;
		this.titles = titles;
		this.actions = actions;
		this.subMenu = false;
		
		// All options must have a title and all options must have an action - otherwise null out
		// the entire object
		if(this.options.length != this.titles.length || this.options.length != this.actions.length){
			this.prompt = "[ERROR]: Invalid menu";
			this.options = new char[1];
			this.titles = new String[1];
			this.actions = null;
			
			this.options[0] = 255;
			this.titles[0] = "";
		}
	}
	
	// Takes an action based on the choice given - if the choice isn't in the menu,
	// then returns false. Otherwise, returns the result of the action taken
	public boolean action(char choice){
		for(int i = 0; i < this.options.length; i++){
			if(choice == this.options[i]){
				return actions[i].execute();
			}
		}
		
		return false;
	}
	
	// Checks to see if the option given is actually an option in this menu
	boolean isOption(char option){
		for(int i = 0; i < options.length; i++){
			if(option == this.options[i]){
				return true;
			}
		}
		
		return false;
	}
	
	// Renders the menu, and returns a choice from the menu chosen by the user
	public char getMenuChoice(){
		Render render = Application.GetRenderer();
		
		// Uses a single entry form with the prompt to get and partially validate the users choice
		Form menuForm = new Form("char" , this.prompt);
		char choice;
		
		// While we haven't gotten a valid choice, keep trying to get one
		while(true){
			String buffer;
			
			// Render the menu
			render.Menu(this);
			
			// Get the first response given by the user
			buffer = menuForm.response()[0];
			
			// Truncate the response if its longer than a character
			choice = buffer.charAt(0);
			
			// If the response is a valid option, then break from the loop and return the response
			if(this.isOption(choice)){
				break;
			}
			
			// If it is not a valid response, render an error and try again
			render.error("Invalid menu entry. Please try again.");
		}
		
		return choice;
	}
	
	/***
	 *    Getters and setters for the menu
	 */
	
	void setPrompt(String prompt){
		this.prompt = prompt;
	}
	
	String getPrompt(){
		return this.prompt;
	}
	
	char[] getOptions(){
		return this.options;
	}
	
	String[] getTitles(){
		return this.titles;
	}
	
	boolean isSubmenu(){
		return this.subMenu;
	}

}
