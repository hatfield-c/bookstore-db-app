package bks;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

// Abstraction class to handle hard SQL querying with the database
public class DBConnection {
	// Internal data for connecting to the DB
	private String url;
	private String user;
	private String pass;
	
	DBConnection(String url, String user, String pass){
		this.url = url;
		this.user = user;
		this.pass = pass; 
	}
	
	// Reads data from the database, and returns the result as an array of QueryData objects
	public QueryData[] read(String table, String columns[], Condition conditions) throws SQLException{
		// Ensure the column data is processable
		if(columns == null || columns.length < 1){
			throw new SQLException("Not enough columns");
		}
		
		// Build the where condition
		String where = (conditions == null ? "" : this.whereBuilder(conditions) );
		
		// Build the order condition
		String order = "";
		if(conditions != null && !conditions.getOrder().equals("")){
			order = " " + conditions.getOrder();
		}
		
		// Build the total query
		String qry = "SELECT " + this.selectBuilder(columns) + " FROM " + table + where + order;
		
		// Prepare the query data
		ResultSet result;
		PreparedStatement state;
		QueryData data[];
		int count = 0;
		
		// Get a connection to the database
		try (Connection connection = DriverManager.getConnection(this.url, this.user, this.pass)){
			// Get the sql statement from the connection, based on the query
			state = connection.prepareStatement(qry, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);

			// If there are conditions, then set their values into the statement
			if(conditions != null && conditions.getSize() > 0){
				String wildcard = conditions.wildcard ? "%" : "";
				for(int i = 0; i < conditions.getSize(); i++){
					state.setString( i + 1, wildcard + conditions.value(i) + wildcard);
				}
			}
			
			// Execute the query and get its raw data set
			result = state.executeQuery();
			count = this.resultSize(result);
			
			
			// Map the raw result set to the QueryData structure
			data = new QueryData[count];
			for(int i = 0; i < count; i++){
				result.next();
				data[i] = new QueryData(columns.length);
				data[i].assign(result);
			}
		}
		
		// Return the dataset
		return data;
	}
	
	// Inserts data into the given tables - values must have an entry for every column in the table
	public boolean insert(String table, String values[]) throws SQLException{
		// Build the SQL string
		String sql = "INSERT INTO "  + table + " VALUES " + this.valuesBuilder(values);
		
		// Check for valid values
		if(values == null || values.length < 1){
			throw new SQLException("Must supply valid values");
		}
		
		// Get a connection to the database
		try (Connection connection = DriverManager.getConnection(this.url, this.user, this.pass)){
			// Get the statement object
			PreparedStatement state = connection.prepareStatement(sql);

			// Set the values into the statement object
			for(int i = 1; i < values.length + 1; i++){
				state.setString(i, values[i - 1]);
			}
			
			try{
				// Execute the statement
				state.executeUpdate();
				return true;
			} catch(SQLException e){
				// If there was a problem, return false to let the application know we failed
				return false;
			}
		}
	}
	
	// Updates the table, setting the columns equal to the values given, according to a condition
	public boolean update(String table, String columns[], String values[], Condition conditions) throws SQLException{
		// Build the "set" statement for the string
		String set = this.setBuilder(columns, values);
		
		// If there are any unrecoverable issues, return false to signify failure
		if(set == null || conditions == null || conditions.getSize() < 1)
			return false;
		
		// Build the sql string
		String sql = "UPDATE " + table + " SET " + set + this.whereBuilder(conditions); 
		
		// Get a connection to the database
		try (Connection connection = DriverManager.getConnection(this.url, this.user, this.pass)){
			// Get the statement object
			PreparedStatement state = connection.prepareStatement(sql);

			// Set the values into the statement
			for(int i = 1; i < values.length + 1; i++){
				state.setString(i, values[i - 1]);
			}

			// If there are valid conditions, set them into the statement object
			if(conditions != null && conditions.getSize() > 0){
				for(int i = 0; i < conditions.getSize(); i++){
					state.setString(i + values.length + 1, conditions.value(i));
				}
			}

			try{
				// Execute the statement
				state.executeUpdate();
				return true;
			} catch(SQLException e){
				// If there was a problem, return false to let the application know
				return false;
			}
		}
	}
	
	// Deletes records from the table according to the conditions given
	public boolean delete(String table, Condition conditions) throws SQLException{
		
		// If there are no valid conditions, return false, to prevent deleting the whole database
		if(conditions == null || conditions.getSize() < 1){
			return false;
		}
		
		// Build the sql statement
		String sql = "DELETE FROM " + table + this.whereBuilder(conditions); 
		
		// Get a connection to the database
		try (Connection connection = DriverManager.getConnection(this.url, this.user, this.pass)){
			// Get the statement object
			PreparedStatement state = connection.prepareStatement(sql);
			
			// Set the condition values into the statement
			for(int i = 0; i < conditions.getSize(); i++){
				state.setString(i + 1, conditions.value(i));
			}
			
			try{
				// Execute the statement object
				state.executeUpdate();
				return true;
			} catch(SQLException e){
				// If there was a problem, return false to let the application know
				return false;
			}
		}
		
	}
	
	// Builds the where conditions for sql statements
	private String whereBuilder(Condition conditions){
		// Init the returned where string, and where operator
		String str = "";
		String operator = "=?";
		
		// If no valid conditions, return an empty string
		if(conditions.getSize() < 1)
			return str;
		
		// If wildcard, then change the operator for pattern matching
		if(conditions.wildcard){
			operator = " LIKE ?";
		}
		
		// Add the where statement
		str = str + " WHERE ";
		
		// Add the condition fields to the string, linked by an AND statement
		for(int i = 0 ; i < conditions.getSize(); i++){
			str = str + conditions.field(i) + operator;
			
			if(i != conditions.getSize() - 1)
				str = str + " AND ";
		}
		
		// Return the built where string
		return str;
	}
	
	// Build the set statements for sql statements
	private String setBuilder(String columns[], String values[]){
		String str = "";
		
		// If we don't have any valid values to work with, return null
		if(columns.length != values.length || columns.length < 1){
			return null;
		}
		
		// Add each column to set into the statement
		for(int i = 0; i < columns.length; i++){
			str = str + columns[i] + " = ?,";
		}
		
		// Remove the last comma off the end of the statement
		str = str.substring(0, str.length() - 1);
		
		// Return the built string
		return str;
	}
	
	// Build the values string for sql statements
	private String valuesBuilder(String values[]){
		String str;
		
		// If the values aren't valid, then return an empty set of values
		if(values == null || values.length == 0){
			str = "()";
		} else {
			str ="(";
			
			// Add the prepared statement value placeholder for each value in the set
			for(int i = 0; i < values.length; i++){
				str = str + "?,";
			}
			
			// Remove the last comma off of the values list
			str = str.substring(0, str.length() - 1);
			str = str + ")";
		}

		// Return the built list of values
		return str;
	}
	
	// Builds the SELECT statement for sql operations
	private String selectBuilder(String columns[]){
		String str;
		
		str = "";
			
		// Build the list of columns for the select statement
		for(int i = 0; i < columns.length; i++){
			str = str + columns[i] + ",";
		}
			
		// Remove the comma off the end
		str = str.substring(0, str.length() - 1);
		
		// Return the built string
		return str;
	}
	
	// Gets the size of the result
	private int resultSize(ResultSet result) throws SQLException{
		int size = 0;
		
		// Jump to the last result, and get its row number
		if(result.last()){
			size = result.getRow();
			result.beforeFirst();
		}
		
		return size;
	}
	
}
