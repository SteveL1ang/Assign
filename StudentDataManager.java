package student.information.management.system;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StudentDataManager implements DataManager<Student> {
    private static final Logger logger = Logger.getLogger(StudentDataManager.class.getName());

    @Override
    public List<Student> readFromDB() throws SQLException {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM Students";
        try (Connection conn = DBManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                students.add(new Student(rs.getString("id"), rs.getString("name"), rs.getInt("age"),
                        rs.getString("address"), rs.getString("courseEnrollment"), rs.getString("grades"),
                        rs.getString("honors")));
            }
            logger.info("Successfully read " + students.size() + " students from database.");
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error reading students from database", e);
            throw new SQLException("Failed to read students from database: " + e.getMessage(), e);
        }
        return students;
    }

    @Override
    public void writeToDB(List<Student> students) throws SQLException {
        String sql = "INSERT INTO Students (id, name, age, address, courseEnrollment, grades, honors) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            for (Student student : students) {
                pstmt.setString(1, student.getId());
                pstmt.setString(2, student.getName());
                pstmt.setInt(3, student.getAge());
                pstmt.setString(4, student.getAddress());
                pstmt.setString(5, student.getCourseEnrollment());
                pstmt.setString(6, student.getGrades());
                pstmt.setString(7, student.getHonors());
                pstmt.executeUpdate();
            }
            logger.info("Successfully wrote " + students.size() + " students to database.");
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error writing students to database", e);
            throw new SQLException("Failed to write students to database: " + e.getMessage(), e);
        }
    }

    public void addStudent(Student student) throws SQLException {
        String sql = "INSERT INTO Students (id, name, age, address, courseEnrollment, grades, honors) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, student.getId());
            pstmt.setString(2, student.getName());
            pstmt.setInt(3, student.getAge());
            pstmt.setString(4, student.getAddress());
            pstmt.setString(5, student.getCourseEnrollment());
            pstmt.setString(6, student.getGrades());
            pstmt.setString(7, student.getHonors());
            pstmt.executeUpdate();
            logger.info("Student added successfully: " + student.getName());
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error adding student to database", e);
            throw new SQLException("Failed to add student to database", e);
        }
    }

    public void updateStudent(Student student) throws SQLException {
        String sql = "UPDATE Students SET name = ?, age = ?, address = ?, courseEnrollment = ?, grades = ?, honors = ? WHERE id = ?";
        try (Connection conn = DBManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, student.getName());
            pstmt.setInt(2, student.getAge());
            pstmt.setString(3, student.getAddress());
            pstmt.setString(4, student.getCourseEnrollment());
            pstmt.setString(5, student.getGrades());
            pstmt.setString(6, student.getHonors());
            pstmt.setString(7, student.getId());
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