/*
    Name: Thiha Swan Htet
    Class: DIT/FT/2B/01
    Admin No: p2336671
*/

package models.timeSlot;

import util.DB;
import java.time.LocalTime;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;

public class TimeSlotDAO {
	public TimeSlot getTimeSlotByTime(LocalTime time) throws SQLException {

		Connection conn = DB.connect();
		TimeSlot timeSlot = new TimeSlot();
		try {
			String sqlStr = "SELECT slot_id FROM time_slot WHERE start_time = ?;";
			PreparedStatement pstmt = conn.prepareStatement(sqlStr);
			pstmt.setTime(1, Time.valueOf(time));
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				timeSlot.setId(rs.getInt("slot_id"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return timeSlot;
	}
}
