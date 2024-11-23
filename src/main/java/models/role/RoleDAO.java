package models.role;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import util.DB;

public class RoleDAO {
    
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
                role.setRoleId(rs.getInt("role_id"));
                role.setRoleName(rs.getString("role_name"));
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
}