package Model;

import Provider.FileManager;
import java.util.List;

public class Admin extends User {

    public Admin(String username, String password, String email) {
        super(username, password, email, "admin");
    }

    @Override
    public void displayMenu() {
        System.out.println("=== Admin Menu ===");
        System.out.println("1. View All Bugs");
        System.out.println("2. Add User");
        System.out.println("3. Update User");
        System.out.println("4. Delete User");
        System.out.println("5. View All Users");
        System.out.println("6. Delete Bug");
        System.out.println("7. Logout");
    }

    // Method to add a new user
    public boolean addUser(String username, String password, String email, String role) {
        FileManager fileManager = new FileManager();
        List<User> users = fileManager.loadUsers();

        // Check if username already exists
        for (User user : users) {
            if (user.getUsername().equalsIgnoreCase(username)) {
                System.out.println("Error: Username already exists!");
                return false;
            }
        }

        User newUser = null;
        switch (role.toLowerCase()) {
            case "admin":
                newUser = new Admin(username, password, email);
                break;
            case "developer":
                newUser = new Developer(username, password, email);
                break;
            case "tester":
                newUser = new Tester(username, password, email);
                break;
            case "pm":
                newUser = new PM(username, password, email);
                break;
            default:
                System.out.println("Invalid role!");
                return false;
        }

        users.add(newUser);
        fileManager.saveUsers(users);
        System.out.println("User added successfully!");
        return true;
    }

    // Method to update user information
    public boolean updateUser(String username, String newPassword, String newEmail) {
        FileManager fileManager = new FileManager();
        List<User> users = fileManager.loadUsers();

        for (User user : users) {
            if (user.getUsername().equalsIgnoreCase(username)) {
                if (newPassword != null && !newPassword.isEmpty()) {
                    user.setPassword(newPassword);
                }
                if (newEmail != null && !newEmail.isEmpty()) {
                    user.setEmail(newEmail);
                }
                fileManager.saveUsers(users);
                System.out.println("User updated successfully!");
                return true;
            }
        }

        System.out.println("User not found!");
        return false;
    }

    // Method to delete a user
    public boolean deleteUser(String username) {
        FileManager fileManager = new FileManager();
        List<User> users = fileManager.loadUsers();

        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equalsIgnoreCase(username)) {
                users.remove(i);
                fileManager.saveUsers(users);
                System.out.println("User deleted successfully!");
                return true;
            }
        }

        System.out.println("User not found!");
        return false;
    }

    // View all bugs in the system
    public List<Bug> viewAllBugs() {
        FileManager fileManager = new FileManager();
        return fileManager.loadBugs();
    }

    // View all users
    public List<User> viewAllUsers() {
        FileManager fileManager = new FileManager();
        return fileManager.loadUsers();
    }

    // Delete a bug
//    public boolean deleteBug(String bugName) {
//        FileManager fileManager = new FileManager();
//        return fileManager.deleteBug(bugName);
//    }
}