/*
    Name: Thiha Swan Htet
    Class: DIT/FT/2B/01
    Admin No: p2336671
*/

package models.booking;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.time.LocalTime;

public class Booking {
	private Date date;
	private List<LocalTime> slots;

	// Implicit Constructor
	public Booking() {
		this.date = new Date();
		this.slots = new ArrayList<>();
	}

	// Explicit Constructor
	public Booking(Date date, List<LocalTime> slots) {
		this.date = date;
		this.slots = slots;
	}

	// Getters and setters
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public List<LocalTime> getSlots() {
		return slots;
	}

	public void setSlots(List<LocalTime> slots) {
		this.slots = slots;
	}
}
