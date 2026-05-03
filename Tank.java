/*
Name: Max Ramos
Date: may 2, 2026
SDC330 Week 4 Course Project - Database Support
Stores aquarium-specific information for each customer account.
*/

public class Tank {
    private int tankId;
    private String tankType;
    private double tankSize;

    public Tank(int tankId, String tankType, double tankSize) {
        this.tankId = tankId;
        this.tankType = tankType;
        this.tankSize = tankSize;
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

    @Override
    public String toString() {
        return "Tank ID: " + tankId +
                "\nTank Type: " + tankType +
                "\nTank Size: " + tankSize + " gallons";
    }
}