package Controller;

import Model.Bug;
import Model.Developer;
import Provider.FileManager;
import View.DeveloperView;
import javax.swing.*;

public class DeveloperController {

    private DeveloperView view;
    private Developer developer;
    private FileManager fileManager;

    public DeveloperController(DeveloperView view, Developer developer) {
        this.view = view;
        this.developer = developer;
        this.fileManager = new FileManager();
    }

    public void loadBugs() {
        view.loadBugs();
    }

    public void updateBugStatus() {
        int row = view.bugsTable.getSelectedRow();
        if (row == -1) {
            showAlert("Error", "Select a bug first.");
            return;
        }

        String newStatus = view.statusField.getText().trim();
        if (newStatus.isEmpty()) {
            showAlert("Error", "Enter a new status.");
            return;
        }

        String bugName = view.bugsTable.getValueAt(row, 0).toString();
        Bug bug = findBugByName(bugName);

        if (bug != null) {
            developer.updateBugStatus(bug, newStatus);
            fileManager.updateBug(bugName, bug);
            showAlert("Success", "Bug status updated!");
            view.statusField.setText("");
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