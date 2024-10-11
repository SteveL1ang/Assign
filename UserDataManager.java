
package student.information.management.system;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UserDataManager implements DataManager<User> {

    @Override
    public List<User> readFromFile(String fileName) throws IOException {
        List<User> users = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {  // Assuming users have 3 fields: username, password, phoneNumber
                    users.add(new User(parts[0], parts[1], parts[2]));
                }
            }
        }
        return users;
    }

    @Override
    public void writeToFile(String fileName, List<User> users) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (User user : users) {
                writer.write(user.getUserName() + "," + user.getPassword() + "," + user.getPhoneNumber());
                writer.newLine();
            }
        }
    }
}


