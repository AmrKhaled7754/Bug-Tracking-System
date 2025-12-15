package View;

import Controller.PMController;
import Model.Bug;
import Model.PM;
import Provider.FileManager;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PMView extends JFrame {

    private PM pm;
    private PMController controller;
    private FileManager fileManager;

    public JTable bugsTable;

    public PMView(PM pm) {
        this.pm = pm;
        this.controller = new PMController(this, pm);
        this.fileManager = new FileManager();

        setTitle("Project Manager Dashboard - " + pm.getUsername());
        setSize(1100, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Title Panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(70, 130, 180));
        JLabel titleLabel = new JLabel("Project Manager Dashboard - " + pm.getUsername());
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);

        // Bugs Table
        String[] columns = {"Bug Name", "Type", "Priority", "Level", "Project", "Status", "Developer", "Tester"};
        bugsTable = new JTable(new DefaultTableModel(columns, 0));
        bugsTable.setRowHeight(25);
        JScrollPane scrollPane = new JScrollPane(bugsTable);
        add(scrollPane, BorderLayout.CENTER);

        // Control Panel
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(2, 3, 10, 10));
        controlPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JButton devPerformanceBtn = new JButton("Developer Performance");
        devPerformanceBtn.setBackground(new Color(40, 167, 69));
        devPerformanceBtn.setForeground(Color.WHITE);
        devPerformanceBtn.addActionListener(e -> controller.checkDeveloperPerformance());

        JButton testerPerformanceBtn = new JButton("Tester Performance");
        testerPerformanceBtn.setBackground(new Color(23, 162, 184));
        testerPerformanceBtn.setForeground(Color.WHITE);
        testerPerformanceBtn.addActionListener(e -> controller.checkTesterPerformance());

        JButton monitorBtn = new JButton("Monitor Bugs");
        monitorBtn.setBackground(new Color(255, 193, 7));
        monitorBtn.addActionListener(e -> controller.monitorBugs());

        JButton reportBtn = new JButton("Generate Report");
        reportBtn.setBackground(new Color(108, 117, 125));
        reportBtn.setForeground(Color.WHITE);
        reportBtn.addActionListener(e -> controller.generateReport());

        JButton refreshBtn = new JButton("Refresh");
        refreshBtn.addActionListener(e -> controller.loadBugs());

        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setBackground(new Color(220, 53, 69));
        logoutBtn.setForeground(Color.WHITE);
        logoutBtn.addActionListener(e -> controller.logout());

        controlPanel.add(devPerformanceBtn);
        controlPanel.add(testerPerformanceBtn);
        controlPanel.add(monitorBtn);
        controlPanel.add(reportBtn);
        controlPanel.add(refreshBtn);
        controlPanel.add(logoutBtn);

        add(controlPanel, BorderLayout.SOUTH);

        loadAllBugs();
    }

    public void loadAllBugs() {
        DefaultTableModel model = (DefaultTableModel) bugsTable.getModel();
        model.setRowCount(0);

        for (Bug bug : fileManager.loadBugs()) {
            model.addRow(new Object[]{
                bug.getBugName(),
                bug.getType(),
                bug.getPriority(),
                bug.getLevel(),
                bug.getProjectName(),
                bug.getStatus(),
                bug.getAssignedDeveloper().isEmpty() ? "None" : bug.getAssignedDeveloper(),
                bug.getReportedBy()
            });
        }
    }
}
