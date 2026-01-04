import java.io.*;
import java.util.*;

public class BankSystem {
    private Map<String, Account> accountMap = new HashMap<>();
    private final String FILE_NAME = "accounts.txt";

    public BankSystem() {
        loadFileData();
    }

    public void createAccount(String accNum, String name, double initialDeposit, int pin) {
        if (!accountMap.containsKey(accNum)) {
            accountMap.put(accNum, new Account(accNum, name, initialDeposit, pin));
            saveFileData();
            System.out.println("SUCCESS: Account created for " + name);
        } else {
            System.out.println("ERROR: Account number already exists.");
        }
    }

    public void performDeposit(String accNum, double amount) {
        Account acc = accountMap.get(accNum);
        if (acc != null) {
            acc.deposit(amount);
            saveFileData();
            System.out.println("SUCCESS: New Balance: $" + acc.getBalance());
        } else {
            System.out.println("ERROR: Account not found.");
        }
    }

    public void performWithdraw(String accNum, int pin, double amount) {
        Account acc = accountMap.get(accNum);
        if (acc != null && acc.getPin() == pin) {
            if (acc.withdraw(amount)) {
                saveFileData();
                System.out.println("SUCCESS: New Balance: $" + acc.getBalance());
            } else {
                System.out.println("ERROR: Insufficient funds.");
            }
        } else {
            System.out.println("ERROR: Invalid Account Number or PIN.");
        }
    }

    public void performTransfer(String fromAcc, int pin, String toAcc, double amount) {
        Account sender = accountMap.get(fromAcc);
        Account receiver = accountMap.get(toAcc);

        if (sender != null && receiver != null && sender.getPin() == pin) {
            if (sender.withdraw(amount)) {
                receiver.deposit(amount);
                saveFileData();
                System.out.println("SUCCESS: Transfer complete!");
            } else {
                System.out.println("ERROR: Insufficient funds.");
            }
        } else {
            System.out.println("ERROR: Invalid details or PIN.");
        }
    }

    public void checkBalance(String accNum, int pin) {
        Account acc = accountMap.get(accNum);
        if (acc != null && acc.getPin() == pin) {
            System.out.println("\n--- ACCOUNT DETAILS ---");
            System.out.println("Holder: " + acc.getAccountHolder());
            System.out.println("Balance: $" + acc.getBalance());
        } else {
            System.out.println("ERROR: Invalid Account Number or PIN.");
        }
    }

    private void saveFileData() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Account acc : accountMap.values()) {
                writer.println(acc.toString());
            }
        } catch (IOException e) {
            System.out.println("File Error: " + e.getMessage());
        }
    }

   // Add these two methods to your existing BankSystem class

public void showTransactionHistory(String accNum, int pin) {
    Account acc = accountMap.get(accNum);
    if (acc != null && acc.getPin() == pin) {
        System.out.println("\n--- TRANSACTION HISTORY FOR " + accNum + " ---");
        for (String record : acc.getHistory()) {
            System.out.println("- " + record);
        }
    } else {
        System.out.println("ERROR: Invalid Account Number or PIN.");
    }
}

// Update the loadFileData method to handle history strings
private void loadFileData() {
    try (Scanner fileScanner = new Scanner(new File(FILE_NAME))) {
        while (fileScanner.hasNextLine()) {
            String[] data = fileScanner.nextLine().split(",");
            Account acc = new Account(data[0], data[1], Double.parseDouble(data[2]), Integer.parseInt(data[3]));
            
            // If there is history data (the 5th column), load it
            if (data.length > 4) {
                String[] pastRecords = data[4].split(";");
                for (String r : pastRecords) {
                    acc.getHistory().add(r);
                }
            }
            accountMap.put(data[0], acc);
        }
    } catch (Exception e) { /* New file will be created */ }
}
public void adminViewAllAccounts() {
    if (accountMap.isEmpty()) {
        System.out.println("No accounts found in the system.");
        return;
    }
    System.out.println("\n--- ADMIN: ALL REGISTERED ACCOUNTS ---");
    System.out.printf("%-15s | %-20s | %-10s%n", "Acc Num", "Holder", "Balance");
    System.out.println("---------------------------------------------------------");
    
    double totalBankAssets = 0;
    for (Account acc : accountMap.values()) {
        System.out.printf("%-15s | %-20s | $%-10.2f%n", 
            acc.getAccountNumber(), acc.getAccountHolder(), acc.getBalance());
        totalBankAssets += acc.getBalance();
    }
    System.out.println("---------------------------------------------------------");
    System.out.println("Total Bank Assets: $" + totalBankAssets);
    System.out.println("Total Customers: " + accountMap.size());
}
    
}