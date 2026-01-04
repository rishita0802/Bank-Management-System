# 🏦 Secure Bank Management System (Pro Version)

A robust, console-based banking application built with **Java**. This project simulates a real-world banking environment, focusing on secure transactions, administrative oversight, and data persistence.

## 🚀 Key Features
- **Account Management**: Create unique accounts with personalized names and starting balances.
- **PIN-Based Security**: Every transaction (Withdrawal, Transfer, Balance Check) requires a 4-digit PIN for authentication.
- **Fund Transfers**: Securely move money between two accounts with real-time balance validation.
- **Administrative Dashboard**: A hidden, password-protected mode for bank managers to view all account details and total bank assets.
- **Data Persistence**: Uses **File I/O** to save all account details and transaction history to a local `accounts.txt` file.
- **Transaction Auditing**: Every account maintains a private ledger of all past deposits, withdrawals, and transfers.
- **Robust Error Handling**: Prevents system crashes from invalid user inputs using try-catch blocks.

## 🛠️ Technical Implementation
- **Java Collections Framework**: Utilized `HashMap` for account storage to achieve **O(1) search efficiency**.
- **Role-Based Access Control**: Implemented separate access levels for Customers (PIN) and Admins (Password).
- **Object-Oriented Programming (OOP)**: 
    - **Encapsulation**: Account data is kept private and modified only through secure methods.
    - **Abstraction**: Separated the data model (`Account.java`) from the business logic (`BankSystem.java`).
- **File Handling**: Custom logic for parsing and writing complex strings to maintain state across sessions.

## 📂 Project Structure
- `Main.java`: The interactive command-line interface, menu handler, and entry point.
- `BankSystem.java`: The "Engine" of the project. Manages account logic, admin reporting, and File I/O operations.
- `Account.java`: The data model representing a single bank user and their transaction ledger.

## 💻 How to Run
1. Clone this repository to your local machine.
2. Compile the Java files:
   ```bash
   javac *.java