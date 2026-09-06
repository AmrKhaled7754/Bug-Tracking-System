package Controller;

import Model.*;
import Provider.FileManager;
import View.*;
import javax.swing.*;

public class LoginController {

    private LoginView view;
    private FileManager fileManager = new FileManager();
    private User user;

    public LoginController(LoginView view) {
        this.view = view;
    }

    public void handleLogin() {
        String username = view.usernameField.getText().trim();
        String password = new String(view.passwordField.getPassword()).trim();

        if (username.isEmpty() || password.isEmpty()) {
            showAlert("Error", "Please fill username and password!");
            return;
        }

        user = fileManager.findUser(username, password);

        if (user != null) {

            // Redirect based on user role automatically
            switch (user.getRole().toLowerCase()) {
                case "admin":
                    openWindow(new AdminView((Admin) user));
                    break;
                case "developer":
                    openWindow(new DeveloperView((Developer) user));
                    break;
                case "pm":
                    openWindow(new PMView((PM) user));
                    break;
                case "tester":
                    openWindow(new TesterView((Tester) user));
                    break;
                default:
                    showAlert("Unknown role", "User role not recognized.");
                    break;
            }

        } else {
            showAlert("Login Failed", "Invalid username or password.");
        }
    }

    private void openWindow(JFrame frame) {
        frame.setVisible(true);
        view.dispose();
    }

    private void showAlert(String title, String message) {
        JOptionPane.showMessageDialog(view, message, title, JOptionPane.ERROR_MESSAGE);
    }
}