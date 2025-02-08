/***********************************************************
 * Name: Soe Zaw Aung, Scott
 * Class: DIT/FT/2B/01
 * Admin No: P2340474
 * Description: Model class to store database operations related to service
 ************************************************************/
package models.service;

import util.DB;
import util.CloudinaryConnection;

import java.io.IOException;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.cloudinary.Cloudinary;

import jakarta.servlet.http.Part;

public class ServiceDAO {
	private Cloudinary cloudinary;

	// retrieves all services
	public ArrayList<Service> getAllServices() throws SQLException {
		Connection conn = DB.connect();
		ArrayList<Service> services = new ArrayList<Service>();
		try {
			String sqlStr = "SELECT s.*, c.name AS category_name FROM service s LEFT JOIN category c ON s.category_id = c.id ORDER BY s.id";
			PreparedStatement pstmt = conn.prepareStatement(sqlStr);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				Service service = new Service();
				service.setServiceId(rs.getInt("id"));
				service.setServiceName(rs.getString("name"));
				service.setServiceDescription(rs.getString("description"));
				service.setCategoryId(rs.getInt("id"));
				service.setCategoryName(rs.getString("category_name"));
				service.setPrice(rs.getFloat("price"));
				service.setImageUrl(rs.getString("img_url"));
				service.setIsActive(rs.getBoolean("is_active"));
				services.add(service);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return services;
	}

	// retrieves all active services
	public ArrayList<Service> getAllActiveServices() throws SQLException {
		Connection conn = DB.connect();
		ArrayList<Service> services = new ArrayList<Service>();
		try {
			String sqlStr = "SELECT s.*, c.name AS category_name FROM service s LEFT JOIN category c ON s.category_id = c.id WHERE s.is_active = true ORDER BY s.id";
			PreparedStatement pstmt = conn.prepareStatement(sqlStr);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				Service service = new Service();
				service.setServiceId(rs.getInt("id"));
				service.setServiceName(rs.getString("name"));
				service.setServiceDescription(rs.getString("description"));
				service.setCategoryId(rs.getInt("id"));
				service.setCategoryName(rs.getString("category_name"));
				service.setPrice(rs.getFloat("price"));
				service.setImageUrl(rs.getString("img_url"));
				service.setIsActive(rs.getBoolean("is_active"));
				services.add(service);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return services;
	}

	// retrieve service by specific id
	public Service getServiceById(int serviceId) throws SQLException {
		Connection conn = DB.connect();
		Service service = null;
		try {
			String sqlStr = "SELECT s.*, c.name AS category_name FROM service s LEFT JOIN category c ON s.category_id = c.id WHERE s.id = ?";
			PreparedStatement pstmt = conn.prepareStatement(sqlStr);
			pstmt.setInt(1, serviceId);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				service = new Service();
				service.setServiceId(rs.getInt("id"));
				service.setServiceName(rs.getString("name"));
				service.setServiceDescription(rs.getString("description"));
				service.setCategoryId(rs.getInt("id"));
				service.setCategoryName(rs.getString("category_name"));
				service.setPrice(rs.getFloat("price"));
				service.setImageUrl(rs.getString("img_url"));
				service.setIsActive(rs.getBoolean("is_active"));
			}
		} finally {
			conn.close();
		}
		return service;
	}

	// insert new service with optional image
	public boolean createService(Service service, Part imagePart) throws SQLException {
		Connection conn = DB.connect();
		boolean success = false;
		this.cloudinary = CloudinaryConnection.getCloudinary();
		try {

			String imageUrl = (imagePart.getSize() > 0)
					? CloudinaryConnection.uploadImageToCloudinary(cloudinary, imagePart)
					: null;
			String sqlStr = (imagePart.getSize() > 0)
					? "INSERT INTO service (name, description, category_id, price, img_url) VALUES (?, ?, ?, ?, ?)"
					: "INSERT INTO service (name, description, category_id, price) VALUES (?, ?, ?, ?)";
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

	public boolean updateService(Service service, Part imagePart) throws SQLException {
		Connection conn = DB.connect();
		boolean success = false;
		this.cloudinary = CloudinaryConnection.getCloudinary();

		try {
			StringBuilder sqlStr = new StringBuilder(
					"UPDATE service SET name=?, description=?, category_id=?, price=?, is_active=?");

			String newImageUrl = null;
			boolean hasNewImage = false; 

			// Only handle image if a new one is uploaded
			if (imagePart != null && imagePart.getSize() > 0) {
				// Get current image URL for deletion
				String currentImageUrl = getServiceById(service.getServiceId()).getImageUrl();

				// Upload new image first
				newImageUrl = CloudinaryConnection.uploadImageToCloudinary(cloudinary, imagePart);

				// Only add img_url to SQL if there's a new image
				if (newImageUrl != null) {
					sqlStr.append(", img_url=?");
					hasNewImage = true;

					// Delete old image if it's not the default
					if (currentImageUrl != null && !currentImageUrl.equals(
							"https://res.cloudinary.com/dnaulhgz8/image/upload/v1738989578/cleaning_service_otzmkd.png")) {
						try {
							CloudinaryConnection.deleteFromCloudinary(cloudinary, currentImageUrl);
						} catch (Exception e) {
							System.out.println("Error deleting old image: " + e.getMessage());
						}
					}
				}
			}

			sqlStr.append(" WHERE id=?");

			PreparedStatement pstmt = conn.prepareStatement(sqlStr.toString());
			int paramIndex = 1;

			// Set the basic parameters
			pstmt.setString(paramIndex++, service.getServiceName());
			pstmt.setString(paramIndex++, service.getServiceDescription());
			pstmt.setInt(paramIndex++, service.getCategoryId());
			pstmt.setFloat(paramIndex++, service.getPrice());
			pstmt.setBoolean(paramIndex++, service.getIsActive());

			// Add image URL if we have one
			if (hasNewImage) {
				pstmt.setString(paramIndex++, newImageUrl);
			}

			// Set the WHERE clause parameter
			pstmt.setInt(paramIndex, service.getServiceId());

			success = pstmt.executeUpdate() > 0;
		} finally {
			conn.close();
		}
		return success;
	}

	// delete service and Cloudinary image
	public boolean deleteService(int serviceId) throws SQLException {
		Connection conn = DB.connect();
		this.cloudinary = CloudinaryConnection.getCloudinary();
		boolean success = false;
		try {
			// Get service info first for image deletion
			Service service = getServiceById(serviceId);

			// Delete the service from database
			String sqlStr = "DELETE FROM service WHERE id = ?";
			PreparedStatement pstmt = conn.prepareStatement(sqlStr);
			pstmt.setInt(1, serviceId);
			int rowsAffected = pstmt.executeUpdate();
			success = rowsAffected > 0;

			// If successful and image is not default, delete from Cloudinary but check to
			// not be default image
			if (success) {
				if (service != null && service.getImageUrl() != null && !service.getImageUrl().equals(
						"https://res.cloudinary.com/dnaulhgz8/image/upload/v1732466480/default_cleaner_photo_xcufh7.jpg")) {
					try {
						CloudinaryConnection.deleteFromCloudinary(cloudinary, service.getImageUrl());
					} catch (IOException e) {
						System.out.println("Error deleting service image: " + e.getMessage());
					}
				}
			}
		} finally {
			conn.close();
		}
		return success;
	}

	// retrieve services filtered by category
	public ArrayList<Service> getServicesByCategory(int categoryId) throws SQLException {
		Connection conn = DB.connect();
		ArrayList<Service> services = new ArrayList<Service>();

		try {
			String sqlStr = """
					    SELECT s.*, c.name
					    FROM service s
					    LEFT JOIN category c ON s.category_id = c.id
					    WHERE s.category_id = ? AND s.is_active = true
					    ORDER BY s.id
					""";

			PreparedStatement pstmt = conn.prepareStatement(sqlStr);
			pstmt.setInt(1, categoryId);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				Service service = new Service();
				service.setServiceId(rs.getInt("id"));
				service.setServiceName(rs.getString("name"));
				service.setServiceDescription(rs.getString("description"));
				service.setCategoryId(rs.getInt("id"));
				service.setCategoryName(rs.getString("name"));
				service.setPrice(rs.getFloat("price"));
				service.setImageUrl(rs.getString("img_url"));
				service.setIsActive(rs.getBoolean("is_active"));
				services.add(service);
			}
		} finally {
			conn.close();
		}
		return services;
	}

	// Seed the overall data from the csv file
	public void seedData(Service service) throws SQLException {
		Connection conn = DB.connect();
		try {
			String sqlStr = "CALL seed_service(?, ?, ?, ?, ?);";
			CallableStatement stmt = conn.prepareCall(sqlStr);

			stmt.setString(1, service.getServiceName());
			stmt.setString(2, service.getServiceDescription());
			stmt.setFloat(3, service.getPrice());
			stmt.setString(4, service.getImageUrl());
			stmt.setInt(5, service.getCategoryId());

			stmt.execute();

		} catch (Exception e) {
			System.out.println("Error Seeding Service Data.");
			e.printStackTrace();
		} finally {
			conn.close();
		}
	}
}