/***********************************************************
* Name: Soe Zaw Aung, Scott
* Class: DIT/FT/2B/01
* Admin No: P2340474
* Description: Model class to store service information
************************************************************/
package models.service;

public class Service {
	private int serviceId;
	private String serviceName;
	private String serviceDescription;
	private int categoryId;
	private String categoryName;
	private float price;
	private String imageUrl;
	private boolean isActive;

	// Default Constructor
	public Service() {
		this.serviceId = 0;
		this.serviceName = "";
		this.serviceDescription = "";
		this.categoryId = 0;
		this.price = 0.0f;
		this.categoryName = "";
		this.imageUrl = "";
		this.isActive = true;
	}

	// Explicit Constructors
	public Service(int serviceId, String serviceName, String serviceDescription, int categoryId, float price) {
		this.serviceId = serviceId;
		this.serviceName = serviceName;
		this.serviceDescription = serviceDescription;
		this.categoryId = categoryId;
		this.price = price;
		this.categoryName = "";
	}

	public Service(int serviceId, String serviceName, String serviceDescription, int categoryId, float price,
			String categoryName, String imageUrl, Boolean isActive) {
		this.serviceId = serviceId;
		this.serviceName = serviceName;
		this.serviceDescription = serviceDescription;
		this.categoryId = categoryId;
		this.price = price;
		this.categoryName = categoryName;
		this.imageUrl = imageUrl;
		this.isActive = isActive;
	}

	public Service(int serviceId, String serviceName, String serviceDescription, int categoryId, float price,
			String categoryName, String imageUrl, boolean isActive) {
		this.serviceId = serviceId;
		this.serviceName = serviceName;
		this.serviceDescription = serviceDescription;
		this.categoryId = categoryId;
		this.price = price;
		this.categoryName = categoryName;
		this.imageUrl = imageUrl;
		this.isActive = isActive;
	}

	// Getters and Setters
	public int getServiceId() {
		return serviceId;
	}

	public void setServiceId(int serviceId) {
		this.serviceId = serviceId;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getServiceDescription() {
		return serviceDescription;
	}

	public void setServiceDescription(String serviceDescription) {
		this.serviceDescription = serviceDescription;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}
}
