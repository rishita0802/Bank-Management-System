import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        BankSystem bank = new BankSystem();
        Scanner sc = new Scanner(System.in);
        
        while (true) {
            try {
                System.out.println("\n--- ATM SYSTEM ---");
                System.out.println("1. Open Account\n2. Deposit\n3. Withdraw\n4. Transfer\n5. Check Balance\n6. Transaction History\n7. Admin Mode\n8. Exit");
                System.out.print("Select Option: ");
                int choice = Integer.parseInt(sc.nextLine());

                switch (choice) {
                    case 1:
                        System.out.print("Acc Num: "); String acc = sc.nextLine();
                        System.out.print("Name: "); String name = sc.nextLine();
                        System.out.print("Initial Deposit: "); double dep = Double.parseDouble(sc.nextLine());
                        System.out.print("Set 4-Digit PIN: "); int pin = Integer.parseInt(sc.nextLine());
                        bank.createAccount(acc, name, dep, pin);
                        break;
                    case 2:
                        System.out.print("Acc Num: "); String dAcc = sc.nextLine();
                        System.out.print("Amount: "); double dAmt = Double.parseDouble(sc.nextLine());
                        bank.performDeposit(dAcc, dAmt);
                        break;
                    case 3:
                        System.out.print("Acc Num: "); String wAcc = sc.nextLine();
                        System.out.print("PIN: "); int wPin = Integer.parseInt(sc.nextLine());
                        System.out.print("Amount: "); double wAmt = Double.parseDouble(sc.nextLine());
                        bank.performWithdraw(wAcc, wPin, wAmt);
                        break;
                    case 4:
                        System.out.print("Your Acc: "); String fAcc = sc.nextLine();
                        System.out.print("Your PIN: "); int fPin = Integer.parseInt(sc.nextLine());
                        System.out.print("Recipient Acc: "); String tAcc = sc.nextLine();
                        System.out.print("Amount: "); double tAmt = Double.parseDouble(sc.nextLine());
                        bank.performTransfer(fAcc, fPin, tAcc, tAmt);
                        break;
                    case 5:
                        System.out.print("Acc Num: "); String bAcc = sc.nextLine();
                        System.out.print("PIN: "); int bPin = Integer.parseInt(sc.nextLine());
                        bank.checkBalance(bAcc, bPin);
                        break;
                    // Inside your switch(choice) block:

                     case 6:
                        System.out.print("Acc Num: "); String hAcc = sc.nextLine();
                        System.out.print("PIN: "); int hPin = Integer.parseInt(sc.nextLine());
                        bank.showTransactionHistory(hAcc, hPin);
                        break;
                    // Inside your switch(choice) block in Main.java:

case 7: // Admin Mode
    System.out.print("Enter Admin Password: ");
    String adminPass = sc.nextLine();
    if (adminPass.equals("admin123")) {
        bank.adminViewAllAccounts();
    } else {
        System.out.println("ACCESS DENIED: Incorrect Admin Password.");
    }
    break;

case 8: // Exit
    System.out.println("System Shutdown...");
    return;
                }
            } catch (Exception e) {
                System.out.println("Invalid Input. Please try again.");
            }
        }
    }
}