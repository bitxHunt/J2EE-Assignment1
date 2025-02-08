/*
    Name: Thiha Swan Htet
    Class: DIT/FT/2B/01
    Admin No: p2336671
*/

package models.booking;

import java.time.LocalDate;
import java.util.ArrayList;

import models.address.Address;
import models.bundle.Bundle;
import models.payment.Payment;
import models.service.Service;
import models.timeSlot.TimeSlot;
import models.user.User;

public class Booking {
	private int id;
	private LocalDate date;
	private TimeSlot timeSlot;
	private User user;
	private Address address;
	private ArrayList<Service> services;
	private Bundle bundle;
	private String status;
	private Payment payment;

	public Booking() {
		this.id = 0;
		this.status = "";
		this.date = LocalDate.now();
		this.timeSlot = null;
		this.user = null;
		this.address = null;
		this.services = null;
		this.bundle = null;
		this.payment = null;
	}

	public Booking(int id, String status, LocalDate date, TimeSlot timeSlot, User user, Address address,
			ArrayList<Service> services, Bundle bundle, Payment payment) {
		this.id = id;
		this.status = status;
		this.date = date;
		this.timeSlot = timeSlot;
		this.user = user;
		this.address = address;
		this.services = services;
		this.bundle = bundle;
		this.payment = payment;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDate getDate() {
		return date;
	}

	public ArrayList<Service> getServices() {
		return services;
	}

	public void setServices(ArrayList<Service> services) {
		this.services = services;
	}

	public Bundle getBundle() {
		return bundle;
	}

	public void setBundle(Bundle bundle) {
		this.bundle = bundle;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public TimeSlot getTimeSlot() {
		return timeSlot;
	}

	public void setTimeSlot(TimeSlot timeSlot) {
		this.timeSlot = timeSlot;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}
}
