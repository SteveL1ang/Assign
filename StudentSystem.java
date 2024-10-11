
package student.information.management.system;

import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;

public class StudentSystem {
   private static final String STUDENT_FILE = "students.txt"; // name the file
    private static StudentDataManager studentDataManager = new StudentDataManager();
    private static Map<String, Student> studentMap = new HashMap<>();

    private static final String ADD_STUDENT = "1";
    private static final String DELETE_STUDENT = "2";
    private static final String UPDATE_STUDENT = "3";
    private static final String QUERY_STUDENT = "4";
    private static final String EXIT = "5";
   
    public static void StartSystem() {
    try {
        List<Student> list = studentDataManager.readFromFile(STUDENT_FILE); // use List<Student>
        for (Student s : list) {
            studentMap.put(s.getId(), s); // put student in HashMap
        }
    } catch (IOException e) {
        System.out.println("Error loading students from file: " + e.getMessage());
    }

        Scanner sc = new Scanner(System.in);
 
        loop:
        while (true) {
            System.out.println();
            System.out.println("--------------------Welcome to the Student Information Management System ------------------");
            System.out.println("Press 1: Add new student's information");
            System.out.println("Press 2: Delete a new student's information");
            System.out.println("Press 3: Edit new student's information");
            System.out.println("Press 4: Query new student's information");
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
                    try {
                        studentDataManager.writeToFile(STUDENT_FILE, new ArrayList<>(studentMap.values())); 
                    } catch (IOException e) {
                        System.out.println("Error saving students to file: " + e.getMessage());
                    }
                    System.out.println("Exiting the system. Goodbye!");
                    break loop;
                } else {
                    System.out.println("Exit cancelled.");
                }
                break;
            }
        }
    }

    //add new Student method
    public static void addStudent() {
    Student s = new Student();
    Scanner sc = new Scanner(System.in);
    String id = null;
    
    // Student ID input
    while (true) {
        System.out.println();
        System.out.println("please enter a student's ID:");
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

    // Student Name input
    System.out.println("please enter a student's Name:");
    String name = sc.next();
    s.setName(name);

    // Student Age input with validation
    int age = 0;
    boolean validAge = false;

    while (!validAge) {
        try {
            System.out.println();
            System.out.println("please enter a student's Age:");
            age = sc.nextInt();  // Attempt to read an integer

            if (age > 0) {
                validAge = true;  // Valid age entered
            } else {
                System.out.println("Age must be a positive number. Please try again.");
            }

        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a valid age (numbers only).");
            sc.next();  // Clear the invalid input
        }
    }

    // After validation, set the age
    s.setAge(age);

    // Continue with other student details (address, etc.) or store the student
    System.out.println("please enter a student's Address:");
    String address = sc.next();
    s.setAddress(address);

    // Add the student to the student map
    studentMap.put(id, s);

    // Confirmation message
    System.out.println("The student's information has been added successfully.");
}

    //delete Student method
    public static void deleteStudent() {
        Scanner sc=new Scanner(System.in);
        System.out.println("please enter the id you want delete:");
        String id = sc.next();
        if (studentMap.containsKey(id)) {
            studentMap.remove(id); // HashMap delete student
            System.out.println("The student whose ID is " + id + " has been deleted");
        } else {
            System.out.println("This ID does not exist and cannot be deleted");
        }
    }

    public static void editStudent() {
    Scanner sc = new Scanner(System.in);
    System.out.println("please enter the Id of the Student who needs their information edited:");
    String id = sc.next();

    // Check if the student exists in the system
    if (studentMap.containsKey(id)) {
        Student student = studentMap.get(id);

        // Editing student name
        System.out.println("Please enter a new name:");
        String rename = sc.next();
        student.setName(rename);

        // Editing student age with validation
        int newAge = 0;
        boolean validAge = false;

        while (!validAge) {
            try {
                System.out.println();
                System.out.println("Please enter a new age:");
                newAge = sc.nextInt();  // Attempt to read an integer

                if (newAge > 0) {
                    validAge = true;  // Valid age entered
                } else {
                    System.out.println("Age must be a positive number. Please try again.");
                }

            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid age (numbers only).");
                sc.next();  // Clear the invalid input
            }
        }

        // After validation, set the new age
        student.setAge(newAge);

        // Editing student address
        System.out.println("Please enter a new address:");
        String newAddress = sc.next();
        student.setAddress(newAddress);

        // Confirm the update
        System.out.println("Student information has been edited successfully.");
    } else {
        // If student ID does not exist
        System.out.println("This ID does not exist, please enter again.");
    }
}
    //query new Student method
    public static void queryStudent() {
        if (studentMap.isEmpty()) {
            System.out.println("There is no information, please add first");
            return;
        }
        System.out.println("ID\tName\tAge\tAddress");
        for (Student student : studentMap.values()) {
            System.out.println(student.getId() + "\t" + student.getName() + "\t" + student.getAge() + "\t" + student.getAddress());
        }
    }
    }


 

