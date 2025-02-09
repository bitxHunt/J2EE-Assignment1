/***********************************************************
* Name: Thiha Swan Htet, Harry
* Class: DIT/FT/2B/01
* Admin No: P2336671
************************************************************/

package models.status;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import util.DB;

public class StatusDAO {
	// Seed Status Data
	public void seedData(Status status) throws SQLException {
		Connection conn = DB.connect();

		try {
			String sqlStr = "CALL seed_status(?);";
			CallableStatement stmt = conn.prepareCall(sqlStr);

			stmt.setString(1, status.getName());

			stmt.execute();

		} catch (Exception e) {
			System.out.println("Error Seeding Status Data.");
			e.printStackTrace();
		} finally {
			conn.close();
		}
	}
}
