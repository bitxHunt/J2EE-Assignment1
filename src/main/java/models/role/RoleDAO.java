/***********************************************************
 * Name: Soe Zaw Aung, Scott
 * Class: DIT/FT/2B/01
 * Admin No: P2340474
 * Description: Model class to store database operations related to role
 ************************************************************/
package models.role;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import models.bundle.Bundle;
import util.DB;

public class RoleDAO {
	// Retrieves all roles from the database
	public ArrayList<Role> getAllRoles() throws SQLException {
		ArrayList<Role> roles = new ArrayList<>();
		Connection conn = null;

		try {
			conn = DB.connect();
			String sql = "SELECT * FROM role";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				Role role = new Role();
				role.setRoleId(rs.getInt("id"));
				role.setRoleName(rs.getString("name"));
				roles.add(role);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (conn != null) {
				conn.close();
			}
		}

		return roles;
	}


	// Seed the overall data from the csv file
	public void seedData(Role role) throws SQLException {
		Connection conn = DB.connect();

		try {
			String sqlStr = "CALL seed_role(?);";
			CallableStatement stmt = conn.prepareCall(sqlStr);

			stmt.setString(1, role.getRoleName());

			stmt.execute();

		} catch (Exception e) {
			System.out.println("Error Seeding Role Data.");
			e.printStackTrace();
		} finally {
			conn.close();
		}
	}
}