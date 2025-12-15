package View;

import Controller.LoginController;
import javax.swing.*;
import java.awt.*;

public class LoginView extends JFrame {

    public JTextField usernameField;
    public JTextField emailField;
    public JPasswordField passwordField;
    public JComboBox<String> roleBox;
    public JButton loginButton;

    private LoginController controller;

    public LoginView() {
        controller = new LoginController(this);

        setTitle("Bug Tracking System - Login");
        setSize(500, 450);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Title
        JLabel title = new JLabel("Bug Tracking System");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setBounds(120, 20, 300, 30);
        add(title);

        JLabel subtitle = new JLabel("Login");
        subtitle.setFont(new Font("Arial", Font.PLAIN, 18));
        subtitle.setBounds(220, 55, 100, 25);
        add(subtitle);

        // Username
        JLabel userLbl = new JLabel("Username:");
        userLbl.setBounds(50, 100, 100, 25);
        add(userLbl);

        usernameField = new JTextField();
        usernameField.setBounds(150, 100, 250, 30);
        add(usernameField);

        // Email
        JLabel emailLbl = new JLabel("Email:");
        emailLbl.setBounds(50, 150, 100, 25);
        add(emailLbl);

        emailField = new JTextField();
        emailField.setBounds(150, 150, 250, 30);
        add(emailField);

        // Password
        JLabel passLbl = new JLabel("Password:");
        passLbl.setBounds(50, 200, 100, 25);
        add(passLbl);

        passwordField = new JPasswordField();
        passwordField.setBounds(150, 200, 250, 30);
        add(passwordField);

        // Role
        JLabel roleLbl = new JLabel("Role:");
        roleLbl.setBounds(50, 250, 100, 25);
        add(roleLbl);

        roleBox = new JComboBox<>();
        roleBox.setBounds(150, 250, 250, 30);
        add(roleBox);

        // Login Button
        loginButton = new JButton("Login");
        loginButton.setBounds(200, 320, 100, 40);
        loginButton.setBackground(new Color(70, 130, 180));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        add(loginButton);

        loginButton.addActionListener(e -> controller.handleLogin());

        controller.initialize();

        setVisible(true);
    }
}