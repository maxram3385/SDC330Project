/*
Name: Max Ramos
Date: May 2, 2026
SDC330 Week 4 Course Project - Database Support
Stores shared account information for customer accounts.
*/

public abstract class Account {
    protected int accountId;
    protected String customerName;
    protected String phoneNumber;
    protected String email;

    public Account(int accountId, String customerName, String phoneNumber, String email) {
        this.accountId = accountId;
        this.customerName = customerName;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public int getAccountId() {
        return accountId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public abstract String getSummary();
}