package Shopping_App;

public class CartItem {

    private final Product product;
    private int quantity;

    // Create Cart Item
    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    // Get Product
    public Product getProduct() {
        return product;
    }

    // Get Selected Quantity
    public int getQuantity() {
        return quantity;
    }

    // Increase Quantity
    public void increaseQuantity(int quantity) {
        this.quantity += quantity;
    }

    // Calculate Individual Product Total
    public double calculateItemTotal() {
        return product.getPrice() * quantity;
    }

    // Display Selected Product Details
    public void displayItemDetails() {

        System.out.println("Product ID    : "
                + product.getProductId());

        System.out.println("Product Name  : "
                + product.getProductName());

        System.out.println("Quantity      : " + quantity);

        System.out.println("Individual Price : Rs. "
                + product.getPrice());

        System.out.println("Item Total    : Rs. "
                + calculateItemTotal());

        System.out.println("-----------------------------------");
    }
}
