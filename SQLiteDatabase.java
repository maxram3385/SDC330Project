/*
Name: Max Ramos
Date: May 2, 2026
SDC330 Course Project - Aquarium Maintenance App

Creates and returns a connection to the SQLite database.
*/

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLiteDatabase {
    public static Connection connect(String databaseName) {
        String url = "jdbc:sqlite:" + databaseName;

        try {
            Connection conn = DriverManager.getConnection(url);

            // Enables foreign key support in SQLite.
            try (Statement stmt = conn.createStatement()) {
                stmt.execute("PRAGMA foreign_keys = ON");
            }

            return conn;
        } catch (SQLException e) {
            System.out.println("Database connection error: " + e.getMessage());
            return null;
        }
    }
}