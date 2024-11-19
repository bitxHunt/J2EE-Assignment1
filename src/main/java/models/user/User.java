package models.user;

public class User {
	private String first_name = "";
	private String last_name = "";
	private String email = "";
	private String password = "";
	private int role = 0;

	// Implicit Constructor
	public User() {
		this.first_name = "";
		this.last_name = "";
		this.email = "";
		this.password = "";
		this.role = 0;
	}
	
	// Explicit Constructor
	public User(String first_name, String last_name, String email, String password, int role) {

		// Booting Up
		this.first_name = first_name;
		this.last_name = last_name;
		this.email = email;
		this.password = password;
		this.role = role;
	}

	public String getFirstName() {
		return first_name;
	}

	public void setFirstName(String first_name) {
		this.first_name = first_name;
	}

	public String getLastName() {
		return last_name;
	}

	public void setLastName(String last_name) {
		this.last_name = last_name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}
}
