package models.category;

import java.util.ArrayList;

public class Category {
	private int categoryId;
	private String categoryName;
	private ArrayList<String> services;
	private int serviceCount;

	// Default Constructor
	public Category() {
		this.categoryId = 0;
		this.categoryName = "";
		this.services = null;
		this.serviceCount = 0;
	}

	// Explicit Constructors
	public Category(int categoryId, String categoryName) {
		this.categoryId = categoryId;
		this.categoryName = categoryName;
		this.services = null;
		this.serviceCount = 0;
	}

	public Category(int categoryId, String categoryName, ArrayList<String> Services, int serviceCount) {
		this.categoryId = categoryId;
		this.categoryName = categoryName;
		this.services = services;
		this.serviceCount = serviceCount;
	}

	// Getters and Setters
	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public ArrayList<String> getServices() {
		return services;
	}

	public void setServices(ArrayList<String> services) {
		this.services = services;
	}

	public int getServiceCount() {
		return serviceCount;
	}

	public void setServiceCount(int serviceCount) {
		this.serviceCount = serviceCount;
	}

}