package OS_System;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseTest {

    public static void main(String[] args) {

        try (Connection con =
                     DatabaseConnection.getConnection()) {

            System.out.println(
                    "Database Connected Successfully!"
            );

        } catch (SQLException e) {

            System.out.println(
                    "Database Connection Failed."
            );

            e.printStackTrace();
        }
    }
}
