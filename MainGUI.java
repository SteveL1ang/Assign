
package student.information.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainGUI {

    // Main JFrame (the main window)
    private JFrame frame;

    public static void main(String[] args) {
        // Create the main GUI window
        MainGUI window = new MainGUI();
        window.frame.setVisible(true);
    }

    // Constructor to set up the GUI
    public MainGUI() {
        initialize();
    }

    // Initialize the contents of the frame
    private void initialize() {
        // Create the frame
        frame = new JFrame("Student Information Management System");
        frame.setBounds(100, 100, 400, 300);  // Set the window size
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Close when "X" is clicked
        frame.getContentPane().setLayout(null);  // Use absolute layout

        // Create a label for the title
        JLabel titleLabel = new JLabel("Welcome");
        titleLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
        titleLabel.setBounds(100, 20, 200, 25);  // Set position and size
        frame.getContentPane().add(titleLabel);

        // Create a button for "Login"
        JButton loginButton = new JButton("Login");
        loginButton.setBounds(100, 60, 200, 30);  // Set position and size
        frame.getContentPane().add(loginButton);

        // Create a button for "Register"
        JButton registerButton = new JButton("Register");
        registerButton.setBounds(100, 100, 200, 30);  // Set position and size
        frame.getContentPane().add(registerButton);

        // Create a button for "Forgot Password"
        JButton forgotPasswordButton = new JButton("Forgot Password");
        forgotPasswordButton.setBounds(100, 140, 200, 30);  // Set position and size
        frame.getContentPane().add(forgotPasswordButton);

        // Create a button for "Exit"
        JButton exitButton = new JButton("Exit");
        exitButton.setBounds(100, 180, 200, 30);  // Set position and size
        frame.getContentPane().add(exitButton);

        // Add action listeners for the buttons
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Open login panel (to be implemented)
                System.out.println("Login button clicked");
            }
        });

        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Open register panel (to be implemented)
                System.out.println("Register button clicked");
            }
        });

        forgotPasswordButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Open forgot password panel (to be implemented)
                System.out.println("Forgot Password button clicked");
            }
        });

        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Exit the application
                System.exit(0);
            }
        });
    }
}