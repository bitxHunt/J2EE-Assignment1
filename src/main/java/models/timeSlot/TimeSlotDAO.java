/*
    Name: Thiha Swan Htet
    Class: DIT/FT/2B/01
    Admin No: p2336671
*/

package models.timeSlot;

import util.DB;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import models.user.UserDAO;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;

public class TimeSlotDAO {

	// Get Week Dates to display on the booking page
	public ArrayList<LocalDate> getWeekDates() {
		ArrayList<LocalDate> dates = new ArrayList<>();

		for (int i = 0; i < 7; i++) {
			dates.add(LocalDate.now().plusDays(i));
		}

		return dates;
	}

	// Get slots and the available capacity for the booking page
	// When user picks a date, this function will run to display the slots.
	public ArrayList<TimeSlot> getSlotsByDate(LocalDate chosenDate) throws SQLException {

		Connection conn = DB.connect();
		ArrayList<TimeSlot> timeSlots = new ArrayList<TimeSlot>();
		UserDAO userDB = new UserDAO();

		if (chosenDate == null) {
			chosenDate = LocalDate.now();
		}

		try {
			String sqlStr = "SELECT * FROM time_slot;";
			PreparedStatement pstmt = conn.prepareStatement(sqlStr);
			ResultSet rs = pstmt.executeQuery();

			int rowNumber = 0;
			while (rs.next()) {

				// Add row count for looping the time slots
				rowNumber++;
				
				TimeSlot timeSlot = new TimeSlot();
				timeSlot.setId(rs.getInt("id"));
				timeSlot.setStartTime(rs.getTime("start_time").toLocalTime());
				timeSlot.setEndTime(rs.getTime("end_time").toLocalTime());

				int totalStaffs = userDB.getTotalStaffs();
				int bookedStaffs = userDB.bookedStaffForDate(chosenDate, rowNumber);
				int capacity = totalStaffs - bookedStaffs;

				timeSlot.setCapacity(capacity);

				timeSlots.add(timeSlot);
			}

		} catch (Exception e) {
			System.out.println("Error Getting Available Slots On the Chosen Date.");
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return timeSlots;
	}

	public TimeSlot getTimeSlotById(int timeSlotId) throws SQLException {

		Connection conn = DB.connect();
		TimeSlot timeSlot = new TimeSlot();

		try {
			String sqlStr = "SELECT * FROM time_slot WHERE id = ?;";
			PreparedStatement pstmt = conn.prepareStatement(sqlStr);
			pstmt.setInt(1, timeSlotId);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				timeSlot.setId(rs.getInt("id"));
				timeSlot.setStartTime(rs.getTime("start_time").toLocalTime());
				timeSlot.setEndTime(rs.getTime("end_time").toLocalTime());
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return timeSlot;
	}

	// public TimeSlot getTimeSlotByTime(LocalTime time) throws SQLException {

	// 	Connection conn = DB.connect();
	// 	TimeSlot timeSlot = new TimeSlot();

	// 	try {
	// 		String sqlStr = "SELECT slot_id FROM time_slot WHERE start_time = ?;";
	// 		PreparedStatement pstmt = conn.prepareStatement(sqlStr);
	// 		pstmt.setTime(1, Time.valueOf(time));
	// 		ResultSet rs = pstmt.executeQuery();

	// 		if (rs.next()) {
	// 			timeSlot.setId(rs.getInt("slot_id"));
	// 		}

	// 	} catch (Exception e) {
	// 		e.printStackTrace();
	// 	} finally {
	// 		conn.close();
	// 	}
	// 	return timeSlot;
	// }

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
