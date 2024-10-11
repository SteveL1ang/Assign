
package student.information.management.system;

public class Student extends Person {
    private int age;
    private String courseEnrollment;  // New field for course enrollment
    private String grades;            // New field for student grades
    private String honors;            // New field for student honors

    // Constructor
     public Student() {
    }

    // Parameterized constructor
    public Student(String id, String name, int age, String address, String courseEnrollment, String grades, String honors) {
        super(id, name, address);
        this.age = age;
        this.courseEnrollment = courseEnrollment;
        this.grades = grades;
        this.honors = honors;
    }

    // Getters and setters
    public String getCourseEnrollment() {
        return courseEnrollment;
    }

    public void setCourseEnrollment(String courseEnrollment) {
        this.courseEnrollment = courseEnrollment;
    }

    public String getGrades() {
        return grades;
    }

    public void setGrades(String grades) {
        this.grades = grades;
    }

    public String getHonors() {
        return honors;
    }

    public void setHonors(String honors) {
        this.honors = honors;
    }

    // Existing getters and setters for age, etc.
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
