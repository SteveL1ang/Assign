package student.information.management.system;

import java.util.List;  // Import List instead of ArrayList
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.io.Console;
import java.io.IOException;

public class MainInterFace {
    private static final String USER_FILE = "users.txt";
    private static UserDataManager userDataManager = new UserDataManager();

    public static void main(String[] args) {
        List<User> list = null;  // Use List<User> here
        try {
            list = userDataManager.readFromFile(USER_FILE);
        } catch (IOException e) {
            System.out.println("Error loading users from file: " + e.getMessage());
        }
        Scanner sc = new Scanner(System.in);

        loop:
        while (true) {
            System.out.println();
            System.out.println("Welcome to Student Information Management System");
            System.out.println("Press 1: Login");
            System.out.println("Press 2: Register");
            System.out.println("Press 3: Forgot Password?");
            System.out.println("Press 4: Exit the Program");
            String choose = sc.next();

            switch (choose) {
                case "1":
                    login(list);
                    break;

                case "2":
                    register(list);
                    break;

                case "3":
                    forgetPassword(list);
                    break;

                case "4":
                    try {
                        userDataManager.writeToFile(USER_FILE, list);  // Save using List<User>
                    } catch (IOException e) {
                        System.out.println("Error saving users to file: " + e.getMessage());
                    }
                    System.out.println("Thanks and bye!");
                    break loop;

                default:
                    System.out.println("We don't have this choice");
            }
        }
    }

    private static void forgetPassword(List<User> list) {  // Change to List<User>
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter a username: ");
        String username = scanner.next();
        boolean flag = contains(list, username);

        if (!flag) {
            System.out.println("Not a registered username, please register first");
            return;
        }

        // find user information by index
        int index = findIndex(list, username);
        User user = list.get(index);
       
        System.out.println("Please enter your phone number: ");
        String phoneNumber = scanner.next();

        if (!user.getPhoneNumber().equals(phoneNumber)) {
            System.out.println("Phone number not found, please register first");
            return;
        }

        // all information correct
        String password;
        Console console = System.console();

        while (true) {
            if (console != null) {
                char[] passwordArray = console.readPassword("Please enter a new password: ");
                password = new String(passwordArray);

                char[] againPasswordArray = console.readPassword("Please enter the password again: ");
                String againPassword = new String(againPasswordArray);

                if (password.equals(againPassword)) {
                    System.out.println("Edit successful");
                    break;
                } else {
                    System.out.println("The password you entered is not the same, please enter again:");
                }
            } else {
                // Fallback to use Scanner if System.console() is not available (like in NetBeans)
                System.out.println("Please enter a new password:");
                password = scanner.next();

                System.out.println("Please enter the password again:");
                String againPassword = scanner.next();

                if (password.equals(againPassword)) {
                    System.out.println("Edit successfully");
                    break;
                } else {
                    System.out.println("The password you entered is not the same, please enter again:");
                }
            }
        }

        user.setPassword(password);
    }

    private static int findIndex(List<User> list, String username) {  // Change to List<User>
        for (int i = 0; i < list.size(); i++) {
            User user = list.get(i);
            if (user.getUserName().equals(username)) {
                return i;
            }
        }
        return -1;
    }

    private static void register(List<User> list) {  // Change to List<User>
        // put user information into list
        Scanner scanner = new Scanner(System.in);

        // username set
        String userName;
        while (true) {
            System.out.println("Please enter a username (between 3-15 characters):");
            userName = scanner.next();

            boolean flag = CheckUserName(userName);
            if (!flag) {
                System.out.println("Not a valid username, please enter again");
                continue;
            }

            // check if the username is already used
            boolean flag2 = contains(list, userName);
            if (flag2) {
                System.out.println("This username exists, please enter another one");
            } else {
                System.out.println("Username " + userName + " set successfully");
                break;
            }
        }

        // password set
        String password;
        Console console = System.console();

        while (true) {
            if (console != null) {
                char[] passwordArray = console.readPassword("Please enter a password: ");
                password = new String(passwordArray);

                char[] againPasswordArray = console.readPassword("Recheck the password: ");
                String checkPassword = new String(againPasswordArray);

                if (!password.equals(checkPassword)) {
                    System.out.println("Passwords do not match, please enter again");
                } else {
                    System.out.println("Password set successfully");
                    break;
                }
            } else {
                System.out.println("Please enter a password:");
                password = scanner.next();

                System.out.println("Recheck the password:");
                String checkPassword = scanner.next();

                if (!password.equals(checkPassword)) {
                    System.out.println("Passwords do not match, please enter again");
                } else {
                    System.out.println("Password set successfully");
                    break;
                }
            }
        }

        // set phone number
    // set phone number
    String phoneNumber;
    while (true) {
        System.out.println();
        System.out.println("Please enter a phone number (at least 9 digits):");
        phoneNumber = scanner.next();

        // Check if the phone number is at least 9 digits long and contains only digits
        if (phoneNumber.length() >= 9 && phoneNumber.matches("\\d+")) {  
            System.out.println("Phone number set successfully");
            break;
        } else {
            System.out.println("Invalid phone number, please enter again");
        }
    }

    // add user to list
    User user = new User(userName, password, phoneNumber);
    list.add(user);
    System.out.println("Registration successful!");
    }


    private static boolean CheckUserName(String userName) {
        int len = userName.length();
        if (len < 3 || len > 15) {
            return false;
        }

        // check if username contains valid characters
        for (int i = 0; i < userName.length(); i++) {
            char s = userName.charAt(i);
            if (!((s <= 'z' && s >= 'a') || (s <= 'Z' && s >= 'A') || (s <= '9' && s >= '0'))) {
                return false;
            }
        }

        // check if username contains at least one letter
        int count = 0;
        for (int i = 0; i < userName.length(); i++) {
            char s = userName.charAt(i);
            if ((s <= 'z' && s >= 'a') || (s <= 'Z' && s >= 'A')) {
                count++;
                break;  // improve efficiency
            }
        }
        return count > 0;
    }

    private static void login(List<User> list) {  // Change to List<User>
        Scanner sc = new Scanner(System.in);

        for (int i = 0; i < 3; i++) {
            System.out.println("Please enter a username:");
            String username = sc.next();
            // check if username exists
            boolean flag = contains(list, username);
            if (!flag) {
                System.out.println("Username " + username + " does not exist, please register first.");
                return;
            }

            System.out.println("Please enter the password:");
            String password = sc.next();

            while (true) {
                String rightCaptcha = getCaptcha();
                System.out.println();
                System.out.println("The captcha is " + rightCaptcha);
                System.out.println("Please enter the captcha:");
                String captcha = sc.next();
                if (captcha.equalsIgnoreCase(rightCaptcha)) {
                    System.out.println("Correct captcha");
                    break;
                } else {
                    System.out.println("Wrong captcha, please enter the new captcha:");
                }
            }

            User userInfo = new User(username, password, null);
            boolean result = checkUserInfo(list, userInfo);
            if (result) {
                System.out.println("Successful login");
                StudentSystem ss = new StudentSystem();
                ss.StartSystem();
                break;
            } else {
                if (i == 2) {
                    System.out.println("The account " + username + " is locked");
                    return;
                } else {
                    System.out.println("Wrong username or password, you have " + (2 - i) + " attempts left");
                }
            }
        }
    }

    private static boolean checkUserInfo(List<User> list, User userInfo) {
        for (User user : list) {
            if (user.getPassword().equals(userInfo.getPassword()) && user.getUserName().equals(userInfo.getUserName())) {
                return true;
            }
        }
        return false;
    }

    private static boolean contains(List<User> list, String userName) {
        for (User user : list) {
            if (user.getUserName().equals(userName)) {
                return true;
            }
        }
        return false;
    }

    public static String getCaptcha() {
        List<Character> list = new ArrayList<>();  // You can still use ArrayList here, no problem

        for (int i = 0; i < 26; i++) {
            list.add((char) ('a' + i));
            list.add((char) ('A' + i));
        }

        StringBuilder stringBuilder = new StringBuilder();
        Random r = new Random();
        for (int i = 0; i < 4; i++) {
            int index = r.nextInt(list.size());
            char c = list.get(index);
            stringBuilder.append(c);
        }

        int a = r.nextInt(10);
        stringBuilder.append(a);

        // Shuffle the string content
        char[] arr = stringBuilder.toString().toCharArray();
        int index = r.nextInt(arr.length);
        char temp = arr[index];
        arr[index] = arr[arr.length - 1];
        arr[arr.length - 1] = temp;

        return new String(arr);
    }
}