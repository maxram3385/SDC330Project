/*
Name: Max Ramos
Date: May 2, 2026
SDC330 Course Project - Aquarium Maintenance App

Stores aquarium tank information for a customer account.
This class is used through composition inside CustomerAccount.
*/

public class Tank {
    private int tankId;
    private String tankType;
    private double tankSize;
    private String waterType;

    public Tank(int tankId, String tankType, double tankSize, String waterType) {
        this.tankId = tankId;
        this.tankType = tankType;
        this.tankSize = tankSize;
        this.waterType = waterType;
    }

    public int getTankId() {
        return tankId;
    }

    public String getTankType() {
        return tankType;
    }

    public void setTankType(String tankType) {
        this.tankType = tankType;
    }

    public double getTankSize() {
        return tankSize;
    }

    public void setTankSize(double tankSize) {
        this.tankSize = tankSize;
    }

    public String getWaterType() {
        return waterType;
    }

    public void setWaterType(String waterType) {
        this.waterType = waterType;
    }

    @Override
    public String toString() {
        return "Tank ID: " + tankId +
                "\nTank Type: " + tankType +
                "\nTank Size: " + tankSize + " gallons" +
                "\nWater Type: " + waterType;
    }
}