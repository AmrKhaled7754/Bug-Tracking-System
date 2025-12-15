package Controller;

import Model.Bug;
import Model.Tester;
import Provider.FileManager;
import View.TesterView;
import javax.swing.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TesterController {

    private TesterView view;
    private Tester tester;
    private FileManager fileManager;

    public TesterController(TesterView view, Tester tester) {
        this.view = view;
        this.tester = tester;
        this.fileManager = new FileManager();
    }

    public void loadBugs() {
        view.loadBugs();
    }

    public void reportBug() {
        String bugName = view.bugNameField.getText().trim();
        String type = (String) view.bugTypeBox.getSelectedItem();
        String priority = (String) view.bugPriorityBox.getSelectedItem();
        String level = (String) view.bugLevelBox.getSelectedItem();
        String projectName = view.projectNameField.getText().trim();

        if (bugName.isEmpty() || projectName.isEmpty()) {
            showAlert("Error", "Please fill bug name and project name!");
            return;
        }

        // Get current date
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        Bug newBug = tester.reportBug(bugName, type, priority, level, projectName, date);
        fileManager.addBug(newBug);

        showAlert("Success", "Bug reported successfully!");
        view.clearBugFields();
        loadBugs();
    }

    public void assignBug() {
        int row = view.bugsTable.getSelectedRow();
        if (row == -1) {
            showAlert("Error", "Please select a bug first!");
            return;
        }

        String developer = view.developerField.getText().trim();
        if (developer.isEmpty()) {
            showAlert("Error", "Please enter developer username!");
            return;
        }

        String bugName = view.bugsTable.getValueAt(row, 0).toString();
        Bug bug = findBugByName(bugName);

        if (bug != null) {
            tester.assignBugToDeveloper(bug, developer);
            fileManager.updateBug(bugName, bug);
            showAlert("Success", "Bug assigned to " + developer + " successfully!");
            view.developerField.setText("");
            loadBugs();
        }
    }

    public void attachScreenshot() {
        int row = view.bugsTable.getSelectedRow();
        if (row == -1) {
            showAlert("Error", "Please select a bug first!");
            return;
        }

        String screenshot = view.screenshotField.getText().trim();
        if (screenshot.isEmpty()) {
            showAlert("Error", "Please enter screenshot path!");
            return;
        }

        String bugName = view.bugsTable.getValueAt(row, 0).toString();
        Bug bug = findBugByName(bugName);

        if (bug != null) {
            tester.attachScreenshot(bug, screenshot);
            fileManager.updateBug(bugName, bug);
            showAlert("Success", "Screenshot attached successfully!");
            view.screenshotField.setText("");
            loadBugs();
        }
    }

    private Bug findBugByName(String bugName) {
        for (Bug bug : fileManager.loadBugs()) {
            if (bug.getBugName().equals(bugName)) {
                return bug;
            }
        }
        return null;
    }

    public void logout() {
        view.dispose();
        new View.LoginView();
    }

    private void showAlert(String title, String message) {
        JOptionPane.showMessageDialog(view, message, title,
            title.equals("Success") ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE);
    }
}