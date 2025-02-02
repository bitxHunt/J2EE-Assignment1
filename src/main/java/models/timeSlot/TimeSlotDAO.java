/*
    Name: Thiha Swan Htet
    Class: DIT/FT/2B/01
    Admin No: p2336671
*/

package models.timeSlot;

import util.DB;
import java.time.LocalTime;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;

public class TimeSlotDAO {
//	public TimeSlot getTimeSlotByTime(LocalTime time) throws SQLException {
//
//		Connection conn = DB.connect();
//		TimeSlot timeSlot = new TimeSlot();
//		try {
//			String sqlStr = "SELECT slot_id FROM time_slot WHERE start_time = ?;";
//			PreparedStatement pstmt = conn.prepareStatement(sqlStr);
//			pstmt.setTime(1, Time.valueOf(time));
//			ResultSet rs = pstmt.executeQuery();
//
//			if (rs.next()) {
//				timeSlot.setId(rs.getInt("slot_id"));
//			}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			conn.close();
//		}
//		return timeSlot;
//	}

	// Seed the overall data from the csv file
	public void seedData(TimeSlot timeSlot) throws SQLException {
		Connection conn = DB.connect();

		try {
			String sqlStr = "CALL seed_time_slot(?, ?);";
			CallableStatement stmt = conn.prepareCall(sqlStr);

			stmt.setTime(1, Time.valueOf(timeSlot.getStartTime()));
			stmt.setTime(2, Time.valueOf(timeSlot.getEndTime()));

			stmt.execute();

		} catch (Exception e) {
			System.out.println("Error Seeding Time Slot Data.");
			e.printStackTrace();
		} finally {
			conn.close();
		}
	}
}
