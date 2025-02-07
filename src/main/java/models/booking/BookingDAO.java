/*
    Name: Thiha Swan Htet
    Class: DIT/FT/2B/01
    Admin No: p2336671
*/

package models.booking;

import models.service.*;
import models.bundle.*;
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
import java.util.ArrayList;

import util.DB;

public class BookingDAO {
	// Seed Customer Booking Data
//	public void seedData(Booking booking) throws SQLException {
//		Connection conn = DB.connect();
//
//		try {
//			String sqlStr = "CALL seed_customer_booking(?, ?, ?, ?);";
//			CallableStatement stmt = conn.prepareCall(sqlStr);
//
//			stmt.setString(1, booking.getStatus());
//			stmt.setString(2, booking.get);
//			stmt.setString(3, organization.getSecretKey());
//			stmt.setInt(4, organization.getUser().getId());
//
//			stmt.execute();
//
//		} catch (Exception e) {
//			System.out.println("Error Seeding User Data.");
//			e.printStackTrace();
//		} finally {
//			conn.close();
//		}
//	}

	// Insert into customer_booking table
	public void createBooking(int userId, int addressId, int timeSlotId, LocalDate bookingDate,
			ArrayList<Service> services, Bundle bundle) throws SQLException {

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Booking booking = new Booking();

		try {
			conn = DB.connect();
			conn.setAutoCommit(false);

			// Insert into customer_booking first
			String sqlInsertBooking = "INSERT INTO customer_booking (date, slot_id, user_id, address_id, status_id) "
					+ "VALUES (?, ?, ?, ?, 3) RETURNING id;";

			pstmt = conn.prepareStatement(sqlInsertBooking);
			pstmt.setDate(1, Date.valueOf(bookingDate));
			pstmt.setInt(2, timeSlotId);
			pstmt.setInt(3, userId);
			pstmt.setInt(4, addressId);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				int bookingId = rs.getInt("id");
				booking.setId(bookingId);

				// Insert booking services if any
				if (services != null && !services.isEmpty()) {
					String sqlInsertService = "INSERT INTO booking_service (booking_id, service_id, amount) "
							+ "VALUES (?, ?, ?);";

					// Insert additional services if any
					for (int i = 1; i < services.size(); i++) {
						Service service = services.get(i);
						pstmt = conn.prepareStatement(sqlInsertService);
						pstmt.setInt(1, bookingId);
						pstmt.setInt(2, service.getServiceId());
						pstmt.setFloat(3, service.getPrice());
						pstmt.executeUpdate();
					}
				}

				// Insert booking bundle if exists
				if (bundle != null) {
					String sqlInsertBundle = "INSERT INTO booking_bundle (booking_id, bundle_id, discount_percent);"
							+ "VALUES (?, ?, ?);";

					// Insert additional services if any
					pstmt = conn.prepareStatement(sqlInsertBundle);
					pstmt.setInt(1, bookingId);
					pstmt.setInt(2, bundle.getBundleId());
					pstmt.setInt(3, bundle.getDiscountPercent());
				}

				// Commit the transaction
				conn.commit();
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
	}

	// Insert bundle into booking
//	public void bookingBundle() throws SQLException {
//		Connection conn = DB.connect();
//
//	}
}
