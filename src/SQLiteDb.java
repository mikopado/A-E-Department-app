
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

// Implementation of IDatabase for SQLite database. Create connection to database and implementing the execute update for INSERT, CREATE statements and execute query for SELECT
public class SQLiteDb implements IDatabase {
	
	private Connection conn;
	private Statement statem;
	private String dbName;
	
	
	public SQLiteDb(String dbName){
		
		this.dbName = dbName;	
		
		
	}
	
	public String getDbName() {
		return dbName;
	}
	
	
	//Create database connection
	@Override
	public Connection connect() {
		
		try{
			
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:" + getDbName() + ".sqlite");
			
			
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		
		return conn;
	}
	
	
	//Can execute INSERT, UPDATE AND DELETE statements
	@Override
	public void executeUpdate(String query) throws SQLException{
		
		this.statem = conn.createStatement();
		statem.executeUpdate(query);
		
	}
	
	//For select statement
	public ResultSet executeQuery(String query) throws SQLException {
		
		ResultSet rs = null;		
			
		this.statem = conn.createStatement();
		rs = statem.executeQuery(query);
		
		return rs;
	}
	
	
		
		
	


}
