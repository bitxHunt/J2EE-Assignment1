/*
    Name: Thiha Swan Htet
    Class: DIT/FT/2B/01
    Admin No: p2336671
*/

package models.booking;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

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
}
