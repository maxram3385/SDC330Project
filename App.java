/*
 * Name: Max Ramos
 * Date: April 26, 2026
 * Assignment: Week 3 Project - Employee Management Application
 * Purpose: Main application file that allows the user to manage employees
 * through a console-based menu system. This version demonstrates abstraction,
 * constructors, access specifiers, composition, and polymorphism.
 */

import java.util.ArrayList;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Employee> employees = new ArrayList<>();

        // These objects instantiate each employee subclass with realistic sample information.
        // Each employee also receives a Department object, which demonstrates composition.
        employees.add(new HourlyEmployee(
                101, "John", "Smith",
                new Department("Sales", "Building A"),
                18.50, 40
        ));

        employees.add(new SalariedEmployee(
                102, "Sarah", "Jones",
                new Department("Human Resources", "Building B"),
                55000
        ));

        employees.add(new CommissionEmployee(
                103, "Mike", "Brown",
                new Department("Marketing", "Building C"),
                700.00, 0.10, 5000
        ));

        int choice;

        System.out.println("==================================================");
        System.out.println("Project Week 3 - Employee Management Application");
        System.out.println("By Max Ramos");
        System.out.println("==================================================");
        System.out.println("Welcome to the Employee Management Application.");
        System.out.println("Use the menu below to add, remove, update, display,");
        System.out.println("and calculate employee pay information.");
        System.out.println("This version demonstrates abstraction, constructors,");
        System.out.println("access specifiers, inheritance, composition, and polymorphism.");

        do {
            System.out.println("\n================ MENU ================");
            System.out.println("1. Add Employee");
            System.out.println("2. Remove Employee");
            System.out.println("3. Update Employee");
            System.out.println("4. Display All Employees");
            System.out.println("5. Display Employees by Type");
            System.out.println("6. Display Employee Pay");
            System.out.println("7. Exit");

            choice = getIntInput(scanner, "Enter your choice: ");

            switch (choice) {
                case 1:
                    addEmployee(scanner, employees);
                    break;
                case 2:
                    removeEmployee(scanner, employees);
                    break;
                case 3:
                    updateEmployee(scanner, employees);
                    break;
                case 4:
                    displayAllEmployees(employees);
                    break;
                case 5:
                    displayEmployeesByType(scanner, employees);
                    break;
                case 6:
                    displayEmployeePay(employees);
                    break;
                case 7:
                    System.out.println("Thank you for using the Employee Management Application. Goodbye.");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number from 1 to 7.");
            }

        } while (choice != 7);

        scanner.close();
    }

    /*
     * This method is private because it is only used inside the App class.
     * This demonstrates access specifiers by not exposing helper methods publicly.
     */
    private static void addEmployee(Scanner scanner, ArrayList<Employee> employees) {
        int id = getIntInput(scanner, "Enter employee ID: ");

        System.out.print("Enter first name: ");
        String firstName = scanner.nextLine();

        System.out.print("Enter last name: ");
        String lastName = scanner.nextLine();

        System.out.print("Enter department name: ");
        String deptName = scanner.nextLine();

        System.out.print("Enter department location: ");
        String deptLocation = scanner.nextLine();

        // Constructor creates a Department object that will become part of the Employee object.
        Department department = new Department(deptName, deptLocation);

        System.out.println("Choose employee type:");
        System.out.println("1. Hourly");
        System.out.println("2. Salaried");
        System.out.println("3. Commission");

        int typeChoice = getIntInput(scanner, "Enter choice: ");

        switch (typeChoice) {
            case 1:
                double hourlyRate = getDoubleInput(scanner, "Enter hourly rate: ");
                double hoursWorked = getDoubleInput(scanner, "Enter hours worked: ");

                employees.add(new HourlyEmployee(id, firstName, lastName, department, hourlyRate, hoursWorked));
                System.out.println("Hourly employee added successfully.");
                break;

            case 2:
                double annualSalary = getDoubleInput(scanner, "Enter annual salary: ");

                employees.add(new SalariedEmployee(id, firstName, lastName, department, annualSalary));
                System.out.println("Salaried employee added successfully.");
                break;

            case 3:
                double basePay = getDoubleInput(scanner, "Enter base pay: ");
                double commissionRate = getDoubleInput(scanner, "Enter commission rate: ");
                double salesAmount = getDoubleInput(scanner, "Enter sales amount: ");

                employees.add(new CommissionEmployee(id, firstName, lastName, department, basePay, commissionRate, salesAmount));
                System.out.println("Commission employee added successfully.");
                break;

            default:
                System.out.println("Invalid employee type. Employee was not added.");
        }
    }

    private static void removeEmployee(Scanner scanner, ArrayList<Employee> employees) {
        int id = getIntInput(scanner, "Enter employee ID to remove: ");

        Employee employeeToRemove = null;

        for (Employee employee : employees) {
            if (employee.getEmployeeId() == id) {
                employeeToRemove = employee;
                break;
            }
        }

        if (employeeToRemove != null) {
            employees.remove(employeeToRemove);
            System.out.println("Employee removed successfully.");
        } else {
            System.out.println("Employee not found.");
        }
    }

    private static void updateEmployee(Scanner scanner, ArrayList<Employee> employees) {
        int id = getIntInput(scanner, "Enter employee ID to update: ");

        Employee employeeToUpdate = null;

        for (Employee employee : employees) {
            if (employee.getEmployeeId() == id) {
                employeeToUpdate = employee;
                break;
            }
        }

        if (employeeToUpdate == null) {
            System.out.println("Employee not found.");
            return;
        }

        System.out.print("Enter new first name: ");
        String firstName = scanner.nextLine();

        System.out.print("Enter new last name: ");
        String lastName = scanner.nextLine();

        System.out.print("Enter new department name: ");
        String deptName = scanner.nextLine();

        System.out.print("Enter new department location: ");
        String deptLocation = scanner.nextLine();

        employeeToUpdate.setFirstName(firstName);
        employeeToUpdate.setLastName(lastName);
        employeeToUpdate.setDepartment(new Department(deptName, deptLocation));

        System.out.println("Employee updated successfully.");
    }

    private static void displayAllEmployees(ArrayList<Employee> employees) {
        if (employees.isEmpty()) {
            System.out.println("No employees to display.");
            return;
        }

        for (Employee employee : employees) {
            System.out.println("----------------------------------");
            System.out.println(employee);
        }
    }

    private static void displayEmployeesByType(Scanner scanner, ArrayList<Employee> employees) {
        System.out.println("Display which type?");
        System.out.println("1. Hourly");
        System.out.println("2. Salaried");
        System.out.println("3. Commission");

        int choice = getIntInput(scanner, "Enter choice: ");

        boolean found = false;

        for (Employee employee : employees) {
            if ((choice == 1 && employee instanceof HourlyEmployee) ||
                (choice == 2 && employee instanceof SalariedEmployee) ||
                (choice == 3 && employee instanceof CommissionEmployee)) {
                System.out.println("----------------------------------");
                System.out.println(employee);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No employees of that type found.");
        }
    }

    private static void displayEmployeePay(ArrayList<Employee> employees) {
        if (employees.isEmpty()) {
            System.out.println("No employees to display.");
            return;
        }

        System.out.println("\nEmployee Payment Information");
        System.out.println("==========================================");

        /*
         * Polymorphism is shown here.
         * The ArrayList stores Employee references, but the correct calculatePay()
         * method runs depending on whether the object is hourly, salaried, or commission.
         */
        for (Employee employee : employees) {
            System.out.println("----------------------------------");
            System.out.println(employee.getFirstName() + " " + employee.getLastName());
            System.out.println("Type: " + employee.getEmployeeType());
            System.out.println("Pay this period: $" + String.format("%.2f", employee.calculatePay()));
        }
    }

    /*
     * This private helper method improves input validation.
     * It keeps the program from crashing if the user enters text instead of a number.
     */
    private static int getIntInput(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);

            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a whole number.");
            }
        }
    }

    /*
     * This private helper method validates decimal number input.
     */
    private static double getDoubleInput(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);

            try {
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }
}