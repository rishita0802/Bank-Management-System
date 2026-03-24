import java.util.Scanner;

public class Main {
    
        public static void main(String[] args) {
        BankSystem bank = new BankSystem();
        Scanner sc = new Scanner(System.in);
        
        while (true) {
            try {
                System.out.println("\n--- ATM SYSTEM (SQL BACKEND) ---"); // Updated title
                System.out.println("1. Open Account\n2. Deposit\n3. Withdraw\n4. Transfer\n5. Check Balance\n6. Transaction History\n7. Admin Mode\n8. Exit\n9. Search Account");
                System.out.print("Select Option: ");
                
                // Using sc.nextLine() is good practice to avoid the "newline" bug
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
                     case 6:
                        System.out.print("Acc Num: "); String hAcc = sc.nextLine();
                        System.out.print("PIN: "); int hPin = Integer.parseInt(sc.nextLine());
                        bank.showTransactionHistory(hAcc, hPin);
                        break;
                    case 7: 
                        System.out.print("Enter Admin Password: ");
                        String adminPass = sc.nextLine();
                        if (adminPass.equals("admin123")) {
                            bank.adminViewAllAccounts();
                        } else {
                            System.out.println("ACCESS DENIED: Incorrect Admin Password.");
                        }
                        break;
                    case 8: 
                        System.out.println("System Shutdown...");
                        // Close Scanner before exiting
                        sc.close(); 
                        return;
                        // Inside your switch(choice) block in Main.java:
                    case 9:
                         System.out.print("Enter Name or Account Number to search: ");
                         String keyword = sc.nextLine();
                         bank.searchAccount(keyword);
                         break;
                    default:
                        System.out.println("Invalid Option. Choose 1-8.");
                }
            } catch (Exception e) {
                System.out.println("Invalid Input. Please enter numbers for amounts and PINs.");
            }
        }
    }
}