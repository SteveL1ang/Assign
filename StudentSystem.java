/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package student_information_management_system;

/**
 *
 * @author 梁豪森
 */
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
        private static CourseDataManager courseDataManager = new CourseDataManager();
        private static Map<String, Student> studentMap = new HashMap<>();
        private static Map<String, Course> courseMap = new HashMap<>();

        private static final String MANAGE_STUDENTS = "1";
        private static final String MANAGE_COURSES = "2";
        private static final String EXIT = "3";

        /**
         * Starts the education management system and provides the main menu for user interaction.
         */
        public static void StartSystem() {
            loadData();
            Scanner sc = new Scanner(System.in);

            loop:
            while (true) {
                // Display main menu
                System.out.println();
                System.out.println("--------------------Welcome to the Education Management System ------------------");
                System.out.println("Press 1: Manage Students");
                System.out.println("Press 2: Manage Courses");
                System.out.println("Press 3: Exit system");
                System.out.println("Please enter a number:");
                String choose = sc.next();

                // Process user choice
                switch (choose) {
                    case MANAGE_STUDENTS:
                        manageStudents();
                        break;
                    case MANAGE_COURSES:
                        manageCourses();
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

        private static void loadData() {
            try {
                // Load existing students from the database
                List<Student> studentList = studentDataManager.readFromDB();
                for (Student s : studentList) {
                    studentMap.put(s.getId(), s);
                }
                logger.info("Successfully loaded " + studentList.size() + " students from database.");

                // Load existing courses from the database
                List<Course> courseList = courseDataManager.readFromDB();
                for (Course c : courseList) {
                    courseMap.put(c.getCourseId(), c);
                }
                logger.info("Successfully loaded " + courseList.size() + " courses from database.");
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "Error loading data from database", e);
                System.out.println("Error loading data from database: " + e.getMessage());
            }
        }

        private static void manageStudents() {
            Scanner sc = new Scanner(System.in);

            while (true) {
                System.out.println("\n-------- Student Management --------");
                System.out.println("1: Add new student");
                System.out.println("2: Delete a student");
                System.out.println("3: Edit student information");
                System.out.println("4: Query student information");
                System.out.println("5: Return to main menu");
                System.out.println("Please enter a number:");
                String choice = sc.next();

                switch (choice) {
                    case "1":
                        addStudent();
                        break;
                    case "2":
                        deleteStudent();
                        break;
                    case "3":
                        editStudent();
                        break;
                    case "4":
                        queryStudent();
                        break;
                    case "5":
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        }

        private static void manageCourses() {
            Scanner sc = new Scanner(System.in);

            while (true) {
                System.out.println("\n-------- Course Management --------");
                System.out.println("1: Add new course");
                System.out.println("2: Delete a course");
                System.out.println("3: Edit course information");
                System.out.println("4: Query course information");
                System.out.println("5: Return to main menu");
                System.out.println("Please enter a number:");
                String choice = sc.next();

                switch (choice) {
                    case "1":
                        addCourse();
                        break;
                    case "2":
                        deleteCourse();
                        break;
                    case "3":
                        editCourse();
                        break;
                    case "4":
                        queryCourse();
                        break;
                    case "5":
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        }


        /**
         * Adds a new student to the system.
         * Prompts the user for student details and validates the input.
         */
        public static void addStudent() {
            Student s = new Student();
            Scanner sc = new Scanner(System.in);
            String id = null;

            // Input and validate student ID
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

            // Input student name
            System.out.println("Please enter a student's Name:");
            String name = sc.next();
            s.setName(name);

            // Input and validate student age
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
                    sc.next();// Clear the invalid input
                }
            }
            s.setAge(age);

            // Input student address
            System.out.println("Please enter a student's Address:");
            String address = sc.next();
            s.setAddress(address);

            // Add student to database and local map
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

        /**
         * Deletes a student from the system.
         * Prompts the user for the student ID and removes the student if found.
         */
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

        /**
         * Edits an existing student's information.
         * Prompts the user for the student ID and allows updating of student details.
         */
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

        /**
         * Queries and displays all student information in the system.
         */
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

        // Course management methods
        private static void addCourse() {
            Course c = new Course();
            Scanner sc = new Scanner(System.in);
            String courseId = null;

            // Input and validate course ID
            while (true) {
                System.out.println("Please enter a course ID:");
                courseId = sc.next();
                if (courseId.trim().isEmpty()) {
                    System.out.println("Course ID cannot be blank. Please enter a valid ID.");
                    continue;
                }
                if (courseMap.containsKey(courseId)) {
                    System.out.println("This course ID already exists, please enter another one.");
                } else {
                    c.setCourseId(courseId);
                    break;
                }
            }

            // Input course name
            System.out.println("Please enter the course name:");
            String courseName = sc.next();
            c.setCourseName(courseName);

            // Input and validate course credits
            int credits = 0;
            boolean validCredits = false;

            while (!validCredits) {
                try {
                    System.out.println("Please enter the course credits:");
                    credits = sc.nextInt();
                    if (credits > 0) {
                        validCredits = true;
                    } else {
                        System.out.println("Credits must be a positive number. Please try again.");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a valid number for credits.");
                    sc.next(); // Clear the invalid input
                }
            }
            c.setCredits(credits);

            // Input teacher name
            System.out.println("Please enter the teacher's name:");
            String teacherName = sc.next();
            c.setTeacherName(teacherName);

            // Add course to database and local map
            try {
                courseDataManager.addCourse(c);
                courseMap.put(courseId, c);
                System.out.println("The course information has been added successfully.");
                logger.info("Course added: " + c.getCourseName());
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "Error adding course to database", e);
                System.out.println("Error adding course to database: " + e.getMessage());
            }
        }

        private static void deleteCourse() {
            Scanner sc = new Scanner(System.in);
            System.out.println("Please enter the course ID you want to delete:");
            String courseId = sc.next();
            if (courseMap.containsKey(courseId)) {
                try {
                    courseDataManager.deleteCourse(courseId);
                    courseMap.remove(courseId);
                    System.out.println("The course with ID " + courseId + " has been deleted");
                    logger.info("Course deleted: ID " + courseId);
                } catch (SQLException e) {
                    logger.log(Level.SEVERE, "Error deleting course from database", e);
                    System.out.println("Error deleting course from database: " + e.getMessage());
                }
            } else {
                System.out.println("This course ID does not exist and cannot be deleted");
            }
        }

        private static void editCourse() {
            Scanner sc = new Scanner(System.in);
            System.out.println("Please enter the ID of the course that needs to be edited:");
            String courseId = sc.next();

            if (courseMap.containsKey(courseId)) {
                Course course = courseMap.get(courseId);

                System.out.println("Please enter a new course name:");
                String newName = sc.next();
                course.setCourseName(newName);

                int newCredits = 0;
                boolean validCredits = false;

                while (!validCredits) {
                    try {
                        System.out.println("Please enter new credits:");
                        newCredits = sc.nextInt();
                        if (newCredits > 0) {
                            validCredits = true;
                        } else {
                            System.out.println("Credits must be a positive number. Please try again.");
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input. Please enter a valid number for credits.");
                        sc.next();
                    }
                }
                course.setCredits(newCredits);

                System.out.println("Please enter a new teacher name:");
                String newTeacherName = sc.next();
                course.setTeacherName(newTeacherName);

                try {
                    courseDataManager.updateCourse(course);
                    System.out.println("Course information has been edited successfully.");
                    logger.info("Course updated: " + course.getCourseName());
                } catch (SQLException e) {
                    logger.log(Level.SEVERE, "Error updating course in database", e);
                    System.out.println("Error updating course in database: " + e.getMessage());
                }
            } else {
                System.out.println("This course ID does not exist, please enter again.");
            }
        }

        private static void queryCourse() {
            try {
                List<Course> courses = courseDataManager.readFromDB();
                if (courses.isEmpty()) {
                    System.out.println("There is no course information, please add first");
                    return;
                }
                System.out.println("Course ID\tCourse Name\tCredits\tTeacher Name");
                for (Course course : courses) {
                    System.out.println(course.getCourseId() + "\t" + course.getCourseName() + "\t" +
                            course.getCredits() + "\t" + course.getTeacherName());
                }
                logger.info("Course query executed");
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "Error querying courses from database", e);
                System.out.println("Error querying courses from database: " + e.getMessage());
            }
        }
    }

