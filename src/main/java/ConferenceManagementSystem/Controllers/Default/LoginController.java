package ConferenceManagementSystem.Controllers.Default;

import ConferenceManagementSystem.Controllers.Admin.ConferenceManagementController;
import ConferenceManagementSystem.DAOs.AccountDAO;
import ConferenceManagementSystem.Entities.Account;
import ConferenceManagementSystem.Utils.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private GridPane gridPane;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Button goBackHomeButton;

    public LoginController() {

    }

    public Pane getLoginPane() {
        return gridPane;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void login(ActionEvent actionEvent) throws IOException {
        Window owner = loginButton.getScene().getWindow();

        if (usernameField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Login failed", "Please enter username!");
            return;
        }
        if (passwordField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Login failed", "Please enter password!");
            return;
        }

        String username = usernameField.getText();
        String password = passwordField.getText();

        // get hashedPassword to check if password was typed is right
        String hashedPassword = AccountDAO.getInstance().getHashedPassword(username);

        if (hashedPassword == null) {
            showAlert(Alert.AlertType.ERROR, owner, "Login failed", "Tài khoản không tồn tại!");
            return;
        }

        boolean chk = checkPassword(password, hashedPassword);

        if (chk) {
            // password matches
            Account account = AccountDAO.getInstance().getAccountForSession(username, hashedPassword);

            if (account.getBlocked() == 1) {
                showAlert(Alert.AlertType.WARNING, owner, "Login failed", "Sorry, your account has been blocked!" +
                        "\nConnect to admin for more information!");
            } else {
                Session.getInstance().setAccount(account);

                showAlert(Alert.AlertType.INFORMATION, owner, "Login successful", "Welcome to the system");

                // load home screen
                Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();

                if (account.getType() == 0) {
                    // admin
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Admin/ConferenceManagement.fxml"));
                    ConferenceManagementController conferenceManagementController = new ConferenceManagementController();
                    loader.setController(conferenceManagementController);
                    Parent parent = loader.load();
                    Scene scene = new Scene(parent);
                    stage.setScene(scene);
                } else {
                    // user
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Default/HomeScreen.fxml"));
                    HomeController homeController = new HomeController();
                    loader.setController(homeController);
                    Parent parent = loader.load();
                    Scene scene = new Scene(parent);
                    stage.setScene(scene);
                }

                stage.show();
            }
        } else {
            showAlert(Alert.AlertType.ERROR, owner, "Login error!", "Failed to log in to the system");
        }



//
//        if (account != null) {
//            showAlert(Alert.AlertType.INFORMATION, owner, "Login successful", "Welcome to the system");
//
////            Alert alert = new Alert(Alert.AlertType.ERROR);
////            alert.setTitle("Login status");
////            alert.setHeaderText("Alert Information");
////            alert.setContentText("Please enter username!");
////
////            ButtonType buttonTypeOK = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
////            ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
////
////            alert.getButtonTypes().setAll(buttonTypeOK, buttonTypeCancel);
////
////            Optional<ButtonType> result = alert.showAndWait();
////
////            if (result.get() == buttonTypeOK) {
////                System.out.println("Cliked button OK when login successful");
////            } else {
////                System.out.println("Cliked button Cancel when login successful");
////            }
//
////            Controller.getInstance().loadScreen(actionEvent,"HomeScreen");
////            // Init session
////            Session session = new Session(account);
//            Session.getInstance().setAccount(account);
//
//            // load home screen
//            Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Default/HomeScreen.fxml"));
//            HomeController homeController = new HomeController();
//            loader.setController(homeController);
//            Parent parent = loader.load();
//            Scene scene = new Scene(parent);
//            stage.setScene(scene);
//            stage.show();
//
//        } else {
//            showAlert(Alert.AlertType.ERROR, owner, "Login error!", "Failed to log in to the system");
//        }
    }

    public void goBackHomeScreen(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        FXMLLoader homeScreenLoader = new FXMLLoader(getClass().getResource("/FXML/Default/HomeScreen.fxml"));
        homeScreenLoader.setController(new HomeController());
        Parent homeScreenParent = homeScreenLoader.load();
        Scene scene = new Scene(homeScreenParent);
        stage.setScene(scene);
        stage.show();
    }

    private static void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }

    public boolean checkPassword(String plainTextPassword, String hashedPassword) {
        if (BCrypt.checkpw(plainTextPassword, hashedPassword)) {
            // Password matches!
            return true;
        }

        return false;
    }
}
