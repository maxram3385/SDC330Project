/*
Name: Max Ramos
Date: May 2, 2026
SDC330 Week 4 Course Project - Database Support

Handles database operations for customer accounts and tanks.
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
        String createTanksTable = """
                CREATE TABLE IF NOT EXISTS tanks (
                    tank_id INTEGER PRIMARY KEY AUTOINCREMENT,
                    tank_type TEXT NOT NULL,
                    tank_size REAL NOT NULL
                );
                """;

        String createCustomerAccountsTable = """
                CREATE TABLE IF NOT EXISTS customer_accounts (
                    account_id INTEGER PRIMARY KEY AUTOINCREMENT,
                    customer_name TEXT NOT NULL,
                    phone_number TEXT,
                    email TEXT,
                    assigned_worker TEXT,
                    service_frequency TEXT,
                    service_hours REAL,
                    tank_id INTEGER,
                    FOREIGN KEY (tank_id) REFERENCES tanks(tank_id)
                );
                """;

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(createTanksTable);
            stmt.execute(createCustomerAccountsTable);
        } catch (SQLException e) {
            System.out.println("Error creating tables: " + e.getMessage());
        }
    }

    public int getAccountCount() {
        String sql = "SELECT COUNT(*) AS total FROM customer_accounts";

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
        String insertTankSql = """
                INSERT INTO tanks (tank_type, tank_size)
                VALUES (?, ?);
                """;

        String insertAccountSql = """
                INSERT INTO customer_accounts
                (customer_name, phone_number, email, assigned_worker, service_frequency, service_hours, tank_id)
                VALUES (?, ?, ?, ?, ?, ?, ?);
                """;

        try {
            conn.setAutoCommit(false);

            int tankId = -1;

            try (PreparedStatement tankStmt = conn.prepareStatement(insertTankSql, Statement.RETURN_GENERATED_KEYS)) {
                tankStmt.setString(1, account.getTank().getTankType());
                tankStmt.setDouble(2, account.getTank().getTankSize());
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
                accountStmt.setDouble(6, account.getServiceHours());
                accountStmt.setInt(7, tankId);
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

        String sql = """
                SELECT
                    ca.account_id,
                    ca.customer_name,
                    ca.phone_number,
                    ca.email,
                    ca.assigned_worker,
                    ca.service_frequency,
                    ca.service_hours,
                    t.tank_id,
                    t.tank_type,
                    t.tank_size
                FROM customer_accounts ca
                JOIN tanks t ON ca.tank_id = t.tank_id
                ORDER BY ca.account_id;
                """;

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Tank tank = new Tank(
                        rs.getInt("tank_id"),
                        rs.getString("tank_type"),
                        rs.getDouble("tank_size")
                );

                CustomerAccount account = new CustomerAccount(
                        rs.getInt("account_id"),
                        rs.getString("customer_name"),
                        rs.getString("phone_number"),
                        rs.getString("email"),
                        rs.getString("assigned_worker"),
                        rs.getString("service_frequency"),
                        rs.getDouble("service_hours"),
                        tank
                );

                accounts.add(account);
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving accounts: " + e.getMessage());
        }

        return accounts;
    }

    public ArrayList<CustomerAccount> searchCustomerAccountsByName(String searchName) {
        ArrayList<CustomerAccount> accounts = new ArrayList<>();

        String sql = """
                SELECT
                    ca.account_id,
                    ca.customer_name,
                    ca.phone_number,
                    ca.email,
                    ca.assigned_worker,
                    ca.service_frequency,
                    ca.service_hours,
                    t.tank_id,
                    t.tank_type,
                    t.tank_size
                FROM customer_accounts ca
                JOIN tanks t ON ca.tank_id = t.tank_id
                WHERE ca.customer_name LIKE ?
                ORDER BY ca.account_id;
                """;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + searchName + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Tank tank = new Tank(
                            rs.getInt("tank_id"),
                            rs.getString("tank_type"),
                            rs.getDouble("tank_size")
                    );

                    CustomerAccount account = new CustomerAccount(
                            rs.getInt("account_id"),
                            rs.getString("customer_name"),
                            rs.getString("phone_number"),
                            rs.getString("email"),
                            rs.getString("assigned_worker"),
                            rs.getString("service_frequency"),
                            rs.getDouble("service_hours"),
                            tank
                    );

                    accounts.add(account);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error searching accounts: " + e.getMessage());
        }

        return accounts;
    }

    public boolean updateServiceHours(int accountId, double newServiceHours) {
        String sql = """
                UPDATE customer_accounts
                SET service_hours = ?
                WHERE account_id = ?;
                """;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, newServiceHours);
            stmt.setInt(2, accountId);

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            System.out.println("Error updating service hours: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteCustomerAccount(int accountId) {
        String findTankSql = """
                SELECT tank_id
                FROM customer_accounts
                WHERE account_id = ?;
                """;

        String deleteAccountSql = """
                DELETE FROM customer_accounts
                WHERE account_id = ?;
                """;

        String deleteTankSql = """
                DELETE FROM tanks
                WHERE tank_id = ?;
                """;

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
                1.5,
                new Tank(0, "Saltwater Fish Only", 75)
        ));

        insertCustomerAccount(new CustomerAccount(
                0,
                "Lyn Primo",
                "757-555-2202",
                "lyn.primo@email.com",
                "Max Ramos",
                "Weekly",
                2.0,
                new Tank(0, "Freshwater Planted", 55)
        ));

        insertCustomerAccount(new CustomerAccount(
                0,
                "Michelle Joseph",
                "757-555-3303",
                "michelle.joseph@email.com",
                "David Schlamee",
                "Monthly",
                1.0,
                new Tank(0, "Freshwater Planted", 40)
        ));

        insertCustomerAccount(new CustomerAccount(
                0,
                "Ashlee Marvelle",
                "757-555-4404",
                "ashlee.marvelle@email.com",
                "Max Ramos",
                "Weekly",
                2.5,
                new Tank(0, "Saltwater Reef", 90)
        ));

        insertCustomerAccount(new CustomerAccount(
                0,
                "Cure Coffeehouse",
                "757-555-5505",
                "contact@curecoffeehouse.com",
                "Patrick David",
                "Quarterly",
                3.0,
                new Tank(0, "Saltwater Reef", 125)
        ));
    }
}