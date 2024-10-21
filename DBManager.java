/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package student.information.management.system;

/**
 *
 * @author 梁豪森
 */
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBManager {
    private static final Logger logger = Logger.getLogger(DBManager.class.getName());
    private static final String URL = "jdbc:derby:StudentDB;create=true";
    private static Connection conn = null;

    public static Connection getConnection() throws SQLException {
        if (conn == null || conn.isClosed()) {
            try {
                conn = DriverManager.getConnection(URL);
                createTablesAndInsertData();
                logger.info("Database connection established and initialized.");
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "Error connecting to database", e);
                throw new SQLException("Failed to connect to the database", e);
            }
        }
        return conn;
    }

    private static void createTablesAndInsertData() throws SQLException {
        try (Statement stmt = conn.createStatement()) {
            // Create User table
            stmt.execute("CREATE TABLE Users (" +
                    "userName VARCHAR(15) PRIMARY KEY, " +
                    "password VARCHAR(50), " +
                    "phoneNumber VARCHAR(20))");

            // Create Student table
            stmt.execute("CREATE TABLE Students (" +
                    "id VARCHAR(20) PRIMARY KEY, " +
                    "name VARCHAR(50), " +
                    "age INT, " +
                    "address VARCHAR(100), " +
                    "courseEnrollment VARCHAR(100), " +
                    "grades VARCHAR(100), " +
                    "honors VARCHAR(100))");
            
            // Insert sample data into Users table
            stmt.execute("INSERT INTO Users VALUES " +
                    "('admin', 'password123', '1234567890'), " +
                    "('john_doe', 'pass456', '9876543210'), " +
                    "('jane_smith', 'secret789', '5555555555')");

            // Insert sample data into Students table
            stmt.execute("INSERT INTO Students VALUES " +
                    "('S001', 'John Doe', 20, '123 Main St, City', 'Computer Science', 'A,B+,A-', 'Dean''s List'), " +
                    "('S002', 'Jane Smith', 22, '456 Elm St, Town', 'Engineering', 'B,A,B+', 'Honors Program'), " +
                    "('S003', 'Bob Johnson', 21, '789 Oak St, Village', 'Mathematics', 'A-,B,A', 'Research Assistant')");

            logger.info("Database tables created and sample data inserted.");
        } catch (SQLException e) {
            // Table already exists
            if (!e.getSQLState().equals("X0Y32")) {
                logger.log(Level.SEVERE, "Error creating database tables or inserting data", e);
                throw e;
            }
        }
    }

    public static void closeConnection() {
        if (conn != null) {
            try {
                conn.close();
                logger.info("Database connection closed.");
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "Error closing database connection", e);
            }
        }
    }
}