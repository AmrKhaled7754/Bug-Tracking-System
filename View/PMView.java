package View;

import Controller.PMController;
import Model.Bug;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PMView extends JFrame {

    private PMController controller;

    public PMView() {

        controller = new PMController();

        setTitle("Project Manager Dashboard");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Bug Tracking System - PM Module", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(0, 0, 102));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        add(titleLabel, BorderLayout.NORTH);


        String[] columns = { "Title", "Type", "Priority", "Level", "Project", "Date", "Status", "Developer",
                "Tester" };
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        List<Bug> bugs = controller.loadAllBugs();
        for (Bug b : bugs) {
            Object[] row = {
                    b.getBugName(),
                    b.getType(),
                    b.getPriority(),
                    b.getLevel(),
                    b.getProjectName(),
                    b.getDate(),
                    b.getStatus(),
                    b.getAssignedDeveloper(),
                    b.getReportedBy()
            };
            model.addRow(row);
        }

        JTable table = new JTable(model);


        table.getColumnModel().getColumn(0).setPreferredWidth(200); // Title
        table.getColumnModel().getColumn(1).setPreferredWidth(80);  // Type
        table.getColumnModel().getColumn(2).setPreferredWidth(70);  // Priority
        table.getColumnModel().getColumn(3).setPreferredWidth(70);  // Level
        table.getColumnModel().getColumn(4).setPreferredWidth(120); // Project
        table.getColumnModel().getColumn(5).setPreferredWidth(90);  // Date
        table.getColumnModel().getColumn(6).setPreferredWidth(70);  // Status
        table.getColumnModel().getColumn(7).setPreferredWidth(80);  // Dev
        table.getColumnModel().getColumn(8).setPreferredWidth(80);  // Tester

        table.setRowHeight(30);
        table.setFont(new Font("SansSerif", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(230, 230, 230));

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel statsPanel = new JPanel();
        statsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 40, 20));
        statsPanel.setBackground(new Color(245, 245, 245));

        //  String literals بدل Bug.STATUS_OPEN و Bug.STATUS_CLOSED
        int openVal = controller.countBugs("open");
        int closedVal = controller.countBugs("closed");

        JLabel lblTotal = new JLabel("Total Bugs: " + bugs.size());
        lblTotal.setFont(new Font("Arial", Font.BOLD, 16));

        JLabel lblOpen = new JLabel("Open: " + openVal);
        lblOpen.setFont(new Font("Arial", Font.BOLD, 16));
        lblOpen.setForeground(Color.RED);

        JLabel lblClosed = new JLabel("Closed: " + closedVal);
        lblClosed.setFont(new Font("Arial", Font.BOLD, 16));
        lblClosed.setForeground(new Color(0, 150, 0));

        statsPanel.add(lblTotal);
        statsPanel.add(lblOpen);
        statsPanel.add(lblClosed);

        JButton btnReport = new JButton("Generate Report");
        btnReport.setFont(new Font("Arial", Font.BOLD, 14));
        btnReport.setBackground(new Color(70, 130, 180));
        btnReport.setForeground(Color.WHITE);

        btnReport.addActionListener(e -> {
            String report = controller.generateReport();
            JOptionPane.showMessageDialog(this, report, "Project Report", JOptionPane.INFORMATION_MESSAGE);
        });

        statsPanel.add(btnReport);
        add(statsPanel, BorderLayout.SOUTH);
    }
}

