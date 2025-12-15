package Model;

import Provider.FileManager;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class PM extends User {

    public PM(String username, String password, String email) {
        super(username, password, email, "pm");
    }

    @Override
    public void displayMenu() {
        System.out.println("=== Project Manager Menu ===");
        System.out.println("1. Check Performance");
        System.out.println("2. Monitor Bugs");
        System.out.println("3. Generate Reports");
        System.out.println("4. Logout");
    }

    // Check performance of developers
    public Map<String, Integer> checkDeveloperPerformance() {
        FileManager fileManager = new FileManager();
        List<Bug> bugs = fileManager.loadBugs();
        Map<String, Integer> performance = new HashMap<>();

        for (Bug bug : bugs) {
            String developer = bug.getAssignedDeveloper();
            if (!developer.isEmpty() && (bug.getStatus().equalsIgnoreCase("closed") || 
                                         bug.getStatus().equalsIgnoreCase("completed"))) {
                performance.put(developer, performance.getOrDefault(developer, 0) + 1);
            }
        }

        System.out.println("=== Developer Performance ===");
        for (Map.Entry<String, Integer> entry : performance.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue() + " bugs resolved");
        }

        return performance;
    }

    // Check performance of testers
    public Map<String, Integer> checkTesterPerformance() {
        FileManager fileManager = new FileManager();
        List<Bug> bugs = fileManager.loadBugs();
        Map<String, Integer> performance = new HashMap<>();

        for (Bug bug : bugs) {
            String tester = bug.getReportedBy();
            performance.put(tester, performance.getOrDefault(tester, 0) + 1);
        }

        System.out.println("=== Tester Performance ===");
        for (Map.Entry<String, Integer> entry : performance.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue() + " bugs reported");
        }

        return performance;
    }

    // Monitor open and closed bugs
    public void monitorBugs() {
        FileManager fileManager = new FileManager();
        List<Bug> bugs = fileManager.loadBugs();

        int openBugs = 0;
        int closedBugs = 0;

        for (Bug bug : bugs) {
            if (bug.getStatus().equalsIgnoreCase("open") ||
                    bug.getStatus().equalsIgnoreCase("in progress")) {
                openBugs++;
            } else if (bug.getStatus().equalsIgnoreCase("closed") ||
                    bug.getStatus().equalsIgnoreCase("completed")) {
                closedBugs++;
            }
        }

        System.out.println("=== Bug Status Report ===");
        System.out.println("Open Bugs: " + openBugs);
        System.out.println("Closed Bugs: " + closedBugs);
        System.out.println("Total Bugs: " + bugs.size());
        System.out.println("Completion Rate: " +
                (bugs.size() > 0 ? (closedBugs * 100 / bugs.size()) : 0) + "%");
    }

    // Get bugs by priority
    public Map<String, Integer> getBugsByPriority() {
        FileManager fileManager = new FileManager();
        List<Bug> bugs = fileManager.loadBugs();
        Map<String, Integer> priorityCount = new HashMap<>();

        for (Bug bug : bugs) {
            String priority = bug.getPriority();
            priorityCount.put(priority, priorityCount.getOrDefault(priority, 0) + 1);
        }

        return priorityCount;
    }

    // Generate report
    public String generateReport() {
        FileManager fileManager = new FileManager();
        List<Bug> bugs = fileManager.loadBugs();
        
        int totalBugs = bugs.size();
        int openBugs = 0;
        int closedBugs = 0;

        for (Bug bug : bugs) {
            if (bug.getStatus().equalsIgnoreCase("open") || 
                bug.getStatus().equalsIgnoreCase("in progress")) {
                openBugs++;
            } else if (bug.getStatus().equalsIgnoreCase("closed") || 
                       bug.getStatus().equalsIgnoreCase("completed")) {
                closedBugs++;
            }
        }

        StringBuilder report = new StringBuilder();
        report.append("=== Project Report ===\n");
        report.append("Total Bugs: ").append(totalBugs).append("\n");
        report.append("Open Bugs: ").append(openBugs).append("\n");
        report.append("Closed Bugs: ").append(closedBugs).append("\n");

        if (totalBugs > 0) {
            double progress = ((double) closedBugs / totalBugs) * 100;
            report.append("Progress: ").append(String.format("%.2f", progress)).append("%\n");
        } else {
            report.append("Progress: 0%\n");
        }

        return report.toString();
    }
}