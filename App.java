/*
Name: Max Ramos
Date: May 2, 2026
SDC330 Course Project - Aquarium Maintenance App

Runs the aquarium maintenance console application.

This program allows the user to:
- Add customer accounts
- View all customer accounts
- Search customer accounts
- Update customer account information
- Delete customer accounts

The application uses a SQLite database for CRUD operations.
*/

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class App {
    private static final String DATABASE_NAME = "aquarium_maintenance.db";
    private static Scanner input = new Scanner(System.in);
    private static CustomerAccountDAO dao;

    public static void main(String[] args) {
        Connection conn = SQLiteDatabase.connect(DATABASE_NAME);

        if (conn == null) {
            System.out.println("Could not connect to the database. Program ending.");
            return;
        }

        dao = new CustomerAccountDAO(conn);
        dao.createTables();
        dao.seedSampleData();

        int choice;

        do {
            displayMenu();
            choice = getIntInput("Enter your choice: ");

            switch (choice) {
                case 1:
                    addCustomerAccount();
                    break;
                case 2:
                    viewAllAccounts();
                    break;
                case 3:
                    searchAccount();
                    break;
                case 4:
                    updateAccount();
                    break;
                case 5:
                    deleteAccount();
                    break;
                case 6:
                    System.out.println("Exiting Aquarium Maintenance App. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }

        } while (choice != 6);

        try {
            conn.close();
        } catch (SQLException e) {
            System.out.println("Error closing database connection: " + e.getMessage());
        }
    }

    public static void displayMenu() {
        System.out.println("\n=== Aquarium Maintenance Account Manager ===");
        System.out.println("1. Add Customer Account");
        System.out.println("2. View All Accounts");
        System.out.println("3. Search Account by Customer Name");
        System.out.println("4. Update Customer Account Information");
        System.out.println("5. Delete Account");
        System.out.println("6. Exit");
    }

    public static void addCustomerAccount() {
        System.out.println("\n--- Add Customer Account ---");

        System.out.print("Customer Name: ");
        String customerName = input.nextLine();

        System.out.print("Phone Number: ");
        String phoneNumber = input.nextLine();

        System.out.print("Email: ");
        String email = input.nextLine();

        System.out.print("Assigned Worker: ");
        String assignedWorker = input.nextLine();

        System.out.print("Service Frequency: ");
        String serviceFrequency = input.nextLine();

        double monthlyPrice = getDoubleInput("Monthly Price: ");

        System.out.print("Maintenance Notes: ");
        String maintenanceNotes = input.nextLine();

        System.out.print("Tank Type: ");
        String tankType = input.nextLine();

        double tankSize = getDoubleInput("Tank Size in Gallons: ");

        System.out.print("Water Type: ");
        String waterType = input.nextLine();

        Tank tank = new Tank(0, tankType, tankSize, waterType);

        CustomerAccount customerAccount = new CustomerAccount(
                0,
                customerName,
                phoneNumber,
                email,
                assignedWorker,
                serviceFrequency,
                monthlyPrice,
                maintenanceNotes,
                tank
        );

        dao.insertCustomerAccount(customerAccount);
    }

    public static void viewAllAccounts() {
        System.out.println("\n--- All Customer Accounts ---");

        ArrayList<CustomerAccount> customerAccounts = dao.getAllCustomerAccounts();

        if (customerAccounts.isEmpty()) {
            System.out.println("No customer accounts found.");
            return;
        }

        /*
         This demonstrates polymorphism.
         CustomerAccount objects are being stored and processed as Account objects.
        */
        ArrayList<Account> accounts = new ArrayList<>();

        for (CustomerAccount customerAccount : customerAccounts) {
            accounts.add(customerAccount);
        }

        for (Account account : accounts) {
            System.out.println("\n-----------------------------");
            System.out.println(account.getSummary());
        }
    }

    public static void searchAccount() {
        System.out.println("\n--- Search Account ---");
        System.out.print("Enter customer name to search: ");
        String searchName = input.nextLine();

        ArrayList<CustomerAccount> customerAccounts = dao.searchCustomerAccountsByName(searchName);

        if (customerAccounts.isEmpty()) {
            System.out.println("No account found with that customer name.");
            return;
        }

        /*
         This also demonstrates polymorphism.
         The search results are CustomerAccount objects, but they can be treated as Account objects.
        */
        for (Account account : customerAccounts) {
            System.out.println("\nAccount Found:");
            System.out.println(account.getSummary());
        }
    }

    public static void updateAccount() {
        System.out.println("\n--- Update Customer Account ---");

        int accountId = getIntInput("Enter account ID to update: ");

        System.out.print("New Customer Name: ");
        String newCustomerName = input.nextLine();

        System.out.print("New Phone Number: ");
        String newPhoneNumber = input.nextLine();

        System.out.print("New Email: ");
        String newEmail = input.nextLine();

        System.out.print("New Assigned Worker: ");
        String newAssignedWorker = input.nextLine();

        System.out.print("New Service Frequency: ");
        String newServiceFrequency = input.nextLine();

        double newMonthlyPrice = getDoubleInput("New Monthly Price: ");

        System.out.print("New Maintenance Notes: ");
        String newMaintenanceNotes = input.nextLine();

        boolean updated = dao.updateCustomerAccount(
                accountId,
                newCustomerName,
                newPhoneNumber,
                newEmail,
                newAssignedWorker,
                newServiceFrequency,
                newMonthlyPrice,
                newMaintenanceNotes
        );

        if (updated) {
            System.out.println("Customer account updated successfully.");
        } else {
            System.out.println("No account found with that ID.");
        }
    }

    public static void deleteAccount() {
        System.out.println("\n--- Delete Account ---");

        int accountId = getIntInput("Enter account ID to delete: ");

        boolean deleted = dao.deleteCustomerAccount(accountId);

        if (deleted) {
            System.out.println("Account deleted successfully.");
        } else {
            System.out.println("No account found with that ID.");
        }
    }

    public static int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(input.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid whole number.");
            }
        }
    }

    public static double getDoubleInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Double.parseDouble(input.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }
}