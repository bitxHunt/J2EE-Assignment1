/***********************************************************
 * Author-1
 * Name: Thiha Swan Htet, Harry
 * Class: DIT/FT/2B/01
 * Admin No: 
 *
 * Author-2
 * Name: Soe Zaw Aung, Scott
 * Class: DIT/FT/2B/01
 * Admin No: P2340474
 * 
 * Description: Model class to store database operations related to user
 ************************************************************/
package models.user;

import util.DB;
import com.cloudinary.Cloudinary;
import util.CloudinaryConnection;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;

import jakarta.servlet.http.Part;

public class UserDAO {
	private Cloudinary cloudinary;

	public ArrayList<User> getAllUsers() throws SQLException {

		Connection conn = DB.connect();
		ArrayList<User> users = new ArrayList<User>();
		try {
			String sqlStr = "SELECT * FROM users;";
			PreparedStatement pstmt = conn.prepareStatement(sqlStr);
			ResultSet rs = pstmt.executeQuery();

			System.out.println(rs.getMetaData().getColumnCount());

			while (rs.next()) {
				User user = new User();
				user.setFirstName(rs.getString("first_name"));
				user.setLastName(rs.getString("last_name"));
				user.setEmail(rs.getString("email"));
				user.setPassword(rs.getString("password"));
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

	public User getUserById(Integer userId) throws SQLException {
		Connection conn = DB.connect();
		User user = new User();
		try {
			String sqlStr = "SELECT first_name, last_name, email, image_url, phone_number FROM users WHERE user_id = ?;";
			PreparedStatement pstmt = conn.prepareStatement(sqlStr);
			pstmt.setInt(1, userId);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				user.setFirstName(rs.getString("first_name"));
				user.setLastName(rs.getString("last_name"));
				user.setEmail(rs.getString("email"));
				user.setPhoneNo(rs.getString("phone_number"));
				user.setImageURL(rs.getString("image_url"));
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
			String sqlStr = "SELECT * FROM users WHERE email = ?;";
			PreparedStatement pstmt = conn.prepareStatement(sqlStr);
			pstmt.setString(1, email);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				user.setId(rs.getInt("user_id"));
				user.setFirstName(rs.getString("first_name"));
				user.setLastName(rs.getString("last_name"));
				user.setEmail(rs.getString("email"));
				user.setPassword(rs.getString("password"));
				user.setRole(rs.getInt("role_id"));
				user.setImageURL(rs.getString("image_url"));
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
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Integer userId = null;

		try {
			conn = DB.connect();
			conn.setAutoCommit(false);

			String sqlStr = "SELECT register_user_with_addresses(?, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sqlStr);

			pstmt.setString(1, firstName);
			pstmt.setString(2, lastName);
			pstmt.setString(3, email);
			pstmt.setString(4, hashedPassword);
			pstmt.setString(5, phoneNo);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				userId = rs.getInt(1);
				conn.commit();
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return userId;
	}

	public void updateUserProfile(int userId, String firstName, String lastName, Part imagePart, String phoneNo)
			throws SQLException {
		Connection conn = DB.connect();
		this.cloudinary = CloudinaryConnection.getCloudinary();
		try {
			if (imagePart.getSize() > 0) {
				// Get current image URL for deletion
				User user = getUserById(userId);
				String currentImageUrl = user.getImageURL();

				// Upload new image
				String newImageUrl = CloudinaryConnection.uploadImageToCloudinary(cloudinary, imagePart);

				if (newImageUrl != null && currentImageUrl != null && !currentImageUrl.equals(
						"https://res.cloudinary.com/dnaulhgz8/image/upload/v1732446530/bizbynfxadhthnoymbdo.webp")) {
					// Delete old image
					try {
						CloudinaryConnection.deleteFromCloudinary(cloudinary, currentImageUrl);
					} catch (Exception e) {
						System.out.println("Error deleting old image: " + e.getMessage());
					}
				}

				String sqlStr = "UPDATE users SET first_name = ?, last_name = ?, phone_number = ?, image_url = ? WHERE user_id = ?;";
				PreparedStatement pstmt = conn.prepareStatement(sqlStr);
				pstmt.setString(1, firstName);
				pstmt.setString(2, lastName);
				pstmt.setString(3, phoneNo);
				pstmt.setString(4, newImageUrl);
				pstmt.setInt(5, userId);
				pstmt.executeUpdate();
			} else {
				String sqlStr = "UPDATE users SET first_name = ?, last_name = ?, phone_number = ? WHERE user_id = ?;";
				PreparedStatement pstmt = conn.prepareStatement(sqlStr);
				pstmt.setString(1, firstName);
				pstmt.setString(2, lastName);
				pstmt.setString(3, phoneNo);
				pstmt.setInt(4, userId);
				pstmt.executeUpdate();
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
	}

	// Author : Soe Zaw Aung
	// Retrieve users with pagination support
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

	// Get total count of users in database
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

	// Register new user with specified role including admin
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

	// Update user's role by user ID
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

	// Delete user by user ID
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
		return rowsAffected == 1;
	}

	// Get Total Number of staffs
	public int getTotalStaffs() throws SQLException {
		Connection conn = DB.connect();
		int totalStaffs = 0;

		try {
			String sqlStr = "SELECT COUNT(id) AS staffs FROM users WHERE role_id = ?;";
			PreparedStatement pstmt = conn.prepareStatement(sqlStr);

			// Assign role id 2, which is staff role.
			pstmt.setInt(1, 2);

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				totalStaffs = rs.getInt("staffs");
			}

		} catch (Exception e) {
			System.out.println("Error Getting Total Number of Staffs.");
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return totalStaffs;
	}

	// Get Staffs Booked On the Given Date
	public int bookedStaffForDate(LocalDate chosenDate, int chosenSlot) throws SQLException {
		Connection conn = DB.connect();
		int bookedStaff = 0;

		try {
			String sqlStr = "SELECT COUNT(staff_id) AS staffs FROM staff_booking WHERE date = ? AND slot_id = ?";
			PreparedStatement pstmt = conn.prepareStatement(sqlStr);

			pstmt.setDate(1, Date.valueOf(chosenDate));
			pstmt.setInt(2, chosenSlot);

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				bookedStaff = rs.getInt("staffs");
			}

		} catch (Exception e) {
			System.out.println("Error Getting Total Number of Booked Staffs.");
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return bookedStaff;
	}

	// Seed User Data
	public void seedData(User user) throws SQLException {
		Connection conn = DB.connect();

		try {
			String sqlStr = "CALL seed_user(?, ?, ?, ?, ?, ?, ?);";
			CallableStatement stmt = conn.prepareCall(sqlStr);

			stmt.setString(1, user.getCustomerId());
			stmt.setString(2, user.getFirstName());
			stmt.setString(3, user.getLastName());
			stmt.setString(4, user.getEmail());
			stmt.setString(5, user.getPassword());
			stmt.setString(6, user.getPhoneNo());
			stmt.setInt(7, user.getRole());

			stmt.execute();

		} catch (Exception e) {
			System.out.println("Error Seeding User Data.");
			e.printStackTrace();
		} finally {
			conn.close();
		}
	}
}
