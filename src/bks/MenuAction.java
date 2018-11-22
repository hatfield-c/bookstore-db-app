package bks;

// Interface for an action that a menu can take
public interface MenuAction {

	// All actions must be able to execute, and return the result of their execution
	public boolean execute();
	
}
