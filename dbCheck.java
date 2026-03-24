import java.sql.Connection;
import java.sql.DriverManager;

public class dbCheck {
    public static void main(String[] args) {
        // Change 'your_password' to the one you set in the MySQL Installer
        String url = "jdbc:mysql://localhost:3306/banking_system"; 
        String user = "root";
        String password = "#Rishita08!"; 

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            System.out.println("✅ Connection Successful! You are ready for the Philips project.");
        } catch (Exception e) {
            System.out.println("❌ Connection Failed. Check your password or if the MySQL service is running.");
            e.printStackTrace();
        }
    }
}