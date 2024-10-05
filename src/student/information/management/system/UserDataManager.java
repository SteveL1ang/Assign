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

public class UserDataManager implements DataManager<User> {

    @Override
    public ArrayList<User> readFromFile(String fileName) throws IOException {
        ArrayList<User> users = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    users.add(new User(parts[0], parts[1], parts[2]));
                }
            }
        }
        return users;
    }

    @Override
    public void writeToFile(String fileName, ArrayList<User> users) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (User user : users) {
                writer.write(user.getUserName() + "," + user.getPassword() + "," + user.getPhoneNumber());
                writer.newLine();
            }
        }
    }
}


