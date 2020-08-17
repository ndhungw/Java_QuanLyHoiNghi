package ConferenceManagementSystem.Controllers.Default;

import ConferenceManagementSystem.Controllers.Controller;
import ConferenceManagementSystem.DAOs.AccountConferenceDAO;
import ConferenceManagementSystem.DAOs.ConferenceDAO;
import ConferenceManagementSystem.DAOs.LocationDAO;
import ConferenceManagementSystem.Entities.AccountConference;
import ConferenceManagementSystem.Entities.Conference;
import ConferenceManagementSystem.Entities.Location;
import ConferenceManagementSystem.Utils.Session;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class ConferenceDetailController implements Initializable {
    private FXMLLoader conferenceDetailLoader;
//    private Object[] conferenceInfo;
    private Conference conference;

    @FXML
    private GridPane gridPane;

    @FXML
    private Button backButton;

    @FXML
    private Label useDisplayName;

    @FXML
    private Button subscribeButton;

    @FXML
    private Label conferenceNameLabel;

    @FXML
    private ImageView conferenceImageView;

    @FXML
    private Label timeLabel;

    @FXML
    private Label locationLabel;

    @FXML
    private Label numOfParticipantsLabel;

    @FXML
    private Text detailDescriptionText;

//    @FXML
//    private ListView<Account> subscriberListView;

    @FXML
    private ListView<String> subscriberListView;

    private ObservableList<String> subscriberObservableList;

    @FXML
    private Label noSubscriberLabel;

    public ConferenceDetailController(Conference conference) {
        this.conference = conference;

        subscriberObservableList = FXCollections.observableArrayList();
        // get all subscribers' name
        List<String> allSubscribersName = AccountConferenceDAO.getInstance().getSubscriberList(conference.getId());
        subscriberObservableList.addAll(allSubscribersName);
    }

//    public ConferenceDetailController(Object[] conferenceInfo) {
//        this.conferenceInfo = conferenceInfo;
//
////        // "select c.id, c.name, c.shortDescription, c.detailDescription, i.url, c.time, l.name, l.address, c.numOfParticipants"
////        // 0: conference.id
////        // 1: conference.name
////        // 2: conference.shortDescription
////        // 3: conference.detailDescription
////        // 4: images.url
////        // 5: conference.time
////        // 6: location.name
////        // 7: location.address
////        // 8: conference.numOfParticipants
////        this.conferenceNameLabel.setText(conferenceInfo[1].toString());
////        this.detailDescriptionText.setText(conferenceInfo[3].toString());
////        this.conferenceImageView.setImage(new Image(conferenceInfo[4].toString()));
////        this.timeLabel.setText(conferenceInfo[5].toString());
////        this.locationLabel.setText(conferenceInfo[7].toString());
////        this.numOfParticipantsLabel.setText(conferenceInfo[8].toString());
//
//        subscriberObservableList = FXCollections.observableArrayList();
//
//        // get all subscribers' name
//        List<String> allSubscribersName = AccountConferenceDAO.getInstance().getSubscriberList((int)conferenceInfo[0]);
//
//        subscriberObservableList.addAll(allSubscribersName);
//    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (Session.getInstance().getAccount() != null) {
            if (Session.getInstance().getAccount().getType() == 0) {
                // admin
                backButton.setVisible(false);
            }
        }

        if (Session.getInstance().getAccount() != null) {
            if (Session.getInstance().getAccount().getType() == 1) {
                useDisplayName.setText(Session.getInstance().getAccount().getDisplayName());
            }
        }

        if (conference != null) {
            this.setConferenceInfo(conference);
        }

        if (subscriberObservableList.size() == 0) {
            this.noSubscriberLabel.setVisible(true);
        } else {
            this.noSubscriberLabel.setVisible(false);
        }

        subscriberListView.setItems(subscriberObservableList);
    }

    public void setConferenceInfo(Conference conference) {
        conferenceNameLabel.setText(conference.getName());
        detailDescriptionText.setText(conference.getDetailDescription());
        conferenceImageView.setImage(new Image(conference.getImgUrl()));
        timeLabel.setText(timeLabel.getText() + conference.getTime().toString());

        // Truy vấn địa điểm
        Location location = LocationDAO.getInstance().getById(conference.getLocation());
        locationLabel.setText(locationLabel.getText() + location.getAddress());

        numOfParticipantsLabel.setText(numOfParticipantsLabel.getText() + String.valueOf(conference.getNumOfParticipants()));
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

    public void subscribe(ActionEvent actionEvent) {
        if (Session.getInstance().getAccount() == null) {
            // chưa đăng nhập
            // yêu cầu đăng nhập để đăng kí tham dự
            // pop up a dialog, 2 nút đăng nhập & đăng kí
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Login is required");
            dialog.setHeaderText("Please log in or register");

            ButtonType loginButtonType = new ButtonType("Log in", ButtonBar.ButtonData.OK_DONE);
            ButtonType registerButtonType = new ButtonType("Register", ButtonBar.ButtonData.OTHER);
            ButtonType cancelButtonType = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

            dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, registerButtonType, cancelButtonType);

            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == loginButtonType) {
                    // load login screen
                    System.out.println("load login screen");
                    return loginButtonType;
                } else if (dialogButton == registerButtonType) {
                    // load register screen
                    System.out.println("load register screen");
                    return registerButtonType;
                } else if (dialogButton == cancelButtonType) {
                    // close
                    System.out.println("close the dialog");
                    return cancelButtonType;
                }
                return null;
            });

            Optional<ButtonType> result = dialog.showAndWait();
            result.ifPresent(userChoice -> {
                if (userChoice == loginButtonType) {
                    Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Default/LoginForm.fxml"));
                    loader.setController(new LoginController());
                    Parent parent = null;
                    try {
                        parent = loader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Scene scene = new Scene(parent);
                    stage.setScene(scene);
                    stage.show();
                } else if (userChoice == registerButtonType) {
                    Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Default/RegisterForm.fxml"));
                    loader.setController(new RegisterController());
                    Parent parent = null;
                    try {
                        parent = loader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Scene scene = new Scene(parent);
                    stage.setScene(scene);
                    stage.show();
                } else if (userChoice == cancelButtonType) {
                    return;
                }
            });
        } else {

            Window owner = subscribeButton.getScene().getWindow();

            // xét thời gian diễn ra
            boolean isOccurredConference = ConferenceDAO.getInstance().checkIfConferenceOccured(conference.getId());

            // tìm số người đã subscribe, nếu còn thi cho đăng kí, nếu hết chỗ thì thông báo

            //kiểm tra user này đã đăng kí hội nghị này chưa
            // subscribedTimes: 0 => not yet
            // subscribedTimes: >0 => already
//            int subscribedTimes = AccountConferenceDAO.getInstance().checkIfSubscribed(Session.getInstance().getAccount().getId(), (int) conferenceInfo[0]);
            int subscribedTimes = AccountConferenceDAO.getInstance().checkIfSubscribed(Session.getInstance().getAccount().getId(), conference.getId());

            if (isOccurredConference) {
                System.out.println("Subscribe failed! - Conference already occurred!");
                Controller.getInstance().showAlert(Alert.AlertType.ERROR, owner, "Subscribe failed!", "Conference already occurred!");
                return;
            }

            // Nếu số lượng đăng kí + 1 > Location.capacity -> failed
            // get số lượng đã đk hội nghị này
            // làm 1 SP để gọi
//            Location location = LocationDAO.getInstance().getById(conference.getLocation());
//            if (conference.getNumOfParticipants() == location.getCapacity()) {
//                System.out.println("Subscribe failed! - Conference is full!");
//                Controller.getInstance().showAlert(Alert.AlertType.ERROR, owner, "Subscribe failed!", "Conference is full!");
//                return;
//            }
            if (subscribedTimes > 0) {
                System.out.println("Subscribe failed! - You subscribed this conference!");
                Controller.getInstance().showAlert(Alert.AlertType.ERROR, owner, "Subscribe failed!", "You subscribed this conference!");
                return;
            }

            // kiểm tra user đã đăng kí hội nghị khác có thời gian diễn ra cùng thời điểm với hội nghị này
            // ...


            // pass hết -> thực hiện đăng kí tham dự cho user này
            // Tạo obj
            AccountConference accountConference = new AccountConference();
            accountConference.setId(AccountConferenceDAO.getInstance().getMaxId());
            accountConference.setIdAccount(Session.getInstance().getAccount().getId());
            accountConference.setIdConference(conference.getId());
            accountConference.setStatus(2); // pending

            boolean inserted = AccountConferenceDAO.getInstance().insert(accountConference);

            if (inserted) {
                System.out.println("User with id " + Session.getInstance().getAccount().getId() + " subscribed to conference " + conference.getName() + " successfully!");
                Controller.getInstance().showAlert(Alert.AlertType.INFORMATION, owner, "Subscribe successfully!", "You subscribed this conference!");
            }
            return;
        }
    }
}
