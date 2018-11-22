package bks;

// An empty action, which always returns false
public class Quit implements MenuAction {

	// Executes when this action is chosen in a menu
	public boolean execute(){
		return false;
	}
	
}
