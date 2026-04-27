/*
 * Name: Max Ramos
 * Date: April 26, 2026
 * Assignment: Week 3 Project - Employee Management Application
 * Purpose: Interface used to define payment behavior for employee types.
 */

public interface Payable {
    /*
     * This method is required for any class that implements Payable.
     * Each employee type calculates pay differently.
     */
    double calculatePay();
}