package models.service;

import util.CloudinaryConnection;
import util.DB;
import java.sql.*;
import java.util.ArrayList;

import com.cloudinary.Cloudinary;

import jakarta.servlet.http.Part;

public class ServiceDAO {
	private Cloudinary cloudinary;

	public ArrayList<Service> getAllServices() throws SQLException {
		Connection conn = DB.connect();
		ArrayList<Service> services = new ArrayList<Service>();
		try {
			String sqlStr = "SELECT s.*, c.category_name FROM service s LEFT JOIN category c ON s.category_id = c.category_id";
			PreparedStatement pstmt = conn.prepareStatement(sqlStr);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				Service service = new Service();
				service.setServiceId(rs.getInt("service_id"));
				service.setServiceName(rs.getString("service_name"));
				service.setServiceDescription(rs.getString("service_description"));
				service.setCategoryId(rs.getInt("category_id"));
				service.setCategoryName(rs.getString("category_name"));
				service.setPrice(rs.getFloat("price"));
				service.setImageUrl(rs.getString("image_url"));
				services.add(service);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return services;
	}

	public boolean createService(Service service, Part imagePart) throws SQLException {
		Connection conn = DB.connect();
		boolean success = false;
		this.cloudinary = CloudinaryConnection.getCloudinary();
		try {

			String imageUrl = ( imagePart.getSize() > 0)? CloudinaryConnection.uploadImageToCloudinary(cloudinary, imagePart)
					: null;
			String sqlStr = (imagePart.getSize() > 0)
					? "INSERT INTO service (service_name, service_description, category_id, price, image_url) VALUES (?, ?, ?, ?, ?)"
					: "INSERT INTO service (service_name, service_description, category_id, price) VALUES (?, ?, ?, ?)";
			PreparedStatement pstmt = conn.prepareStatement(sqlStr);
			pstmt.setString(1, service.getServiceName());
			pstmt.setString(2, service.getServiceDescription());
			pstmt.setInt(3, service.getCategoryId());
			pstmt.setFloat(4, service.getPrice());
			if (imagePart.getSize() > 0) {
				pstmt.setString(5, imageUrl);
			}

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