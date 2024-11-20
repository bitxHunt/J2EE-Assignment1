package models.service;

import util.DB;
import java.sql.*;
import java.util.ArrayList;


public class ServiceDAO {
    public ArrayList<Service> getAllServices() throws SQLException {
        Connection conn = DB.connect();
        ArrayList<Service> services = new ArrayList<Service>();
        try {
            String sqlStr = "SELECT * FROM service";
            PreparedStatement pstmt = conn.prepareStatement(sqlStr);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Service service = new Service();
                service.setServiceId(rs.getInt("service_id"));
                service.setServiceName(rs.getString("service_name"));
                service.setServiceDescription(rs.getString("service_description"));
                service.setCategoryId(rs.getInt("category_id"));
                service.setPrice(rs.getFloat("price"));
                services.add(service);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.close();
        }
        return services;
    }


    public boolean createService(Service service) throws SQLException {
        Connection conn = DB.connect();
        boolean success = false;
        try {
            String sqlStr = "INSERT INTO service (service_name, service_description, category_id, price) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sqlStr);
            pstmt.setString(1, service.getServiceName());
            pstmt.setString(2, service.getServiceDescription());
            pstmt.setInt(3, service.getCategoryId());
            pstmt.setFloat(4, service.getPrice());
            
            int rowsAffected = pstmt.executeUpdate();
            success = rowsAffected > 0;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.close();
        }
        return success;
    }

}