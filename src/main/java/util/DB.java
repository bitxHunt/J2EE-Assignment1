/*
    Name: Thiha Swan Htet
    Class: DIT/FT/2B/01
    Admin No: p2336671
*/

package util;


import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DB {
	public static Connection connect() throws SQLException {

		try {
			// Get database credentials from databaseConfig class
			String jdbcUrl = SecretsConfig.getDbUrl();
			String user = SecretsConfig.getDbUsername();
			String password = SecretsConfig.getDbPassword();
			String dbClass = SecretsConfig.getDbClass();

			try {
				Class.forName(dbClass);
			} catch (Exception e) {
				e.printStackTrace();
			}

			System.out.println("Connecting to database...");

			// Open a connection
			return DriverManager.getConnection(jdbcUrl, user, password);

		} catch (SQLException e) {
			System.err.println(e.getMessage());
			return null;
		}
	}

	public void resetDB() throws SQLException {
		Connection conn = DB.connect();

		try {
			String sqlStr = "CALL reset_database();";
			CallableStatement stmt = conn.prepareCall(sqlStr);
			
			stmt.execute();

		} catch (Exception e) {
			System.out.println("Resetting Database Failed.");
			e.printStackTrace();
		} finally {
			System.out.println("Database Reset Successful.");
			conn.close();
		}
	}
	
	public void initTables() throws SQLException {
		Connection conn = DB.connect();
		
		try {
			String sqlStr = "CALL init_tables();";
			CallableStatement stmt = conn.prepareCall(sqlStr);
			
			stmt.execute();
		} catch (Exception e) {
			System.out.println("Initiating Tables Failed.");
			e.printStackTrace();
		} finally {
			System.out.println("Tables Initialization Successful.");
			conn.close();
		}
	}
}