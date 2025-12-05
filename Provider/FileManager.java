package Provider;

import Model.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileManager {
    private static final String USERS_FILE = "src/Data/users.txt";
    private static final String BUGS_FILE = "src/Data/bugs.txt";

    // getting all users from file
    public List<User> loadUsers() {
        List<User> users = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(USERS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    String username = parts[0].trim();
                    String password = parts[1].trim();
                    String email = parts[2].trim();
                    String role = parts[3].trim();

                    User user = null;
                    switch (role.toLowerCase()) {
                        case "admin":
                            user = new Admin(username, password, email);
                            break;
                        case "developer":
                            user = new Developer(username, password, email);
                            break;
                        case "tester":
                            user = new Tester(username, password, email);
                            break;
                        case "pm":
                            user = new ProjectManager(username, password, email);
                            break;
                    }

                    if (user != null) {
                        users.add(user);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading users file: " + e.getMessage());
        }

        return users;
    }

    // save the users to the file
    public void saveUsers(List<User> users) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USERS_FILE))) {
            for (User user : users) {
                writer.write(user.getUsername() + "," + user.getPassword() + "," +
                        user.getEmail() + "," + user.getRole());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing to users file: " + e.getMessage());
        }
    }

    // getting bugs from its file
    public List<Bug> loadBugs() {
        List<Bug> bugs = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(BUGS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 10) {
                    Bug bug = new Bug(
                            parts[0].trim(), // bugName
                            parts[1].trim(), // type
                            parts[2].trim(), // priority
                            parts[3].trim(), // level
                            parts[4].trim(), // projectName
                            parts[5].trim(), // date
                            parts[6].trim(), // status
                            parts[7].trim(), // reportedBy
                            parts[8].trim(), // assignedDeveloper
                            parts[9].trim()  // screenshot
                    );
                    bugs.add(bug);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading bugs file: " + e.getMessage());
        }

        return bugs;
    }

    // save bugs to the file
    public void saveBugs(List<Bug> bugs) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(BUGS_FILE))) {
            for (Bug bug : bugs) {
                writer.write(bug.toCSV());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing to bugs file: " + e.getMessage());
        }
    }

    // add bug
    public void addBug(Bug bug) {
        List<Bug> bugs = loadBugs();
        bugs.add(bug);
        saveBugs(bugs);
    }

    // update an existing bug
    public boolean updateBug(String bugName, Bug updatedBug) {
        List<Bug> bugs = loadBugs();

        for (int i = 0; i < bugs.size(); i++) {
            if (bugs.get(i).getBugName().equals(bugName)) {
                bugs.set(i, updatedBug);
                saveBugs(bugs);
                return true;
            }
        }

        return false;
    }

    // delete a bug
    public boolean deleteBug(String bugName) {
        List<Bug> bugs = loadBugs();

        for (int i = 0; i < bugs.size(); i++) {
            if (bugs.get(i).getBugName().equals(bugName)) {
                bugs.remove(i);
                saveBugs(bugs);
                return true;
            }
        }

        return false;
    }

    // find user by username and password
    public User findUser(String username, String password) {
        List<User> users = loadUsers();

        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }

        return null;
    }

    // get bugs assigned to a developer
    public List<Bug> getBugsForDeveloper(String developerUsername) {
        List<Bug> allBugs = loadBugs();
        List<Bug> developerBugs = new ArrayList<>();

        for (Bug bug : allBugs) {
            if (bug.getAssignedDeveloper().equals(developerUsername)) {
                developerBugs.add(bug);
            }
        }

        return developerBugs;
    }

    // get bugs reported by a tester
    public List<Bug> getBugsForTester(String testerUsername) {
        List<Bug> allBugs = loadBugs();
        List<Bug> testerBugs = new ArrayList<>();

        for (Bug bug : allBugs) {
            if (bug.getReportedBy().equals(testerUsername)) {
                testerBugs.add(bug);
            }
        }

        return testerBugs;
    }
}
