/*
Name: Max Ramos
Date: May 2, 2026
SDC330 Course Project - Aquarium Maintenance App

DAO stands for Data Access Object.
This class handles all SQLite database operations for customer accounts and tanks.

It supports:
- Create
- Read
- Update
- Delete
*/

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CustomerAccountDAO {
    private Connection conn;

    public CustomerAccountDAO(Connection conn) {
        this.conn = conn;
    }

    public void createTables() {
        String createTanksTable =
                "CREATE TABLE IF NOT EXISTS Tanks (" +
                        "tank_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "tank_type TEXT NOT NULL, " +
                        "tank_size REAL NOT NULL, " +
                        "water_type TEXT" +
                        ");";

        String createCustomerAccountsTable =
                "CREATE TABLE IF NOT EXISTS CustomerAccounts (" +
                        "account_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "customer_name TEXT NOT NULL, " +
                        "phone_number TEXT, " +
                        "email TEXT, " +
                        "assigned_worker TEXT, " +
                        "service_frequency TEXT, " +
                        "monthly_price REAL, " +
                        "maintenance_notes TEXT, " +
                        "tank_id INTEGER, " +
                        "FOREIGN KEY (tank_id) REFERENCES Tanks(tank_id)" +
                        ");";

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(createTanksTable);
            stmt.execute(createCustomerAccountsTable);
        } catch (SQLException e) {
            System.out.println("Error creating tables: " + e.getMessage());
        }
    }

    public int getAccountCount() {
        String sql = "SELECT COUNT(*) AS total FROM CustomerAccounts;";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getInt("total");
            }

        } catch (SQLException e) {
            System.out.println("Error counting accounts: " + e.getMessage());
        }

        return 0;
    }

    public void insertCustomerAccount(CustomerAccount account) {
        String insertTankSql =
                "INSERT INTO Tanks (tank_type, tank_size, water_type) VALUES (?, ?, ?);";

        String insertAccountSql =
                "INSERT INTO CustomerAccounts " +
                        "(customer_name, phone_number, email, assigned_worker, service_frequency, monthly_price, maintenance_notes, tank_id) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?);";

        try {
            conn.setAutoCommit(false);

            int tankId = -1;

            try (PreparedStatement tankStmt = conn.prepareStatement(insertTankSql, Statement.RETURN_GENERATED_KEYS)) {
                tankStmt.setString(1, account.getTank().getTankType());
                tankStmt.setDouble(2, account.getTank().getTankSize());
                tankStmt.setString(3, account.getTank().getWaterType());
                tankStmt.executeUpdate();

                try (ResultSet generatedKeys = tankStmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        tankId = generatedKeys.getInt(1);
                    }
                }
            }

            try (PreparedStatement accountStmt = conn.prepareStatement(insertAccountSql)) {
                accountStmt.setString(1, account.getCustomerName());
                accountStmt.setString(2, account.getPhoneNumber());
                accountStmt.setString(3, account.getEmail());
                accountStmt.setString(4, account.getAssignedWorker());
                accountStmt.setString(5, account.getServiceFrequency());
                accountStmt.setDouble(6, account.getMonthlyPrice());
                accountStmt.setString(7, account.getMaintenanceNotes());
                accountStmt.setInt(8, tankId);
                accountStmt.executeUpdate();
            }

            conn.commit();
            System.out.println("Customer account saved to database.");

        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException rollbackError) {
                System.out.println("Rollback error: " + rollbackError.getMessage());
            }

            System.out.println("Error inserting account: " + e.getMessage());

        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                System.out.println("Auto-commit error: " + e.getMessage());
            }
        }
    }

    public ArrayList<CustomerAccount> getAllCustomerAccounts() {
        ArrayList<CustomerAccount> accounts = new ArrayList<>();

        String sql =
                "SELECT " +
                        "ca.account_id, " +
                        "ca.customer_name, " +
                        "ca.phone_number, " +
                        "ca.email, " +
                        "ca.assigned_worker, " +
                        "ca.service_frequency, " +
                        "ca.monthly_price, " +
                        "ca.maintenance_notes, " +
                        "t.tank_id, " +
                        "t.tank_type, " +
                        "t.tank_size, " +
                        "t.water_type " +
                "FROM CustomerAccounts ca " +
                "JOIN Tanks t ON ca.tank_id = t.tank_id " +
                "ORDER BY ca.account_id;";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                accounts.add(buildCustomerAccountFromResultSet(rs));
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving accounts: " + e.getMessage());
        }

        return accounts;
    }

    public ArrayList<CustomerAccount> searchCustomerAccountsByName(String searchName) {
        ArrayList<CustomerAccount> accounts = new ArrayList<>();

        String sql =
                "SELECT " +
                        "ca.account_id, " +
                        "ca.customer_name, " +
                        "ca.phone_number, " +
                        "ca.email, " +
                        "ca.assigned_worker, " +
                        "ca.service_frequency, " +
                        "ca.monthly_price, " +
                        "ca.maintenance_notes, " +
                        "t.tank_id, " +
                        "t.tank_type, " +
                        "t.tank_size, " +
                        "t.water_type " +
                "FROM CustomerAccounts ca " +
                "JOIN Tanks t ON ca.tank_id = t.tank_id " +
                "WHERE ca.customer_name LIKE ? " +
                "ORDER BY ca.account_id;";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + searchName + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    accounts.add(buildCustomerAccountFromResultSet(rs));
                }
            }

        } catch (SQLException e) {
            System.out.println("Error searching accounts: " + e.getMessage());
        }

        return accounts;
    }

    public boolean updateCustomerAccount(int accountId, String newCustomerName, String newPhoneNumber,
                                         String newEmail, String newAssignedWorker,
                                         String newServiceFrequency, double newMonthlyPrice,
                                         String newMaintenanceNotes) {
        String sql =
                "UPDATE CustomerAccounts " +
                        "SET customer_name = ?, " +
                        "phone_number = ?, " +
                        "email = ?, " +
                        "assigned_worker = ?, " +
                        "service_frequency = ?, " +
                        "monthly_price = ?, " +
                        "maintenance_notes = ? " +
                        "WHERE account_id = ?;";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, newCustomerName);
            stmt.setString(2, newPhoneNumber);
            stmt.setString(3, newEmail);
            stmt.setString(4, newAssignedWorker);
            stmt.setString(5, newServiceFrequency);
            stmt.setDouble(6, newMonthlyPrice);
            stmt.setString(7, newMaintenanceNotes);
            stmt.setInt(8, accountId);

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            System.out.println("Error updating account: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteCustomerAccount(int accountId) {
        String findTankSql =
                "SELECT tank_id FROM CustomerAccounts WHERE account_id = ?;";

        String deleteAccountSql =
                "DELETE FROM CustomerAccounts WHERE account_id = ?;";

        String deleteTankSql =
                "DELETE FROM Tanks WHERE tank_id = ?;";

        try {
            conn.setAutoCommit(false);

            int tankId = -1;

            try (PreparedStatement findTankStmt = conn.prepareStatement(findTankSql)) {
                findTankStmt.setInt(1, accountId);

                try (ResultSet rs = findTankStmt.executeQuery()) {
                    if (rs.next()) {
                        tankId = rs.getInt("tank_id");
                    } else {
                        conn.rollback();
                        return false;
                    }
                }
            }

            try (PreparedStatement deleteAccountStmt = conn.prepareStatement(deleteAccountSql)) {
                deleteAccountStmt.setInt(1, accountId);
                deleteAccountStmt.executeUpdate();
            }

            try (PreparedStatement deleteTankStmt = conn.prepareStatement(deleteTankSql)) {
                deleteTankStmt.setInt(1, tankId);
                deleteTankStmt.executeUpdate();
            }

            conn.commit();
            return true;

        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException rollbackError) {
                System.out.println("Rollback error: " + rollbackError.getMessage());
            }

            System.out.println("Error deleting account: " + e.getMessage());
            return false;

        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                System.out.println("Auto-commit error: " + e.getMessage());
            }
        }
    }

    public void seedSampleData() {
        if (getAccountCount() > 0) {
            return;
        }

        insertCustomerAccount(new CustomerAccount(
                0,
                "Smiley's HVAC",
                "757-555-1101",
                "service@smileyshvac.com",
                "Patrick David",
                "Monthly",
                150.00,
                "Check salinity and clean glass.",
                new Tank(0, "Saltwater Fish Only", 75, "Saltwater")
        ));

        insertCustomerAccount(new CustomerAccount(
                0,
                "Lyn Primo",
                "757-555-2202",
                "lyn.primo@email.com",
                "Max Ramos",
                "Weekly",
                220.00,
                "Trim plants and test nitrate levels.",
                new Tank(0, "Freshwater Planted", 55, "Freshwater")
        ));

        insertCustomerAccount(new CustomerAccount(
                0,
                "Michelle Joseph",
                "757-555-3303",
                "michelle.joseph@email.com",
                "David Schlamee",
                "Monthly",
                125.00,
                "Vacuum substrate and replace filter floss.",
                new Tank(0, "Freshwater Planted", 40, "Freshwater")
        ));

        insertCustomerAccount(new CustomerAccount(
                0,
                "Ashlee Marvelle",
                "757-555-4404",
                "ashlee.marvelle@email.com",
                "Max Ramos",
                "Weekly",
                350.00,
                "Clean protein skimmer and test alkalinity.",
                new Tank(0, "Saltwater Reef", 90, "Saltwater")
        ));

        insertCustomerAccount(new CustomerAccount(
                0,
                "Cure Coffeehouse",
                "757-555-5505",
                "contact@curecoffeehouse.com",
                "Patrick David",
                "Quarterly",
                400.00,
                "Large display tank. Clean glass and inspect pumps.",
                new Tank(0, "Saltwater Reef", 125, "Saltwater")
        ));
    }

    private CustomerAccount buildCustomerAccountFromResultSet(ResultSet rs) throws SQLException {
        Tank tank = new Tank(
                rs.getInt("tank_id"),
                rs.getString("tank_type"),
                rs.getDouble("tank_size"),
                rs.getString("water_type")
        );

        return new CustomerAccount(
                rs.getInt("account_id"),
                rs.getString("customer_name"),
                rs.getString("phone_number"),
                rs.getString("email"),
                rs.getString("assigned_worker"),
                rs.getString("service_frequency"),
                rs.getDouble("monthly_price"),
                rs.getString("maintenance_notes"),
                tank
        );
    }
}