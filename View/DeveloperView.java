package View;

import Controller.DeveloperController;
import Model.Bug;
import Model.Developer;
import Provider.FileManager;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class DeveloperView extends JFrame {

    private Developer developer;
    private DeveloperController controller;
    private FileManager fileManager;

    public JTable bugsTable;
    public JTextField statusField;

    public DeveloperView(Developer developer) {
        this.developer = developer;
        this.controller = new DeveloperController(this, developer);
        this.fileManager = new FileManager();

        setTitle("Developer Dashboard - " + developer.getUsername());
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Title Panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(70, 130, 180));
        JLabel titleLabel = new JLabel("Developer Dashboard - " + developer.getUsername());
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);

        // Bugs Table
        String[] columns = {"Bug Name", "Type", "Priority", "Status", "Reported By"};
        bugsTable = new JTable(new DefaultTableModel(columns, 0));
        bugsTable.setRowHeight(30);
        JScrollPane scrollPane = new JScrollPane(bugsTable);
        add(scrollPane, BorderLayout.CENTER);

        // Control Panel
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        controlPanel.setBorder(BorderFactory.createTitledBorder("Update Bug Status"));

        controlPanel.add(new JLabel("New Status:"));
        statusField = new JTextField(20);
        controlPanel.add(statusField);

        JButton updateBtn = new JButton("Update Status");
        updateBtn.setBackground(new Color(40, 167, 69));
        updateBtn.setForeground(Color.WHITE);
        updateBtn.setFont(new Font("Arial", Font.BOLD, 14));
        updateBtn.addActionListener(e -> controller.updateBugStatus());
        controlPanel.add(updateBtn);

        add(controlPanel, BorderLayout.SOUTH);

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

        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(controlPanel, BorderLayout.CENTER);
        southPanel.add(bottomPanel, BorderLayout.SOUTH);
        add(southPanel, BorderLayout.SOUTH);

        controller.loadBugs();
    }

    public void loadBugs() {
        DefaultTableModel model = (DefaultTableModel) bugsTable.getModel();
        model.setRowCount(0);

        for (Bug bug : fileManager.getBugsForDeveloper(developer.getUsername())) {
            model.addRow(new Object[]{
                bug.getBugName(),
                bug.getType(),
                bug.getPriority(),
                bug.getStatus(),
                bug.getReportedBy()
            });
        }
    }
}