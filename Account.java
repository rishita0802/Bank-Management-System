
public class Account {
    private String accountNumber;
    private String accountHolder;
    private double balance;
    private int pin;

    // Constructor used when fetching data from the Database
    public Account(String accountNumber, String accountHolder, double balance, int pin) {
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.balance = balance;
        this.pin = pin;
    }

    // Getters - used by the Admin View or Search features
    public String getAccountNumber() { return accountNumber; }
    public String getAccountHolder() { return accountHolder; }
    public double getBalance() { return balance; }
    public int getPin() { return pin; }
}