package Model;

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

    // method to create a new bug report
    public Bug reportBug(String bugName, String type, String priority, String level,
                         String projectName, String date, String status, String screenshot) {
        Bug newBug = new Bug(bugName, type, priority, level, projectName,
                date, status, this.username, "", screenshot);
        System.out.println("Bug reported successfully by " + this.username);
        return newBug;
    }

    // method to assign bug to a developer
    public void assignBugToDeveloper(Bug bug, String developerUsername) {
        bug.setAssignedDeveloper(developerUsername);
        notifyDeveloper(developerUsername, bug);
    }

    // email notification to developer
    private void notifyDeveloper(String developerUsername, Bug bug) {
        System.out.println("=== NOTIFICATION ===");
        System.out.println("Email sent to: " + developerUsername);
        System.out.println("Subject: New Bug Assigned - " + bug.getBugName());
        System.out.println("Bug Details: " + bug.getType() + " | Priority: " + bug.getPriority());
        System.out.println("====================");
    }

    // attaching screenshot
    public void attachScreenshot(Bug bug, String screenshotPath) {
        bug.setScreenshot(screenshotPath);
        System.out.println("Screenshot attached: " + screenshotPath);
    }
}