package OS_System;

import java.util.ArrayList;

public class Cart {

    private final ArrayList<CartItem> cartItems =
            new ArrayList<>();

    // Add Product to Cart and Reduce Available Stock
    public boolean addProduct(Product product, int quantity) {

        // Validate Product
        if (product == null) {
            System.out.println("Product Not Found.");
            return false;
        }

        // Validate Quantity
        if (quantity <= 0) {
            System.out.println(
                    "Quantity Must Be Greater Than Zero."
            );
            return false;
        }

        // Check Available Stock
        if (quantity > product.getAvailableQuantity()) {

            System.out.println("Insufficient Stock.");
            System.out.println(
                    "Available Quantity : "
                            + product.getAvailableQuantity()
            );

            return false;
        }

        // Reduce Stock Immediately
        if (!product.reduceStock(quantity)) {
            System.out.println("Unable to Update Stock.");
            return false;
        }

        // Check Whether Product Already Exists in Cart
        for (CartItem item : cartItems) {

            if (item.getProduct().getProductId()
                    == product.getProductId()) {

                item.increaseQuantity(quantity);

                System.out.println(
                        "Product Quantity Updated Successfully."
                );

                System.out.println(
                        "Remaining Stock : "
                                + product.getAvailableQuantity()
                );

                return true;
            }
        }

        // Add New Product to Cart
        cartItems.add(new CartItem(product, quantity));

        System.out.println(
                "Product Added to Cart Successfully."
        );

        System.out.println(
                "Remaining Stock : "
                        + product.getAvailableQuantity()
        );

        return true;
    }

    // Cancel Product and Restore Its Stock
    public boolean removeProduct(int productId) {

        for (int i = 0; i < cartItems.size(); i++) {

            CartItem item = cartItems.get(i);

            if (item.getProduct().getProductId() == productId) {

                // Restore the Cancelled Product Quantity
                item.getProduct().addStock(item.getQuantity());

                // Remove Product from Cart
                cartItems.remove(i);

                System.out.println(
                        "Product Cancelled Successfully."
                );

                System.out.println(
                        "Restored Stock : "
                                + item.getProduct().getAvailableQuantity()
                );

                return true;
            }
        }

        System.out.println("Product ID Not Found in Cart.");
        return false;
    }

    // Display All Cart Items
    public void displayCart() {

        if (cartItems.isEmpty()) {
            System.out.println("Your Cart Is Empty.");
            return;
        }

        System.out.println("\n========== SHOPPING CART ==========");

        for (CartItem item : cartItems) {
            item.displayItemDetails();
        }

        System.out.println(
                "Total Bill : Rs. " + calculateTotal()
        );
    }

    // Calculate Total Bill
    public double calculateTotal() {

        double total = 0;

        for (CartItem item : cartItems) {
            total += item.calculateItemTotal();
        }

        return total;
    }

    // Check Whether Cart Is Empty
    public boolean isEmpty() {
        return cartItems.isEmpty();
    }

    // Restore All Remaining Cart Stock If Order Is Cancelled
    public void releaseAllStock() {

        for (CartItem item : cartItems) {
            item.getProduct().addStock(item.getQuantity());
        }

        cartItems.clear();

        System.out.println(
                "Cart Cancelled. All Selected Stock Has Been Restored."
        );
    }

    // Clear Cart After Successful Order
    // Do Not Restore Stock Here
    public void clearCart() {
        cartItems.clear();
    }
}
