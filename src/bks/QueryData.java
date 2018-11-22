package bks;
import java.sql.ResultSet;
import java.sql.SQLException;

// Data structure for results from SQL queries.
public class QueryData {
	
	// Data from the query - each entry represents a returned result from a column
	private String data[];
	
	// When the object is initialized, it needs to know how many columns it will store
	QueryData(int length){
		this.data = new String[length];
	}
	
	// Maps data from the raw SQL ResultSet object, into this QueryData object
	public void assign(ResultSet result) throws SQLException {
		for(int i = 0; i < this.data.length; i++){
			this.data[i] = result.getString(i + 1);
		}
	}
	
	// Returns the data from the query
	public String[] getData(){
		return this.data;
	}

}
