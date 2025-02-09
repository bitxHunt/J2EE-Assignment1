/*
    Name: Thiha Swan Htet
    Class: DIT/FT/2B/01
    Admin No: p2336671
*/

package models.booking;

import models.service.*;
import models.bundle.*;
import models.payment.Payment;
import models.user.*;
import models.address.*;
import models.status.*;
import models.timeSlot.*;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import util.DB;

public class BookingDAO {

	// Get Booking By id
	public Booking getBookingById(int bookingId) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Booking booking = null;

		try {
			conn = DB.connect();
			String sqlStr = """
					    SELECT b.*, t.start_time, t.end_time,
					           a.street, a.unit, a.postal_code,
					           p.amount, p.status as payment_status,
					           p.payment_intent_id, p.paid_at, p.refund_id
					    FROM customer_booking b
					    LEFT JOIN time_slot t ON b.slot_id = t.id
					    LEFT JOIN address a ON b.address_id = a.id
					    LEFT JOIN payment p ON p.booking_id = b.id
					    WHERE b.id = ?
					""";

			pstmt = conn.prepareStatement(sqlStr);
			pstmt.setInt(1, bookingId);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				booking = new Booking();
				TimeSlot timeSlot = new TimeSlot();
				Address address = new Address();
				Payment payment = new Payment();

				// Set booking details
				booking.setId(rs.getInt("id"));
				booking.setDate(rs.getDate("date").toLocalDate());
				booking.setStatus(rs.getString("status"));

				// Set time slot
				timeSlot.setStartTime(rs.getTime("start_time").toLocalTime());
				timeSlot.setEndTime(rs.getTime("end_time").toLocalTime());
				booking.setTimeSlot(timeSlot);

				// Set address
				address.setAddress(rs.getString("street"));
				address.setUnit(rs.getString("unit"));
				address.setPostalCode(rs.getInt("postal_code"));
				booking.setAddress(address);

				// Set payment
				payment.setAmount(rs.getFloat("amount"));
				payment.setStatus(rs.getString("payment_status"));
				booking.setPayment(payment);

				// Get services
				loadBookingServices(conn, booking);
			}

			return booking;
		} finally {
			if (rs != null)
				rs.close();
			if (pstmt != null)
				pstmt.close();
			if (conn != null)
				conn.close();
		}
	}

	// Helper method to load services
	private void loadBookingServices(Connection conn, Booking booking) throws SQLException {
		String sqlServices = """
				    SELECT s.*, bs.amount
				    FROM booking_service bs
				    JOIN service s ON bs.service_id = s.id
				    WHERE bs.booking_id = ?
				""";

		try (PreparedStatement pstmt = conn.prepareStatement(sqlServices)) {
			pstmt.setInt(1, booking.getId());
			ResultSet rs = pstmt.executeQuery();

			ArrayList<Service> services = new ArrayList<>();
			while (rs.next()) {
				Service service = new Service();
				service.setServiceId(rs.getInt("id"));
				service.setServiceName(rs.getString("name"));
				service.setPrice(rs.getFloat("amount"));
				services.add(service);
			}
			booking.setServices(services);
		}
	}

	// Get All Bookings by User
	public ArrayList<Booking> getAllBookings(int userId) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<Booking> bookings = new ArrayList<Booking>();

		try {
			conn = DB.connect();
			// Main booking query with basic joins including payment
			String sqlStr = "SELECT b.*, t.start_time, t.end_time, " + "a.street, a.unit, a.postal_code, "
					+ "p.amount, p.status as payment_status, p.payment_intent_id, " + "p.paid_at, p.refund_id "
					+ "FROM customer_booking b " + "LEFT JOIN time_slot t ON b.slot_id = t.id "
					+ "LEFT JOIN address a ON b.address_id = a.id " + "LEFT JOIN payment p ON p.booking_id = b.id "
					+ "WHERE b.user_id = ? " + "ORDER BY b.date DESC, b.created_at DESC";

			pstmt = conn.prepareStatement(sqlStr);
			pstmt.setInt(1, userId);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Booking booking = new Booking();
				TimeSlot timeSlot = new TimeSlot();
				Address address = new Address();
				Payment payment = new Payment();

				// Set booking details
				booking.setId(rs.getInt("id"));
				booking.setDate(rs.getDate("date").toLocalDate());
				booking.setStatus(rs.getString("status"));

				// Set time slot
				timeSlot.setStartTime(rs.getTime("start_time").toLocalTime());
				timeSlot.setEndTime(rs.getTime("end_time").toLocalTime());
				booking.setTimeSlot(timeSlot);

				// Set address
				address.setAddress(rs.getString("street"));
				address.setUnit(rs.getString("unit"));
				address.setPostalCode(rs.getInt("postal_code"));
				booking.setAddress(address);

				// Set payment details
				payment.setAmount(rs.getFloat("amount"));
				payment.setStatus(rs.getString("payment_status"));
				booking.setPayment(payment);

				// Get services for this booking
				String sqlServices = "SELECT s.*, bs.amount FROM booking_service bs "
						+ "JOIN service s ON bs.service_id = s.id " + "WHERE bs.booking_id = ?";

				PreparedStatement pstmtServices = conn.prepareStatement(sqlServices);
				pstmtServices.setInt(1, booking.getId());
				ResultSet rsServices = pstmtServices.executeQuery();

				ArrayList<Service> services = new ArrayList<>();
				while (rsServices.next()) {
					Service service = new Service();
					service.setServiceId(rsServices.getInt("id"));
					service.setServiceName(rsServices.getString("name"));
					service.setPrice(rsServices.getFloat("amount"));
					services.add(service);
				}
				booking.setServices(services);

				// Get bundle if exists
				String sqlBundle = "SELECT b.*, bb.discount_percent FROM booking_bundle bb "
						+ "JOIN bundle b ON bb.bundle_id = b.id " + "WHERE bb.booking_id = ?";

				PreparedStatement pstmtBundle = conn.prepareStatement(sqlBundle);
				pstmtBundle.setInt(1, booking.getId());
				ResultSet rsBundle = pstmtBundle.executeQuery();

				if (rsBundle.next()) {
					Bundle bundle = new Bundle();
					bundle.setBundleId(rsBundle.getInt("id"));
					bundle.setBundleName(rsBundle.getString("name"));
					bundle.setDiscountPercent(rsBundle.getInt("discount_percent"));
					booking.setBundle(bundle);
				}

				rsBundle.close();
				pstmtBundle.close();
				rsServices.close();
				pstmtServices.close();

				bookings.add(booking);
			}

		} catch (Exception e) {
			System.out.println("Error getting bookings: " + e.getMessage());
			e.printStackTrace();
		} finally {
			if (rs != null)
				rs.close();
			if (pstmt != null)
				pstmt.close();
			if (conn != null)
				conn.close();
		}
		return bookings;
	}

	// Insert into customer_booking table
	public void createBooking(int userId, int addressId, int timeSlotId, LocalDate bookingDate,
			ArrayList<Service> services, Bundle bundle) throws SQLException {

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Booking booking = new Booking();
		float totalAmount = 0;

		try {
			conn = DB.connect();
			conn.setAutoCommit(false);

			// Insert into customer_booking first
			String sqlInsertBooking = "INSERT INTO customer_booking (date, slot_id, user_id, address_id) "
					+ "VALUES (?, ?, ?, ?) RETURNING id;";

			pstmt = conn.prepareStatement(sqlInsertBooking);
			pstmt.setDate(1, Date.valueOf(bookingDate));
			pstmt.setInt(2, timeSlotId);
			pstmt.setInt(3, userId);
			pstmt.setInt(4, addressId);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				int bookingId = rs.getInt("id");
				booking.setId(bookingId);

				// Insert Standalone booking services if any and calculate service total
				if (services != null && !services.isEmpty()) {
					String sqlInsertService = "INSERT INTO booking_service (booking_id, service_id, amount) "
							+ "VALUES (?, ?, ?);";

					// Calculate total from all services
					for (Service service : services) {
						pstmt = conn.prepareStatement(sqlInsertService);
						pstmt.setInt(1, bookingId);
						pstmt.setInt(2, service.getServiceId());
						pstmt.setFloat(3, service.getPrice());
						pstmt.executeUpdate();

						totalAmount += service.getPrice();
					}

					System.out.println("Total Service Amount: " + totalAmount);
				}

				// Insert booking bundle if exists and apply discount
				if (bundle != null) {
					String sqlInsertBundle = "INSERT INTO booking_bundle (booking_id, bundle_id, discount_percent) "
							+ "VALUES (?, ?, ?);";

					pstmt = conn.prepareStatement(sqlInsertBundle);
					pstmt.setInt(1, bookingId);
					pstmt.setInt(2, bundle.getBundleId());
					pstmt.setInt(3, bundle.getDiscountPercent());
					pstmt.executeUpdate();

					// Insert all services that belong to the bundle
					String sqlInsertBundleService = "INSERT INTO booking_service (booking_id, service_id, amount) "
							+ "VALUES (?, ?, ?);";

					for (Service bundleService : bundle.getServices()) {
						pstmt = conn.prepareStatement(sqlInsertBundleService);
						pstmt.setInt(1, bookingId);
						pstmt.setInt(2, bundleService.getServiceId());
						pstmt.setFloat(3, bundleService.getPrice());
						pstmt.executeUpdate();
					}

					System.out.println("Total Bundle Amount: " + bundle.getDiscountedPrice());
					totalAmount += bundle.getDiscountedPrice();
				}

				// Insert into payment table with just booking_id and amount
				String sqlInsertPayment = "INSERT INTO payment (booking_id, amount) VALUES (?, ?);";

				pstmt = conn.prepareStatement(sqlInsertPayment);
				pstmt.setInt(1, bookingId);
				pstmt.setFloat(2, totalAmount);
				pstmt.executeUpdate();

				// Commit the transaction
				conn.commit();
			}

		} catch (Exception e) {
			if (conn != null) {
				try {
					conn.rollback();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
			throw new SQLException("Error creating booking: " + e.getMessage());
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
	}

	public boolean completeBooking(int bookingId, int userId) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = DB.connect();
			conn.setAutoCommit(false);

			// Get booking details
			Booking booking = getBookingById(bookingId);
			if (booking == null)
				return false;

			// Check ownership and status
			if (!booking.getStatus().equals("CONFIRMED"))
				return false;

			// Create LocalDateTime for comparison
			LocalDateTime serviceEndTime = LocalDateTime.of(booking.getDate(), booking.getTimeSlot().getEndTime());

			// Check if current time is after service end time
			if (LocalDateTime.now().isBefore(serviceEndTime)) {
				return false;
			}

			// Update booking status
			String updateSql = """
					    UPDATE customer_booking
					    SET status = 'COMPLETED',
					        completed_at = CURRENT_TIMESTAMP
					    WHERE id = ? AND user_id = ?
					""";

			pstmt = conn.prepareStatement(updateSql);
			pstmt.setInt(1, bookingId);
			pstmt.setInt(2, userId);

			int rowsAffected = pstmt.executeUpdate();

			if (rowsAffected > 0) {
				conn.commit();
				return true;
			} else {
				conn.rollback();
				return false;
			}

		} catch (Exception e) {
			if (conn != null)
				conn.rollback();
			throw e;
		} finally {
			if (pstmt != null)
				pstmt.close();
			if (conn != null)
				conn.close();
		}
	}
}
