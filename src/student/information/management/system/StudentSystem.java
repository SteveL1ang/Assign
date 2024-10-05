/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package student.information.management.system;

/**
 *
 * @author 梁豪森
 */
import java.io.IOException;
import java.util.ArrayList;
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
            ArrayList<Student> list = studentDataManager.readFromFile(STUDENT_FILE); // load student
            for (Student s : list) {
                studentMap.put(s.getId(), s); // put student  HashMap
            }
        } catch (IOException e) {
            System.out.println("Error loading students from file: " + e.getMessage());
        }

        Scanner sc = new Scanner(System.in);
         
        

        loop:
        while (true) {
            System.out.println("--------------------Welcome to the Student Information Management System ------------------");
            System.out.println("press 1 to add a new student's information");
            System.out.println("press 2 to delete a new student's information");
            System.out.println("press 3 to edit a new student's information");
            System.out.println("press 4 to query a new student's information");
            System.out.println("press 5 to exit this system");
            System.out.println("please enter a number:");
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
                    try {
                        studentDataManager.writeToFile(STUDENT_FILE, new ArrayList<>(studentMap.values())); // put student into the file
                    } catch (IOException e) {
                        System.out.println("Error saving students to file: " + e.getMessage());
                    }
                    System.out.println("Exit this system");
                    break loop;
                default:
                    System.out.println("we dont have this choose");
            }
        }
    }

    //add new Student method
    public static void addStudent() {
        Student s = new Student();
        Scanner sc = new Scanner(System.in);
        String id = null;
        while (true) {
            System.out.println("please enter a student's ID");
            id = sc.next();
            if (studentMap.containsKey(id)) {
                System.out.println("This ID already existed, please enter another one");
            } else {
                s.setId(id);
                break;
            }
        }

        System.out.println("please enter a student's Name");
        String name = sc.next();
        s.setName(name);

        System.out.println("please enter a student's Age");
        int age = sc.nextInt();
        s.setAge(age);

        System.out.println("please enter a student's Address");
        String address = sc.next();
        s.setAddress(address);

        studentMap.put(id, s);

        //tell the consequence to the user
        System.out.println("the information has add successfully");

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

    //edit Student method
    public static void editStudent() {
        Scanner sc = new Scanner(System.in);
        System.out.println("please enter the Id of the Student who need edit information");
        String id =sc.next();



        if (studentMap.containsKey(id)) {
            Student student = studentMap.get(id);

            System.out.println("Please enter a new name:");
            String rename = sc.next();
            student.setName(rename);

            System.out.println("Please enter a new age:");
            int newAge = sc.nextInt();
            student.setAge(newAge);

            System.out.println("Please enter a new address:");
            String newAddress = sc.next();
            student.setAddress(newAddress);

            System.out.println("Student information has been edited");
        } else {
            System.out.println("This ID does not exist, please enter again");
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


 

