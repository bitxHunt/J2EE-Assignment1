/*
    Name: Thiha Swan Htet
    Class: DIT/FT/2B/01
    Admin No: p2336671
*/

package models.timeSlot;

import java.time.LocalTime;

public class TimeSlot {
	private int id = 0;
	private LocalTime startTime = LocalTime.now();
	private LocalTime endTime = LocalTime.now();
	private int capacity = 0;

	// Implicit Constructor
	public TimeSlot() {
		this.id = 0;
		this.startTime = LocalTime.now();
		this.endTime = LocalTime.now();
		this.capacity = 0;
	}

	// Explicit Constructor
	public TimeSlot(Integer id, LocalTime startTime, LocalTime endTime, int capacity) {

		// Booting Up
		this.id = id;
		this.startTime = startTime;
		this.endTime = endTime;
		this.capacity = capacity;
	}

	public Integer getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}
	
	public LocalTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}
	
	public Integer getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

}
