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

public class StudentDataManager implements DataManager<Student> {
    private static final Logger logger = Logger.getLogger(StudentDataManager.class.getName());

    /**
     * Reads all students from the database.
     * @return A list of all students in the database.
     * @throws SQLException if a database access error occurs.
     */
    @Override
    public List<Student> readFromDB() throws SQLException {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM Students";
        try (Connection conn = DBManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                students.add(new Student(rs.getString("id"), rs.getString("name"),
                        rs.getInt("age"), rs.getString("address")));
            }
            logger.info("Successfully read " + students.size() + " students from database.");
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error reading students from database", e);
            throw new SQLException("Failed to read students from database: " + e.getMessage(), e);
        }
        return students;
    }

    /**
     * Writes a list of students to the database.
     * @param students The list of students to be written to the database.
     * @throws SQLException if a database access error occurs.
     */
    @Override
    public void writeToDB(List<Student> students) throws SQLException {
        String sql = "INSERT INTO Students (id, name, age, address) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            for (Student student : students) {
                pstmt.setString(1, student.getId());
                pstmt.setString(2, student.getName());
                pstmt.setInt(3, student.getAge());
                pstmt.setString(4, student.getAddress());
                pstmt.executeUpdate();
            }
            logger.info("Successfully wrote " + students.size() + " students to database.");
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error writing students to database", e);
            throw new SQLException("Failed to write students to database: " + e.getMessage(), e);
        }
    }

    /**
     * Adds a new student to the database.
     * @param student The student to be added.
     * @throws SQLException if a database access error occurs.
     */
    public void addStudent(Student student) throws SQLException {
        String sql = "INSERT INTO Students (id, name, age, address) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, student.getId());
            pstmt.setString(2, student.getName());
            pstmt.setInt(3, student.getAge());
            pstmt.setString(4, student.getAddress());
            pstmt.executeUpdate();
            logger.info("Student added successfully: " + student.getName());
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error adding student to database", e);
            throw new SQLException("Failed to add student to database", e);
        }
    }

    public Student getStudentById(String studentId) throws SQLException {
        String sql = "SELECT * FROM Students WHERE id = ?";
        try (Connection conn = DBManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, studentId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Student(rs.getString("id"), rs.getString("name"),
                            rs.getInt("age"), rs.getString("address"));
                } else {
                    return null; // No student found
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Error retrieving student by ID", e);
        }
    }
    
    public void updateStudent(Student student) throws SQLException {
        String sql = "UPDATE Students SET name = ?, age = ?, address = ? WHERE id = ?";
        try (Connection conn = DBManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, student.getName());
            pstmt.setInt(2, student.getAge());
            pstmt.setString(3, student.getAddress());
            pstmt.setString(4, student.getId());
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Updating student failed, no rows affected.");
            }
            logger.info("Student updated successfully: " + student.getName());
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error updating student in database", e);
            throw new SQLException("Failed to update student in database", e);
        }
    }

    /**
     * Deletes a student from the database.
     * @param id The ID of the student to be deleted.
     * @throws SQLException if a database access error occurs or if the deletion fails.
     */
    public void deleteStudent(String id) throws SQLException {
        String sql = "DELETE FROM Students WHERE id = ?";
        try (Connection conn = DBManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Deleting student failed, no rows affected.");
            }
            logger.info("Student deleted successfully: ID " + id);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error deleting student from database", e);
            throw new SQLException("Failed to delete student from database", e);
        }
    }
}
