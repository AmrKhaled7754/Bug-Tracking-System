package Controller;

import Model.PM;
import View.PMView;
import javax.swing.*;

public class PMController {

    private PMView view;
    private PM pm;

    public PMController(PMView view, PM pm) {
        this.view = view;
        this.pm = pm;
    }

    public void loadBugs() {
        view.loadAllBugs();
    }

    public void checkDeveloperPerformance() {
        pm.checkDeveloperPerformance();
        showAlert("Success", "Developer performance displayed in console!");
    }

    public void checkTesterPerformance() {
        pm.checkTesterPerformance();
        showAlert("Success", "Tester performance displayed in console!");
    }

    public void generateReport() {
        String report = pm.generateReport();
        JOptionPane.showMessageDialog(view, report, "Project Report", JOptionPane.INFORMATION_MESSAGE);
    }

    public void monitorBugs() {
        pm.monitorBugs();
        showAlert("Success", "Bug monitoring details displayed in console!");
    }

    public void logout() {
        view.dispose();
        new View.LoginView();
    }

    private void showAlert(String title, String message) {
        JOptionPane.showMessageDialog(view, message, title, JOptionPane.INFORMATION_MESSAGE);
    }
}