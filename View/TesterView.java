package View;

import Controller.TesterController;
import Model.Bug;
import Model.Tester;
import Provider.FileManager;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class TesterView extends JFrame {

    private Tester tester;
    private TesterController controller;
    private FileManager fileManager;

    public JTable bugsTable;
    public JTextField bugNameField;
    public JComboBox<String> bugTypeBox;
    public JComboBox<String> bugPriorityBox;
    public JComboBox<String> bugLevelBox;
    public JTextField projectNameField;
    public JTextField screenshotField;
    public JTextField developerField;

    public TesterView() {
        this(new Tester("tester", "test123", "tester@mail.com"));
    }

    public TesterView(Tester tester) {
        this.tester = tester;
        this.controller = new TesterController(this, tester);
        this.fileManager = new FileManager();

        setTitle("Tester Dashboard - " + tester.getUsername());
        setSize(1400, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Title Panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(70, 130, 180));
        JLabel titleLabel = new JLabel("Tester Dashboard - " + tester.getUsername());
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);


        JTabbedPane tabbedPane = new JTabbedPane();
//        tabbedPane.setFont(new Font("Arial", Font.BOLD, 14));
        JPanel reportAssignPanel = createReportAndAssignPanel();
        tabbedPane.addTab(" Report & Assign Bug", reportAssignPanel);

        // Tab 2: Monitor Bugs
        JPanel monitorPanel = createMonitorPanel();
        tabbedPane.addTab("Monitor Bugs", monitorPanel);

        add(tabbedPane, BorderLayout.CENTER);

        // Bottom Panel
        JPanel bottomPanel = new JPanel();
        JButton refreshBtn = new JButton(" Refresh");
        refreshBtn.setFont(new Font("Arial", Font.BOLD, 14));
        refreshBtn.addActionListener(e -> controller.loadBugs());

        JButton logoutBtn = new JButton(" Logout");
        logoutBtn.setBackground(new Color(220, 53, 69));
        logoutBtn.setForeground(Color.WHITE);
        logoutBtn.setFont(new Font("Arial", Font.BOLD, 14));
        logoutBtn.addActionListener(e -> controller.logout());

        bottomPanel.add(refreshBtn);
        bottomPanel.add(logoutBtn);
        add(bottomPanel, BorderLayout.SOUTH);

        controller.loadBugs();
    }


    private JPanel createReportAndAssignPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Top: Report Bug Form
        JPanel reportPanel = new JPanel(new BorderLayout());
        reportPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 10, 15));

        JLabel reportTitle = new JLabel(" Report New Bug");
        reportTitle.setFont(new Font("Arial", Font.BOLD, 20));
        reportTitle.setHorizontalAlignment(SwingConstants.CENTER);
        reportPanel.add(reportTitle, BorderLayout.NORTH);

        // Report Form
        JPanel reportFormPanel = new JPanel();
        reportFormPanel.setLayout(new GridLayout(8, 2, 10, 10));
        reportFormPanel.setBorder(BorderFactory.createEmptyBorder(15, 50, 15, 50));

        // Bug Name
        reportFormPanel.add(new JLabel("Bug Name:"));
        bugNameField = new JTextField();
        bugNameField.setFont(new Font("Arial", Font.PLAIN, 14));
        reportFormPanel.add(bugNameField);

        // Type
        reportFormPanel.add(new JLabel("Type:"));
        bugTypeBox = new JComboBox<>(new String[]{"Functional", "Performance", "UI", "Security", "Technical"});
        bugTypeBox.setFont(new Font("Arial", Font.PLAIN, 14));
        reportFormPanel.add(bugTypeBox);

        // Priority
        reportFormPanel.add(new JLabel("Priority:"));
        bugPriorityBox = new JComboBox<>(new String[]{"Low", "Medium", "High", "Critical"});
        bugPriorityBox.setFont(new Font("Arial", Font.PLAIN, 14));
        reportFormPanel.add(bugPriorityBox);

        // Level
        reportFormPanel.add(new JLabel("Level:"));
        bugLevelBox = new JComboBox<>(new String[]{"Minor", "Major", "Critical"});
        bugLevelBox.setFont(new Font("Arial", Font.PLAIN, 14));
        reportFormPanel.add(bugLevelBox);

        // Project Name
        reportFormPanel.add(new JLabel("Project Name:"));
        projectNameField = new JTextField();
        projectNameField.setFont(new Font("Arial", Font.PLAIN, 14));
        reportFormPanel.add(projectNameField);

        // Screenshot
        reportFormPanel.add(new JLabel("Screenshot Path :"));
        screenshotField = new JTextField();
        screenshotField.setFont(new Font("Arial", Font.PLAIN, 14));
        reportFormPanel.add(screenshotField);

        // Assign to Developer
        reportFormPanel.add(new JLabel("Assign to Developer :"));
        developerField = new JTextField();
        developerField.setFont(new Font("Arial", Font.PLAIN, 14));
        reportFormPanel.add(developerField);

        // Report Button
        reportFormPanel.add(new JLabel(""));
        JButton reportBtn = new JButton(" Report Bug");
        reportBtn.setBackground(new Color(40, 167, 69));
        reportBtn.setForeground(Color.WHITE);
        reportBtn.setFont(new Font("Arial", Font.BOLD, 16));
        reportBtn.addActionListener(e -> controller.reportBug());
        reportFormPanel.add(reportBtn);

        reportPanel.add(reportFormPanel, BorderLayout.CENTER);

        mainPanel.add(reportPanel, BorderLayout.NORTH);


        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 15, 15));
        tablePanel.setBorder(BorderFactory.createTitledBorder("My Reported Bugs"));

        String[] columns = {"Bug Name", "Type", "Priority", "Status", "Assigned To"};
        bugsTable = new JTable(new DefaultTableModel(columns, 0));
        bugsTable.setRowHeight(25);
        bugsTable.setFont(new Font("Arial", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(bugsTable);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        mainPanel.add(tablePanel, BorderLayout.CENTER);

        return mainPanel;
    }


    private JPanel createMonitorPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Title
        JLabel titleLabel = new JLabel("Monitor All My Bugs");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(titleLabel, BorderLayout.NORTH);


        JPanel statsPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        statsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel totalLabel = new JLabel("Total Bugs: 0", SwingConstants.CENTER);
        totalLabel.setFont(new Font("Arial", Font.BOLD, 16));
        totalLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));

        JLabel openLabel = new JLabel("Open: 0", SwingConstants.CENTER);
        openLabel.setFont(new Font("Arial", Font.BOLD, 16));
        openLabel.setForeground(Color.RED);
        openLabel.setBorder(BorderFactory.createLineBorder(Color.RED, 2));

        JLabel closedLabel = new JLabel("Closed: 0", SwingConstants.CENTER);
        closedLabel.setFont(new Font("Arial", Font.BOLD, 16));
        closedLabel.setForeground(new Color(0, 150, 0));
        closedLabel.setBorder(BorderFactory.createLineBorder(new Color(0, 150, 0), 2));

        statsPanel.add(totalLabel);
        statsPanel.add(openLabel);
        statsPanel.add(closedLabel);

        panel.add(statsPanel, BorderLayout.NORTH);

        // All Bugs Table
        String[] columns = {"Bug Name", "Type", "Priority", "Level", "Status", "Assigned To", "Screenshot", "Date"};
        JTable allBugsTable = new JTable(new DefaultTableModel(columns, 0));
        allBugsTable.setRowHeight(25);
        allBugsTable.setFont(new Font("Arial", Font.PLAIN, 13));
        JScrollPane scrollPane = new JScrollPane(allBugsTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        updateMonitorPanel(allBugsTable, totalLabel, openLabel, closedLabel);

        return panel;
    }


    private void updateMonitorPanel(JTable table, JLabel totalLabel, JLabel openLabel, JLabel closedLabel) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        int total = 0, open = 0, closed = 0;

        for (Bug bug : fileManager.getBugsForTester(tester.getUsername())) {
            model.addRow(new Object[]{
                    bug.getBugName(),
                    bug.getType(),
                    bug.getPriority(),
                    bug.getLevel(),
                    bug.getStatus(),
                    bug.getAssignedDeveloper().isEmpty() ? "None" : bug.getAssignedDeveloper(),
                    bug.getScreenshot().isEmpty() ? "None" : bug.getScreenshot(),
                    bug.getDate()
            });

            total++;
            if (bug.getStatus().equalsIgnoreCase("open") || bug.getStatus().equalsIgnoreCase("in progress")) {
                open++;
            } else if (bug.getStatus().equalsIgnoreCase("closed") || bug.getStatus().equalsIgnoreCase("completed")) {
                closed++;
            }
        }

        totalLabel.setText("Total Bugs: " + total);
        openLabel.setText("Open: " + open);
        closedLabel.setText("Closed: " + closed);
    }

    public void loadBugs() {

        DefaultTableModel model = (DefaultTableModel) bugsTable.getModel();
        model.setRowCount(0);

        for (Bug bug : fileManager.getBugsForTester(tester.getUsername())) {
            model.addRow(new Object[]{
                    bug.getBugName(),
                    bug.getType(),
                    bug.getPriority(),
                    bug.getStatus(),
                    bug.getAssignedDeveloper().isEmpty() ? "None" : bug.getAssignedDeveloper()
            });
        }


    }



    public void clearBugFields() {
        bugNameField.setText("");
        projectNameField.setText("");
        screenshotField.setText("");
        developerField.setText("");
        bugTypeBox.setSelectedIndex(0);
        bugPriorityBox.setSelectedIndex(0);
        bugLevelBox.setSelectedIndex(0);
    }
}