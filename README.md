# 🏦 Banking Management System (Java + MySQL)

A robust, console-based Banking Application developed as part of the **Placement Preparation** for technical roles at **Philips** and **LG Soft India**. This project demonstrates the integration of **Java SE** with **MySQL Database** using **JDBC**.

## 🛠️ Project Tech Stack
- **Language:** Java 17+ (Current: JDK 24)
- **Database:** MySQL 8.0
- **Driver:** MySQL Connector/J 9.x
- **IDE:** VS Code

---

## 🗄️ Database Configuration (MySQL)
Execute the following commands in **MySQL Workbench** before running the Java application. This sets up the schema and ensures data integrity.

```sql
-- 1. Create the Database
CREATE DATABASE IF NOT EXISTS banking_system;
USE banking_system;

-- 2. Create the Accounts Table
CREATE TABLE IF NOT EXISTS accounts (
    account_number VARCHAR(20) PRIMARY KEY,
    holder_name VARCHAR(100) NOT NULL,
    balance DOUBLE DEFAULT 0.0,
    pin INT NOT NULL
);

-- 3. Create the Transactions Table (Audit Log)
CREATE TABLE IF NOT EXISTS transactions (
    id INT AUTO_INCREMENT PRIMARY KEY,
    account_number VARCHAR(20),
    description VARCHAR(255),
    amount DOUBLE,
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (account_number) REFERENCES accounts(account_number) ON DELETE CASCADE
);
🚀 Setup & Installation Instructions
1. Folder Structure
Ensure your files are in the root folder for easy compilation:

Plaintext
The Bank Management System/
├── Main.java             # Entry point & User Menu
├── BankSystem.java       # JDBC Logic & SQL Queries
├── Account.java          # POJO Data Model
└── lib/                  # Put mysql-connector-j-9.x.jar here
2. Configure Java Classpath
In VS Code, go to the Java Projects view (bottom-left).

Click Referenced Libraries ➔ + (Plus Icon).

Select the mysql-connector-j-9.x.jar file.

3. Update Credentials
Open BankSystem.java and update these lines with your local MySQL details:

Java
private final String url = "jdbc:mysql://localhost:3306/banking_system";
private final String user = "root";
private final String password = "YOUR_MYSQL_PASSWORD";
4. Run the App
Open Main.java and click Run.

Use the console to Open Account, Deposit, Withdraw, or check Balance.