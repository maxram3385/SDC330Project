/*
 * Name: Max Ramos
 * Date: April 26, 2026
 * Assignment: Week 3 Project - Employee Management Application
 * Purpose: Department class used to demonstrate composition and constructors.
 */

public class Department {
    private String departmentName;
    private String location;

    /*
     * Default constructor.
     * This can be used when department information is not provided yet.
     */
    public Department() {
        this.departmentName = "Unknown Department";
        this.location = "Unknown Location";
    }

    /*
     * Constructor with only department name.
     * This is an overloaded constructor that gives the class another way
     * to create a Department object.
     */
    public Department(String departmentName) {
        this.departmentName = departmentName;
        this.location = "Unknown Location";
    }

    /*
     * Full constructor.
     * This is used when both department name and location are known.
     */
    public Department(String departmentName, String location) {
        this.departmentName = departmentName;
        this.location = location;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public String getLocation() {
        return location;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return departmentName + " - " + location;
    }
}