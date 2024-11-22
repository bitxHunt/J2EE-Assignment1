package models.category;

public class Category {
    private int categoryId;
    private String categoryName;
    
    // Default Constructor
    public Category() {
        this.categoryId = 0;
        this.categoryName = "";
    }
    
    // Explicit Constructor
    public Category(int categoryId, String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
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
}