/***********************************************************
 * Name: Soe Zaw Aung, Scott
 * Class: DIT/FT/2B/01
 * Admin No: P2340474
 ************************************************************/
package models.BookingDetails;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.LocalTime;

public class BookingDetails {
	private String status;
	private LocalDate scheduledDate;
	private LocalDateTime bookedAt;
	private LocalTime startTime;
	private LocalTime endTime;
	private String street;
	private String unit;
	private int postalCode;
	private String customerPhone;
	private String assignedStaffEmail;
	private int bookingId;
	private boolean isAssigned;
	private boolean isInHouse; 

	// Default constructor
	public BookingDetails() {
	}

	// Getters and setters
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDate getScheduledDate() {
		return scheduledDate;
	}

	public void setScheduledDate(LocalDate scheduledDate) {
		this.scheduledDate = scheduledDate;
	}

	public LocalDateTime getBookedAt() {
		return bookedAt;
	}

	public void setBookedAt(LocalDateTime bookedAt) {
		this.bookedAt = bookedAt;
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

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public int getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(int postalCode) {
		this.postalCode = postalCode;
	}

	public String getCustomerPhone() {
		return customerPhone;
	}

	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}

	public String getAssignedStaffEmail() {
		return assignedStaffEmail;
	}

	public void setAssignedStaffEmail(String assignedStaffEmail) {
		this.assignedStaffEmail = assignedStaffEmail;
	}

	public int getBookingId() {
		return bookingId;
	}

	public void setBookingId(int bookingId) {
		this.bookingId = bookingId;
	}

	public boolean isAssigned() {
		return isAssigned;
	}

	public void setAssigned(boolean assigned) {
		isAssigned = assigned;
	}
	
	public boolean isInHouse() {
	    return isInHouse;
	}

	public void setInHouse(boolean inHouse) {
	    isInHouse = inHouse;
	}
}