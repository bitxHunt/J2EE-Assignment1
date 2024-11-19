package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB {
	public static Connection connect() throws SQLException {

		try {
			// Get database credentials from DatabaseConfig class
			var jdbcUrl = databaseConfig.getDbUrl();
			var user = databaseConfig.getDbUsername();
			var password = databaseConfig.getDbPassword();
			var dbClass = databaseConfig.getDbClass();

			// Connect the driver
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