package bks;
import java.sql.ResultSet;
import java.sql.SQLException;

public class QueryData {
	
	private String data[];
	
	QueryData(int length){
		this.data = new String[length];
	}
	
	public void assign(ResultSet result) throws SQLException {
		for(int i = 0; i < this.data.length; i++){
			this.data[i] = result.getString(i + 1);
		}
	}
	
	public String[] getData(){
		return this.data;
	}

}
