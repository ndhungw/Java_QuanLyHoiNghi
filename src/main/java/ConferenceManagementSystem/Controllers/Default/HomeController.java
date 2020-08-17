package ConferenceManagementSystem.Controllers.Default;

import ConferenceManagementSystem.Controllers.User.ProfileController;
import ConferenceManagementSystem.DAOs.ConferenceDAO;
import ConferenceManagementSystem.Entities.Conference;
import ConferenceManagementSystem.Utils.Session;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class HomeController implements Initializable {
    private FXMLLoader loginFormLoader;
    private FXMLLoader registerFormLoader;

    @FXML
    private Parent root;

    @FXML
    private Button loginButton;

    @FXML
    private Button logoutButton;

    @FXML
    private Button registerButton;

    @FXML
    private MenuButton accountMenuButton;

    @FXML
    private MenuItem registerMenuItem;

    @FXML
    private MenuItem logInMenuItem;

    @FXML
    private MenuItem profileMenuItem;

    @FXML
    private MenuItem logOutMenuItem;

    @FXML
    private ListView<Conference> conferencesListView;

    @FXML
    private ToggleButton listViewToggleButton;

    @FXML
    private ToggleButton cardViewToggleButton;

    @FXML
    private Label accountNameLabel;

    @FXML
    private GridPane cardsGridPane; // grid pane contains conference cards

    private ObservableList<Conference> conferenceObservableList;

    public HomeController()  {
        conferenceObservableList = FXCollections.observableArrayList();

        // get all conferences
        List<Conference> allConferences = ConferenceDAO.getInstance().getAll();
        for (int i = 0; i < allConferences.size(); i++) {
            System.out.println(allConferences.get(i));
        }
        conferenceObservableList.addAll(allConferences);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // set account info
        if (Session.getInstance().getAccount() != null) {
            // Đã đăng nhập
//            accountNameLabel.setText(Session.getInstance().getAccount().getDisplayName());
//            logoutButton.setVisible(true);
//            loginButton.setVisible(false);
//            registerButton.setVisible(false);

            accountMenuButton.setText("Chào " + Session.getInstance().getAccount().getDisplayName());
            registerMenuItem.setVisible(false);
            logInMenuItem.setVisible(false);
            profileMenuItem.setVisible(true);
            logOutMenuItem.setVisible(true);
        } else {
            // Chưa đăng nhập
//            logoutButton.setVisible(false);
//            loginButton.setVisible(true);
//            registerButton.setVisible(true);

            registerMenuItem.setVisible(true);
            logInMenuItem.setVisible(true);
            profileMenuItem.setVisible(false);
            logOutMenuItem.setVisible(false);
        }

        // set up list view
        conferencesListView.setItems(conferenceObservableList);
        conferencesListView.setCellFactory(conferencesListView -> new ConferenceListViewCellController());

        // set up card view: 9 cells = 9 cards
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3 && (i*3+j) < conferenceObservableList.size(); j++) {
                // tao ra mot card chua thong tin cua 1 hoi nghi
                Conference conference = conferenceObservableList.get(i*3+j);
//                Object[] conferenceInfo = ConferenceDAO.getInstance().getConferenceInfoByName(conference.getName());//*

                ConferenceCardViewController conferenceCardViewController = new ConferenceCardViewController(conference);//*
                cardsGridPane.add(conferenceCardViewController.getGridPane(),j,i);
            }
        }

        // set corresponding view
        if (listViewToggleButton.isSelected()) {
            showList();
        } else if (cardViewToggleButton.isSelected()) {
            showCard();
        }
    }

    public void login(ActionEvent actionEvent) throws IOException {
        loginFormLoader = new FXMLLoader(getClass().getResource("/FXML/Default/LoginForm.fxml"));
        loginFormLoader.setController(new LoginController());
        Parent loginFormParent = loginFormLoader.load();;
//        Stage loginStage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        Stage loginStage = (Stage)root.getScene().getWindow();
        Scene loginScene = new Scene(loginFormParent);
        loginStage.setScene(loginScene);
        loginStage.show();
    }

    public void logout(ActionEvent actionEvent) throws IOException {
        Session.getInstance().cleanSession();
        login(actionEvent);
    }

    public void register(ActionEvent actionEvent) throws IOException {
        registerFormLoader = new FXMLLoader(getClass().getResource("/FXML/Default/RegisterForm.fxml"));

        registerFormLoader.setController(new RegisterController());
        Parent registerFormParent = registerFormLoader.load();;
//        Stage registerStage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        Stage registerStage = (Stage)root.getScene().getWindow();

        Scene registerScene = new Scene(registerFormParent);
        registerStage.setScene(registerScene);
        registerStage.show();
    }

    public void goProfileScreen(ActionEvent actionEvent) throws IOException {
        FXMLLoader profileLoader = new FXMLLoader(getClass().getResource("/FXML/User/Profile.fxml"));

        profileLoader.setController(new ProfileController(Session.getInstance().getAccount()));
        Parent profileParent = profileLoader.load();;
        Stage profileStage = (Stage)root.getScene().getWindow();

        Scene profileScene = new Scene(profileParent);
        profileStage.setScene(profileScene);
        profileStage.show();
    }

    public void showList() {
        conferencesListView.setVisible(true);
        cardsGridPane.setVisible(false);
    }

    public void showCard() {
        conferencesListView.setVisible(false);
        cardsGridPane.setVisible(true);
    }
}
