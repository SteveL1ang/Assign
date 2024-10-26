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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDataManager {
    private static final Logger logger = Logger.getLogger(UserDataManager.class.getName());


    // Reads all users from the database.

    public List<User> readUsersFromDB() throws SQLException {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM Users";
        try (Connection conn = DBManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                users.add(new User(rs.getString("userName"), rs.getString("password"), rs.getString("phoneNumber")));
            }
            logger.info("Successfully read " + users.size() + " users from database.");
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error reading users from database", e);
            throw new SQLException("Failed to read users from database", e);
        }
        return users;
    }


    // Adds a new user to the database.

    public void addUser(User newUser) throws SQLException {
        String sql = "INSERT INTO Users (userName, password, phoneNumber) VALUES (?, ?, ?)";
        try (Connection conn = DBManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newUser.getUserName());
            pstmt.setString(2, newUser.getPassword());
            pstmt.setString(3, newUser.getPhoneNumber());
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }
            logger.info("User added successfully: " + newUser.getUserName());
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error adding user to database", e);
            throw new SQLException("Failed to add user to database", e);
        }
    }


    // Updates an existing user's information in the database.

    public void updateUser(User user) throws SQLException {
        String sql = "UPDATE Users SET password = ?, phoneNumber = ? WHERE userName = ?";
        try (Connection conn = DBManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user.getPassword());
            pstmt.setString(2, user.getPhoneNumber());
            pstmt.setString(3, user.getUserName());
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Updating user failed, no rows affected.");
            }
            logger.info("User updated successfully: " + user.getUserName());
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error updating user in database", e);
            throw new SQLException("Failed to update user in database", e);
        }
    }


    // Deletes a user from the database.

    public void deleteUser(String userName) throws SQLException {
        String sql = "DELETE FROM Users WHERE userName = ?";
        try (Connection conn = DBManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, userName);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Deleting user failed, no rows affected.");
            }
            logger.info("User deleted successfully: " + userName);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error deleting user from database", e);
            throw new SQLException("Failed to delete user from database", e);
        }
    }


    // Retrieves a single user from the database by username.

    public User getUserByUsername(String userName) throws SQLException {
        String sql = "SELECT * FROM Users WHERE userName = ?";
        try (Connection conn = DBManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, userName);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new User(rs.getString("userName"), rs.getString("password"), rs.getString("phoneNumber"));
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error retrieving user from database", e);
            throw new SQLException("Failed to retrieve user from database", e);
        }
        return null;
    }


    // Checks if a user exists in the database.

    public boolean userExists(String userName) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Users WHERE userName = ?";
        try (Connection conn = DBManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, userName);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error checking user existence in database", e);
            throw new SQLException("Failed to check user existence in database", e);
        }
        return false;
    }


    // Authenticates a user.

    public boolean authenticateUser(String userName, String password) throws SQLException {
        String sql = "SELECT * FROM Users WHERE userName = ? AND password = ?";
        try (Connection conn = DBManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, userName);
            pstmt.setString(2, password);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next(); // If there's a result, authentication is successful
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error authenticating user", e);
            throw new SQLException("Failed to authenticate user", e);
        }
    }
}
