/*
    Name: Thiha Swan Htet
    Class: DIT/FT/2B/01
    Admin No: p2336671
*/

package models.address;

public class Address {
	private int id = 0;
	private int userId = 0;
	private AddressType addType = null;
	private String address = "";
	private Integer postalCode = 0;
	private String unit = "";

	// Implicit Constructor
	public Address() {
		this.id = 0;
		this.userId = 0;
		this.addType = null;
		this.address = "";
		this.postalCode = 0;
		this.unit = "";
	}

	// Explicit Constructor
	public Address(Integer id, Integer userId, AddressType addType, String address, Integer postalCode, String unit,
			Boolean isActive) {

		// Booting Up
		this.id = id;
		this.userId = userId;
		this.addType = addType;
		this.address = address;
		this.postalCode = postalCode;
		this.unit = unit;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public AddressType getAddType() {
		return addType;
	}

	public void setAddType(AddressType addType) {
		this.addType = addType;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(Integer postalCode) {
		this.postalCode = postalCode;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
}
