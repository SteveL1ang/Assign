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
public class UserDataManagerTest {
    private UserDataManager userManager;
    private static final String TEST_USERNAME = "testUser";
    private static final String TEST_PASSWORD = "testPass";
    private static final String TEST_PHONE = "1234567890";
    
    public UserDataManagerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
     userManager = new UserDataManager();
        //  Clean test Data
        try {
            cleanupTestData();
        } catch (SQLException e) {
            fail("Failed to clean up test data: " + e.getMessage());
        }
    }
    private void cleanupTestData() throws SQLException {
        try {
            userManager.deleteUser(TEST_USERNAME);
        } catch (SQLException e) {
            // Ignore non-existent user deletion exceptions
        }
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of readUsersFromDB method, of class UserDataManager.
     */
    @Test
    public void testReadUsersFromDB() throws Exception {
        System.out.println("readUsersFromDB");
        UserDataManager instance = new UserDataManager();
        List<User> expResult = null;
        List<User> result = instance.readUsersFromDB();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

     @Test
    public void testAddAndGetUser() throws SQLException {
        // Prepare test data
        User user = new User(TEST_USERNAME, TEST_PASSWORD, TEST_PHONE);
        
        // Test add user
        userManager.addUser(user);
        
        // Verify that the user is successfully added
        User retrieved = userManager.getUserByUsername(TEST_USERNAME);
        assertNotNull("Retrieved user should not be null", retrieved);
        assertEquals("Username should match", TEST_USERNAME, retrieved.getUserName());
        assertEquals("Phone number should match", TEST_PHONE, retrieved.getPhoneNumber());
    }
    
    @Test
    public void testAuthenticateUser() throws SQLException {
        //  Add test user for authentication
        User user = new User(TEST_USERNAME, TEST_PASSWORD, TEST_PHONE);
        userManager.addUser(user);
        
        // Test valid credentials
        assertTrue("Authentication should succeed with correct credentials",
                userManager.authenticateUser(TEST_USERNAME, TEST_PASSWORD));
        
        // Test invalid password
        assertFalse("Authentication should fail with wrong password",
                userManager.authenticateUser(TEST_USERNAME, "wrongpass"));
                
        //Test non-existent user
        assertFalse("Authentication should fail with non-existent user",
                userManager.authenticateUser("nonexistent", TEST_PASSWORD));
    }
    
    @Test(expected = SQLException.class)
    public void testAddDuplicateUser() throws SQLException {
        User user = new User(TEST_USERNAME, TEST_PASSWORD, TEST_PHONE);
        userManager.addUser(user);
        userManager.addUser(user); // Should throw SQLException
    }
    
}
