package View;

import Controller.LoginController;
import javax.swing.*;
import java.awt.*;

public class LoginView extends JFrame {

    public JTextField usernameField;
    public JPasswordField passwordField;
    public JButton loginButton;

    private LoginController controller;

    public LoginView() {
        controller = new LoginController(this);

        setTitle("Bug Tracking System - Login");
        setSize(450, 350);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Title
        JLabel title = new JLabel("Bug Tracking System");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setBounds(90, 30, 300, 30);
        add(title);

        JLabel subtitle = new JLabel("Login");
        subtitle.setFont(new Font("Arial", Font.PLAIN, 18));
        subtitle.setBounds(190, 70, 100, 25);
        add(subtitle);

        // Username
        JLabel userLbl = new JLabel("Username:");
        userLbl.setFont(new Font("Arial", Font.PLAIN, 14));
        userLbl.setBounds(60, 130, 100, 25);
        add(userLbl);

        usernameField = new JTextField();
        usernameField.setBounds(160, 130, 220, 30);
        usernameField.setFont(new Font("Arial", Font.PLAIN, 14));
        add(usernameField);

        // Password
        JLabel passLbl = new JLabel("Password:");
        passLbl.setFont(new Font("Arial", Font.PLAIN, 14));
        passLbl.setBounds(60, 180, 100, 25);
        add(passLbl);

        passwordField = new JPasswordField();
        passwordField.setBounds(160, 180, 220, 30);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
        add(passwordField);

        // Login Button
        loginButton = new JButton("Login");
        loginButton.setBounds(170, 250, 100, 40);
        loginButton.setBackground(new Color(70, 130, 180));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("Arial", Font.BOLD, 16));
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(loginButton);

        loginButton.addActionListener(e -> controller.handleLogin());

        setVisible(true);
    }
}