/***********************************************************
* Name: Thiha Swan Htet, Harry
* Class: DIT/FT/2B/01
* Admin No: P2336671
************************************************************/

package models.request;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import util.DB;

public class RequestDAO {
	
	// Get request by token
	public Request getStatusByToken(String token) throws SQLException {
		Connection conn = null;
		Request request = new Request();

		try {
			conn = DB.connect();
			String sqlStr = "SELECT is_active FROM request WHERE token = ?;";
			PreparedStatement pstmt = conn.prepareStatement(sqlStr);
			pstmt.setString(1, token);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				request.setStatus(rs.getBoolean("is_active"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return request;
	}

	// Set all tokens to inactive so that attackers cannot abuse token usage for
	// broken access control
	public void invalidateToken(boolean status, int emailServiceId, int userId) throws SQLException {
		Connection conn = null;

		try {
			conn = DB.connect();
			String sqlStr = "UPDATE request SET is_active = ? WHERE emailService_id = ? AND user_id = ?;";
			PreparedStatement pstmt = conn.prepareStatement(sqlStr);

			pstmt.setBoolean(1, status);
			pstmt.setInt(2, emailServiceId);
			pstmt.setInt(3, userId);

			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
	}

	// Insert records into request table (token)
	public void addRequest(String token, int userId, int emailServiceId) throws SQLException {
		Connection conn = null;

		try {
			conn = DB.connect();
			String sqlStr = "INSERT INTO request(token, user_id, emailService_id) VALUES(?, ?, ?);";
			PreparedStatement pstmt = conn.prepareStatement(sqlStr);

			pstmt.setString(1, token);
			pstmt.setInt(2, userId);
			pstmt.setInt(3, emailServiceId);

			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
	}

	// Get token to set the status back to false after verified
	public void setRequestStatus(boolean status, String token) throws SQLException {
		Connection conn = null;

		try {
			conn = DB.connect();
			String sqlStr = "UPDATE request SET is_active = ? WHERE token = ?;";
			PreparedStatement pstmt = conn.prepareStatement(sqlStr);

			pstmt.setBoolean(1, status);
			pstmt.setString(2, token);

			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
	}
}
