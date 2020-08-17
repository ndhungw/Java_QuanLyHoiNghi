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
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ConferenceListViewCellController extends ListCell<Conference> implements Initializable {
    @FXML
    private GridPane gridPane;

    @FXML
    private Label conferenceNameLabel;

    @FXML
    private Label shortDescriptionLabel;

    private FXMLLoader mLLoader;

    @Override
    protected void updateItem(Conference conference, boolean empty) {
        super.updateItem(conference, empty);

        if(empty || conference == null) {
            setText(null);
            setGraphic(null);
        } else {
            if (mLLoader == null) {
                mLLoader = new FXMLLoader(getClass().getResource("/FXML/Default/ConferenceListViewCell.fxml"));
                mLLoader.setController(this);

                try {
                    mLLoader.load();

                    conferenceNameLabel.setText(conference.getName());
                    shortDescriptionLabel.setText(conference.getShortDescription());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

//            icon
//            if(conference.getGender().equals(Student.GENDER.MALE)) {
//                fxIconGender.setIcon(FontAwesomeIcon.MARS);
//            } else if(conference.getGender().equals(Student.GENDER.FEMALE)) {
//                fxIconGender.setIcon(FontAwesomeIcon.VENUS);
//            } else {
//                fxIconGender.setIcon(FontAwesomeIcon.GENDERLESS);
//            }

            setText(null);
            setGraphic(gridPane);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gridPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                System.out.println(conferenceNameLabel.getText());

//                Stage stage = new Stage();
                Stage stage = (Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Default/ConferenceDetail.fxml"));

                // truy van hoi nghi duoc chon
//                Object[] selectedConferenceInfo = ConferenceDAO.getInstance().getConferenceInfoByName(conferenceNameLabel.getText());
//                ClearlyConferenceInfo selectedConferenceInfo = ConferenceDAO.getInstance().getClearlyConferenceInfoByName(conferenceNameLabel.getText());
                Conference selectedConference = ConferenceDAO.getInstance().getConferenceByName(conferenceNameLabel.getText());

                loader.setController(new ConferenceDetailController(selectedConference));

                try {
                    Parent conferenceDetailParent = loader.load();
                    Scene scene = new Scene(conferenceDetailParent);
                    stage.setScene(scene);

                    // truy van hoi nghi duoc chon
//                    Object[] selectedConference = ConferenceDAO.getInstance().getConferenceInfoByName(conferenceNameLabel.getText());

                    // set du lieu ben man hinh detail
//                    ConferenceDetailController conferenceDetailController = loader.getController();
//                    conferenceDetailController.setConferenceInfo(selectedConferenceInfo);

                    stage.show();
                    
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
