package models.transaction;

public class Transaction {
	private Integer id = 0;
	private String firstName = "";
	private String lastName = "";
	private String email = "";
	private String password = "";
	private int role = 0;   
	private String phoneNo = "";

	// Implicit Constructor
	public Transaction() {
		this.id = 0;
		this.firstName = "";
		this.lastName = "";
		this.email = "";
		this.password = "";
		this.role = 0;
		this.phoneNo = "";
	}

	// Explicit Constructor
	public Transaction(Integer id, String firstName, String lastName, String email, String password, int role, String phoneNo) {

		// Booting Up
		this.id = 0;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.role = role;
		this.phoneNo = phoneNo;
	}
}
