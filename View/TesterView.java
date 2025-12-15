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
    public JTextField developerField;
    public JTextField screenshotField;

    public TesterView(Tester tester) {
        this.tester = tester;
        this.controller = new TesterController(this, tester);
        this.fileManager = new FileManager();

        setTitle("Tester Dashboard - " + tester.getUsername());
        setSize(1100, 700);
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

        // Main Panel
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Bugs Table
        String[] columns = {"Bug Name", "Type", "Priority", "Level", "Status", "Assigned To"};
        bugsTable = new JTable(new DefaultTableModel(columns, 0));
        bugsTable.setRowHeight(25);
        JScrollPane scrollPane = new JScrollPane(bugsTable);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Form Panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(8, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createTitledBorder("Bug Operations"));

        // Report Bug Section
        formPanel.add(new JLabel("Bug Name:"));
        bugNameField = new JTextField();
        formPanel.add(bugNameField);

        formPanel.add(new JLabel("Type:"));
        bugTypeBox = new JComboBox<>(new String[]{"Functional", "Performance", "UI", "Security", "Technical"});
        formPanel.add(bugTypeBox);

        formPanel.add(new JLabel("Priority:"));
        bugPriorityBox = new JComboBox<>(new String[]{"Low", "Medium", "High", "Critical"});
        formPanel.add(bugPriorityBox);

        formPanel.add(new JLabel("Level:"));
        bugLevelBox = new JComboBox<>(new String[]{"Minor", "Major", "Critical"});
        formPanel.add(bugLevelBox);

        formPanel.add(new JLabel("Project Name:"));
        projectNameField = new JTextField();
        formPanel.add(projectNameField);

        JButton reportBtn = new JButton("Report Bug");
        reportBtn.setBackground(new Color(40, 167, 69));
        reportBtn.setForeground(Color.WHITE);
        reportBtn.addActionListener(e -> controller.reportBug());
        formPanel.add(new JLabel());
        formPanel.add(reportBtn);

        // Assign Bug Section
        formPanel.add(new JLabel("Assign to Developer:"));
        developerField = new JTextField();
        formPanel.add(developerField);

        JButton assignBtn = new JButton("Assign Bug");
        assignBtn.setBackground(new Color(255, 193, 7));
        assignBtn.addActionListener(e -> controller.assignBug());
        formPanel.add(new JLabel());
        formPanel.add(assignBtn);

        // Screenshot Section
        formPanel.add(new JLabel("Screenshot Path:"));
        screenshotField = new JTextField();
        formPanel.add(screenshotField);

        JButton screenshotBtn = new JButton("Attach Screenshot");
        screenshotBtn.setBackground(new Color(23, 162, 184));
        screenshotBtn.setForeground(Color.WHITE);
        screenshotBtn.addActionListener(e -> controller.attachScreenshot());
        formPanel.add(new JLabel());
        formPanel.add(screenshotBtn);

        mainPanel.add(formPanel, BorderLayout.EAST);

        add(mainPanel, BorderLayout.CENTER);

        // Bottom Panel
        JPanel bottomPanel = new JPanel();
        JButton refreshBtn = new JButton("Refresh");
        refreshBtn.addActionListener(e -> controller.loadBugs());

        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setBackground(new Color(220, 53, 69));
        logoutBtn.setForeground(Color.WHITE);
        logoutBtn.addActionListener(e -> controller.logout());

        bottomPanel.add(refreshBtn);
        bottomPanel.add(logoutBtn);
        add(bottomPanel, BorderLayout.SOUTH);

        controller.loadBugs();
    }

    public void loadBugs() {
        DefaultTableModel model = (DefaultTableModel) bugsTable.getModel();
        model.setRowCount(0);

        for (Bug bug : fileManager.getBugsForTester(tester.getUsername())) {
            model.addRow(new Object[]{
                bug.getBugName(),
                bug.getType(),
                bug.getPriority(),
                bug.getLevel(),
                bug.getStatus(),
                bug.getAssignedDeveloper().isEmpty() ? "None" : bug.getAssignedDeveloper()
            });
        }
    }

    public void clearBugFields() {
        bugNameField.setText("");
        projectNameField.setText("");
        bugTypeBox.setSelectedIndex(0);
        bugPriorityBox.setSelectedIndex(0);
        bugLevelBox.setSelectedIndex(0);
    }
}