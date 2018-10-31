package bks;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Main {
	
	public static void main(String[] args){
		Render render = new Render();
		DBConnection db = new DBConnection("jdbc:oracle:thin:@localhost:1521:bookstore", "system", "1o11o11o1");

		Application app = new Application(render, db);
		app.run();
	}
	
}
