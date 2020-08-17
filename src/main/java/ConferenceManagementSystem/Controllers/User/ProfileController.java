package ConferenceManagementSystem.Controllers.User;

import ConferenceManagementSystem.Controllers.Controller;
import ConferenceManagementSystem.Controllers.Default.HomeController;
import ConferenceManagementSystem.DAOs.AccountDAO;
import ConferenceManagementSystem.Entities.Account;
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
import javafx.stage.Stage;
import javafx.stage.Window;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ProfileController implements Initializable {
    private Account account;

    @FXML
    private Parent root;

    @FXML
    private Button backButton;

    @FXML
    private TextField displayNameTextField;

    @FXML
    private TextField usernameTextField;

    @FXML
    private PasswordField passwordPasswordField;

    @FXML
    private PasswordField rePasswordPasswordField;

    @FXML
    private Button updateAccountInfoButton;

    public ProfileController () {}

    public ProfileController (Account account) {
        this.account = account;
    }

    public void setAccountInfoToFields(Account accountInfoToFields) {
        displayNameTextField.setText(accountInfoToFields.getDisplayName());
        usernameTextField.setText(accountInfoToFields.getUsername());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setAccountInfoToFields(this.account);

        displayNameTextField.textProperty().addListener(((observable, oldValue, newValue) -> {
            updateAccountInfoButton.setDisable(newValue.trim().isEmpty());
        }));
        usernameTextField.textProperty().addListener(((observable, oldValue, newValue) -> {
            updateAccountInfoButton.setDisable(newValue.trim().isEmpty());
        }));
        passwordPasswordField.textProperty().addListener(((observable, oldValue, newValue) -> {
            updateAccountInfoButton.setDisable(newValue.trim().isEmpty());
        }));
        rePasswordPasswordField.textProperty().addListener(((observable, oldValue, newValue) -> {
            updateAccountInfoButton.setDisable(newValue.trim().isEmpty());
        }));
    }

    public void goBack (ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        FXMLLoader homeScreenLoader = new FXMLLoader(getClass().getResource("/FXML/Default/HomeScreen.fxml"));
        homeScreenLoader.setController(new HomeController());
        Parent homeScreenParent = homeScreenLoader.load();
        Scene scene = new Scene(homeScreenParent);
        stage.setScene(scene);
        stage.show();
    }

    public void updateAccountInfo(ActionEvent actionEvent) {
        Window owner = root.getScene().getWindow();

        if (displayNameTextField.getText().isEmpty()) {
            Controller.showAlert(Alert.AlertType.ERROR, owner, "Thông tin chưa hợp lệ",
                    "Vui lòng điền tên hiển thị cho tài khoản!");
            return;
        }
        if (passwordPasswordField.getText().length() < 6) {
            Controller.showAlert(Alert.AlertType.ERROR, owner, "Thông tin chưa hợp lệ",
                    "Vui lòng nhập vào mật khẩu có độ dài lớn hơn hoặc bằng 6!");
            return;
        }
        if (!rePasswordPasswordField.getText().equals(passwordPasswordField.getText())) {
            Controller.showAlert(Alert.AlertType.ERROR, owner, "Thông tin chưa hợp lệ",
                    "Mật khẩu nhập lại không giống với mật khẩu mới!");
            return;
        }

        String displayName = displayNameTextField.getText();
        String password = passwordPasswordField.getText();
        // Hash password
        String hashedPassword = hashPassword(password);

        Account accountToUpdate = new Account(
                account.getId(),
                displayName,
                account.getUsername(),
                hashedPassword,
                account.getType(),
                account.getBlocked()/*0: allowed, 1: blocked*/
        );
        // Đối tượng hiện đang có trạng thái Transient

        boolean updated = AccountDAO.getInstance().update(account.getId(),accountToUpdate); // chuyển trạng thái thành Persistent

        if (updated) {
            Controller.showAlert(Alert.AlertType.INFORMATION, owner, "Update account information", "Account updated!");
        } else {
            Controller.showAlert(Alert.AlertType.WARNING, owner, "Update account information", "Update failed!");
        }
    }

    public String hashPassword(String plainTextPassword) {
        return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt(10));
    }
}
