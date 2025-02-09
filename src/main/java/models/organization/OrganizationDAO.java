/***********************************************************
* Name: Soe Zaw Aung, Scott
* Class: DIT/FT/2B/01
* Admin No: P2340474
************************************************************/
package models.organization;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import util.DB;
import util.KeyPairGenerator;

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

	public int create(String name, int userId) throws SQLException {
		Connection conn = DB.connect();
		int rowsAffected = 0;

		try {
			String sql = "INSERT INTO organization (name, access_key, secret_key, owner_id) VALUES (?, ?, ?, ?)";
			PreparedStatement stmt = conn.prepareStatement(sql);

			String accessKey = KeyPairGenerator.generateAccessKey();
			String secretKey = KeyPairGenerator.generateSecretKey();

			stmt.setString(1, name);
			stmt.setString(2, accessKey);
			stmt.setString(3, secretKey);
			stmt.setInt(4, userId);

			rowsAffected = stmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println("Error creating organization: " + e.getMessage());
			throw e;
		} finally {
			conn.close();
		}

		return rowsAffected;
	}

	public ArrayList<Organization> getOrganizationsByUserId(int userId) throws SQLException {
		Connection conn = DB.connect();
		ArrayList<Organization> organizations = new ArrayList<>();

		try {
			String sql = "SELECT id, name, access_key, secret_key FROM organization WHERE owner_id = ? ORDER BY id;";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, userId);

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Organization organization = new Organization();
				organization.setId(rs.getInt("id"));
				organization.setName(rs.getString("name"));
				organization.setAccessKey(rs.getString("access_key"));
				organization.setSecretKey(rs.getString("secret_key"));
				organizations.add(organization);
			}

		} catch (SQLException e) {
			System.out.println("Error retrieving organizations: " + e.getMessage());
			throw e;
		} finally {
			conn.close();
		}

		return organizations;
	}
}
