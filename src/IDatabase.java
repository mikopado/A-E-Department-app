import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

// Interface for database. 
public interface IDatabase {
	
	Connection connect();
	void executeUpdate(String query) throws SQLException;
	ResultSet executeQuery(String query) throws SQLException;
}
