/*
    Name: Thiha Swan Htet
    Class: DIT/FT/2B/01
    Admin No: p2336671
*/

package models.user;

public class User {
	private int id = 0;
	private String customerId = "";
	private String firstName = "";
	private String lastName = "";
	private String imageURL = "";
	private String email = "";
	private String password = "";
	private String phoneNo = "";
	private int role = 0;

	// Implicit Constructor
	public User() {
		this.id = 0;
		this.customerId = "";
		this.firstName = "";
		this.lastName = "";
		this.imageURL = "";
		this.email = "";
		this.password = "";
		this.phoneNo = "";
		this.role = 0;
	}

	// Explicit Constructor
	public User(int id, String customer_id, String firstName, String lastName, String imageURL, String email, String password,
			String phoneNo, int role) {

		// Booting Up
		this.id = id;
		this.customerId = customer_id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.imageURL = imageURL;
		this.email = email;
		this.password = password;
		this.phoneNo = phoneNo;
		this.role = role;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
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

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}
}
