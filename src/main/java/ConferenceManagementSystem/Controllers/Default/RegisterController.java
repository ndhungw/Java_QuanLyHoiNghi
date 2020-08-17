package ConferenceManagementSystem.Controllers.Default;

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
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {
    @FXML
    private Button backHomeButton;

    @FXML
    private TextField fullNameField;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button submitButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        usernameField.setPromptText("Username");
        passwordField.setPromptText("Password");
        submitButton.setDisable(true);

        usernameField.textProperty().addListener(((observable, oldValue, newValue) -> {
            submitButton.setDisable(newValue.trim().isEmpty());
        }));
    }

    public void register(ActionEvent actionEvent) throws IOException {
        Window owner = submitButton.getScene().getWindow();

        if (fullNameField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Thông tin chưa hợp lệ",
                    "Vui lòng điền tên hiển thị cho tài khoản!");
            return;
        }
        if (usernameField.getText().length() < 6) {
            showAlert(Alert.AlertType.ERROR, owner, "Thông tin chưa hợp lệ",
                    "Vui lòng nhập tên đăng nhập có độ dài lớn hơn hoặc bằng 6!");
            return;
        }
        if (passwordField.getText().length() < 6) {
            showAlert(Alert.AlertType.ERROR, owner, "Thông tin chưa hợp lệ",
                    "Vui lòng nhập vào mật khẩu có độ dài lớn hơn hoặc bằng 6!");
            return;
        }

        String displayName = fullNameField.getText();
        String username = usernameField.getText();
        String password = passwordField.getText();
        // Hash password
        String hashedPassword = hashPassword(password);

        int newAccountId = AccountDAO.getInstance().getMaxId() + 1;
//        Account accountToInsert = new Account();
//        accountToInsert.setId(newAccountId);
//
//        accountToInsert.setDisplayName(fullName);
//        accountToInsert.setUsername(username);
//        accountToInsert.setPassword(hashedPassword);
//        accountToInsert.setType(1); // default value
        Account accountToInsert = new Account(
                newAccountId,
                displayName,
                username,
                hashedPassword,
                1,
                0/*0: allowed, 1: blocked*/
                );
        // Đối tượng hiện đang có trạng thái Transient

        boolean inserted = AccountDAO.getInstance().insert(accountToInsert); // chuyển trạng thái thành Persistent

        if (inserted) {
            Session.getInstance().cleanSession(); // to make sure that we can init a new session with this new account
            Session.getInstance().setAccount(accountToInsert);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Register new account successfully");
            alert.setHeaderText("Information");

            ButtonType buttonTypeOK = new ButtonType("Go to Home screen", ButtonBar.ButtonData.OK_DONE);
            ButtonType buttonTypeCANCEL = new ButtonType("Create another account", ButtonBar.ButtonData.CANCEL_CLOSE);
            ButtonType buttonTypeOTHER = new ButtonType("Log in with another account", ButtonBar.ButtonData.OTHER);

            alert.getButtonTypes().setAll(buttonTypeOK, buttonTypeCANCEL, buttonTypeOTHER);

            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == buttonTypeOK) {
                // load the home screen with new account
                System.out.println("Cliked button OK_DONE when login successful");
                this.goBackHomeScreen(actionEvent);
            } else if (result.get() == buttonTypeOTHER) {
                // load login screen
                System.out.println("Cliked button OTHER when login successful");

                Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Default/LoginForm.fxml"));
                loader.setController(new LoginController());
                Parent parent = loader.load();
                Scene scene = new Scene(parent);
                stage.setScene(scene);
                stage.show();
            } else if (result.get() == buttonTypeCANCEL) {
                fullNameField.clear();
                usernameField.clear();
                passwordField.clear();
                return;
            }
        } else {

        }


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

    public String hashPassword(String plainTextPassword) {
        return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt(10));
    }
}
