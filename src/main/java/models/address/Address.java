package models.address;

public class Address {
	private Integer id = 0;
	private Integer userId = 0;
	private String addType = "";
	private String address = "";
	private Integer postalCode = 0;
	private String unit = "";
	private Boolean isActive = false;

	// Implicit Constructor
	public Address() {
		this.id = 0;
		this.userId = 0;
		this.addType = "";
		this.address = "";
		this.postalCode = 0;
		this.unit = "";
		this.isActive = false;
	}

		// Explicit Constructor
	public Address(Integer id, Integer userId, String addType, String address, Integer postalCode, String unit,
			Boolean isActive) {

		// Booting Up
		this.id = id;
		this.userId = userId;
		this.addType = addType;
		this.address = address;
		this.postalCode = postalCode;
		this.unit = unit;
		this.isActive = isActive;
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

	public String getAddType() {
		return addType;
	}

	public void setAddType(String addType) {
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

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
}
