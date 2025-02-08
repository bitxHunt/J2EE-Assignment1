package models.payment;

public class Payment {
	private int id;
	private String status;
	private String refundId;
	private String paymentIntentId;
	private float amount;
	private int bookingId;

	// Implicit Constructor
	public Payment() {
		this.id = 0;
		this.status = "";
		this.refundId = "";
		this.paymentIntentId = "";
		this.amount = 0;
		this.bookingId = 0;
	}

	// Explicit Constructor
	public Payment(int id, String status, String refundId, String paymentIntentId, float amount, int bookingId) {

		// Booting Up
		this.id = id;
		this.status = status;
		this.refundId = refundId;
		this.paymentIntentId = paymentIntentId;
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

	public String getRefundId() {
		return refundId;
	}

	public void setRefundId(String refundId) {
		this.refundId = refundId;
	}

	public String getPaymentIntentId() {
		return paymentIntentId;
	}

	public void setPaymentIntentId(String paymentIntentId) {
		this.paymentIntentId = paymentIntentId;
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
