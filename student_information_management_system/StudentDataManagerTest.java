/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package student_information_management_system;

import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import java.sql.SQLException;

/**
 *
 * @author 梁豪森
 */
public class StudentDataManagerTest {
    private StudentDataManager studentManager;
    private static final String TEST_ID = "TEST001";
    
    public StudentDataManagerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
      public void setUp() {
        studentManager = new StudentDataManager();
        try {
            cleanupTestData();
        } catch (SQLException e) {
            fail("Failed to clean up test data: " + e.getMessage());
        }
    }
    private void cleanupTestData() throws SQLException {
      try {
            studentManager.deleteStudent(TEST_ID);
        } catch (SQLException e) {
            // Ignore exception for non-existent test student
        }
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of readFromDB method, of class StudentDataManager.
     */
    @Test
    public void testReadFromDB() throws Exception {
        System.out.println("readFromDB");
        StudentDataManager instance = new StudentDataManager();
        List<Student> expResult = null;
        List<Student> result = instance.readFromDB();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of writeToDB method, of class StudentDataManager.
     */
    @Test
    public void testWriteToDB() throws Exception {
        System.out.println("writeToDB");
        List<Student> students = null;
        StudentDataManager instance = new StudentDataManager();
        instance.writeToDB(students);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

@Test
    public void testAddAndGetStudent() throws SQLException {
        // Prepare test data
        Student student = new Student(TEST_ID, "Test Student", 20, "Test Address");
        
        // Add student to database
        studentManager.addStudent(student);
        
        // Verify student was added correctly
        Student retrieved = studentManager.getStudentById(TEST_ID);
        assertNotNull("Retrieved student should not be null", retrieved);
        assertEquals("Student name should match", "Test Student", retrieved.getName());
        assertEquals("Student age should match", 20, retrieved.getAge());
        assertEquals("Student address should match", "Test Address", retrieved.getAddress());
    }

    /**
     * Test of getStudentById method, of class StudentDataManager.
     */
    @Test
    public void testGetStudentById() throws Exception {
        System.out.println("getStudentById");
        String studentId = "";
        StudentDataManager instance = new StudentDataManager();
        Student expResult = null;
        Student result = instance.getStudentById(studentId);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of updateStudent method, of class StudentDataManager.
     */
@Test
    public void testUpdateStudent() throws SQLException {
        // Add initial student record
        Student student = new Student(TEST_ID, "Test Student", 20, "Test Address");
        studentManager.addStudent(student);
        
        // Update student information
        student.setAge(21);
        student.setAddress("New Address");
        studentManager.updateStudent(student);
        
        // Verify updates were applied
        Student updated = studentManager.getStudentById(TEST_ID);
        assertEquals("Updated age should match", 21, updated.getAge());
        assertEquals("Updated address should match", "New Address", updated.getAddress());
    }

    /**
     * Test of deleteStudent method, of class StudentDataManager.
     */
    @Test
    public void testDeleteStudent() throws Exception {
        System.out.println("deleteStudent");
        String id = "";
        StudentDataManager instance = new StudentDataManager();
        instance.deleteStudent(id);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
