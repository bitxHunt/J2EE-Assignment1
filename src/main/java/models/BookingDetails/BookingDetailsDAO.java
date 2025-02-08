/***********************************************************
 * Name: Soe Zaw Aung, Scott
 * Class: DIT/FT/2B/01
 * Admin No: P2340474
 ************************************************************/
package models.BookingDetails;

import util.DB;
import java.sql.*;
import java.util.ArrayList;
import java.time.LocalDateTime;

public class BookingDetailsDAO {
    
    public ArrayList<BookingDetails> getAllBookingsWithAssignment() throws SQLException {
        ArrayList<BookingDetails> bookings = new ArrayList<>();
        Connection conn = null;
        
        try {
            conn = DB.connect();
            String sql = "SELECT cb.id, cb.status, cb.date, cb.created_at, " +
                        "ts.start_time, ts.end_time, " +
                        "a.street, a.unit, a.postal_code, " +
                        "u.phone_no, " +
                        "staff.email as staff_email " +
                        "FROM customer_booking cb " +
                        "LEFT JOIN time_slot ts ON cb.slot_id = ts.id " +
                        "LEFT JOIN address a ON cb.address_id = a.id " +
                        "LEFT JOIN users u ON cb.user_id = u.id " +
                        "LEFT JOIN staff_booking sb ON cb.id = sb.customer_booking_id " +
                        "LEFT JOIN users staff ON sb.staff_id = staff.id " +
                        "ORDER BY cb.created_at DESC";
                        
            PreparedStatement pstmt = conn.prepareStatement(sql);
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
            if (conn != null) conn.close();
        }
        return bookings;
    }
    
    public boolean assignStaffToBooking(int bookingId, int staffId, int slotId) throws SQLException {
        Connection conn = null;
        try {
            conn = DB.connect();
            String sql = "INSERT INTO staff_booking (staff_id, slot_id, customer_booking_id) VALUES (?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, staffId);
            pstmt.setInt(2, slotId);
            pstmt.setInt(3, bookingId);
            
            return pstmt.executeUpdate() > 0;
        } finally {
            if (conn != null) conn.close();
        }
    }
}