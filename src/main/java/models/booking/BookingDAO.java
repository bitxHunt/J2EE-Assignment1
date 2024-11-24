/*
    Name: Thiha Swan Htet
    Class: DIT/FT/2B/01
    Admin No: p2336671
*/

package models.booking;

import util.*;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class BookingDAO {
	public List<Booking> getAvailableSlots() throws SQLException {

		Connection conn = DB.connect();
		List<Booking> availableSlots = new ArrayList<>();
		try {
			String sqlStr = "SELECT * FROM get_available_slots(CURRENT_DATE, 7)";
			PreparedStatement pstmt = conn.prepareStatement(sqlStr);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				Booking booking = new Booking();
				booking.setDate(rs.getDate("slot_date"));

				// Convert the PostgreSQL TIME[] to List<LocalTime>
				Array timeArray = rs.getArray("slot_times");
				if (timeArray != null) {
					Time[] times = (Time[]) timeArray.getArray();
					List<LocalTime> slots = new ArrayList<>();
                    for (Time time : times) {
                        // Convert java.sql.Time to LocalTime
                    	slots.add(time.toLocalTime());
                    }
                    booking.setSlots(slots);
				}
				availableSlots.add(booking);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return availableSlots;
	}
}
