/***********************************************************
* Name: Soe Zaw Aung, Scott
* Class: DIT/FT/2B/01
* Admin No: P2340474
* Description: Model class to store database operations related to bundles
************************************************************/
package models.bundle;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.cloudinary.Cloudinary;

import jakarta.servlet.http.Part;
import models.service.*;
import util.*;

public class BundleDAO {
	private Cloudinary cloudinary;

	// Get all bundles with their services and calculated prices
	public ArrayList<Bundle> getAllBundlesWithServices() throws SQLException {
		Connection conn = DB.connect();
		ArrayList<Bundle> bundles = new ArrayList<Bundle>();

		try {
			String sqlStr = """
					SELECT
					   b.id,
					   b.name,
					   b.description,
					   b.discount_percent,
					   b.img_url,
					   b.is_active,
					   s.id as service_id,
					   s.name as service_name,
					   s.description as service_description,
					   s.price,
					   s.category_id,
					   (SELECT SUM(s2.price)
					    FROM bundle_service bs2
					    JOIN service s2 ON bs2.service_id = s2.id
					    WHERE bs2.bundle_id = b.id) as original_price,
					   (SELECT SUM(s2.price) * (1 - b.discount_percent::float/100)
					    FROM bundle_service bs2
					    JOIN service s2 ON bs2.service_id = s2.id
					    WHERE bs2.bundle_id = b.id) as discounted_price
					FROM bundle b
					LEFT JOIN bundle_service bs ON b.id = bs.bundle_id
					LEFT JOIN service s ON bs.service_id = s.id
					ORDER BY b.id;
									""";

			PreparedStatement pstmt = conn.prepareStatement(sqlStr);
			ResultSet rs = pstmt.executeQuery();

			Bundle currentBundle = null;
			int currentBundleId = -1;

			while (rs.next()) {
				int bundleId = rs.getInt("id");

				if (currentBundle == null || bundleId != currentBundleId) {
					currentBundle = new Bundle();
					currentBundle.setBundleId(bundleId);
					currentBundle.setBundleName(rs.getString("name"));
					currentBundle.setDiscountPercent(rs.getInt("discount_percent"));
					currentBundle.setOriginalPrice(rs.getFloat("original_price"));
					currentBundle.setDiscountedPrice(rs.getFloat("discounted_price"));
					currentBundle.setImageUrl(rs.getString("img_url"));
					currentBundle.setIsActive(rs.getBoolean("is_active"));
					bundles.add(currentBundle);
					currentBundleId = bundleId;
				}

				// Add service if it exists
				if (rs.getInt("id") != 0) {
					Service service = new Service();
					service.setServiceId(rs.getInt("service_id"));
					service.setServiceName(rs.getString("service_name"));
					service.setServiceDescription(rs.getString("service_description"));
					service.setCategoryId(rs.getInt("category_id"));
					service.setPrice(rs.getFloat("price"));
					currentBundle.addService(service);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return bundles;
	}

	// Get only active bundles with their services and calculated prices
	public ArrayList<Bundle> getAllActiveBundlesWithServices() throws SQLException {
		Connection conn = DB.connect();
		ArrayList<Bundle> bundles = new ArrayList<Bundle>();

		try {
			String sqlStr = """
					SELECT
					    b.*,
					    s.id as service_id,
					    s.name as service_name,
					    s.description as service_description,
					    s.category_id,
					    s.price,
					    (SELECT SUM(s2.price)
					     FROM bundle_service bs2
					     JOIN service s2 ON bs2.service_id = s2.id
					     WHERE bs2.bundle_id = b.id) as original_price,
					    (SELECT SUM(s2.price) * (1 - b.discount_percent::float/100)
					     FROM bundle_service bs2
					     JOIN service s2 ON bs2.service_id = s2.id
					     WHERE bs2.bundle_id = b.id) as discounted_price
					FROM bundle b
					LEFT JOIN bundle_service bs ON b.id = bs.bundle_id
					LEFT JOIN service s ON bs.service_id = s.id
					WHERE b.is_active = true
					ORDER BY b.id;
					""";

			PreparedStatement pstmt = conn.prepareStatement(sqlStr);
			ResultSet rs = pstmt.executeQuery();

			Bundle currentBundle = null;
			int currentBundleId = -1;

			while (rs.next()) {
				int bundleId = rs.getInt("id");

				if (currentBundle == null || bundleId != currentBundleId) {
					currentBundle = new Bundle();
					currentBundle.setBundleId(bundleId);
					currentBundle.setBundleName(rs.getString("name"));
					currentBundle.setDiscountPercent(rs.getInt("discount_percent"));
					currentBundle.setOriginalPrice(rs.getFloat("original_price"));
					currentBundle.setDiscountedPrice(rs.getFloat("discounted_price"));
					currentBundle.setImageUrl(rs.getString("img_url"));
					currentBundle.setIsActive(rs.getBoolean("is_active"));
					bundles.add(currentBundle);
					currentBundleId = bundleId;
				}

				// Add service if it exists
				if (rs.getInt("service_id") != 0) {
					Service service = new Service();
					service.setServiceId(rs.getInt("service_id"));
					service.setServiceName(rs.getString("service_name"));
					service.setServiceDescription(rs.getString("service_description"));
					service.setCategoryId(rs.getInt("category_id"));
					service.setPrice(rs.getFloat("price"));
					currentBundle.addService(service);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return bundles;
	}

	// Get specific bundle by ID with its services
	public Bundle getBundleById(int bundleId) throws SQLException {
		Connection conn = DB.connect();
		Bundle bundle = null;

		try {
			String sqlStr = """
					    SELECT b.*,
					           s.id as service_id,
					           s.name as service_name,
					           s.description as service_description,
					           s.category_id,
					           s.price,
					           (SELECT SUM(s2.price) FROM bundle_service bs2
					            JOIN service s2 ON bs2.service_id = s2.id
					            WHERE bs2.bundle_id = b.id) as original_price,
					           (SELECT SUM(s2.price) * (1 - b.discount_percent::float/100)
					            FROM bundle_service bs2
					            JOIN service s2 ON bs2.service_id = s2.id
					            WHERE bs2.bundle_id = b.id) as discounted_price
					    FROM bundle b
					    LEFT JOIN bundle_service bs ON b.id = bs.bundle_id
					    LEFT JOIN service s ON bs.service_id = s.id
					    WHERE b.id = ?
					    ORDER BY b.id
					""";

			PreparedStatement pstmt = conn.prepareStatement(sqlStr);
			pstmt.setInt(1, bundleId);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				if (bundle == null) {
					bundle = new Bundle();
					bundle.setBundleId(bundleId);
					bundle.setBundleName(rs.getString("name"));
					bundle.setDiscountPercent(rs.getInt("discount_percent"));
					bundle.setOriginalPrice(rs.getFloat("original_price"));
					bundle.setDiscountedPrice(rs.getFloat("discounted_price"));
					bundle.setImageUrl(rs.getString("img_url"));
					bundle.setIsActive(rs.getBoolean("is_active"));
				}

				// Add service if it exists
				if (rs.getInt("service_id") != 0) {
					Service service = new Service();
					service.setServiceId(rs.getInt("service_id"));
					service.setServiceName(rs.getString("service_name"));
					service.setServiceDescription(rs.getString("service_description"));
					service.setCategoryId(rs.getInt("category_id"));
					service.setPrice(rs.getFloat("price"));
					bundle.addService(service);
				}
			}
		} finally {
			conn.close();
		}
		return bundle;
	}

	// Create new bundle with services and optional image
	public boolean createBundle(Bundle bundle, List<Integer> serviceIds, Part imagePart) throws SQLException {
		Connection conn = DB.connect();
		this.cloudinary = CloudinaryConnection.getCloudinary();
		try {
			// Skip Cloudinary upload if no image, database will use default image_url
			String imageUrl = imagePart.getSize() > 0
					? CloudinaryConnection.uploadImageToCloudinary(cloudinary, imagePart)
					: null;
			String serviceIdsStr = String.join(",",
					serviceIds.stream().map(String::valueOf).collect(Collectors.toList()));

			CallableStatement cstmt = imagePart.getSize() > 0 ? conn.prepareCall("CALL sp_create_bundle(?, ?, ?, ?)")
					: conn.prepareCall("CALL sp_create_bundle_without_image(?, ?, ?)");
			cstmt.setString(1, bundle.getBundleName());
			cstmt.setInt(2, bundle.getDiscountPercent());
			cstmt.setString(3, serviceIdsStr);
			if (imagePart.getSize() > 0) {
				cstmt.setString(4, imageUrl); // For sp_create_bundle
			}

			cstmt.execute();
			return true;
		} finally {
			conn.close();
		}
	}

	// Update bundle info, services and optionally its image
	public boolean updateBundle(Bundle bundle, List<Integer> serviceIds, Part imagePart) throws SQLException {
		Connection conn = DB.connect();
		this.cloudinary = CloudinaryConnection.getCloudinary();
		boolean success = false;

		try {
			// Prepare serviceIds string
			String serviceIdsStr = String.join(",",
					serviceIds.stream().map(String::valueOf).collect(Collectors.toList()));

			if (imagePart.getSize() > 0) {
				// Get current image URL for deletion
				Bundle currentBundle = getBundleById(bundle.getBundleId());
				String currentImageUrl = currentBundle.getImageUrl();

				// Upload new image
				String newImageUrl = CloudinaryConnection.uploadImageToCloudinary(cloudinary, imagePart);

				// Only delete old image from Cloudinary if it's not the default image
				if (newImageUrl != null && currentImageUrl != null && !currentImageUrl.equals(
						"https://res.cloudinary.com/dnaulhgz8/image/upload/v1732466480/default_cleaner_photo_xcufh7.jpg")) {
					try {
						CloudinaryConnection.deleteFromCloudinary(cloudinary, currentImageUrl);
					} catch (Exception e) {
						System.out.println("Error deleting old image: " + e.getMessage());
					}
				}

				// Call stored procedure with new image
				CallableStatement cstmt = conn.prepareCall("CALL sp_update_bundle_with_image(?, ?, ?, ?, ?, ?)");
				cstmt.setInt(1, bundle.getBundleId());
				cstmt.setString(2, bundle.getBundleName());
				cstmt.setInt(3, bundle.getDiscountPercent());
				cstmt.setString(4, serviceIdsStr);
				cstmt.setString(5, newImageUrl);
				cstmt.setBoolean(6, bundle.getIsActive());
				cstmt.execute();
				success = true;
			} else {
				// Call stored procedure without image
				CallableStatement cstmt = conn.prepareCall("CALL sp_update_bundle(?, ?, ?, ?, ?)");
				cstmt.setInt(1, bundle.getBundleId());
				cstmt.setString(2, bundle.getBundleName());
				cstmt.setInt(3, bundle.getDiscountPercent());
				cstmt.setString(4, serviceIdsStr);
				cstmt.setBoolean(5, bundle.getIsActive());
				cstmt.execute();
				success = true;
			}
		} finally {
			conn.close();
		}
		return success;
	}

	// Delete bundle and its non-default image
	public boolean deleteBundle(int bundleId) throws SQLException {
		Connection conn = DB.connect();
		this.cloudinary = CloudinaryConnection.getCloudinary();
		boolean success = false;
		try {
			// Get bundle info first for image deletion
			Bundle bundle = getBundleById(bundleId);

			// Call stored procedure to delete bundle
			CallableStatement cstmt = conn.prepareCall("CALL sp_delete_bundle(?)");
			cstmt.setInt(1, bundleId);
			cstmt.execute();
			success = true;
			// if successful, delete image inside Cloudinary
			if (success) {
				// Only delete from Cloudinary if not using default image
				if (bundle != null && bundle.getImageUrl() != null && !bundle.getImageUrl().equals(
						"https://res.cloudinary.com/dnaulhgz8/image/upload/v1732466480/default_cleaner_photo_xcufh7.jpg")) {
					try {
						CloudinaryConnection.deleteFromCloudinary(cloudinary, bundle.getImageUrl());
					} catch (IOException e) {
						System.out.println("Error deleting bundle image: " + e.getMessage());
					}
				}
			}
		} finally {
			conn.close();
		}
		return success;
	}

	// Check Bundle Service Relation
	public ArrayList<Integer> checkBundleService(int bundleId) throws SQLException {
		Connection conn = DB.connect();
		ArrayList<Integer> serviceIds = new ArrayList<Integer>();

		try {
			String sqlStr = "SELECT service_id FROM bundle_service WHERE bundle_id = ?;";
			PreparedStatement pstmt = conn.prepareCall(sqlStr);

			pstmt.setInt(1, bundleId);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				serviceIds.add(Integer.parseInt(rs.getString("service_id")));
			}

		} catch (Exception e) {
			System.out.println("Error Seeding Bundle Data.");
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return serviceIds;
	}

	// Seed the overall data from the csv file
	public void seedData(Bundle bundle) throws SQLException {
		Connection conn = DB.connect();

		try {
			String sqlStr = "CALL seed_bundle(?, ?, ?, ?);";
			CallableStatement stmt = conn.prepareCall(sqlStr);

			stmt.setString(1, bundle.getBundleName());
			stmt.setString(2, bundle.getBundleDescription());
			stmt.setInt(3, bundle.getDiscountPercent());
			stmt.setString(4, bundle.getImageUrl());

			stmt.execute();

		} catch (Exception e) {
			System.out.println("Error Seeding Bundle Data.");
			e.printStackTrace();
		} finally {
			conn.close();
		}
	}
	
	// Seed Bundle Service Data
	public void seedBundleServiceData(Bundle bundle) throws SQLException {
		Connection conn = DB.connect();

		try {
			String sqlStr = "CALL seed_bundle_service(?, ?);";
			CallableStatement stmt = conn.prepareCall(sqlStr);

			stmt.setInt(1, bundle.getBundleId());
			stmt.setInt(2, bundle.getServices().get(0).getServiceId());

			stmt.execute();

		} catch (Exception e) {
			System.out.println("Error Seeding Bundle Service Data.");
			e.printStackTrace();
		} finally {
			conn.close();
		}
	}

}