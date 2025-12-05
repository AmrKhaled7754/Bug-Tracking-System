package Model;

public class Bug {
    private String bugName;
    private String type;
    private String priority;
    private String level;
    private String projectName;
    private String date;
    private String status;
    private String reportedBy;
    private String assignedDeveloper;
    private String screenshot;

    public Bug(String bugName, String type, String priority, String level,
               String projectName, String date, String status, String reportedBy,
               String assignedDeveloper, String screenshot) {
        this.bugName = bugName;
        this.type = type;
        this.priority = priority;
        this.level = level;
        this.projectName = projectName;
        this.date = date;
        this.status = status;
        this.reportedBy = reportedBy;
        this.assignedDeveloper = assignedDeveloper;
        this.screenshot = screenshot;
    }

    // Getters
    public String getBugName() {
        return bugName;
    }

    public String getType() {
        return type;
    }

    public String getPriority() {
        return priority;
    }

    public String getLevel() {
        return level;
    }

    public String getProjectName() {
        return projectName;
    }

    public String getDate() {
        return date;
    }

    public String getStatus() {
        return status;
    }

    public String getReportedBy() {
        return reportedBy;
    }

    public String getAssignedDeveloper() {
        return assignedDeveloper;
    }

    public String getScreenshot() {
        return screenshot;
    }

    // Setters
    public void setBugName(String bugName) {
        this.bugName = bugName;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setReportedBy(String reportedBy) {
        this.reportedBy = reportedBy;
    }

    public void setAssignedDeveloper(String assignedDeveloper) {
        this.assignedDeveloper = assignedDeveloper;
    }

    public void setScreenshot(String screenshot) {
        this.screenshot = screenshot;
    }

    @Override
    public String toString() {
        return "Bug: " + bugName + " | Type: " + type + " | Priority: " + priority +
                " | Status: " + status + " | Assigned to: " +
                (assignedDeveloper.isEmpty() ? "None" : assignedDeveloper);
    }

    // Convert bug to CSV format for file storage
    public String toCSV() {
        return bugName + "," + type + "," + priority + "," + level + "," +
                projectName + "," + date + "," + status + "," + reportedBy + "," +
                assignedDeveloper + "," + screenshot;
    }
}