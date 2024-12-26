/***********************************************************
 * Name: Soe Zaw Aung, Scott
 * Class: DIT/FT/2B/01
 * Admin No: P2340474
 * Description: Model class to store database operations related to category
 ************************************************************/

package models.category;

import util.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

public class CategoryDAO {
	// Retrieves all categories from the database
	public ArrayList<Category> getAllCategories() throws SQLException {
		Connection conn = DB.connect();
		ArrayList<Category> categories = new ArrayList<Category>();

		try {
			String sqlStr = "SELECT * FROM category ORDER BY category_id";
			PreparedStatement pstmt = conn.prepareStatement(sqlStr);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				Category category = new Category();
				category.setCategoryId(rs.getInt("category_id"));
				category.setCategoryName(rs.getString("category_name"));
				categories.add(category);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return categories;
	}

	// Retrieves a category by its ID
	public Category getCategoryById(int categoryId) throws SQLException {
		Connection conn = DB.connect();
		Category category = null;
		try {
			String sqlStr = "SELECT * FROM category WHERE category_id = ?";
			PreparedStatement pstmt = conn.prepareStatement(sqlStr);
			pstmt.setInt(1, categoryId);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				category = new Category();
				category.setCategoryId(rs.getInt("category_id"));
				category.setCategoryName(rs.getString("category_name"));
			}
		} finally {
			conn.close();
		}
		return category;
	}

	// Checks if a category can be deleted based on its usage in services
	public boolean getCategoryDeleteableOrNot(int categoryId) throws SQLException {
		Connection conn = DB.connect();
		boolean isDeleteable = true;

		try {
			String checkServiceSql = "SELECT COUNT(*) FROM service WHERE category_id = ?";
			PreparedStatement checkServiceStmt = conn.prepareStatement(checkServiceSql);
			checkServiceStmt.setInt(1, categoryId);
			ResultSet checkServiceRS = checkServiceStmt.executeQuery();

			if (checkServiceRS.next()) {
				int serviceCount = checkServiceRS.getInt(1);
				isDeleteable = serviceCount == 0;
			}

		} finally {
			conn.close();
		}

		return isDeleteable;
	}

	// Creates a new category in the database
	public boolean createCategory(String categoryName) throws SQLException {
		Connection conn = DB.connect();
		boolean success = false;

		try {
			String sqlStr = "INSERT INTO category (category_name) VALUES (?)";
			PreparedStatement pstmt = conn.prepareStatement(sqlStr);
			pstmt.setString(1, categoryName);

			int rowsAffected = pstmt.executeUpdate();
			success = rowsAffected > 0;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return success;
	}

	// Updates an existing category in the database
	public boolean updateCategory(Category category) throws SQLException {
		Connection conn = DB.connect();
		boolean success = false;

		try {
			String sqlStr = "UPDATE category SET category_name = ? WHERE category_id = ?";
			PreparedStatement pstmt = conn.prepareStatement(sqlStr);
			pstmt.setString(1, category.getCategoryName());
			pstmt.setInt(2, category.getCategoryId());

			int rowsAffected = pstmt.executeUpdate();
			success = rowsAffected > 0;
		} finally {
			conn.close();
		}

		return success;
	}

	// Retrieves all categories along with their service counts
	public ArrayList<Category> getAllCategoriesWithServiceCount() throws SQLException {
		Connection conn = DB.connect();
		ArrayList<Category> categories = new ArrayList<Category>();

		try {
			String sqlStr = """
					    SELECT
					        c.category_id,
					        c.category_name,
					        COUNT(DISTINCT s.service_id) as service_count,
					        STRING_AGG(s.service_name, '||') as service_names
					    FROM category c
					    LEFT JOIN service s ON c.category_id = s.category_id AND s.is_active = true
					    GROUP BY c.category_id, c.category_name
					    ORDER BY c.category_id
					""";

			PreparedStatement pstmt = conn.prepareStatement(sqlStr);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				Category category = new Category();
				category.setCategoryId(rs.getInt("category_id"));
				category.setCategoryName(rs.getString("category_name"));
				category.setServiceCount(rs.getInt("service_count"));

				// Handle service names
				String serviceNames = rs.getString("service_names");
				if (serviceNames != null) {
					category.setServices(new ArrayList<>(Arrays.asList(serviceNames.split("\\|\\|"))));
				} else {
					category.setServices(new ArrayList<>());
				}

				categories.add(category);
			}
		} finally {
			conn.close();
		}

		return categories;
	}

	// Deletes a specified category from the database
	public boolean deleteCategory(int categoryId) throws SQLException {
		Connection conn = DB.connect();
		boolean success = false;

		try {
			String sqlStr = "DELETE FROM category WHERE category_id = ?";
			PreparedStatement pstmt = conn.prepareStatement(sqlStr);
			pstmt.setInt(1, categoryId);

			int rowsAffected = pstmt.executeUpdate();
			success = rowsAffected > 0;
		} finally {
			conn.close();
		}

		return success;
	}
}