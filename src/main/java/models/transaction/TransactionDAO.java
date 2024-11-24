/*
    Name: Thiha Swan Htet
    Class: DIT/FT/2B/01
    Admin No: p2336671
*/

package models.transaction;

import util.DB;

import java.io.StringReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import models.service.Service;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;
import models.address.Address;

public class TransactionDAO {

	public ArrayList<Transaction> getAllBookingsByUserID(Integer userId) throws SQLException {
		Connection conn = DB.connect();
		ArrayList<Transaction> transactions = new ArrayList<>();

		try {
			String sqlStr = """
					    SELECT t.*, ts.start_time, t.start_date as booking_date,
					           t.status as transaction_status
					    FROM transaction t
					    JOIN time_slot ts ON t.slot_id = ts.slot_id
					    WHERE t.user_id = ?
					    ORDER BY t.created_at DESC;
					""";

			PreparedStatement pstmt = conn.prepareStatement(sqlStr);
			pstmt.setInt(1, userId);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				Transaction transaction = new Transaction();
				transaction.setId(rs.getInt("trans_id"));	
				transaction.setUserId(rs.getInt("user_id"));

				// Create Address object
				Address address = new Address();
				address.setAddress(rs.getString("address"));
				address.setPostalCode(rs.getInt("postal_code"));
				address.setUnit(rs.getString("unit"));
				transaction.setAddress(address);

				transaction.setTimeSlot(rs.getTime("start_time").toLocalTime());
				transaction.setStartDate(rs.getTimestamp("booking_date").toLocalDateTime().toLocalDate());

				// Parse services JSON if exists
				String servicesJson = rs.getString("services");
				if (servicesJson != null) {
					JsonReader jsonReader = Json.createReader(new StringReader(servicesJson));
					JsonArray servicesArray = jsonReader.readArray();
					ArrayList<Service> services = new ArrayList<>();

					for (JsonValue serviceValue : servicesArray) {
						JsonObject serviceObj = (JsonObject) serviceValue;
						Service service = new Service();
						service.setServiceName(serviceObj.getString("service"));
						service.setPrice(Float.parseFloat(serviceObj.get("price").toString()));
						service.setImageUrl(serviceObj.getString("img_url"));
						services.add(service);
					}
					transaction.setServices(services);
				}

				// Set bundle info if exists
				transaction.setBundleName(rs.getString("bundle_name"));
				transaction.setBundle_img(rs.getString("bundle_img"));

				// Parse bundle services JSON if exists
				String bundleServicesJson = rs.getString("bundle_service");
				if (bundleServicesJson != null) {
					JsonReader jsonReader = Json.createReader(new StringReader(bundleServicesJson));
					JsonArray bundleServicesArray = jsonReader.readArray();
					ArrayList<Service> bundleServices = new ArrayList<>();

					for (JsonValue serviceValue : bundleServicesArray) {
						JsonObject serviceObj = (JsonObject) serviceValue;
						Service service = new Service();
						service.setServiceName(serviceObj.getString("service"));
						service.setPrice(Float.parseFloat(serviceObj.get("price").toString()));
						service.setImageUrl(serviceObj.getString("img_url"));
						bundleServices.add(service);
					}
					transaction.setBundleServices(bundleServices);
				}

				transaction.setDiscount(rs.getInt("discount_percent"));
				transaction.setSubTotal(rs.getDouble("subtotal"));
				transaction.setStatus(rs.getString("transaction_status"));

				// Get timestamp and convert to LocalDateTime
				Timestamp paidAt = rs.getTimestamp("paid_at");
				if (paidAt != null) {
					transaction.setPaidDate(paidAt.toLocalDateTime());
				}

				transactions.add(transaction);
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			conn.close();
		}

		return transactions;
	}

	public Transaction getTransactionById(Integer transId) throws SQLException {
		Connection conn = DB.connect();
		Transaction transaction = new Transaction();

		try {
			String sqlStr = """
					    SELECT t.*, ts.start_time, t.start_date as booking_date,
					           t.status as transaction_status
					    FROM transaction t
					    JOIN time_slot ts ON t.slot_id = ts.slot_id
					    WHERE t.trans_id = ?;
					""";

			PreparedStatement pstmt = conn.prepareStatement(sqlStr);
			pstmt.setInt(1, transId);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				transaction.setId(rs.getInt("trans_id"));
				transaction.setUserId(rs.getInt("user_id"));

				// Create Address object
				Address address = new Address();
				address.setAddress(rs.getString("address"));
				address.setPostalCode(rs.getInt("postal_code"));
				address.setUnit(rs.getString("unit"));
				transaction.setAddress(address);

				transaction.setTimeSlot(rs.getTime("start_time").toLocalTime());
				transaction.setStartDate(rs.getTimestamp("booking_date").toLocalDateTime().toLocalDate());

				// Parse services JSON if exists
				String servicesJson = rs.getString("services");
				if (servicesJson != null) {
					JsonReader jsonReader = Json.createReader(new StringReader(servicesJson));
					JsonArray servicesArray = jsonReader.readArray();
					ArrayList<Service> services = new ArrayList<>();

					for (JsonValue serviceValue : servicesArray) {
						JsonObject serviceObj = (JsonObject) serviceValue;
						Service service = new Service();
						service.setServiceName(serviceObj.getString("service"));
						service.setPrice(Float.parseFloat(serviceObj.get("price").toString()));
						service.setImageUrl(serviceObj.getString("img_url"));
						services.add(service);
					}
					transaction.setServices(services);
				}

				// Set bundle info if exists
				transaction.setBundleName(rs.getString("bundle_name"));
				transaction.setBundle_img(rs.getString("bundle_img"));

				// Parse bundle services JSON if exists
				String bundleServicesJson = rs.getString("bundle_service");
				if (bundleServicesJson != null) {
					JsonReader jsonReader = Json.createReader(new StringReader(bundleServicesJson));
					JsonArray bundleServicesArray = jsonReader.readArray();
					ArrayList<Service> bundleServices = new ArrayList<>();

					for (JsonValue serviceValue : bundleServicesArray) {
						JsonObject serviceObj = (JsonObject) serviceValue;
						Service service = new Service();
						service.setServiceName(serviceObj.getString("service"));
						service.setPrice(Float.parseFloat(serviceObj.get("price").toString()));
						service.setImageUrl(serviceObj.getString("img_url"));
						bundleServices.add(service);
					}
					transaction.setBundleServices(bundleServices);
				}

				transaction.setDiscount(rs.getInt("discount_percent"));
				transaction.setSubTotal(rs.getDouble("subtotal"));
				transaction.setStatus(rs.getString("transaction_status"));

				// Get timestamp and convert to LocalDateTime
				Timestamp paidAt = rs.getTimestamp("paid_at");
				if (paidAt != null) {
					transaction.setPaidDate(paidAt.toLocalDateTime());
				}
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			conn.close();
		}

		return transaction;
	}

	public ArrayList<Transaction> getActiveBookingsByUserID(Integer userId) throws SQLException {
		Connection conn = DB.connect();
		ArrayList<Transaction> transactions = new ArrayList<>();

		try {
			String sqlStr = """
					    SELECT t.*, ts.start_time, t.start_date as booking_date,
					           t.status as transaction_status
					    FROM transaction t
					    JOIN time_slot ts ON t.slot_id = ts.slot_id
					    WHERE t.user_id = ?
					    AND t.status != 'PENDING'
						AND t.status != 'CANCELLED'
					    ORDER BY t.created_at DESC;
					""";

			PreparedStatement pstmt = conn.prepareStatement(sqlStr);
			pstmt.setInt(1, userId);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				Transaction transaction = new Transaction();
				transaction.setId(rs.getInt("trans_id"));
				transaction.setUserId(rs.getInt("user_id"));

				// Create Address object
				Address address = new Address();
				address.setAddress(rs.getString("address"));
				address.setPostalCode(rs.getInt("postal_code"));
				address.setUnit(rs.getString("unit"));
				transaction.setAddress(address);

				transaction.setTimeSlot(rs.getTime("start_time").toLocalTime());
				transaction.setStartDate(rs.getTimestamp("booking_date").toLocalDateTime().toLocalDate());

				// Parse services JSON if exists
				String servicesJson = rs.getString("services");
				if (servicesJson != null) {
					JsonReader jsonReader = Json.createReader(new StringReader(servicesJson));
					JsonArray servicesArray = jsonReader.readArray();
					ArrayList<Service> services = new ArrayList<>();

					for (JsonValue serviceValue : servicesArray) {
						JsonObject serviceObj = (JsonObject) serviceValue;
						Service service = new Service();
						service.setServiceName(serviceObj.getString("service"));
						service.setPrice(Float.parseFloat(serviceObj.get("price").toString()));
						service.setImageUrl(serviceObj.getString("img_url"));
						services.add(service);
					}
					transaction.setServices(services);
				}

				// Set bundle info if exists
				transaction.setBundleName(rs.getString("bundle_name"));
				transaction.setBundle_img(rs.getString("bundle_img"));

				// Parse bundle services JSON if exists
				String bundleServicesJson = rs.getString("bundle_service");
				if (bundleServicesJson != null) {
					JsonReader jsonReader = Json.createReader(new StringReader(bundleServicesJson));
					JsonArray bundleServicesArray = jsonReader.readArray();
					ArrayList<Service> bundleServices = new ArrayList<>();

					for (JsonValue serviceValue : bundleServicesArray) {
						JsonObject serviceObj = (JsonObject) serviceValue;
						Service service = new Service();
						service.setServiceName(serviceObj.getString("service"));
						service.setPrice(Float.parseFloat(serviceObj.get("price").toString()));
						service.setImageUrl(serviceObj.getString("img_url"));
						bundleServices.add(service);
					}
					transaction.setBundleServices(bundleServices);
				}

				transaction.setDiscount(rs.getInt("discount_percent"));
				transaction.setSubTotal(rs.getDouble("subtotal"));
				transaction.setStatus(rs.getString("transaction_status"));

				// Get timestamp and convert to LocalDateTime
				Timestamp paidAt = rs.getTimestamp("paid_at");
				if (paidAt != null) {
					transaction.setPaidDate(paidAt.toLocalDateTime());
				}

				transactions.add(transaction);
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			conn.close();
		}

		return transactions;
	}

	public ArrayList<Transaction> getCartsByUserID(Integer userId) throws SQLException {
		Connection conn = DB.connect();
		ArrayList<Transaction> transactions = new ArrayList<>();

		try {
			String sqlStr = """
					    SELECT t.*, ts.start_time, t.start_date as booking_date,
					           t.status as transaction_status
					    FROM transaction t
					    JOIN time_slot ts ON t.slot_id = ts.slot_id
					    WHERE t.user_id = ?
					    AND t.status = 'PENDING'
					    ORDER BY t.created_at DESC;
					""";

			PreparedStatement pstmt = conn.prepareStatement(sqlStr);
			pstmt.setInt(1, userId);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				Transaction transaction = new Transaction();
				transaction.setId(rs.getInt("trans_id"));
				transaction.setUserId(rs.getInt("user_id"));

				// Create Address object
				Address address = new Address();
				address.setAddress(rs.getString("address"));
				address.setPostalCode(rs.getInt("postal_code"));
				address.setUnit(rs.getString("unit"));
				transaction.setAddress(address);

				transaction.setTimeSlot(rs.getTime("start_time").toLocalTime());
				transaction.setStartDate(rs.getTimestamp("booking_date").toLocalDateTime().toLocalDate());

				// Parse services JSON if exists
				String servicesJson = rs.getString("services");
				if (servicesJson != null) {
					JsonReader jsonReader = Json.createReader(new StringReader(servicesJson));
					JsonArray servicesArray = jsonReader.readArray();
					ArrayList<Service> services = new ArrayList<>();

					for (JsonValue serviceValue : servicesArray) {
						JsonObject serviceObj = (JsonObject) serviceValue;
						Service service = new Service();
						service.setServiceName(serviceObj.getString("service"));
						service.setPrice(Float.parseFloat(serviceObj.get("price").toString()));
						service.setImageUrl(serviceObj.getString("img_url"));
						services.add(service);
					}
					transaction.setServices(services);
				}

				// Set bundle info if exists
				transaction.setBundleName(rs.getString("bundle_name"));
				transaction.setBundle_img(rs.getString("bundle_img"));

				// Parse bundle services JSON if exists
				String bundleServicesJson = rs.getString("bundle_service");
				if (bundleServicesJson != null) {
					JsonReader jsonReader = Json.createReader(new StringReader(bundleServicesJson));
					JsonArray bundleServicesArray = jsonReader.readArray();
					ArrayList<Service> bundleServices = new ArrayList<>();

					for (JsonValue serviceValue : bundleServicesArray) {
						JsonObject serviceObj = (JsonObject) serviceValue;
						Service service = new Service();
						service.setServiceName(serviceObj.getString("service"));
						service.setPrice(Float.parseFloat(serviceObj.get("price").toString()));
						service.setImageUrl(serviceObj.getString("img_url"));
						bundleServices.add(service);
					}
					transaction.setBundleServices(bundleServices);
				}

				transaction.setDiscount(rs.getInt("discount_percent"));
				transaction.setSubTotal(rs.getDouble("subtotal"));
				transaction.setStatus(rs.getString("transaction_status"));

				// Get timestamp and convert to LocalDateTime
				Timestamp paidAt = rs.getTimestamp("paid_at");
				if (paidAt != null) {
					transaction.setPaidDate(paidAt.toLocalDateTime());
				}

				transactions.add(transaction);
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			conn.close();
		}

		return transactions;
	}

	public void insertTransaction(Integer userId, String address, Integer postalCode, String unit, Integer slotId,
			LocalDate startDate, String servicesJson, String bundleName, String bundleImg, String bundleServicesJson,
			Integer discountPercent, String status, Double subtotal, LocalDateTime paidAt) throws SQLException {
		Connection conn = DB.connect();
		try {
			String sqlStr = """
					INSERT INTO transaction (
					user_id, address, postal_code, unit, slot_id,
					start_date, services, bundle_name, bundle_img,
					bundle_service, discount_percent, status, subtotal, paid_at
					) VALUES (?, ?, ?, ?, ?, ?, ?::jsonb, ?, ?, ?::jsonb, ?, ?::transaction_status, ?, ?);
					""";

			PreparedStatement pstmt = conn.prepareStatement(sqlStr);

			pstmt.setInt(1, userId);
			pstmt.setString(2, address);
			pstmt.setInt(3, postalCode);
			pstmt.setString(4, unit);
			pstmt.setInt(5, slotId);
			pstmt.setDate(6, java.sql.Date.valueOf(startDate));
			pstmt.setString(7, servicesJson);
			pstmt.setString(8, bundleName);
			pstmt.setString(9, bundleImg);
			pstmt.setString(10, bundleServicesJson);
			pstmt.setInt(11, discountPercent);
			pstmt.setString(12, status);
			pstmt.setDouble(13, subtotal);

			if (paidAt == null) {
				pstmt.setNull(14, java.sql.Types.TIMESTAMP);
			} else {
				pstmt.setTimestamp(14, java.sql.Timestamp.valueOf(paidAt));
			}

			pstmt.executeUpdate();

		} catch (SQLException e) {
			throw e;
		} finally {
			conn.close();
		}
	}

	public int updateTransactionStatus(Integer transactionId, String status) throws SQLException {
		Connection conn = DB.connect();
		int rowsAffected = 0;
		try {
			String sqlStr = """
					UPDATE transaction
					SET status = ?::transaction_status
					WHERE
					trans_id = ?;
					""";

			PreparedStatement pstmt = conn.prepareStatement(sqlStr);
			pstmt.setString(1, status);
			pstmt.setInt(2, transactionId);

			rowsAffected = pstmt.executeUpdate();
			return rowsAffected;
		} catch (SQLException e) {
			throw e;
		} finally {
			conn.close();
		}
	}
}
