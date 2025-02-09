package models.request;

import models.booking.Booking;
import models.emailService.EmailService;
import models.user.User;

public class Request {
	private int id;
	private String token;
	private User user;
	private EmailService emailService;
	private Booking booking;
	private String qrCodeUrl;
	private Boolean status;

	public Request() {
		this.id = 0;
		this.token = "";
		this.user = null;
		this.emailService = null;
		this.booking = null;
		this.qrCodeUrl = "";
		this.status = null;
	}

	public Request(int id, String token, User user, EmailService emailService, Booking booking, String qrCodeUrl,
			Boolean status) {
		this.id = id;
		this.token = token;
		this.user = user;
		this.emailService = emailService;
		this.booking = booking;
		this.qrCodeUrl = qrCodeUrl;
		this.status = status;
	}

	public int getId() {
		return id;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Booking getBooking() {
		return booking;
	}

	public void setBooking(Booking booking) {
		this.booking = booking;
	}

	public String getQrCodeUrl() {
		return qrCodeUrl;
	}

	public void setQrCodeUrl(String qrCodeUrl) {
		this.qrCodeUrl = qrCodeUrl;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public EmailService getEmailService() {
		return emailService;
	}

	public void setEmailService(EmailService emailService) {
		this.emailService = emailService;
	}

}
