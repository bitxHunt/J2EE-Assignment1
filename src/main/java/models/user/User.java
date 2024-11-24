package models.user;

public class User {
	private Integer id = 0;
	private String firstName = "";
	private String lastName = "";
	private String imageURL = "";
	private String email = "";
	private String password = "";
	private Integer role = 0;
	private String phoneNo = "";

	// Implicit Constructor
	public User() {
		this.id = 0;
		this.firstName = "";
		this.lastName = "";
		this.imageURL = "";
		this.email = "";
		this.password = "";
		this.role = 0;
		this.phoneNo = "";
	}

	// Explicit Constructor
	public User(Integer id, String firstName, String lastName, String imageURL, String email, String password, Integer role, String phoneNo) {

		// Booting Up
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.imageURL = imageURL;
		this.email = email;
		this.password = password;
		this.role = role;
		this.phoneNo = phoneNo;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
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

	public Integer getRole() {
		return role;
	}

	public void setRole(Integer role) {
		this.role = role;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
}
