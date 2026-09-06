//
//import javax.swing.SwingUtilities;
//import java.util.List;
//
//import View.*;
//import Model.*;
//import Provider.FileManager;
//
//public class Main {
//
//    public static void main(String[] args) {
//
//        SwingUtilities.invokeLater(() -> {
//
//            FileManager fileManager = new FileManager();
//            List<User> users = fileManager.loadUsers();
//
//            Admin admin = null;
//            Developer developer = null;
//            Tester tester = null;
//            PM pm = null;
//
//
//            for (User user : users) {
//                switch (user.getRole().toLowerCase()) {
//                    case "admin":
//                        if (admin == null) admin = (Admin) user;
//                        break;
//                    case "developer":
//                        if (developer == null) developer = (Developer) user;
//                        break;
//                    case "tester":
//                        if (tester == null) tester = (Tester) user;
//                        break;
//                    case "pm":
//                        if (pm == null) pm = (PM
//                                ) user;
//                        break;
//                }
//            }
//
//
//            if (admin != null) {
//                new AdminView(admin).setVisible(true);
//            }
//
//            if (developer != null) {
//                new DeveloperView(developer).setVisible(true);
//            }
//
//            if (tester != null) {
//                new TesterView(tester).setVisible(true);
//            }
//
//            if (pm != null) {
//                new PMView(pm).setVisible(true);
//            }
//
//
//            new LoginView().setVisible(true);
//        });
//    }
//}



import View.LoginView;

public class Main {
    public static void main(String[] args) {
        new LoginView();
    }
}

