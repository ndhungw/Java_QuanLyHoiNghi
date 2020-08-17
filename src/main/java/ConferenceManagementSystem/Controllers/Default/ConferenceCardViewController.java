package ConferenceManagementSystem.Controllers.Default;

import ConferenceManagementSystem.DAOs.ConferenceDAO;
import ConferenceManagementSystem.Entities.Conference;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ConferenceCardViewController implements Initializable {
    private FXMLLoader ConferenceCardViewLoader;

    @FXML
    private GridPane gridPane;

    @FXML
    private ImageView conferenceImageView;

    @FXML
    private Label nameLabel;

    public GridPane getGridPane() {
        return gridPane;
    }

    public void setGridPane(GridPane gridPane) {
        this.gridPane = gridPane;
    }

    @FXML
    private Label timeLabel;

    @FXML
    private Text shortDescriptionText;

    @FXML
    private Button subscribeButton;


    ConferenceCardViewController() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Default/ConferenceCardView.fxml"));
        try {
//            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    ConferenceCardViewController(Conference conference) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Default/ConferenceCardView.fxml"));
        try {
            loader.setController(this);
            loader.load();

            setConference(conference);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

//    ConferenceCardViewController(Object[] conferenceInfo) {
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Default/ConferenceCardView.fxml"));
//        try {
////            loader.setRoot(this);
//            loader.setController(this);
//            loader.load();
//
//            setConference(conferenceInfo);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gridPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                System.out.println(nameLabel.getText());

                // chuyen scene
                Stage stage = (Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
//                Stage stage = new Stage();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Default/ConferenceDetail.fxml"));

                // truy van hoi nghi duoc chon
//                Object[] selectedConference = ConferenceDAO.getInstance().getConferenceInfoByName(nameLabel.getText());
                Conference conference = ConferenceDAO.getInstance().getConferenceByName(nameLabel.getText());
                loader.setController(new ConferenceDetailController(conference));

                try {
                    Parent conferenceDetailParent = loader.load();
                    Scene scene = new Scene(conferenceDetailParent);
                    stage.setScene(scene);

                    ConferenceDetailController conferenceDetailController = loader.getController();

                    // truy van hoi nghi duoc chon
//                    Object[] selectedConference = ConferenceDAO.getInstance().getConferenceInfoByName(nameLabel.getText());

                    // set du lieu ben man hinh detail
//                    conferenceDetailController.setConferenceInfo(selectedConference);

                    stage.show();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void setConference(Conference conference) {
        nameLabel.setText(conference.getName());
        shortDescriptionText.setText(conference.getShortDescription());
        conferenceImageView.setImage(new Image(conference.getImgUrl()));
        timeLabel.setText(conference.getTime().toString());
    }

    public void setConference(Object[] conferenceInfo) {
        // "select c.id, c.name, c.shortDescription, c.detailDescription, i.url, c.time, l.name, l.address, c.numOfParticipants"
        // 0: conference.id
        // 1: conference.name
        // 2: conference.shortDescription
        // 3: conference.detailDescription
        // 4: images.url
        // 5: conference.time
        // 6: location.name
        // 7: location.address
        // 8: conference.numOfParticipants
        nameLabel.setText(conferenceInfo[1].toString());
        shortDescriptionText.setText(conferenceInfo[2].toString());
//        String url = conferenceInfo[4].toString();
//        Image image = new Image("Images\\business-conference-01.jpg");
        conferenceImageView.setImage(new Image(conferenceInfo[4].toString()));
        timeLabel.setText(conferenceInfo[5].toString());
    }
}
