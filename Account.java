import java.util.ArrayList;
import java.util.List;

public class Account {
    private String accountNumber;
    private String accountHolder;
    private double balance;
    private int pin;
    private List<String> history; // New field

    public Account(String accountNumber, String accountHolder, double initialBalance, int pin) {
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.balance = initialBalance;
        this.pin = pin;
        this.history = new ArrayList<>();
        addHistory("Account opened with $" + initialBalance);
    }

    public String getAccountNumber() { return accountNumber; }
    public String getAccountHolder() { return accountHolder; }
    public double getBalance() { return balance; }
    public int getPin() { return pin; }
    public List<String> getHistory() { return history; }

    public void addHistory(String message) {
        history.add(message);
    }

    public void deposit(double amount) {
        balance += amount;
        addHistory("Deposited: $" + amount);
    }

    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            addHistory("Withdrew: $" + amount);
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        // We join the history with a special character like ';' to save it in one line
        return accountNumber + "," + accountHolder + "," + balance + "," + pin + "," + String.join(";", history);
    }
}