package models.category;

import util.*;
import java.sql.*;
import java.util.ArrayList;

public class CategoryDAO {
	public ArrayList<Category> getAllCategories() throws SQLException {
		Connection conn = DB.connect();
		ArrayList<Category> categories = new ArrayList<Category>();

		try {
			String sqlStr = "SELECT * FROM category";
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
	
	public boolean getCategoryDeleteableOrNot(int categoryId) throws SQLException {
	    Connection conn = DB.connect();
	    boolean isDeleteable = true;

	    try {
	        // Check if the category is being used by any services
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