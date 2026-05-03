/*
Name: Max Ramos
Date: May 2, 2026
SDC330 Week 4 Course Project - Database Support

Represents a customer account for an aquarium maintenance business.
This class extends Account, implements Serviceable, and uses composition by containing a Tank object.
*/

public class CustomerAccount extends Account implements Serviceable {
    private String assignedWorker;
    private String serviceFrequency;
    private double serviceHours;
    private Tank tank;

    public CustomerAccount(int accountId, String customerName, String phoneNumber, String email,
                           String assignedWorker, String serviceFrequency, double serviceHours,
                           Tank tank) {
        super(accountId, customerName, phoneNumber, email);
        this.assignedWorker = assignedWorker;
        this.serviceFrequency = serviceFrequency;
        this.serviceHours = serviceHours;
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

    public double getServiceHours() {
        return serviceHours;
    }

    public void setServiceHours(double serviceHours) {
        this.serviceHours = serviceHours;
    }

    public Tank getTank() {
        return tank;
    }

    public void setTank(Tank tank) {
        this.tank = tank;
    }

    public double getHourlyRate() {
        String tankType = tank.getTankType();

        if (tankType.equalsIgnoreCase("Freshwater Planted")) {
            return 80.00;
        } else if (tankType.equalsIgnoreCase("Saltwater Fish Only")) {
            return 100.00;
        } else if (tankType.equalsIgnoreCase("Saltwater Reef")) {
            return 125.00;
        } else {
            return 0.00;
        }
    }

    @Override
    public double calculateServiceCost() {
        return getHourlyRate() * serviceHours;
    }

    @Override
    public String getServiceDetails() {
        return "Assigned Worker: " + assignedWorker +
                "\nService Frequency: " + serviceFrequency +
                "\nService Hours: " + serviceHours +
                "\nHourly Rate: $" + String.format("%.2f", getHourlyRate()) +
                "\nEstimated Service Cost: $" + String.format("%.2f", calculateServiceCost());
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