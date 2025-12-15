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

    public void initialize() {
        view.roleBox.addItem("admin");
        view.roleBox.addItem("developer");
        view.roleBox.addItem("tester");
        view.roleBox.addItem("pm");
    }

    public void handleLogin() {
        String username = view.usernameField.getText().trim();
        String email = view.emailField.getText().trim();
        String password = new String(view.passwordField.getPassword()).trim();
        String role = (String) view.roleBox.getSelectedItem();

        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            showAlert("Error", "Please fill all fields!");
            return;
        }

        user = fileManager.findUser(username, password);

        if (user != null && user.getEmail().equals(email)) {

            if (role != null && !role.equalsIgnoreCase(user.getRole())) {
                showAlert("Role Mismatch", "Selected role does not match user role.");
                return;
            }

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
            showAlert("Login Failed", "Invalid username, password or email.");
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