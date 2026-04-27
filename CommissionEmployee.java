/*
 * Name: Max Ramos
 * Date: April 26, 2026
 * Assignment: Week 3 Project - Employee Management Application
 * Purpose: Child class of Employee used to demonstrate inheritance,
 * abstraction, constructors, and pay calculation.
 */

public class CommissionEmployee extends Employee {
    private double basePay;
    private double commissionRate;
    private double salesAmount;

    /*
     * Default constructor.
     * Calls the Employee default constructor using super().
     */
    public CommissionEmployee() {
        super();
        this.basePay = 0.0;
        this.commissionRate = 0.0;
        this.salesAmount = 0.0;
    }

    /*
     * Full constructor.
     * Sends shared employee information to the abstract Employee base class.
     */
    public CommissionEmployee(int employeeId, String firstName, String lastName,
                              Department department, double basePay,
                              double commissionRate, double salesAmount) {
        super(employeeId, firstName, lastName, department);
        this.basePay = basePay;
        this.commissionRate = commissionRate;
        this.salesAmount = salesAmount;
    }

    @Override
    public String getEmployeeType() {
        return "Commission Employee";
    }

    /*
     * This method is required because Employee defines calculatePay() as abstract.
     * Commission employees receive base pay plus commission based on sales.
     */
    @Override
    public double calculatePay() {
        return basePay + (commissionRate * salesAmount);
    }

    @Override
    public String toString() {
        return getBasicEmployeeInfo() +
               "\nBase Pay: $" + basePay +
               "\nCommission Rate: " + commissionRate +
               "\nSales Amount: $" + salesAmount +
               "\nCalculated Pay: $" + String.format("%.2f", calculatePay());
    }
}