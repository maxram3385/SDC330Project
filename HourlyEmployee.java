/*
 * Name: Max Ramos
 * Date: April 26, 2026
 * Assignment: Week 3 Project - Employee Management Application
 * Purpose: Child class of Employee used to demonstrate inheritance,
 * abstraction, constructors, and pay calculation.
 */

public class HourlyEmployee extends Employee {
    private double hourlyRate;
    private double hoursWorked;

    /*
     * Default constructor.
     * Calls the Employee default constructor using super().
     */
    public HourlyEmployee() {
        super();
        this.hourlyRate = 0.0;
        this.hoursWorked = 0.0;
    }

    /*
     * Full constructor.
     * Sends shared employee information to the abstract Employee base class.
     */
    public HourlyEmployee(int employeeId, String firstName, String lastName,
                          Department department, double hourlyRate, double hoursWorked) {
        super(employeeId, firstName, lastName, department);
        this.hourlyRate = hourlyRate;
        this.hoursWorked = hoursWorked;
    }

    @Override
    public String getEmployeeType() {
        return "Hourly Employee";
    }

    /*
     * This method is required because Employee defines calculatePay() as abstract.
     * Hourly employees are paid by multiplying hourly rate by hours worked.
     */
    @Override
    public double calculatePay() {
        return hourlyRate * hoursWorked;
    }

    @Override
    public String toString() {
        return getBasicEmployeeInfo() +
               "\nHourly Rate: $" + hourlyRate +
               "\nHours Worked: " + hoursWorked +
               "\nCalculated Pay: $" + String.format("%.2f", calculatePay());
    }
}