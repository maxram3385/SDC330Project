/*
Name: Max Ramos
Date: April 2026
SDC330 Week 3 Course Project - Class Implementation

Runs the aquarium maintenance console application.
The program allows the user to create, view, search, update, and delete customer account records.
*/

import java.util.ArrayList;
import java.util.Scanner;

public class App {
    private static ArrayList<Account> accounts = new ArrayList<>();
    private static Scanner input = new Scanner(System.in);
    private static int nextAccountId = 1;
    private static int nextTankId = 1;

    public static void main(String[] args) {
        loadSampleAccounts();

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

    public static void loadSampleAccounts() {
        addSampleAccount("Smiley's HVAC", "757-555-1101", "service@smileyshvac.com",
                "Patrick David", "Monthly", 1.5,
                "Saltwater Fish Only", 75);

        addSampleAccount("Lyn Primo", "757-555-2202", "lyn.primo@email.com",
                "Max Ramos", "Weekly", 2.0,
                "Freshwater Planted", 55);

        addSampleAccount("Michelle Joseph", "757-555-3303", "michelle.joseph@email.com",
                "David Schlamee", "Monthly", 1.0,
                "Freshwater Planted", 40);

        addSampleAccount("Ashlee Marvelle", "757-555-4404", "ashlee.marvelle@email.com",
                "Max Ramos", "Weekly", 2.5,
                "Saltwater Reef", 90);

        addSampleAccount("Cure Coffeehouse", "757-555-5505", "contact@curecoffeehouse.com",
                "Patrick David", "Quarterly", 3.0,
                "Saltwater Reef", 125);
    }

    public static void addSampleAccount(String customerName, String phoneNumber, String email,
                                        String assignedWorker, String serviceFrequency,
                                        double serviceHours, String tankType, double tankSize) {
        Tank tank = new Tank(nextTankId, tankType, tankSize);

        CustomerAccount customerAccount = new CustomerAccount(
                nextAccountId,
                customerName,
                phoneNumber,
                email,
                assignedWorker,
                serviceFrequency,
                serviceHours,
                tank
        );

        accounts.add(customerAccount);

        nextAccountId++;
        nextTankId++;
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

        Tank tank = new Tank(nextTankId, tankType, tankSize);

        CustomerAccount customerAccount = new CustomerAccount(
                nextAccountId,
                customerName,
                phoneNumber,
                email,
                assignedWorker,
                serviceFrequency,
                serviceHours,
                tank
        );

        accounts.add(customerAccount);

        nextAccountId++;
        nextTankId++;

        System.out.println("Customer account added successfully.");
    }

    public static void viewAllAccounts() {
        System.out.println("\n--- All Customer Accounts ---");

        if (accounts.isEmpty()) {
            System.out.println("No customer accounts found.");
            return;
        }

        // Polymorphism is demonstrated here because CustomerAccount objects
        // are stored and processed as Account objects.
        for (Account account : accounts) {
            System.out.println("\n-----------------------------");
            System.out.println(account.getSummary());
        }
    }

    public static void searchAccount() {
        System.out.println("\n--- Search Account ---");
        System.out.print("Enter customer name to search: ");
        String searchName = input.nextLine();

        boolean found = false;

        for (Account account : accounts) {
            if (account.getCustomerName().equalsIgnoreCase(searchName)) {
                System.out.println("\nAccount Found:");
                System.out.println(account.getSummary());
                found = true;
            }
        }

        if (!found) {
            System.out.println("No account found with that customer name.");
        }
    }

    public static void updateAccount() {
        System.out.println("\n--- Update Account Service Hours ---");
        int accountId = getIntInput("Enter account ID to update: ");

        for (Account account : accounts) {
            if (account.getAccountId() == accountId && account instanceof CustomerAccount) {
                CustomerAccount customerAccount = (CustomerAccount) account;

                double newServiceHours = getDoubleInput("Enter new service hours: ");
                customerAccount.setServiceHours(newServiceHours);

                System.out.println("Account service hours updated successfully.");
                return;
            }
        }

        System.out.println("No account found with that ID.");
    }

    public static void deleteAccount() {
        System.out.println("\n--- Delete Account ---");
        int accountId = getIntInput("Enter account ID to delete: ");

        for (int i = 0; i < accounts.size(); i++) {
            if (accounts.get(i).getAccountId() == accountId) {
                accounts.remove(i);
                System.out.println("Account deleted successfully.");
                return;
            }
        }

        System.out.println("No account found with that ID.");
    }

    public static int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                int value = Integer.parseInt(input.nextLine());
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid whole number.");
            }
        }
    }

    public static double getDoubleInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                double value = Double.parseDouble(input.nextLine());
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }
}