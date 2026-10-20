package OS_System;

public class Product {

    private final int productId;
    private final String productName;
    private final double price;
    private int availableQuantity;

    // Create Product Object
    public Product(int productId, String productName,
                   double price, int availableQuantity) {

        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.availableQuantity = availableQuantity;
    }

    // Get Product ID
    public int getProductId() {
        return productId;
    }

    // Get Product Name
    public String getProductName() {
        return productName;
    }

    // Get Product Price
    public double getPrice() {
        return price;
    }

    // Get Available Quantity
    public int getAvailableQuantity() {
        return availableQuantity;
    }

    // Reduce Stock When Product Is Added to Cart
    public boolean reduceStock(int quantity) {

        if (quantity <= 0 || quantity > availableQuantity) {
            return false;
        }

        availableQuantity -= quantity;
        return true;
    }

    // Restore Stock When Product Is Cancelled
    public void addStock(int quantity) {

        if (quantity > 0) {
            availableQuantity += quantity;
        }
    }

    // Display Product Details
    public void displayProductDetails() {

        System.out.println("-----------------------------------");
        System.out.println("Product ID         : " + productId);
        System.out.println("Product Name       : " + productName);
        System.out.println("Product Price      : Rs. " + price);
        System.out.println("Available Quantity : " + availableQuantity);
    }
}
