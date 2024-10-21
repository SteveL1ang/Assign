
package student.information.management.system;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StudentSystem {
    private static final Logger logger = Logger.getLogger(StudentSystem.class.getName());
    private static StudentDataManager studentDataManager = new StudentDataManager();
    private static Map<String, Student> studentMap = new HashMap<>();

    private static final String ADD_STUDENT = "1";
    private static final String DELETE_STUDENT = "2";
    private static final String UPDATE_STUDENT = "3";
    private static final String QUERY_STUDENT = "4";
    private static final String EXIT = "5";
   
    public static void StartSystem() {
        try {
            List<Student> list = studentDataManager.readFromDB(); // Read from the database
            for (Student s : list) {
                studentMap.put(s.getId(), s);
            }
            logger.info("Successfully loaded " + list.size() + " students from database.");
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error loading students from database", e);
            System.out.println("Error loading students from database: " + e.getMessage());
        }

        Scanner sc = new Scanner(System.in);
 
        loop:
        while (true) {
            System.out.println();
            System.out.println("--------------------Welcome to the Student Information Management System ------------------");
            System.out.println("Press 1: Add new student's information");
            System.out.println("Press 2: Delete a student's information");
            System.out.println("Press 3: Edit student's information");
            System.out.println("Press 4: Query student's information");
            System.out.println("Press 5: Exit system");
            System.out.println("Please enter a number:");
            String choose = sc.next();

            switch (choose) {
                case ADD_STUDENT:
                    addStudent();
                    break;
                case DELETE_STUDENT:
                    deleteStudent();
                    break;
                case UPDATE_STUDENT:
                    editStudent();
                    break;
                case QUERY_STUDENT:
                    queryStudent();
                    break;
                case EXIT:
                    System.out.println("Are you sure you want to exit? (yes/no)");
                    String confirmExit = sc.next();
                    if (confirmExit.equalsIgnoreCase("yes")) {
                        System.out.println("Exiting the system. Goodbye!");
                        break loop;
                    } else {
                        System.out.println("Exit cancelled.");
                    }
                    break;
               default:
                        System.out.println("Invalid choice. Please try again.");
            }
        } 
    }

    public static void addStudent() {
        Student s = new Student();
        Scanner sc = new Scanner(System.in);
        String id = null;
        
        while (true) {
            System.out.println("Please enter a student's ID:");
            id = sc.next();
            if (id.trim().isEmpty()) {
                System.out.println("ID cannot be blank. Please enter a valid ID.");
                continue;
            }
            if (studentMap.containsKey(id)) {
                System.out.println("This ID already exists, please enter another one.");
            } else {
                s.setId(id);
                break;
            }
        }

        System.out.println("Please enter a student's Name:");
        String name = sc.next();
        s.setName(name);

        int age = 0;
        boolean validAge = false;

        while (!validAge) {
            try {
                System.out.println("Please enter a student's Age:");
                age = sc.nextInt();
                if (age > 0) {
                    validAge = true;
                } else {
                    System.out.println("Age must be a positive number. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid age (numbers only).");
                sc.next();
            }
        }
        s.setAge(age);

        System.out.println("Please enter a student's Address:");
        String address = sc.next();
        s.setAddress(address);

        try {
            studentDataManager.addStudent(s);
            studentMap.put(id, s);
            System.out.println("The student's information has been added successfully.");
            logger.info("Student added: " + s.getName());
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error adding student to database", e);
            System.out.println("Error adding student to database: " + e.getMessage());
        }
    }

    public static void deleteStudent() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter the id you want to delete:");
        String id = sc.next();
        if (studentMap.containsKey(id)) {
            try {
                studentDataManager.deleteStudent(id);
                studentMap.remove(id);
                System.out.println("The student whose ID is " + id + " has been deleted");
                logger.info("Student deleted: ID " + id);
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "Error deleting student from database", e);
                System.out.println("Error deleting student from database: " + e.getMessage());
            }
        } else {
            System.out.println("This ID does not exist and cannot be deleted");
        }
    }

    public static void editStudent() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter the Id of the Student who needs their information edited:");
        String id = sc.next();

        if (studentMap.containsKey(id)) {
            Student student = studentMap.get(id);

            System.out.println("Please enter a new name:");
            String rename = sc.next();
            student.setName(rename);

            int newAge = 0;
            boolean validAge = false;

            while (!validAge) {
                try {
                    System.out.println("Please enter a new age:");
                    newAge = sc.nextInt();
                    if (newAge > 0) {
                        validAge = true;
                    } else {
                        System.out.println("Age must be a positive number. Please try again.");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a valid age (numbers only).");
                    sc.next();
                }
            }
            student.setAge(newAge);

            System.out.println("Please enter a new address:");
            String newAddress = sc.next();
            student.setAddress(newAddress);

            try {
                studentDataManager.updateStudent(student);
                System.out.println("Student information has been edited successfully.");
                logger.info("Student updated: " + student.getName());
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "Error updating student in database", e);
                System.out.println("Error updating student in database: " + e.getMessage());
            }
        } else {
            System.out.println("This ID does not exist, please enter again.");
        }
    }

    public static void queryStudent() {
        try {
            List<Student> students = studentDataManager.readFromDB();
            if (students.isEmpty()) {
                System.out.println("There is no information, please add first");
                return;
            }
            System.out.println("ID\tName\tAge\tAddress");
            for (Student student : students) {
                System.out.println(student.getId() + "\t" + student.getName() + "\t" + student.getAge() + "\t" + student.getAddress());
            }
            logger.info("Student query executed");
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error querying students from database", e);
            System.out.println("Error querying students from database: " + e.getMessage());
        }
    }
}


 

