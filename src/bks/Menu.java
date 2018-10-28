package bks;

import java.util.Scanner;

public class Menu {
	
	private String prompt;
	private char options[];
	private String titles[];
	private MenuAction actions[];
	boolean subMenu;
	
	Menu(String prompt, char options[], String titles[], MenuAction actions[]){
		this.prompt = prompt;
		this.options = options;
		this.titles = titles;
		this.actions = actions;
		this.subMenu = false;
		
		if(this.options.length != this.titles.length || this.options.length != this.actions.length){
			this.prompt = "[ERROR]: Invalid menu";
			this.options = new char[1];
			this.titles = new String[1];
			this.actions = null;
			
			this.options[0] = 255;
			this.titles[0] = "";
		}
	}
	
	public boolean action(char choice){
		for(int i = 0; i < this.options.length; i++){
			if(choice == this.options[i]){
				return actions[i].execute();
			}
		}
		
		return false;
	}
	
	boolean isOption(char option){
		for(int i = 0; i < options.length; i++){
			if(option == this.options[i]){
				return true;
			}
		}
		
		return false;
	}
	
	public char getMenuChoice(){
		Render render = new Render();
		Form menuForm = new Form("char" , this.prompt);
		char choice;
		
		while(true){
			String buffer;
			render.Menu(this);
			
			buffer = menuForm.response()[0];
			choice = buffer.charAt(0);
			
			if(this.isOption(choice)){
				break;
			}
			
			render.error("Invalid menu entry. Please try again.");
		}
		
		return choice;
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
