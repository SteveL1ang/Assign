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

public class CourseDataManager implements DataManager<Course> {
    private static final Logger logger = Logger.getLogger(CourseDataManager.class.getName());

    @Override
    public List<Course> readFromDB() throws SQLException {
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT * FROM Courses";
        try (Connection conn = DBManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                courses.add(new Course(rs.getString("courseId"), rs.getString("courseName"),
                        rs.getInt("credits"), rs.getString("teacherName")));
            }
            logger.info("Successfully read " + courses.size() + " courses from database.");
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error reading courses from database", e);
            throw new SQLException("Failed to read courses from database: " + e.getMessage(), e);
        }
        return courses;
    }

    @Override
    public void writeToDB(List<Course> courses) throws SQLException {
        String sql = "INSERT INTO Courses (courseId, courseName, credits, teacherName) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            for (Course course : courses) {
                pstmt.setString(1, course.getCourseId());
                pstmt.setString(2, course.getCourseName());
                pstmt.setInt(3, course.getCredits());
                pstmt.setString(4, course.getTeacherName());
                pstmt.executeUpdate();
            }
            logger.info("Successfully wrote " + courses.size() + " courses to database.");
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error writing courses to database", e);
            throw new SQLException("Failed to write courses to database: " + e.getMessage(), e);
        }
    }

    public void addCourse(Course course) throws SQLException {
        String sql = "INSERT INTO Courses (courseId, courseName, credits, teacherName) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, course.getCourseId());
            pstmt.setString(2, course.getCourseName());
            pstmt.setInt(3, course.getCredits());
            pstmt.setString(4, course.getTeacherName());
            pstmt.executeUpdate();
            logger.info("Course added successfully: " + course.getCourseName());
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error adding course to database", e);
            throw new SQLException("Failed to add course to database", e);
        }
    }

    public void updateCourse(Course course) throws SQLException {
        String sql = "UPDATE Courses SET courseName = ?, credits = ?, teacherName = ? WHERE courseId = ?";
        try (Connection conn = DBManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, course.getCourseName());
            pstmt.setInt(2, course.getCredits());
            pstmt.setString(3, course.getTeacherName());
            pstmt.setString(4, course.getCourseId());
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Updating course failed, no rows affected.");
            }
            logger.info("Course updated successfully: " + course.getCourseName());
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error updating course in database", e);
            throw new SQLException("Failed to update course in database", e);
        }
    }
    
    public Course getCourseById(String courseId) throws SQLException {
        String sql = "SELECT * FROM Courses WHERE courseId = ?";
        try (Connection conn = DBManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, courseId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    // Create and return a Course object with retrieved details
                    return new Course(
                        rs.getString("courseId"),
                        rs.getString("courseName"),
                        rs.getInt("credits"),
                        rs.getString("teacherName")
                    );
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Error retrieving course by ID", e);
        }
        return null; // Return null if no course is found with the given ID
}

    public void deleteCourse(String courseId) throws SQLException {
        String sql = "DELETE FROM Courses WHERE courseId = ?";
        try (Connection conn = DBManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, courseId);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Deleting course failed, no rows affected.");
            }
            logger.info("Course deleted successfully: ID " + courseId);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error deleting course from database", e);
            throw new SQLException("Failed to delete course from database", e);
        }
    }
}
