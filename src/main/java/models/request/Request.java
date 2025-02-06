package models.request;

import models.emailService.EmailService;
import models.user.User;

public class Request {
	private int id;
	private String token;
	private User user;
	private EmailService emailService;

	public Request() {
		this.id = 0;
		this.token = "";
		this.user = null;
		this.emailService = null;
	}

	public int getId() {
		return id;
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
