package bks;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Main {
	
	public static void main(String[] args){
		// Instantiate the renderer and the database connection to be used by this application instance
		Render render = new Render();
		DBConnection db = new DBConnection("jdbc:oracle:thin:@localhost:1521:bookstore", "system", "1o11o11o1");

		// Intantiate the application, with the renderer and the database
		Application app = new Application(render, db);
		
		// Run the application
		app.run();
	}
	
}
