/*
Name: Max Ramos
Date: May 2, 2026
SDC330 Week 4 Course Project - Database Support

Runs the aquarium maintenance console application.
The program allows the user to create, view, search, update, and delete customer account records
using a SQLite database.
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
        System.out.println("4. Update Account Service Hours");
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

        double serviceHours = getDoubleInput("Service Hours: ");

        System.out.print("Tank Type: ");
        String tankType = input.nextLine();

        double tankSize = getDoubleInput("Tank Size in Gallons: ");

        Tank tank = new Tank(0, tankType, tankSize);

        CustomerAccount customerAccount = new CustomerAccount(
                0,
                customerName,
                phoneNumber,
                email,
                assignedWorker,
                serviceFrequency,
                serviceHours,
                tank
        );

        dao.insertCustomerAccount(customerAccount);
    }

    public static void viewAllAccounts() {
        System.out.println("\n--- All Customer Accounts ---");

        ArrayList<CustomerAccount> accounts = dao.getAllCustomerAccounts();

        if (accounts.isEmpty()) {
            System.out.println("No customer accounts found.");
            return;
        }

        // Polymorphism is still demonstrated because CustomerAccount extends Account.
        for (Account account : accounts) {
            System.out.println("\n-----------------------------");
            System.out.println(account.getSummary());
        }
    }

    public static void searchAccount() {
        System.out.println("\n--- Search Account ---");
        System.out.print("Enter customer name to search: ");
        String searchName = input.nextLine();

        ArrayList<CustomerAccount> accounts = dao.searchCustomerAccountsByName(searchName);

        if (accounts.isEmpty()) {
            System.out.println("No account found with that customer name.");
            return;
        }

        for (CustomerAccount account : accounts) {
            System.out.println("\nAccount Found:");
            System.out.println(account.getSummary());
        }
    }

    public static void updateAccount() {
        System.out.println("\n--- Update Account Service Hours ---");

        int accountId = getIntInput("Enter account ID to update: ");
        double newServiceHours = getDoubleInput("Enter new service hours: ");

        boolean updated = dao.updateServiceHours(accountId, newServiceHours);

        if (updated) {
            System.out.println("Account service hours updated successfully.");
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