package models.bundle;

import models.service.Service;
import java.util.ArrayList;

public class Bundle {
	private int bundleId;
	private String bundleName;
	private int discountPercent;
	private ArrayList<Service> services;
	private float originalPrice;
	private float discountedPrice;
	private String imageUrl;
	private boolean isActive;

	public Bundle() {
		this.services = new ArrayList<>();
	}

	public Bundle(int bundleId, String bundleName, int discountPercent) {
		this.bundleId = bundleId;
		this.bundleName = bundleName;
		this.discountPercent = discountPercent;
		this.services = new ArrayList<>();
	}

	public Bundle(int bundleId, String bundleName, int discountPercent, ArrayList<Service> services, String imageUrl) {
		this.bundleId = bundleId;
		this.bundleName = bundleName;
		this.discountPercent = discountPercent;
		this.services = services;
		this.imageUrl = imageUrl;

	}

	public Bundle(int bundleId, String bundleName, int discountPercent, ArrayList<Service> services, String imageUrl,
			boolean isActive) {
		this.bundleId = bundleId;
		this.bundleName = bundleName;
		this.discountPercent = discountPercent;
		this.services = services;
		this.imageUrl = imageUrl;
		this.isActive = isActive;

	}

	public int getBundleId() {
		return bundleId;
	}

	public void setBundleId(int bundleId) {
		this.bundleId = bundleId;
	}

	public String getBundleName() {
		return bundleName;
	}

	public void setBundleName(String bundleName) {
		this.bundleName = bundleName;
	}

	public int getDiscountPercent() {
		return discountPercent;
	}

	public void setDiscountPercent(int discountPercent) {
		this.discountPercent = discountPercent;
	}

	public ArrayList<Service> getServices() {
		return services;
	}

	public void setServices(ArrayList<Service> services) {
		this.services = services;
	}

	public void addService(Service service) {
		this.services.add(service);
	}

	public float getOriginalPrice() {
		return originalPrice;
	}

	public void setOriginalPrice(float originalPrice) {
		this.originalPrice = originalPrice;
	}

	public float getDiscountedPrice() {
		return discountedPrice;
	}

	public void setDiscountedPrice(float discountedPrice) {
		this.discountedPrice = discountedPrice;
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