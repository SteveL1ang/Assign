package student.information.management.system;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.io.Console;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainInterFace {
    private static final Logger logger = Logger.getLogger(MainInterFace.class.getName());
    private static UserDataManager userDataManager = new UserDataManager();

    public static void main(String[] args) {
        try {
            DBManager.getConnection(); // Initialize database connection
            logger.info("Database connection established.");

            Scanner sc = new Scanner(System.in);

            while (true) {
                System.out.println("\nWelcome to Student Information Management System");
                System.out.println("Press 1: Login");
                System.out.println("Press 2: Register");
                System.out.println("Press 3: Forgot Password?");
                System.out.println("Press 4: Exit the Program");
                String choice = sc.next();

                switch (choice) {
                    case "1":
                        login();
                        break;
                    case "2":
                        register();
                        break;
                    case "3":
                        forgetPassword();
                        break;
                    case "4":
                        System.out.println("Thanks and bye!");
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Database error", e);
            System.out.println("A database error occurred. Please try again later.");
        } finally {
            DBManager.closeConnection();
            logger.info("Database connection closed.");
        }
    }

    private static void login() {
        Scanner sc = new Scanner(System.in);
        for (int i = 0; i < 3; i++) {
            System.out.println("Please enter your username:");
            String username = sc.next();
            System.out.println("Please enter your password:");
            String password = sc.next();

            try {
                if (userDataManager.authenticateUser(username, password)) {
                    System.out.println("Login successful.");
                    logger.info("User logged in: " + username);
                    
                    while (true) {
                        String captcha = getCaptcha();
                        System.out.println("Captcha: " + captcha);
                        System.out.println("Please enter the captcha:");
                        String userCaptcha = sc.next();

                        if (userCaptcha.equalsIgnoreCase(captcha)) {
                            System.out.println("Captcha correct.");
                            StudentSystem ss = new StudentSystem();
                            ss.StartSystem();
                            return;
                        } else {
                            System.out.println("Captcha incorrect. Please try again.");
                        }
                    }
                } else {
                    System.out.println("Invalid username or password. Attempts remaining: " + (2 - i));
                }
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "Error during login", e);
                System.out.println("Error during login. Please try again.");
                return;
            }
        }
        System.out.println("Account locked after 3 unsuccessful attempts.");
        logger.warning("Account locked due to multiple failed login attempts");
    }

    private static void register() {
        Scanner scanner = new Scanner(System.in);

        String userName;
        while (true) {
            System.out.println("Please enter a username (3-15 characters):");
            userName = scanner.next();

            if (!checkUserName(userName)) {
                System.out.println("Invalid username. Please try again.");
                continue;
            }

            try {
                if (userDataManager.userExists(userName)) {
                    System.out.println("Username already exists. Please enter a different one.");
                } else {
                    System.out.println("Username set successfully.");
                    break;
                }
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "Error checking user existence", e);
                System.out.println("Error during registration. Please try again.");
                return;
            }
        }

        String password = getPasswordInput(scanner);
        if (password == null) return;

        String phoneNumber;
        while (true) {
            System.out.println("Please enter a phone number (at least 9 digits):");
            phoneNumber = scanner.next();

            if (phoneNumber.length() >= 9 && phoneNumber.matches("\\d+")) {
                System.out.println("Phone number set successfully.");
                break;
            } else {
                System.out.println("Invalid phone number. Please try again.");
            }
        }

        User user = new User(userName, password, phoneNumber);
        try {
            userDataManager.addUser(user);
            System.out.println("Registration successful!");
            logger.info("New user registered: " + userName);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error adding user to database", e);
            System.out.println("Error during registration. Please try again.");
        }
    }

    private static void forgetPassword() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter your username: ");
        String username = scanner.next();

        try {
            User user = userDataManager.getUserByUsername(username);
            if (user == null) {
                System.out.println("Username not found. Please register first.");
                return;
            }

            System.out.println("Please enter your phone number: ");
            String phoneNumber = scanner.next();

            if (!user.getPhoneNumber().equals(phoneNumber)) {
                System.out.println("Phone number does not match. Please try again.");
                return;
            }

            String newPassword = getPasswordInput(scanner);
            if (newPassword == null) return;

            user.setPassword(newPassword);
            userDataManager.updateUser(user);
            System.out.println("Password updated successfully.");
            logger.info("Password reset for user: " + username);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error during password reset", e);
            System.out.println("Error resetting password. Please try again.");
        }
    }

    private static String getPasswordInput(Scanner scanner) {
        Console console = System.console();
        String password;

        while (true) {
            if (console != null) {
                char[] passwordArray = console.readPassword("Please enter a password: ");
                password = new String(passwordArray);

                char[] confirmPasswordArray = console.readPassword("Re-enter the password: ");
                String confirmPassword = new String(confirmPasswordArray);

                if (password.equals(confirmPassword)) {
                    System.out.println("Password set successfully.");
                    return password;
                } else {
                    System.out.println("Passwords do not match. Please try again.");
                }
            } else {
                System.out.println("Please enter a password:");
                password = scanner.next();

                System.out.println("Re-enter the password:");
                String confirmPassword = scanner.next();

                if (password.equals(confirmPassword)) {
                    System.out.println("Password set successfully.");
                    return password;
                } else {
                    System.out.println("Passwords do not match. Please try again.");
                }
            }
        }
    }

    private static boolean checkUserName(String userName) {
        if (userName.length() < 3 || userName.length() > 15) {
            return false;
        }

        for (char c : userName.toCharArray()) {
            if (!Character.isLetterOrDigit(c)) {
                return false;
            }
        }

        return userName.matches(".*[a-zA-Z]+.*");
    }

    public static String getCaptcha() {
        List<Character> list = new ArrayList<>();

        for (int i = 0; i < 26; i++) {
            list.add((char) ('a' + i));
            list.add((char) ('A' + i));
        }

        StringBuilder captcha = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 4; i++) {
            int index = random.nextInt(list.size());
            captcha.append(list.get(index));
        }

        captcha.append(random.nextInt(10));

        char[] captchaArray = captcha.toString().toCharArray();
        int index = random.nextInt(captchaArray.length);
        char temp = captchaArray[index];
        captchaArray[index] = captchaArray[captchaArray.length - 1];
        captchaArray[captchaArray.length - 1] = temp;

        return new String(captchaArray);
    }
}