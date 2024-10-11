package student.information.management.system;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDataManager implements DataManager<Student> {

    @Override
    public List<Student> readFromFile(String fileName) throws IOException {
        List<Student> students = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 7) {
                    students.add(new Student(parts[0], parts[1], Integer.parseInt(parts[2]), parts[3], parts[4], parts[5], parts[6]));
                }
            }
        }
        return students;
    }

    @Override
    public void writeToFile(String fileName, List<Student> students) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Student student : students) {
                writer.write(student.getId() + "," + student.getName() + "," + student.getAge() + "," + student.getAddress()
                        + "," + student.getCourseEnrollment() + "," + student.getGrades() + "," + student.getHonors());
                writer.newLine();
            }
        }
    }
}


