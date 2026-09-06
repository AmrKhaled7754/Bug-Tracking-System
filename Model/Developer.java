
package Model;

import javax.swing.JOptionPane;

public class Developer extends User {

    public Developer(String username, String password, String email) {
        super(username, password, email, "developer");
    }

    @Override
    public void displayMenu() {
        System.out.println("=== Developer Menu ===");
        System.out.println("1. View Assigned Bugs");
        System.out.println("2. Update Bug Status");
        System.out.println("3. Logout");
    }

    // method to update bug status
    public void updateBugStatus(Bug bug, String newStatus) {
        String oldStatus = bug.getStatus();
        bug.setStatus(newStatus);
        System.out.println("Bug status updated from '" + oldStatus + "' to '" + newStatus + "'");

        if (newStatus.equalsIgnoreCase("closed") || newStatus.equalsIgnoreCase("completed")) {
            notifyTester(bug);
        }
    }

    private void notifyTester(Bug bug) {
        String message = "📧 Email Notification Sent!\n\n" +
                "Bug completed by: " + this.username + "\n" +
                "Bug Name: " + bug.getBugName() + "\n" +
                "New Status: " + bug.getStatus() + "\n\n" +
                "Email sent to tester: " + bug.getReportedBy() + "@company.com";

        JOptionPane.showMessageDialog(null,
                message,
                "Email Sent ✓",
                JOptionPane.INFORMATION_MESSAGE);

        // Console notification كمان
        System.out.println("\n=== EMAIL NOTIFICATION ===");
        System.out.println("Bug completed by: " + this.username);
        System.out.println("Email sent to tester: " + bug.getReportedBy() + "@company.com");
        System.out.println("==========================\n");
    }

    // check if bug is assigned to this developer
    public boolean isAssignedToMe(Bug bug) {
        return bug.getAssignedDeveloper().equals(this.username);
    }
}