package Controller;

import Model.Admin;
import Model.Bug;
import Model.User;
import View.AdminView;
import javax.swing.*;

public class AdminController {

    private AdminView view;
    private Admin admin;

    public AdminController(AdminView view, Admin admin) {
        this.view = view;
        this.admin = admin;
    }

    public void loadAllUsers() {
        view.loadAllUsers();
    }

    public void loadAllBugs() {
        view.loadAllBugs();
    }

    public void addUser() {
        String username = view.userUsernameField.getText().trim();
        String password = view.userPasswordField.getText().trim();
        String email = view.userEmailField.getText().trim();
        String role = (String) view.userRoleBox.getSelectedItem();

        if (username.isEmpty() || password.isEmpty() || email.isEmpty()) {
            showAlert("Error", "Please fill all fields!");
            return;
        }

        boolean success = admin.addUser(username, password, email, role.toLowerCase());
        if (success) {
            showAlert("Success", "User added successfully!");
            view.clearUserFields();
            loadAllUsers();
        } else {
            showAlert("Error", "Username already exists!");
        }
    }

    public void updateUser() {
        String username = view.userUsernameField.getText().trim();
        String password = view.userPasswordField.getText().trim();
        String email = view.userEmailField.getText().trim();

        if (username.isEmpty()) {
            showAlert("Error", "Please enter username!");
            return;
        }

        boolean success = admin.updateUser(username, password, email);
        if (success) {
            showAlert("Success", "User updated successfully!");
            view.clearUserFields();
            loadAllUsers();
        } else {
            showAlert("Error", "User not found!");
        }
    }

    public void deleteUser() {
        String username = view.userUsernameField.getText().trim();

        if (username.isEmpty()) {
            showAlert("Error", "Please enter username to delete!");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(view,
                "Are you sure you want to delete user: " + username + "?",
                "Confirm Delete", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            boolean success = admin.deleteUser(username);
            if (success) {
                showAlert("Success", "User deleted successfully!");
                view.clearUserFields();
                loadAllUsers();
            } else {
                showAlert("Error", "User not found!");
            }
        }
    }

    public void deleteBug() {
        int row = view.bugsTable.getSelectedRow();
        if (row == -1) {
            showAlert("Error", "Please select a bug to delete!");
            return;
        }

        String bugName = view.bugsTable.getValueAt(row, 0).toString();

        int confirm = JOptionPane.showConfirmDialog(view,
                "Are you sure you want to delete bug: " + bugName + "?",
                "Confirm Delete", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            boolean success = admin.deleteBug(bugName);
            if (success) {
                showAlert("Success", "Bug deleted successfully!");
                loadAllBugs();
            } else {
                showAlert("Error", "Bug not found!");
            }
        }
    }

    public void logout() {
        view.dispose();
        new View.LoginView();
    }

    private void showAlert(String title, String message) {
        JOptionPane.showMessageDialog(view, message, title, 
            title.equals("Success") ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE);
    }
}