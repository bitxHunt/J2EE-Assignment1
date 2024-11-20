package models.bundle;

public class Bundle {
    private int bundleId;
    private String bundleName;
    private int discountPercent;

    // Default constructor
    public Bundle() {
    }

    //Explicit Constructor with all fields
    public Bundle(int bundleId, String bundleName, int discountPercent) {
        this.bundleId = bundleId;
        this.bundleName = bundleName;
        this.discountPercent = discountPercent;
    }

    // Getters and Setters
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

}