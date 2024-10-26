/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package student_information_management_system;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import java.io.*;
import java.sql.SQLException;
/**
 *
 * @author 梁豪森
 */
public class StudentSystemTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final InputStream originalIn = System.in;
    public StudentSystemTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        System.setOut(new PrintStream(outContent));
    }
    
    @After
    public void tearDown() {
        System.setOut(originalOut);
        System.setIn(originalIn);
    }

    /**
     * Test of StartSystem method, of class StudentSystem.
     */
    @Test
    public void testStartSystem() {
        System.out.println("StartSystem");
        StudentSystem.StartSystem();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addStudent method, of class StudentSystem.
     */
    @Test
    public void testAddStudent() throws SQLException {
       // Simulate user input
        String input = "TEST001\nTest Student\n20\nTest Address\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        
        StudentSystem.addStudent();
        
        // Verify success message was displayed
        assertTrue(outContent.toString().contains("added successfully"));
        
        // Verify student was actually added to database
        Student student = new StudentDataManager().getStudentById("TEST001");
        assertNotNull("Student should be found in database", student);
        assertEquals("Test Student", student.getName());
    }

    /**
     * Test of deleteStudent method, of class StudentSystem.
     */
    @Test
    public void testDeleteStudent() {
        System.out.println("deleteStudent");
        StudentSystem.deleteStudent();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of editStudent method, of class StudentSystem.
     */
    @Test
    public void testEditStudent() {
        System.out.println("editStudent");
        StudentSystem.editStudent();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of queryStudent method, of class StudentSystem.
     */
    @Test
    public void testQueryStudent() throws SQLException {
    // Prepare test data
        StudentDataManager manager = new StudentDataManager();
        Student student = new Student("TEST001", "Test Student", 20, "Test Address");
        manager.addStudent(student);
        
        // Perform query
        StudentSystem.queryStudent();
        
        // Verify output contains student information
        String output = outContent.toString();
        assertTrue("Output should contain student ID", output.contains("TEST001"));
        assertTrue("Output should contain student name", output.contains("Test Student"));
        assertTrue("Output should contain student age", output.contains("20"));
        assertTrue("Output should contain student address", output.contains("Test Address"));
    }
    
}
