package Shopping_App;

import java.util.ArrayList;
import java.util.Scanner;

public class Main_Shopping {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        ArrayList<User> users = new ArrayList<>();
        ArrayList<Product> products = new ArrayList<>();

        // Sample Product Inventory
        products.add(new Product(101, "Mouse", 499, 10));
        products.add(new Product(102, "Keyboard", 999, 8));
        products.add(new Product(103, "Headphones", 1499, 5));
        products.add(new Product(104, "USB Cable", 199, 20));
        products.add(new Product(105, "Laptop Stand", 799, 6));

        products.add(new Product(106, "Laptop", 49999, 5));
        products.add(new Product(107, "Smartphone", 15999, 10));
        products.add(new Product(108, "Tablet", 12999, 7));
        products.add(new Product(109, "Monitor", 8999, 6));
        products.add(new Product(110, "Webcam", 2499, 8));

        products.add(new Product(111, "Speakers", 1799, 12));
        products.add(new Product(112, "Power Bank", 1499, 15));
        products.add(new Product(113, "Charger", 699, 20));
        products.add(new Product(114, "Wireless Mouse", 799, 14));
        products.add(new Product(115, "Mechanical Keyboard", 2499, 6));

        products.add(new Product(116, "External SSD", 5999, 5));
        products.add(new Product(117, "Pen Drive", 499, 25));
        products.add(new Product(118, "Router", 1999, 9));
        products.add(new Product(119, "Microphone", 2999, 7));
        products.add(new Product(120, "Gaming Controller", 3499, 8));

        System.out.println("======================================");
        System.out.println("       SHOPPING MANAGEMENT APP");
        System.out.println("======================================");

        // Login or Register
        User user = authenticate(sc, users);

        if (user == null) {
            System.out.println("Application Closed.");
            sc.close();
            return;
        }

        System.out.println(
                "\nWelcome To Our System, " + user.getUsername() + "!"
        );

        Cart cart = new Cart();

        // Display Products and Accept User Input
        while (true) {

            displayProducts(products);

            System.out.println();

            System.out.print(
                    "Enter Product Name (NULL to Finish Shopping): "
            );

            String productName = sc.nextLine().trim();

            // Finish Shopping
            if (productName.equalsIgnoreCase("NULL")) {
                break;
            }

            // Find Selected Product
            Product selectedProduct =
                    findProductByName(products, productName);

            if (selectedProduct == null) {
                System.out.println("Product Not Found.");
                continue;
            }

            // Check Whether Product Is Available
            if (selectedProduct.getAvailableQuantity() == 0) {
                System.out.println("This Product Is Out of Stock.");
                continue;
            }

            // Enter Selected Quantity
            int quantity = readInt(sc, "Enter Quantity : ");

            if (quantity <= 0) {
                System.out.println(
                        "Quantity Must Be Greater Than Zero."
                );
                continue;
            }

            // Add Product and Reduce Stock Immediately
            boolean added = cart.addProduct(
                    selectedProduct, quantity
            );

            if (added) {

                System.out.println("\nSelected Product Details:");

                selectedProduct.displayProductDetails();

                System.out.println(
                        "Selected Quantity : " + quantity
                );

                System.out.println(
                        "Selected Item Total : Rs. "
                                + selectedProduct.getPrice() * quantity
                );
            }

            System.out.print("\nAdd Another Product? (Y/N) : ");

            String more = sc.nextLine().trim();

            if (more.equalsIgnoreCase("N")) {
                break;
            }
        }

        // Check Whether Cart Is Empty
        if (cart.isEmpty()) {

            System.out.println("Your Cart Is Empty.");
            System.out.println("Thank You For Visiting.");

            sc.close();
            return;
        }

        // Display Cart Before Cancellation
        cart.displayCart();

        // Cancel Products If Required
        while (!cart.isEmpty()) {

            System.out.print(
                    "\nDo You Want to Cancel Any Item? (Y/N) : "
            );

            String choice = sc.nextLine().trim();

            if (!choice.equalsIgnoreCase("Y")) {
                break;
            }

            cart.displayCart();

            int productId = readInt(
                    sc, "Enter Product ID to Cancel : "
            );

            cart.removeProduct(productId);

            if (cart.isEmpty()) {
                System.out.println("Your Cart Is Empty.");
                break;
            }

            cart.displayCart();
        }

        // Stop If All Items Were Cancelled
        if (cart.isEmpty()) {

            System.out.println("No Products to Purchase.");

            sc.close();
            return;
        }

        // Select Payment Method
        System.out.println("\n========== PAYMENT METHOD ==========");
        System.out.println("1. Cash on Delivery");
        System.out.println("2. Online Payment");

        int paymentChoice = readInt(
                sc, "Select Payment Method : "
        );

        PaymentMethod paymentMethod;

        switch (paymentChoice) {

            case 1:
                paymentMethod = new CashOnDelivery();
                break;

            case 2:
                paymentMethod = new OnlinePayment();
                break;

            default:
                System.out.println("Invalid Payment Method.");

                // Restore Stock Because the Order Is Not Confirmed
                cart.releaseAllStock();

                sc.close();
                return;
        }

        // Calculate Final Bill
        double total = cart.calculateTotal();

        // Process Payment
        boolean paymentSuccessful =
                paymentMethod.processPayment(total, sc);

        // Restore Stock If Payment Is Cancelled
        if (!paymentSuccessful) {

            System.out.println(
                    "Order Not Confirmed. Payment Was Cancelled."
            );

            cart.releaseAllStock();

            sc.close();
            return;
        }

        // Stock Is Already Deducted. Do Not Deduct It Again.

        // Display Final Bill
        System.out.println("\n======================================");
        System.out.println("              FINAL BILL");
        System.out.println("======================================");

        cart.displayCart();

        System.out.println(
                "Customer Name : " + user.getUsername()
        );

        System.out.println(
                "Payment Method : " + paymentMethod.getMethodName()
        );

        System.out.println("Final Amount : Rs. " + total);
        System.out.println("Order Status : CONFIRMED");

        System.out.println("======================================");
        System.out.println("Thank You For Shopping!");

        // Clear Cart Without Restoring Stock
        cart.clearCart();

        sc.close();
    }


    // Register or Login
    static User authenticate(
            Scanner sc, ArrayList<User> users) {

        while (true) {

            System.out.println("\n1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");

            int choice = readInt(sc, "Enter Choice : ");

            switch (choice) {

                case 1:

                    System.out.print("Enter Username : ");

                    String username = sc.nextLine().trim();

                    if (username.isEmpty()) {
                        System.out.println(
                                "Username Cannot Be Empty."
                        );
                        break;
                    }

                    if (findUser(users, username) != null) {
                        System.out.println(
                                "Username Already Exists."
                        );
                        break;
                    }

                    System.out.print("Create Password : ");

                    String password = sc.nextLine();

                    if (password.isEmpty()) {
                        System.out.println(
                                "Password Cannot Be Empty."
                        );
                        break;
                    }

                    users.add(new User(username, password));

                    System.out.println(
                            "Registration Successful. Please Login."
                    );

                    break;

                case 2:

                    System.out.print("Enter Username : ");

                    String loginName = sc.nextLine().trim();

                    System.out.print("Enter Password : ");

                    String loginPassword = sc.nextLine();

                    User user = findUser(users, loginName);

                    if (user != null
                            && user.checkPassword(loginPassword)) {

                        System.out.println("Login Successful.");
                        return user;
                    }

                    System.out.println(
                            "Invalid Username or Password."
                    );

                    break;

                case 3:
                    return null;

                default:
                    System.out.println("Invalid Choice.");
            }
        }
    }


    // Find Registered User
    static User findUser(
            ArrayList<User> users, String username) {

        for (User user : users) {

            if (user.getUsername().equalsIgnoreCase(username)) {
                return user;
            }
        }

        return null;
    }


    // Display Available Products
    static void displayProducts(ArrayList<Product> products) {

        System.out.println("\n========== AVAILABLE PRODUCTS ==========");

        for (Product product : products) {
            product.displayProductDetails();
        }

        System.out.println("========================================");
    }


    // Find Product Using Product Name
    static Product findProductByName(
            ArrayList<Product> products, String productName) {

        for (Product product : products) {

            if (product.getProductName()
                    .equalsIgnoreCase(productName)) {

                return product;
            }
        }

        return null;
    }


    // Read Integer Without Crashing on Invalid Input
    static int readInt(Scanner sc, String message) {

        while (true) {

            System.out.print(message);

            String input = sc.nextLine().trim();

            try {
                return Integer.parseInt(input);
            }
            catch (NumberFormatException e) {

                System.out.println(
                        "Please Enter a Valid Number."
                );
            }
        }
    }
}