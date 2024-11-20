package models.category;

import util.DB;
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

//    public Category getCategoryById(int id) throws SQLException {
//        Connection conn = DB.connect();
//        Category category = null;
//        
//        try {
//            String sqlStr = "SELECT * FROM category WHERE category_id = ?";
//            PreparedStatement pstmt = conn.prepareStatement(sqlStr);
//            pstmt.setInt(1, id);
//            ResultSet rs = pstmt.executeQuery();
//            
//            if (rs.next()) {
//                category = new Category();
//                category.setCategoryId(rs.getInt("category_id"));
//                category.setCategoryName(rs.getString("category_name"));
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            conn.close();
//        }
//        return category;
//    }
//
//    public boolean createCategory(String categoryName) throws SQLException {
//        Connection conn = DB.connect();
//        boolean success = false;
//        
//        try {
//            String sqlStr = "INSERT INTO category (category_name) VALUES (?)";
//            PreparedStatement pstmt = conn.prepareStatement(sqlStr);
//            pstmt.setString(1, categoryName);
//            
//            int rowsAffected = pstmt.executeUpdate();
//            success = rowsAffected > 0;
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            conn.close();
//        }
//        return success;
//    }
}