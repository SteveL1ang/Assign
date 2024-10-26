/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package student_information_management_system;

/**
 *
 * @author 梁豪森
 */
public class Student extends Person {
    private int age;


    // Constructor
    public Student() {
    }

    // Parameterized constructor
    public Student(String id, String name, int age, String address) {
        super(id, name, address);
        this.age = age;

    }

    // Getters and setters
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
