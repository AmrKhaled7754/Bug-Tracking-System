
package Model;

import javax.swing.JOptionPane;

public class Tester extends User {

    public Tester(String username, String password, String email) {
        super(username, password, email, "tester");
    }

    @Override
    public void displayMenu() {
        System.out.println("=== Tester Menu ===");
        System.out.println("1. Report New Bug");
        System.out.println("2. Assign Bug to Developer");
        System.out.println("3. Attach Screenshot");
        System.out.println("4. Monitor Bugs");
        System.out.println("5. Logout");
    }

    // Method to create a new bug report - status will be open automatically
    public Bug reportBug(String bugName, String type, String priority, String level,
                         String projectName, String date) {
        Bug newBug = new Bug(bugName, type, priority, level, projectName, date, this.username);
        System.out.println("Bug reported successfully by " + this.username + " with status: open");
        return newBug;
    }

    // method to assign bug to a developer
    public void assignBugToDeveloper(Bug bug, String developerUsername) {
        bug.setAssignedDeveloper(developerUsername);
        notifyDeveloper(developerUsername, bug);
    }

    // email notification to developer
    private void notifyDeveloper(String developerUsername, Bug bug) {
        String message = "📧 Email Notification Sent!\n\n" +
                "Bug assigned to developer: " + developerUsername + "\n" +
                "Bug Name: " + bug.getBugName() + "\n" +
                "Priority: " + bug.getPriority() + "\n" +
                "Type: " + bug.getType() + "\n\n" +
                "Email sent to: " + developerUsername + "@company.com";

        JOptionPane.showMessageDialog(null,
                message,
                "Email Sent ✓",
                JOptionPane.INFORMATION_MESSAGE);

        // Console notification
        System.out.println("\n=== EMAIL NOTIFICATION ===");
        System.out.println("Bug assigned to developer: " + developerUsername);
        System.out.println("Email sent to: " + developerUsername + "@company.com");
        System.out.println("==========================\n");
    }

    // attaching screenshot
    public void attachScreenshot(Bug bug, String screenshotPath) {
        bug.setScreenshot(screenshotPath);
        System.out.println("Screenshot attached: " + screenshotPath);
    }
}