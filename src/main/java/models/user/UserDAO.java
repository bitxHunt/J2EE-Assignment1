package models.user;

import util.DB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDAO {
	public ArrayList<User> getAllUsers() throws SQLException {

		Connection conn = DB.connect();
		ArrayList<User> users = new ArrayList<User>();
		try {
			String sqlStr = "SELECT u.user_id, u.first_name, u.last_name, u.email, u.password, r.role_name FROM users u INNER JOIN role r ON r.role_id = u.role_id";
			PreparedStatement pstmt = conn.prepareStatement(sqlStr);
			ResultSet rs = pstmt.executeQuery();

			System.out.println(rs.getMetaData().getColumnCount());

			while (rs.next()) {
				User user = new User();
				user.setFirstName(rs.getString("first_name"));
				user.setLastName(rs.getString("last_name"));
				user.setEmail(rs.getString("email"));
				user.setPassword(rs.getString("password"));
				user.setRole(rs.getString("role_id"));
				users.add(user);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return users;
	}

	public User getUserById(Integer userId) throws SQLException {
		Connection conn = DB.connect();
		User user = new User();
		try {
			String sqlStr = "SELECT first_name, last_name, email, phone_number FROM users WHERE user_id = ?;";
			PreparedStatement pstmt = conn.prepareStatement(sqlStr);
			pstmt.setInt(1, userId);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				user.setFirstName(rs.getString("first_name"));
				user.setLastName(rs.getString("last_name"));
				user.setEmail(rs.getString("email"));
				user.setPhoneNo(rs.getString("phone_number"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return user;
	}

	public User loginUser(String email) throws SQLException {
		Connection conn = DB.connect();
		User user = new User();
		try {
			String sqlStr = "SELECT u.user_id, u.first_name, u.last_name, u.email, u.password, r.role_name FROM users u INNER JOIN role r ON r.role_id = u.role_id WHERE email = ?;";
			PreparedStatement pstmt = conn.prepareStatement(sqlStr);
			pstmt.setString(1, email);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				user.setId(rs.getInt("user_id"));
				user.setFirstName(rs.getString("first_name"));
				user.setLastName(rs.getString("last_name"));
				user.setEmail(rs.getString("email"));
				user.setPassword(rs.getString("password"));
				user.setRole(rs.getString("role_name"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return user;
	}

	public Integer registerUser(String firstName, String lastName, String email, String hashedPassword, String phoneNo)
			throws SQLException {
		Connection conn = DB.connect();
		Integer rowsAffected = 0;
		try {
			String sqlStr = "INSERT INTO users (first_name, last_name, email, password, phone_number) VALUES (?, ?, ?, ?, ?);";

			PreparedStatement pstmt = conn.prepareStatement(sqlStr);
			pstmt.setString(1, firstName);
			pstmt.setString(2, lastName);
			pstmt.setString(3, email);
			pstmt.setString(4, hashedPassword);
			pstmt.setString(5, phoneNo);
			rowsAffected = pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return rowsAffected;
	}

	// Author : Soe Zaw Aung
	public ArrayList<User> getUsersWithPagination(int page, int pageSize) throws SQLException {
		Connection conn = DB.connect();
		ArrayList<User> users = new ArrayList<>();
		try {
			String sqlStr = "SELECT * FROM users ORDER BY user_id LIMIT ? OFFSET ?";
			PreparedStatement pstmt = conn.prepareStatement(sqlStr);
			pstmt.setInt(1, pageSize);
			pstmt.setInt(2, (page - 1) * pageSize);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				User user = new User();
				user.setId(rs.getInt("user_id"));
				user.setFirstName(rs.getString("first_name"));
				user.setLastName(rs.getString("last_name"));
				user.setEmail(rs.getString("email"));
				user.setPhoneNo(rs.getString("phone_number"));
				user.setRole(rs.getInt("role_id"));
				users.add(user);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return users;
	}

	public int getTotalUserCount() throws SQLException {
		Connection conn = DB.connect();
		try {
			String sqlStr = "SELECT COUNT(*) as count FROM users";
			PreparedStatement pstmt = conn.prepareStatement(sqlStr);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				return rs.getInt("count");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return 0;
	}

	public Integer registerUserWithRole(String firstName, String lastName, String email, String hashedPassword,
			String phoneNo, int roleId) throws SQLException {
		Connection conn = DB.connect();
		Integer rowsAffected = 0;

		try {
			String sqlStr = "INSERT INTO users (first_name, last_name, email, password, phone_number, role_id) "
					+ "VALUES (?, ?, ?, ?, ?, ?)";

			PreparedStatement pstmt = conn.prepareStatement(sqlStr);
			pstmt.setString(1, firstName);
			pstmt.setString(2, lastName);
			pstmt.setString(3, email);
			pstmt.setString(4, hashedPassword);
			pstmt.setString(5, phoneNo);
			pstmt.setInt(6, roleId);

			rowsAffected = pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
			throw e; // Re-throw the exception to be handled by the caller
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return rowsAffected;
	}

	public boolean updateUserRole(int userId, int roleId) throws SQLException {
		Connection conn = DB.connect();
		try {
			String sql = "UPDATE users SET role_id = ? WHERE user_id = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, roleId);
			pstmt.setInt(2, userId);
			return pstmt.executeUpdate() == 1;
		} finally {
			if (conn != null)
				conn.close();
		}
	}

	public boolean deleteUser(int userId) throws SQLException {
		Connection conn = DB.connect();
		int rowsAffected = 0;
		try {
			String sqlStr = "DELETE FROM users WHERE user_id=?";
			PreparedStatement pstmt = conn.prepareStatement(sqlStr);
			pstmt.setInt(1, userId);
			rowsAffected = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return rowsAffected==1;
	}
}
