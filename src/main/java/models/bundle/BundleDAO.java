package models.bundle;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.cloudinary.Cloudinary;

import jakarta.servlet.http.Part;
import models.service.*;
import util.CloudinaryConnection;
import util.db;

public class BundleDAO {
	private Cloudinary cloudinary;

	public ArrayList<Bundle> getAllBundlesWithServices() throws SQLException {
		Connection conn = db.connect();
		ArrayList<Bundle> bundles = new ArrayList<Bundle>();

		try {
			String sqlStr = """
					                  SELECT
					   b.*,
					   s.service_id,
					   s.service_name,
					   s.service_description,
					   s.category_id,
					   s.price,
					   (SELECT SUM(s2.price) FROM bundle_service bs2
					    JOIN service s2 ON bs2.service_id = s2.service_id
					    WHERE bs2.bundle_id = b.bundle_id) as original_price,
					   (SELECT SUM(s2.price) * (1 - b.discount_percent::float/100)
					    FROM bundle_service bs2
					    JOIN service s2 ON bs2.service_id = s2.service_id
					    WHERE bs2.bundle_id = b.bundle_id) as discounted_price
					FROM bundle b
					LEFT JOIN bundle_service bs ON b.bundle_id = bs.bundle_id
					LEFT JOIN service s ON bs.service_id = s.service_id
					ORDER BY b.bundle_id;
										""";

			PreparedStatement pstmt = conn.prepareStatement(sqlStr);
			ResultSet rs = pstmt.executeQuery();

			Bundle currentBundle = null;
			int currentBundleId = -1;

			while (rs.next()) {
				int bundleId = rs.getInt("bundle_id");

				if (currentBundle == null || bundleId != currentBundleId) {
					currentBundle = new Bundle();
					currentBundle.setBundleId(bundleId);
					currentBundle.setBundleName(rs.getString("bundle_name"));
					currentBundle.setDiscountPercent(rs.getInt("discount_percent"));
					currentBundle.setOriginalPrice(rs.getFloat("original_price"));
					currentBundle.setDiscountedPrice(rs.getFloat("discounted_price"));
					currentBundle.setImageUrl(rs.getString("image_url"));
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

public Bundle getBundleById(int bundleId) throws SQLException {
		Connection conn = db.connect();
		Bundle bundle = null;

		try {
			String sqlStr = """
					    SELECT b.*,
					           s.service_id,
					           s.service_name,
					           s.service_description,
					           s.category_id,
					           s.price,
					           (SELECT SUM(s2.price) FROM bundle_service bs2
					            JOIN service s2 ON bs2.service_id = s2.service_id
					            WHERE bs2.bundle_id = b.bundle_id) as original_price,
					           (SELECT SUM(s2.price) * (1 - b.discount_percent::float/100)
					            FROM bundle_service bs2
					            JOIN service s2 ON bs2.service_id = s2.service_id
					            WHERE bs2.bundle_id = b.bundle_id) as discounted_price
					    FROM bundle b
					    LEFT JOIN bundle_service bs ON b.bundle_id = bs.bundle_id
					    LEFT JOIN service s ON bs.service_id = s.service_id
					    WHERE b.bundle_id = ?
					    ORDER BY b.bundle_id
					""";

			PreparedStatement pstmt = conn.prepareStatement(sqlStr);
			pstmt.setInt(1, bundleId);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				if (bundle == null) {
					bundle = new Bundle();
					bundle.setBundleId(bundleId);
					bundle.setBundleName(rs.getString("bundle_name"));
					bundle.setDiscountPercent(rs.getInt("discount_percent"));
					bundle.setOriginalPrice(rs.getFloat("original_price"));
					bundle.setDiscountedPrice(rs.getFloat("discounted_price"));
					bundle.setImageUrl(rs.getString("image_url"));
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

	public boolean createBundle(Bundle bundle, List<Integer> serviceIds, Part imagePart) throws SQLException {
		Connection conn = db.connect();
		this.cloudinary = CloudinaryConnection.getCloudinary();
		try {
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

}