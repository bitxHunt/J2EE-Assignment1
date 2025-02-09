/***********************************************************
 * Name: Soe Zaw Aung, Scott
 * Class: DIT/FT/2B/01
 * Admin No: P2340474
 ************************************************************/
package models.BookingDetails;

import util.DB;
import java.sql.*;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class BookingDetailsDAO {
	private static final int PAGE_SIZE = 10;

	public ArrayList<BookingDetails> getAllBookings(int page, Integer postalCode, LocalDate startDate,
			LocalDate endDate) throws SQLException {
		ArrayList<BookingDetails> bookings = new ArrayList<>();
		Connection conn = null;

		try {
			conn = DB.connect();
			StringBuilder sql = new StringBuilder(
					"SELECT cb.id, cb.status, cb.date, cb.created_at, " + "ts.start_time, ts.end_time, "
							+ "a.street, a.unit, a.postal_code, " + "u.phone_no, " + "staff.email as staff_email "
							+ "FROM customer_booking cb " + "LEFT JOIN time_slot ts ON cb.slot_id = ts.id "
							+ "LEFT JOIN address a ON cb.address_id = a.id " + "LEFT JOIN users u ON cb.user_id = u.id "
							+ "LEFT JOIN staff_booking sb ON cb.id = sb.customer_booking_id "
							+ "LEFT JOIN users staff ON sb.staff_id = staff.id " + "WHERE 1=1");

			ArrayList<Object> params = new ArrayList<>();

			if (postalCode != null) {
		        sql.append(" AND CAST(a.postal_code AS VARCHAR) LIKE ?");
		        params.add(postalCode + "%"); 
		    }

			if (startDate != null && endDate != null) {
				sql.append(" AND cb.date BETWEEN ? AND ?");
				params.add(startDate);
				params.add(endDate);
			}

			sql.append(" ORDER BY cb.created_at DESC LIMIT ? OFFSET ?");
			params.add(PAGE_SIZE);
			params.add((page - 1) * PAGE_SIZE);

			PreparedStatement pstmt = conn.prepareStatement(sql.toString());

			// Set parameters
			for (int i = 0; i < params.size(); i++) {
				if (params.get(i) instanceof LocalDate) {
					pstmt.setDate(i + 1, java.sql.Date.valueOf((LocalDate) params.get(i)));
				} else {
					pstmt.setObject(i + 1, params.get(i));
				}
			}

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				BookingDetails booking = new BookingDetails();
				booking.setBookingId(rs.getInt("id"));
				booking.setStatus(rs.getString("status"));
				booking.setScheduledDate(rs.getDate("date").toLocalDate());
				booking.setBookedAt(rs.getTimestamp("created_at").toLocalDateTime());
				booking.setStartTime(rs.getTime("start_time").toLocalTime());
				booking.setEndTime(rs.getTime("end_time").toLocalTime());
				booking.setStreet(rs.getString("street"));
				booking.setUnit(rs.getString("unit"));
				booking.setPostalCode(rs.getInt("postal_code"));
				booking.setCustomerPhone(rs.getString("phone_no"));

				String staffEmail = rs.getString("staff_email");
				booking.setAssignedStaffEmail(staffEmail);
				booking.setAssigned(staffEmail != null);

				bookings.add(booking);
			}

		} finally {
			if (conn != null)
				conn.close();
		}
		return bookings;
	}

	public int getTotalBookings() throws SQLException {
		Connection conn = null;
		try {
			conn = DB.connect();
			String sql = "SELECT COUNT(*) as total FROM customer_booking";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				return rs.getInt("total");
			}
			return 0;
		} finally {
			if (conn != null)
				conn.close();
		}
	}

	public boolean assignStaffToBooking(int bookingId, int staffId) throws SQLException {
		Connection conn = null;
		try {
			conn = DB.connect();
			String sql = "CALL apply_for_booking_inhouse(?, ?)";
			CallableStatement stmt = conn.prepareCall(sql);
			stmt.setInt(1, bookingId);
			stmt.setInt(2, staffId);
			stmt.execute();
			return true;
		} catch (SQLException e) {
			throw new SQLException(e.getMessage());
		} finally {
			if (conn != null)
				conn.close();
		}
	}

	public boolean cancelBooking(int bookingId) throws SQLException {
		Connection conn = null;
		try {
			conn = DB.connect();
			String sql = "CALL cancel_booking(?)";
			CallableStatement stmt = conn.prepareCall(sql);
			stmt.setInt(1, bookingId);
			stmt.execute();
			return true;
		} catch (SQLException e) {
			throw e;
		} finally {
			if (conn != null)
				conn.close();
		}
	}

	public int getTotalBookings(Integer postalCode, LocalDate startDate, LocalDate endDate) throws SQLException {
	    Connection conn = null;
	    try {
	        conn = DB.connect();
	        StringBuilder sql = new StringBuilder("SELECT COUNT(*) as total FROM customer_booking cb "
	                + "LEFT JOIN address a ON cb.address_id = a.id WHERE 1=1");

	        ArrayList<Object> params = new ArrayList<>();
	        if (postalCode != null ) {
	            sql.append(" AND CAST(a.postal_code AS VARCHAR) LIKE ?");
	            params.add(postalCode+ "%");
	        }

	        if (startDate != null && endDate != null) {
	            sql.append(" AND cb.date BETWEEN ? AND ?");
	            params.add(startDate);
	            params.add(endDate);
	        }

	        PreparedStatement pstmt = conn.prepareStatement(sql.toString());

	        // Set parameters
	        for (int i = 0; i < params.size(); i++) {
	            if (params.get(i) instanceof LocalDate) {
	                pstmt.setDate(i + 1, java.sql.Date.valueOf((LocalDate) params.get(i)));
	            } else {
	                pstmt.setObject(i + 1, params.get(i));
	            }
	        }

	        ResultSet rs = pstmt.executeQuery();
	        if (rs.next()) {
	            return rs.getInt("total");
	        }
	        return 0;
	    } catch (SQLException e) {
	        throw e;
	    } finally {
	        if (conn != null)
	            conn.close();
	    }
	}
}
	