package models.organization;

import models.user.User;

public class Organization {
	private int id;
	private String name;
	private String accessKey;
	private String secretKey;
	private User user;

	public Organization() {
		this.id = 0;
		this.name = "";
		this.accessKey = "";
		this.secretKey = "";
	}

	public Organization(int id, String name, String accessKey, String secretKey) {
		this.id = id;
		this.name = name;
		this.accessKey = accessKey;
		this.secretKey = secretKey;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAccessKey() {
		return accessKey;
	}

	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
