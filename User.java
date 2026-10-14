package Shopping_App;

public class User {

    private final String username;
    private final String password;

    // Create User Object
    public User(String username, String password) {
        this.username = username.trim();
        this.password = password;
    }

    // Get Username
    public String getUsername() {
        return username;
    }

    // Validate Password
    public boolean checkPassword(String inputPassword) {
        return password.equals(inputPassword);
    }
}