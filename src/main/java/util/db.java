/**
	Author: Thiha Swan Htet (p2336671)
	Date: 8/11/24
	Description: ST0510/JAD Week4 Submission
**/

package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class db {
	public static Connection connect() throws SQLException {

		try {
			// Get database credentials from databaseConfig class
			String jdbcUrl = databaseConfig.getDbUrl();
			String user = databaseConfig.getDbUsername();
			String password = databaseConfig.getDbPassword();
			String dbClass = databaseConfig.getDbClass();

			try {

				Class.forName(dbClass);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			// Open a connection
			return DriverManager.getConnection(jdbcUrl, user, password);

		} catch (SQLException e) {
			System.err.println(e.getMessage());
			return null;
		}
	}
}