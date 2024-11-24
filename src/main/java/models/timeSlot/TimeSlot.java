package models.timeSlot;

import java.time.LocalTime;

public class TimeSlot {
	private Integer id = 0;
	private LocalTime slotTime = LocalTime.now();

	// Implicit Constructor
	public TimeSlot() {
		this.id = 0;
		this.slotTime = LocalTime.now();
	}

	// Explicit Constructor
	public TimeSlot(Integer id, LocalTime slotTime) {

		// Booting Up
		this.id = id;
		this.slotTime = slotTime;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public LocalTime getSlotTime() {
		return slotTime;
	}

	public void setSlotTime(LocalTime slotTime) {
		this.slotTime = slotTime;
	}

}
