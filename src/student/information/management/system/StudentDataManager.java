/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package student.information.management.system;

/**
 *
 * @author 梁豪森
 */
import java.io.*;
import java.util.ArrayList;

public class StudentDataManager implements DataManager<Student> {

    @Override
    public ArrayList<Student> readFromFile(String fileName) throws IOException {
        ArrayList<Student> students = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    students.add(new Student(parts[0], parts[1], Integer.parseInt(parts[2]), parts[3]));
                }
            }
        }
        return students;
    }

    @Override
    public void writeToFile(String fileName, ArrayList<Student> students) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Student student : students) {
                writer.write(student.getId() + "," + student.getName() + "," + student.getAge() + "," + student.getAddress());
                writer.newLine();
            }
        }
    }
}


