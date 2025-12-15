package Model;

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

    // email notification to tester
    private void notifyTester(Bug bug) {
        System.out.println("=== NOTIFICATION ===");
        System.out.println("Email sent to: " + bug.getReportedBy());
        System.out.println("Subject: Bug Completed - " + bug.getBugName());
        System.out.println("The bug has been fixed by " + this.username);
        System.out.println("====================");
    }

    // check if bug is assigned to this developer
    public boolean isAssignedToMe(Bug bug) {
        return bug.getAssignedDeveloper().equals(this.username);
    }
}