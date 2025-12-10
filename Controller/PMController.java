package Controller;

import Model.Bug;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class PMController {
    private List<Bug> bugList;

    public PMController() {
        this.bugList = new ArrayList<>();
        loadBugsFromCSV();
    }

    private void loadBugsFromCSV() {
        String filePath = "bugs.txt";
        bugList.clear();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] bugData = line.split(",");
                if (bugData.length >= 8) {
                    String title = bugData[0];
                    String type = bugData[1];
                    String priority = bugData[2];
                    String level = bugData[3];
                    String projectName = bugData[4];
                    String date = bugData[5];
                    String status = bugData[6];
                    String reportedBy = bugData[7];
                    String assignedDeveloper = (bugData.length > 8) ? bugData[8] : "";
                    String screenshot = (bugData.length > 9) ? bugData[9] : "";

                    // استخدام الـ Constructor الكامل من الـ Bug class بتاعك
                    Bug bug = new Bug(title, type, priority, level, projectName, date,
                            status, reportedBy, assignedDeveloper, screenshot);
                    bugList.add(bug);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading bugs file: " + e.getMessage());
        }
    }

    public int countBugsByDeveloper(String developerName) {
        int countSolvedBugs = 0;
        for (Bug bug : bugList) {
            boolean isClosed = bug.getStatus().equalsIgnoreCase("closed");
            boolean isAssignedToDev = bug.getAssignedDeveloper().equalsIgnoreCase(developerName);
            if (isAssignedToDev && isClosed) {
                countSolvedBugs++;
            }
        }
        return countSolvedBugs;
    }

    public int countBugsByTester(String testerName) {
        int count = 0;
        for (Bug bug : bugList) {
            // استخدام getReportedBy() بدل getCreatedByTester()
            if (bug.getReportedBy().equalsIgnoreCase(testerName)) {
                count++;
            }
        }
        return count;
    }

    public List<Bug> showOpenBugs() {
        List<Bug> openBugsList = new ArrayList<>();
        for (Bug bug : bugList) {
            if (bug.getStatus().equalsIgnoreCase("open")) {
                openBugsList.add(bug);
            }
        }
        return openBugsList;
    }

    public List<Bug> showClosedBugs() {
        List<Bug> closedBugsList = new ArrayList<>();
        for (Bug bug : bugList) {
            if (bug.getStatus().equalsIgnoreCase("closed")) {
                closedBugsList.add(bug);
            }
        }
        return closedBugsList;
    }

    public int countBugs(String status) {
        int count = 0;
        for (Bug bug : bugList) {
            if (bug.getStatus().equalsIgnoreCase(status)) {
                count++;
            }
        }
        return count;
    }

    public List<Bug> loadAllBugs() {
        return bugList;
    }

    public String generateReport() {
        int totalBugs = bugList.size();
        int openBugs = showOpenBugs().size();
        int closedBugs = showClosedBugs().size();

        StringBuilder report = new StringBuilder();

        report.append("--- Project Report ---\n");
        report.append("Total Bugs: ").append(totalBugs).append("\n");
        report.append("Open Bugs: ").append(openBugs).append("\n");
        report.append("Closed Bugs: ").append(closedBugs).append("\n");

        if (totalBugs > 0) {
            double progress = ((double) closedBugs / totalBugs) * 100;
            report.append("Progress: ").append((int) progress).append("%\n");
        } else {
            report.append("Project Completion: 0%\n");
        }

        return report.toString();
    }
}
