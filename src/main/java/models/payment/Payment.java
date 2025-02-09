package models.payment;

public class Payment {
	private int id;
	private String status;
	private float amount;
	private int bookingId;

	// Implicit Constructor
	public Payment() {
		this.id = 0;
		this.status = "";
		this.amount = 0;
		this.bookingId = 0;
	}

	// Explicit Constructor
	public Payment(int id, String status, String refundId, String paymentIntentId, float amount, int bookingId) {

		// Booting Up
		this.id = id;
		this.status = status;
		this.amount = amount;
		this.bookingId = bookingId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public int getBookingId() {
		return bookingId;
	}

	public void setBookingId(int bookingId) {
		this.bookingId = bookingId;
	}

}
