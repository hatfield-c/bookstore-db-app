package bks;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import oracle.jdbc.pool.OracleDataSource;

public class DBConnection {
	private String url;
	private String user;
	private String pass;
	
	public static String[] BooksCol = {
			"isbn",
			"author",
			"title",
			"price",
			"subject"
	};
	
	public static String[] MembersCol = {
			"fname",
			"lname",
			"address",
			"city",
			"state",
			"zip",
			"phone",
			"email",
			"userid",
			"password",
			"creditcardtype",
			"creditcardnumber"
	};
	
	DBConnection(String url, String user, String pass){
		this.url = url;
		this.user = user;
		this.pass = pass; 
	}
	
	public QueryData[] read(String table, String columns[], Condition condition) throws SQLException{
		if(columns == null || columns.length < 1){
			throw new SQLException("Not enough columns");
		}
		
		String where = (condition == null ? "" : " WHERE " + condition.con[0] + " like ?" ); 
		String qry = "SELECT " + this.selectBuilder(columns) + " FROM " + table + where;
		
		ResultSet result;
		PreparedStatement state;
		QueryData data[];
		int count = 0;
		
		try (Connection connection = DriverManager.getConnection(this.url, this.user, this.pass)){
			state = connection.prepareStatement(qry, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			if(condition != null){
				state.setString(1, condition.con[1]);
			}
			
			result = state.executeQuery();
			count = this.resultSize(result);
			
			data = new QueryData[count];

			for(int i = 0; i < count; i++){
				result.next();
				data[i] = new QueryData(columns.length);
				data[i].assign(result);
			}
		}
		
		return data;
	}
	
	public boolean insert(String table, String values[]) throws SQLException{
		String sql = "INSERT INTO "  + table + " VALUES " + this.valuesBuilder(values);
		
		if(values == null || values.length < 1){
			throw new SQLException("Must supply valid values");
		}
		
		try (Connection connection = DriverManager.getConnection(this.url, this.user, this.pass)){
			PreparedStatement state = connection.prepareStatement(sql);

			for(int i = 1; i < values.length + 1; i++){
				state.setString(i, values[i - 1]);
			}
			
			try{
				state.executeUpdate();
				return true;
			} catch(SQLException e){
				return false;
			}
		}
	}
	
	public boolean update(String table, String columns[], String values[], String constraint[]) throws SQLException{
		String set = this.setBuilder(columns, values);
		
		if(set == null || constraint == null || constraint.length != 2)
			return false;
		
		String sql = "UPDATE " + table + " SET " + set + " WHERE " + constraint[0] + " = ?"; 
		
		try (Connection connection = DriverManager.getConnection(this.url, this.user, this.pass)){
			PreparedStatement state = connection.prepareStatement(sql);

			int i;
			for(i = 1; i < values.length + 1; i++){
				state.setString(i, values[i - 1]);
			}
			
			state.setString(i, constraint[1]);
			
			try{
				state.executeUpdate();
				return true;
			} catch(SQLException e){
				return false;
			}
		}
	}
	
	public boolean delete(String table, String constraint[]) throws SQLException{
		
		if(constraint == null || constraint.length != 2){
			return false;
		}
		
		String sql = "DELETE FROM " + table + " WHERE " + constraint[0] + " = ?"; 
		
		try (Connection connection = DriverManager.getConnection(this.url, this.user, this.pass)){
			PreparedStatement state = connection.prepareStatement(sql);
			
			state.setString(1, constraint[1]);
			
			try{
				state.executeUpdate();
				return true;
			} catch(SQLException e){
				return false;
			}
		}
		
	}
	
	private String setBuilder(String columns[], String values[]){
		String str = "";
		
		if(columns.length != values.length || columns.length < 1){
			return null;
		}
		
		for(int i = 0; i < columns.length; i++){
			str = str + columns[i] + " = ?,";
		}
		
		str = str.substring(0, str.length() - 1);
		
		return str;
	}
	
	private String valuesBuilder(String values[]){
		String str;
		
		if(values == null || values.length == 0){
			str = "()";
		} else {
			str ="(";
			
			for(int i = 0; i < values.length; i++){
				str = str + "?,";
			}
			
			str = str.substring(0, str.length() - 1);
			str = str + ")";
		}

		return str;
	}
	
	private String selectBuilder(String columns[]){
		String str;
		
		str = "";
			
		for(int i = 0; i < columns.length; i++){
			str = str + columns[i] + ",";
		}
			
		str = str.substring(0, str.length() - 1);
		
		return str;
	}
	
	private int resultSize(ResultSet result) throws SQLException{
		int size = 0;
		if(result.last()){
			size = result.getRow();
			result.beforeFirst();
		}
		
		return size;
	}
	
}
