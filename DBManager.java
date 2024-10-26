/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package student_information_management_system;

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
                ensureTablesExist();
                logger.info("Database connection established and initialized.");
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "Error connecting to database", e);
                throw new SQLException("Failed to connect to the database", e);
            }
        }
        return conn;
    }

    private static void ensureTablesExist() throws SQLException {
        if (!tablesExist()) {
            createTablesAndInsertData(true);
        }
    }

    private static boolean tablesExist() {
        try (Statement stmt = conn.createStatement()) {
            stmt.executeQuery("SELECT 1 FROM Users");
            stmt.executeQuery("SELECT 1 FROM Students");
            stmt.executeQuery("SELECT 1 FROM Courses");
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    private static void createTablesAndInsertData(boolean insertInitialData) throws SQLException {
        try (Statement stmt = conn.createStatement()) {
            // Create Users table
            stmt.execute("CREATE TABLE Users (" +
                    "userName VARCHAR(15) PRIMARY KEY, " +
                    "password VARCHAR(50), " +
                    "phoneNumber VARCHAR(20))");

            // Create Students table
            stmt.execute("CREATE TABLE Students (" +
                    "id VARCHAR(20) PRIMARY KEY, " +
                    "name VARCHAR(50), " +
                    "age INT, " +
                    "address VARCHAR(100))");

            // Create Courses table
            stmt.execute("CREATE TABLE Courses (" +
                    "courseId VARCHAR(20) PRIMARY KEY, " +
                    "courseName VARCHAR(100), " +
                    "credits INT, " +
                    "teacherName VARCHAR(50))");

            if (insertInitialData) {
                // Insert sample data into Users table
                stmt.execute("INSERT INTO Users VALUES " +
                        "('admin', 'password123', '1234567890'), " +
                        "('john_doe', 'pass456', '9876543210'), " +
                        "('jane_smith', 'secret789', '5555555555')");

                // Insert sample data into Students table
                stmt.execute("INSERT INTO Students VALUES " +
                        "('S001', 'John', 20, 'City'), " +
                        "('S002', 'Jane', 22, 'Town'), " +
                        "('S003', 'Bob', 21, 'Village')");

                // Insert sample data into Courses table
                stmt.execute("INSERT INTO Courses VALUES " +
                        "('CS101', 'Introduction to Programming', 3, 'Dr. Smith'), " +
                        "('MATH201', 'Calculus I', 4, 'Prof. Johnson'), " +
                        "('ENG102', 'English Composition', 3, 'Ms. Brown')");
                        
            }

            logger.info("Database tables created" + (insertInitialData ? " and sample data inserted." : "."));
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error creating database tables or inserting data", e);
            throw e;
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