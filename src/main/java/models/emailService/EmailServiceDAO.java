/***********************************************************
* Name: Thiha Swan Htet, Harry
* Class: DIT/FT/2B/01
* Admin No: P2336671
************************************************************/

package models.emailService;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import util.DB;

public class EmailServiceDAO {
	// Seed Feature Data
	public void seedData(EmailService emailService) throws SQLException {
		Connection conn = DB.connect();

		try {
			String sqlStr = "CALL seed_email_service(?);";
			CallableStatement stmt = conn.prepareCall(sqlStr);

			stmt.setString(1, emailService.getName());

			stmt.execute();

		} catch (Exception e) {
			System.out.println("Error Seeding Email Service Data.");
			e.printStackTrace();
		} finally {
			conn.close();
		}
	}
}
