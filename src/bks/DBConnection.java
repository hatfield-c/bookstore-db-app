package bks;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class DBConnection {
	private String url;
	private String user;
	private String pass;
	
	DBConnection(String url, String user, String pass){
		this.url = url;
		this.user = user;
		this.pass = pass; 
	}
	
	public QueryData[] read(String table, String columns[], Condition conditions) throws SQLException{
		if(columns == null || columns.length < 1){
			throw new SQLException("Not enough columns");
		}
		
		String where = (conditions == null ? "" : this.whereBuilder(conditions) );
		String qry = "SELECT " + this.selectBuilder(columns) + " FROM " + table + where;
		
		ResultSet result;
		PreparedStatement state;
		QueryData data[];
		int count = 0;
		
		try (Connection connection = DriverManager.getConnection(this.url, this.user, this.pass)){
			state = connection.prepareStatement(qry, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			if(conditions != null && conditions.getSize() > 0){
				String wildcard = conditions.wildcard ? "%" : "";
				for(int i = 0; i < conditions.getSize(); i++){
					state.setString( i + 1, wildcard + conditions.value(i) + wildcard);
				}
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
	
	public boolean update(String table, String columns[], String values[], Condition conditions) throws SQLException{
		String set = this.setBuilder(columns, values);
		
		if(set == null || conditions == null || conditions.getSize() < 1)
			return false;
		
		String sql = "UPDATE " + table + " SET " + set + this.whereBuilder(conditions); 
		
		try (Connection connection = DriverManager.getConnection(this.url, this.user, this.pass)){
			PreparedStatement state = connection.prepareStatement(sql);

			for(int i = 1; i < values.length + 1; i++){
				state.setString(i, values[i - 1]);
			}

			if(conditions != null && conditions.getSize() > 0){
				for(int i = 0; i < conditions.getSize(); i++){
					state.setString(i + values.length + 1, conditions.value(i));
				}
			}
			
			try{
				state.executeUpdate();
				return true;
			} catch(SQLException e){
				e.printStackTrace();
				return false;
			}
		}
	}
	
	public boolean delete(String table, Condition conditions) throws SQLException{
		
		if(conditions == null || conditions.getSize() < 1){
			return false;
		}
		
		String sql = "DELETE FROM " + table + this.whereBuilder(conditions); 
		
		try (Connection connection = DriverManager.getConnection(this.url, this.user, this.pass)){
			PreparedStatement state = connection.prepareStatement(sql);
			
			for(int i = 0; i < conditions.getSize(); i++){
				state.setString(i + 1, conditions.value(i));
			}
			
			try{
				state.executeUpdate();
				return true;
			} catch(SQLException e){
				return false;
			}
		}
		
	}
	
	private String whereBuilder(Condition conditions){
		String str = "";
		String operator = "=?";
		
		if(conditions.getSize() < 1)
			return str;
		
		if(conditions.wildcard){
			operator = " LIKE ?";
		}
			
		str = str + " WHERE ";
		
		for(int i = 0 ; i < conditions.getSize(); i++){
			str = str + conditions.field(i) + operator;
			
			if(i != conditions.getSize() - 1)
				str = str + " AND ";
		}
		
		return str;
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
