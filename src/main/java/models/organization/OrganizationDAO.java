package models.organization;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import util.DB;

public class OrganizationDAO {
	// Seed Organization Data
	public void seedData(Organization organization) throws SQLException {
		Connection conn = DB.connect();
		
		try {
			String sqlStr = "CALL seed_organization(?, ?, ?, ?);";
			CallableStatement stmt = conn.prepareCall(sqlStr);

			stmt.setString(1, organization.getName());
			stmt.setString(2, organization.getAccessKey());
			stmt.setString(3, organization.getSecretKey());
			stmt.setInt(4, organization.getUser().getId());

			stmt.execute();

		} catch (Exception e) {
			System.out.println("Error Seeding User Data.");
			e.printStackTrace();
		} finally {
			conn.close();
		}
	}
}
