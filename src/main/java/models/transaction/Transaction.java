package models.transaction;

import models.service.Service;
import models.address.Address;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Transaction {
	private Integer id = 0;
	private Integer userId = 0;
	private Address address = new Address();
	private Integer slotId = 0;
	private LocalDate startDate = LocalDate.now();
	private ArrayList<Service> services = new ArrayList<Service>();
	private String bundleName = "";
	private String bundle_img = "";
	private ArrayList<Service> bundleServices = new ArrayList<Service>();
	private Integer discount = 0;
	private Double subTotal = 0.0;
	private LocalDateTime paidDate = LocalDateTime.now();

	// Implicit Constructor
	public Transaction() {
		this.id = 0;
		this.userId = 0;
		this.address = new Address();
		this.slotId = 0;
		this.startDate = LocalDate.now();
		this.services = new ArrayList<Service>();
		this.bundleName = "";
		this.bundle_img = "";
		this.bundleServices = new ArrayList<Service>();
		this.discount = 0;
		this.subTotal = 0.0;
		this.paidDate = LocalDateTime.now();
	}

	// Explicit Constructor
	public Transaction(Integer id, Integer userId, Address address, Integer slotId, LocalDate startDate,
			ArrayList<Service> services, String bundleName, String bundle_img, ArrayList<Service> bundleServices,
			Integer discount, Double subTotal, LocalDateTime paidDate) {

		// Booting Up
		this.id = id;
		this.userId = userId;
		this.address = address;
		this.slotId = slotId;
		this.startDate = startDate;
		this.services = services;
		this.bundleName = bundleName;
		this.bundle_img = bundle_img;
		this.bundleServices = bundleServices;
		this.discount = discount;
		this.subTotal = subTotal;
		this.paidDate = paidDate;
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

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Integer getSlotId() {
		return slotId;
	}

	public void setSlotId(Integer slotId) {
		this.slotId = slotId;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public ArrayList<Service> getServices() {
		return services;
	}

	public void setServices(ArrayList<Service> services) {
		this.services = services;
	}

	public String getBundleName() {
		return bundleName;
	}

	public void setBundleName(String bundleName) {
		this.bundleName = bundleName;
	}

	public String getBundle_img() {
		return bundle_img;
	}

	public void setBundle_img(String bundle_img) {
		this.bundle_img = bundle_img;
	}

	public ArrayList<Service> getBundleServices() {
		return bundleServices;
	}

	public void setBundleServices(ArrayList<Service> bundleServices) {
		this.bundleServices = bundleServices;
	}

	public Integer getDiscount() {
		return discount;
	}

	public void setDiscount(Integer discount) {
		this.discount = discount;
	}

	public Double getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(Double subTotal) {
		this.subTotal = subTotal;
	}

	public LocalDateTime getPaidDate() {
		return paidDate;
	}

	public void setPaidDate(LocalDateTime paidDate) {
		this.paidDate = paidDate;
	}
}
