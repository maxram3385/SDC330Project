/*
 * Name: Max Ramos
 * Date: April 26, 2026
 * Assignment: Week 3 Project - Employee Management Application
 * Purpose: Abstract base Employee class used to demonstrate abstraction,
 * inheritance, constructors, composition, and access specifiers.
 */

public abstract class Employee implements Payable {
    /*
     * These fields are private because they should not be accessed directly
     * from outside the class. Access is controlled through public getters
     * and setters.
     */
    private int employeeId;
    private String firstName;
    private String lastName;
    private Department department;

    /*
     * Default constructor.
     * This gives the class a basic way to create an employee object through subclasses
     * when no specific values are provided.
     */
    public Employee() {
        this.employeeId = 0;
        this.firstName = "Unknown";
        this.lastName = "Unknown";
        this.department = new Department();
    }

    /*
     * Full constructor.
     * This allows subclasses to send complete employee information to the base class.
     */
    public Employee(int employeeId, String firstName, String lastName, Department department) {
        this.employeeId = employeeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.department = department;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Department getDepartment() {
        return department;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    /*
     * This protected method is only intended for this class and its subclasses.
     * It lets child classes reuse the same formatted employee information.
     */
    protected String getBasicEmployeeInfo() {
        return "Employee ID: " + employeeId +
               "\nName: " + firstName + " " + lastName +
               "\nEmployee Type: " + getEmployeeType() +
               "\nDepartment: " + department;
    }

    /*
     * Abstract method.
     * The Employee class does not define one generic employee type.
     * Each subclass must provide its own employee type.
     */
    public abstract String getEmployeeType();

    /*
     * Abstract method from the Payable interface.
     * Each subclass must calculate pay in its own way.
     */
    public abstract double calculatePay();

    @Override
    public String toString() {
        return getBasicEmployeeInfo();
    }
}