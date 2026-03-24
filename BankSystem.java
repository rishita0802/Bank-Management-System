import java.sql.*;

public class BankSystem {
    // Database credentials
    private final String url = "jdbc:mysql://localhost:3306/banking_system";
    private final String user = "root";
    private final String password = "your_password"; // Change to your MySQL password

    public BankSystem() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Driver not found: " + e.getMessage());
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    // 1. CREATE ACCOUNT
    public void createAccount(String accNum, String name, double initialDeposit, int pin) {
        String query = "INSERT INTO accounts (account_number, holder_name, balance, pin) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, accNum);
            pstmt.setString(2, name);
            pstmt.setDouble(3, initialDeposit);
            pstmt.setInt(4, pin);
            pstmt.executeUpdate();
            System.out.println("SUCCESS: Account created for " + name);
            recordTransaction(conn, accNum, "Account Opened: +$" + initialDeposit);
        } catch (SQLException e) {
            System.out.println("ERROR: Account number already exists or Database error.");
        }
    }

    // 2. DEPOSIT
    public void performDeposit(String accNum, double amount) {
        String query = "UPDATE accounts SET balance = balance + ? WHERE account_number = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setDouble(1, amount);
            pstmt.setString(2, accNum);
            if (pstmt.executeUpdate() > 0) {
                System.out.println("SUCCESS: Deposit complete.");
                recordTransaction(conn, accNum, "Deposit: +$" + amount);
            } else {
                System.out.println("ERROR: Account not found.");
            }
        } catch (SQLException e) { e.printStackTrace(); }
    }

    // 3. WITHDRAW
    public void performWithdraw(String accNum, int pin, double amount) {
        String checkSql = "SELECT balance FROM accounts WHERE account_number = ? AND pin = ?";
        String updateSql = "UPDATE accounts SET balance = balance - ? WHERE account_number = ?";
        try (Connection conn = getConnection()) {
            try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
                checkStmt.setString(1, accNum);
                checkStmt.setInt(2, pin);
                ResultSet rs = checkStmt.executeQuery();
                if (rs.next()) {
                    double currentBalance = rs.getDouble("balance");
                    if (currentBalance >= amount) {
                        try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                            updateStmt.setDouble(1, amount);
                            updateStmt.setString(2, accNum);
                            updateStmt.executeUpdate();
                            System.out.println("SUCCESS: Withdrawal complete. New Balance: $" + (currentBalance - amount));
                            recordTransaction(conn, accNum, "Withdrawal: -$" + amount);
                        }
                    } else { System.out.println("ERROR: Insufficient funds."); }
                } else { System.out.println("ERROR: Invalid details."); }
            }
        } catch (SQLException e) { e.printStackTrace(); }
    }

    // 4. TRANSFER (Uses SQL Transactions for Safety)
    public void performTransfer(String fromAcc, int pin, String toAcc, double amount) {
        try (Connection conn = getConnection()) {
            conn.setAutoCommit(false); // Start Transaction
            try {
                // Step 1: Withdraw from sender
                String withdraw = "UPDATE accounts SET balance = balance - ? WHERE account_number = ? AND pin = ? AND balance >= ?";
                PreparedStatement ps1 = conn.prepareStatement(withdraw);
                ps1.setDouble(1, amount); ps1.setString(2, fromAcc); ps1.setInt(3, pin); ps1.setDouble(4, amount);
                
                if (ps1.executeUpdate() == 0) throw new SQLException("Sender error/Insufficient funds.");

                // Step 2: Deposit to receiver
                String deposit = "UPDATE accounts SET balance = balance + ? WHERE account_number = ?";
                PreparedStatement ps2 = conn.prepareStatement(deposit);
                ps2.setDouble(1, amount); ps2.setString(2, toAcc);
                
                if (ps2.executeUpdate() == 0) throw new SQLException("Recipient not found.");

                conn.commit(); // Save changes
                System.out.println("SUCCESS: Transfer complete!");
                recordTransaction(conn, fromAcc, "Transfer to " + toAcc + ": -$" + amount);
                recordTransaction(conn, toAcc, "Transfer from " + fromAcc + ": +$" + amount);
            } catch (SQLException e) {
                conn.rollback(); // Undo everything on failure
                System.out.println("ERROR: Transfer failed - " + e.getMessage());
            }
        } catch (SQLException e) { e.printStackTrace(); }
    }

    // 5. CHECK BALANCE
    public void checkBalance(String accNum, int pin) {
        String query = "SELECT holder_name, balance FROM accounts WHERE account_number = ? AND pin = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, accNum);
            pstmt.setInt(2, pin);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                System.out.println("\n--- ACCOUNT DETAILS ---");
                System.out.println("Holder: " + rs.getString("holder_name"));
                System.out.println("Balance: $" + rs.getDouble("balance"));
            } else { System.out.println("ERROR: Invalid details."); }
        } catch (SQLException e) { e.printStackTrace(); }
    }

    // 6. TRANSACTION HISTORY
    public void showTransactionHistory(String accNum, int pin) {
        // First verify identity
        String auth = "SELECT * FROM accounts WHERE account_number = ? AND pin = ?";
        String history = "SELECT description, timestamp FROM transactions WHERE account_number = ? ORDER BY id DESC";
        try (Connection conn = getConnection()) {
            PreparedStatement pAuth = conn.prepareStatement(auth);
            pAuth.setString(1, accNum); pAuth.setInt(2, pin);
            if (pAuth.executeQuery().next()) {
                PreparedStatement pHis = conn.prepareStatement(history);
                pHis.setString(1, accNum);
                ResultSet rs = pHis.executeQuery();
                System.out.println("\n--- HISTORY FOR " + accNum + " ---");
                while (rs.next()) {
                    System.out.println("[" + rs.getTimestamp("timestamp") + "] " + rs.getString("description"));
                }
            } else { System.out.println("ERROR: Invalid details."); }
        } catch (SQLException e) { e.printStackTrace(); }
    }

    // 7. ADMIN VIEW
    public void adminViewAllAccounts() {
        String query = "SELECT account_number, holder_name, balance FROM accounts";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            System.out.println("\n--- ADMIN: ALL ACCOUNTS ---");
            while (rs.next()) {
                System.out.printf("Acc: %-10s | Name: %-15s | Bal: $%.2f%n", 
                    rs.getString(1), rs.getString(2), rs.getDouble(3));
            }
        } catch (SQLException e) { e.printStackTrace(); }
    }
    public void searchAccount(String keyword) {
    // This query searches for the keyword in BOTH account_number and holder_name
    String query = "SELECT account_number, holder_name, balance FROM accounts " +
                   "WHERE account_number LIKE ? OR holder_name LIKE ?";

    try (Connection conn = getConnection();
         PreparedStatement pstmt = conn.prepareStatement(query)) {
        
        // The % wildcard means "contains this text anywhere"
        String searchPattern = "%" + keyword + "%";
        pstmt.setString(1, searchPattern);
        pstmt.setString(2, searchPattern);

        ResultSet rs = pstmt.executeQuery();
        
        System.out.println("\n--- SEARCH RESULTS ---");
        boolean found = false;
        while (rs.next()) {
            found = true;
            System.out.printf("Acc: %-10s | Name: %-20s | Balance: $%.2f%n", 
                rs.getString("account_number"), 
                rs.getString("holder_name"), 
                rs.getDouble("balance"));
        }

        if (!found) {
            System.out.println("No accounts matching '" + keyword + "' were found.");
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
}

    // Private helper to save history
    private void recordTransaction(Connection conn, String accNum, String msg) throws SQLException {
        String query = "INSERT INTO transactions (account_number, description) VALUES (?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, accNum);
            pstmt.setString(2, msg);
            pstmt.executeUpdate();
        }
    }
}