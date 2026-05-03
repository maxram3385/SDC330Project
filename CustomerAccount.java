/*
Name: Max Ramos
Date: May 2, 2026
SDC330 Course Project - Aquarium Maintenance App

Represents a customer account for an aquarium maintenance business.

This class demonstrates:
- Inheritance by extending Account
- Interface implementation by implementing Serviceable
- Composition by storing a Tank object
- Encapsulation through private fields and public getters/setters
*/

public class CustomerAccount extends Account implements Serviceable {
    private String assignedWorker;
    private String serviceFrequency;
    private double monthlyPrice;
    private String maintenanceNotes;
    private Tank tank;

    public CustomerAccount(int accountId, String customerName, String phoneNumber, String email,
                           String assignedWorker, String serviceFrequency, double monthlyPrice,
                           String maintenanceNotes, Tank tank) {
        super(accountId, customerName, phoneNumber, email);
        this.assignedWorker = assignedWorker;
        this.serviceFrequency = serviceFrequency;
        this.monthlyPrice = monthlyPrice;
        this.maintenanceNotes = maintenanceNotes;
        this.tank = tank;
    }

    public String getAssignedWorker() {
        return assignedWorker;
    }

    public void setAssignedWorker(String assignedWorker) {
        this.assignedWorker = assignedWorker;
    }

    public String getServiceFrequency() {
        return serviceFrequency;
    }

    public void setServiceFrequency(String serviceFrequency) {
        this.serviceFrequency = serviceFrequency;
    }

    public double getMonthlyPrice() {
        return monthlyPrice;
    }

    public void setMonthlyPrice(double monthlyPrice) {
        this.monthlyPrice = monthlyPrice;
    }

    public String getMaintenanceNotes() {
        return maintenanceNotes;
    }

    public void setMaintenanceNotes(String maintenanceNotes) {
        this.maintenanceNotes = maintenanceNotes;
    }

    public Tank getTank() {
        return tank;
    }

    public void setTank(Tank tank) {
        this.tank = tank;
    }

    @Override
    public double calculateServiceCost() {
        return monthlyPrice;
    }

    @Override
    public String getServiceDetails() {
        return "Assigned Worker: " + assignedWorker +
                "\nService Frequency: " + serviceFrequency +
                "\nMonthly Price: $" + String.format("%.2f", monthlyPrice) +
                "\nMaintenance Notes: " + maintenanceNotes +
                "\nEstimated Monthly Service Cost: $" + String.format("%.2f", calculateServiceCost());
    }

    @Override
    public String getSummary() {
        return "Account ID: " + accountId +
                "\nCustomer Name: " + customerName +
                "\nPhone Number: " + phoneNumber +
                "\nEmail: " + email +
                "\n" + getServiceDetails() +
                "\n\nTank Information:\n" + tank.toString();
    }

    @Override
    public String toString() {
        return getSummary();
    }
}