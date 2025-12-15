package View;

import Controller.AdminController;
import Model.Admin;
import Model.Bug;
import Model.User;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class AdminView extends JFrame {

    private Admin admin;
    private AdminController controller;

    public JTable usersTable;
    public JTable bugsTable;
    public JTextField userUsernameField;
    public JTextField userPasswordField;
    public JTextField userEmailField;
    public JComboBox<String> userRoleBox;

    public AdminView(Admin admin) {
        this.admin = admin;
        this.controller = new AdminController(this, admin);

        setTitle("Admin Dashboard");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Title Panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(70, 130, 180));
        JLabel titleLabel = new JLabel("Admin Dashboard - " + admin.getUsername());
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);

        // Main Panel with Tabs
        JTabbedPane tabbedPane = new JTabbedPane();

        // Users Tab
        JPanel usersPanel = createUsersPanel();
        tabbedPane.addTab("Users Management", usersPanel);

        // Bugs Tab
        JPanel bugsPanel = createBugsPanel();
        tabbedPane.addTab("Bugs Overview", bugsPanel);

        add(tabbedPane, BorderLayout.CENTER);

        // Bottom Panel
        JPanel bottomPanel = new JPanel();
        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setBackground(new Color(220, 53, 69));
        logoutBtn.setForeground(Color.WHITE);
        logoutBtn.setFont(new Font("Arial", Font.BOLD, 14));
        logoutBtn.addActionListener(e -> controller.logout());
        bottomPanel.add(logoutBtn);
        add(bottomPanel, BorderLayout.SOUTH);

        loadAllUsers();
        loadAllBugs();
    }

    private JPanel createUsersPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        // Users Table
        String[] columns = {"Username", "Email", "Role"};
        usersTable = new JTable(new DefaultTableModel(columns, 0));
        usersTable.setRowHeight(25);
        JScrollPane scrollPane = new JScrollPane(usersTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Form Panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(5, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        formPanel.add(new JLabel("Username:"));
        userUsernameField = new JTextField();
        formPanel.add(userUsernameField);

        formPanel.add(new JLabel("Password:"));
        userPasswordField = new JTextField();
        formPanel.add(userPasswordField);

        formPanel.add(new JLabel("Email:"));
        userEmailField = new JTextField();
        formPanel.add(userEmailField);

        formPanel.add(new JLabel("Role:"));
        userRoleBox = new JComboBox<>(new String[]{"admin", "developer", "tester", "pm"});
        formPanel.add(userRoleBox);

        // Buttons Panel
        JPanel buttonsPanel = new JPanel();
        JButton addBtn = new JButton("Add User");
        addBtn.setBackground(new Color(40, 167, 69));
        addBtn.setForeground(Color.WHITE);
        addBtn.addActionListener(e -> controller.addUser());

        JButton updateBtn = new JButton("Update User");
        updateBtn.setBackground(new Color(255, 193, 7));
        updateBtn.addActionListener(e -> controller.updateUser());

        JButton deleteBtn = new JButton("Delete User");
        deleteBtn.setBackground(new Color(220, 53, 69));
        deleteBtn.setForeground(Color.WHITE);
        deleteBtn.addActionListener(e -> controller.deleteUser());

        buttonsPanel.add(addBtn);
        buttonsPanel.add(updateBtn);
        buttonsPanel.add(deleteBtn);

        formPanel.add(new JLabel());
        formPanel.add(buttonsPanel);

        panel.add(formPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createBugsPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        // Bugs Table
        String[] columns = {"Bug Name", "Type", "Priority", "Level", "Status", "Developer", "Tester"};
        bugsTable = new JTable(new DefaultTableModel(columns, 0));
        bugsTable.setRowHeight(25);
        JScrollPane scrollPane = new JScrollPane(bugsTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Delete Bug Button
        JPanel buttonPanel = new JPanel();
        JButton deleteBugBtn = new JButton("Delete Selected Bug");
        deleteBugBtn.setBackground(new Color(220, 53, 69));
        deleteBugBtn.setForeground(Color.WHITE);
        deleteBugBtn.addActionListener(e -> controller.deleteBug());
        buttonPanel.add(deleteBugBtn);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    public void loadAllUsers() {
        DefaultTableModel model = (DefaultTableModel) usersTable.getModel();
        model.setRowCount(0);

        for (User user : admin.viewAllUsers()) {
            model.addRow(new Object[]{
                user.getUsername(),
                user.getEmail(),
                user.getRole()
            });
        }
    }

    public void loadAllBugs() {
        DefaultTableModel model = (DefaultTableModel) bugsTable.getModel();
        model.setRowCount(0);

        for (Bug bug : admin.viewAllBugs()) {
            model.addRow(new Object[]{
                bug.getBugName(),
                bug.getType(),
                bug.getPriority(),
                bug.getLevel(),
                bug.getStatus(),
                bug.getAssignedDeveloper().isEmpty() ? "None" : bug.getAssignedDeveloper(),
                bug.getReportedBy()
            });
        }
    }

    public void clearUserFields() {
        userUsernameField.setText("");
        userPasswordField.setText("");
        userEmailField.setText("");
        userRoleBox.setSelectedIndex(0);
    }
}