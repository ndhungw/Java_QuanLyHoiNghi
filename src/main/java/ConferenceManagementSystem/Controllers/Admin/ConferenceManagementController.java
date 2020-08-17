package ConferenceManagementSystem.Controllers.Admin;

import ConferenceManagementSystem.Controllers.Controller;
import ConferenceManagementSystem.Controllers.Default.ConferenceDetailController;
import ConferenceManagementSystem.Controllers.Default.LoginController;
import ConferenceManagementSystem.DAOs.AccountConferenceDAO;
import ConferenceManagementSystem.DAOs.AccountDAO;
import ConferenceManagementSystem.DAOs.ConferenceDAO;
import ConferenceManagementSystem.DAOs.LocationDAO;
import ConferenceManagementSystem.Entities.*;
import ConferenceManagementSystem.Utils.Session;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class ConferenceManagementController implements Initializable {
    @FXML
    private Label displayNameLabel;

    @FXML
    private GridPane conferenceManagementGridPane; // pane chung

    @FXML
    private TabPane tabPane; // tab pane chua cac tab quan ly

    // Tab 1
    @FXML
    private Tab conferenceViewTab; // tab de view cac thong tin cua conference, them xoa sua conference

    @FXML
    private TableView<Conference> conferenceViewTable;

    @FXML
    private TableColumn<Conference, Integer> idTableColumn;

    @FXML
    private TableColumn<Conference, String> nameTableColumn;

    @FXML
    private TableColumn<Conference, String> locationTableColumn;

    @FXML
    private TableColumn<Conference, Timestamp> timeTableColumn;

    @FXML
    private TableColumn<Conference, Integer> numOfParticipantsColumn;

//    @FXML
//    private TableColumn<Conference, Integer> capacityTableColumn;

    // ObservableList
    private ObservableList<Conference> conferenceObservableList;

    @FXML
    private TextField conNameTextField;

//    @FXML
//    private TextField conLocationTextField;
    @FXML
    private ComboBox<String> conLocationComboBox;
    private ObservableList<String> locationAddressObservableList;

    @FXML
    private TextField conTimeTextField;

    @FXML
    private TextField conImageURLTextField;

    @FXML
    private TextField conShortDescriptionTextField;

//    @FXML
//    private TextField conCapacityTextField;

    @FXML
    private TextField conNumOfParticipantsTextField;

    @FXML
    private TextArea conDetailDescriptionTextArea;

    @FXML
    private Button goDetailConferenceButton;

    @FXML
    private Button clearSelectionButton;

    @FXML
    private Button addConferenceButton;

    @FXML
    private Button updateConferenceButton;

//    @FXML
//    private Button deleteConferenceButton;

    // Tab 2
    @FXML
    private Tab approvalTab; // tab de duyet cac request dang ky tham du hoi nghi

    @FXML
    private GridPane approvalGridPane;

    @FXML
    private TableView<AccountConference> requestTable; // bảng danh sách các request
    private ObservableList<AccountConference> requestObservableList;

    @FXML
    private TableColumn<AccountConference, Integer> reqRequestIdTableColumn; // id request

    @FXML
    private TableColumn<AccountConference, Integer> reqUserIdTableColumn; // id user gui request

    @FXML
    private TableColumn<AccountConference, Integer> reqConferenceIdTableColumn; // id hoi nghi ma user dang ky

    @FXML
    private TableColumn<AccountConference, requestStatus> reqStatusTableColumn; // trang thai cua request (0:declined, 1:approved, 2:pending)

    @FXML
    private TextField reqUserDisplayNameTextField;

    @FXML
    private TextField reqConferenceNameTextField;

    // Tab 3
    @FXML
    private Tab usersTab; // tab quan ly users

    @FXML
    private GridPane usersGridPane;

    @FXML
    private TableView<Account> usersTable;

    // ObservableList
    private ObservableList<Account> userObservableList;

    @FXML
    private TableColumn<Account, Integer> userIdTableColumn;

    @FXML
    private TableColumn<Account, String> userDisplayNameTableColumn;

    @FXML
    private TableColumn<Account, String> userUsernameTableColumn;

    @FXML
    private TableColumn<Account, String> userPasswordTableColumn;

    @FXML
    private TableColumn<Account, accType> userTypeTableColumn;

    @FXML
    private TableColumn<Account, Boolean> userIsBlockedTableColumn;

    @FXML
    private GridPane userInfoFieldsGridPane;

    @FXML
    private CheckBox isAdminCheckBox;

    @FXML
    private CheckBox isBlockedCheckBox;

    @FXML
    private CheckBox allFilterCheckBox;

    @FXML
    private ListView<String> subscriberListView;
    private ObservableList<String> subscriberObservableList;

    @FXML
    private Label noSubscriberLabel;

    public ConferenceManagementController() {
    }

    public void initConferenceTable () {
        // get all locations
        List<Location> allLocations = LocationDAO.getInstance().getAll();
        List<String> allLocationAddress = new ArrayList<>();
        for (int i = 0 ; i < allLocations.size(); i++) {
            allLocationAddress.add(allLocations.get(i).getName());
        }
        locationAddressObservableList = FXCollections.observableList(allLocationAddress);
        this.conLocationComboBox.setItems(locationAddressObservableList);

        // conferenceViewTable data
        conferenceObservableList = FXCollections.observableList(ConferenceDAO.getInstance().getAll());

        idTableColumn.setCellValueFactory(new PropertyValueFactory<Conference, Integer>("id"));
        nameTableColumn.setCellValueFactory(new PropertyValueFactory<Conference, String>("name"));
        locationTableColumn.setCellValueFactory(new PropertyValueFactory<Conference, String>("location"));
        timeTableColumn.setCellValueFactory(new PropertyValueFactory<Conference, Timestamp>("time"));
        numOfParticipantsColumn.setCellValueFactory(new PropertyValueFactory<Conference, Integer>("numOfParticipants"));
//        capacityTableColumn.setCellValueFactory(new PropertyValueFactory<Conference, Integer>("capacity"));

        conferenceViewTable.setItems(conferenceObservableList);

        ObservableList<Conference> selectedItems = conferenceViewTable.getSelectionModel().getSelectedItems();

        selectedItems.addListener(new ListChangeListener<Conference>() {
            @Override
            public void onChanged(Change<? extends Conference> change) {
                if (change.getList().size() > 0) {
                    System.out.println("Selection changed: " + change.getList());
                    Conference selectedInfo = change.getList().get(0);
                    setConferenceInfoFromSelectedRow(selectedInfo);
                    goDetailConferenceButton.setDisable(false);

                    // set subscriber list view
                    subscriberObservableList = FXCollections.observableArrayList();
                    // get all subscribers' name
                    List<String> allSubscribersName = AccountConferenceDAO.getInstance().getSubscriberList(selectedInfo.getId());
                    subscriberObservableList.addAll(allSubscribersName);
                    subscriberListView.setItems(subscriberObservableList);

                    if (allSubscribersName.size() > 0) {
                        noSubscriberLabel.setVisible(false);
                    } else {
                        noSubscriberLabel.setVisible(true);
                    }
                }
            }
        });
    }

    public void initUserTable() {
        // usersTable data
        userObservableList = FXCollections.observableList(AccountDAO.getInstance().getAll());
        usersTable.setItems(userObservableList);
        allFilterCheckBox.setSelected(true);

        allFilterCheckBox.selectedProperty().addListener(
                (ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
                    isAdminCheckBox.setSelected(false);
                    isBlockedCheckBox.setSelected(false);

                    if (new_val) {
                        userObservableList = FXCollections.observableList(AccountDAO.getInstance().getAll());
                        usersTable.setItems(userObservableList);
                    } else {

                    }
                });

        //Setting action to check boxes
        isAdminCheckBox.selectedProperty().addListener(
                (ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
//                    if (isAdminCheckBox.isSelected()==true) {
//                        if (allFilterCheckBox.isSelected()==true) {
//                            isBlockedCheckBox.setSelected(false);
//                        }
//                    }

                    int allParam = allFilterCheckBox.isSelected() == true ? 1 : 0;
                    int typeParam = new_val == true ? 0: 1; // admin: type=0, user: type=1
                    int statusParam = isBlockedCheckBox.isSelected() == true ? 1 : 0;

//                    if (allParam==1) {
//                        userObservableList = FXCollections.observableList(AccountDAO.getInstance().getAll( typeParam, -1));
//                    } else {
//                        userObservableList = FXCollections.observableList(AccountDAO.getInstance().getAll( typeParam, statusParam));
//                    }

                    userObservableList = FXCollections.observableList(AccountDAO.getInstance().getAll( typeParam, statusParam));

                    usersTable.setItems(userObservableList);
                });

        isBlockedCheckBox.selectedProperty().addListener(
                (ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
                    int allParam = allFilterCheckBox.isSelected() == true ? 1 : 0;
                    int typeParam = isAdminCheckBox.isSelected() == true ? 0: 1;
                    int statusParam = new_val == true ? 1 : 0;

//                    if (allParam==1) {
//                        userObservableList = FXCollections.observableList(AccountDAO.getInstance().getAll( -1, statusParam));
//                    } else {
//                        userObservableList = FXCollections.observableList(AccountDAO.getInstance().getAll( typeParam, statusParam));
//                    }

                    userObservableList = FXCollections.observableList(AccountDAO.getInstance().getAll( typeParam, statusParam));

                    usersTable.setItems(userObservableList);
                });

        // USER: ID (editable=false)
        userIdTableColumn.setCellValueFactory(new PropertyValueFactory<Account, Integer>("id"));
        userIdTableColumn.setEditable(false);

        // USER: displayName
        userDisplayNameTableColumn.setCellValueFactory(new PropertyValueFactory<Account, String>("displayName"));
        userDisplayNameTableColumn.setCellFactory(TextFieldTableCell.<Account>forTableColumn());
        // Khi edit xong 1 ô ở cột Display name
        userDisplayNameTableColumn.setOnEditCommit((TableColumn.CellEditEvent<Account, String> event) -> {
            TablePosition<Account, String> pos = event.getTablePosition();

            String newDisplayName = event.getNewValue();

            int row = pos.getRow();
            Account account = event.getTableView().getItems().get(row);

            account.setDisplayName(newDisplayName);
        });

        // USER: Username
        userUsernameTableColumn.setCellValueFactory(new PropertyValueFactory<Account, String>("username"));
        userUsernameTableColumn.setCellFactory(TextFieldTableCell.<Account>forTableColumn());
        // Khi edit xong 1 ô ở cột Username
        userUsernameTableColumn.setOnEditCommit((TableColumn.CellEditEvent<Account, String> event) -> {
            TablePosition<Account, String> pos = event.getTablePosition();

            String newUsername = event.getNewValue();

            int row = pos.getRow();
            Account account = event.getTableView().getItems().get(row);

            account.setUsername(newUsername);
        });

        // USER: Password (editable=false)
        userPasswordTableColumn.setCellValueFactory(new PropertyValueFactory<Account, String>("password"));
        userPasswordTableColumn.setEditable(false);

        // USER: Type (comboBox)
        userTypeTableColumn.setCellValueFactory(new PropertyValueFactory<Account, accType>("type"));
        ObservableList<accType> typeList = FXCollections.observableArrayList(accType.values());
        userTypeTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Account, accType>, ObservableValue<accType>>() {

            @Override
            public ObservableValue<accType> call(TableColumn.CellDataFeatures<Account, accType> param) {
                Account account = param.getValue();
                // 0, 1
                int typeCode = account.getType();
                accType type = accType.getAccTypeByCode(typeCode);
                return new SimpleObjectProperty<accType>(type);
            }
        });

        userTypeTableColumn.setCellFactory(ComboBoxTableCell.forTableColumn(typeList));

        userTypeTableColumn.setOnEditCommit((TableColumn.CellEditEvent<Account, accType> event) -> {
            TablePosition<Account, accType> pos = event.getTablePosition();

            accType newType = event.getNewValue();

            int row = pos.getRow();
            Account account = event.getTableView().getItems().get(row);

            account.setType(newType.getCode());

            // update DB
            AccountDAO.getInstance().update(account.getId(), account);
        });

        // USER: blocked? (checkBox)
        userIsBlockedTableColumn.setCellValueFactory(new PropertyValueFactory<Account, Boolean>("blocked"));
        userIsBlockedTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Account, Boolean>, ObservableValue<Boolean>>() {

            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Account, Boolean> param) {
                Account account = param.getValue();

                SimpleBooleanProperty booleanProp = new SimpleBooleanProperty(account.getBlocked()==1);

                // Chú ý: isBlocked.setOnEditCommit(): Không làm việc với
                // CheckBoxTableCell.

                // Khi cột "isBlocked?" thay đổi
                booleanProp.addListener(new ChangeListener<Boolean>() {

                    @Override
                    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue,
                                        Boolean newValue) {
                        account.setBlocked(newValue==false?0:1);

                        // update DB
                        AccountDAO.getInstance().update(account.getId(), account);

                        // delete all requests have not occurred yet and made by this account
                        AccountConferenceDAO.getInstance().deleteRequestOfBlockedUserByUserId(account.getId());
                    }
                });
                return booleanProp;
            }
        });

        userIsBlockedTableColumn.setCellFactory(new Callback<TableColumn<Account, Boolean>, //
                TableCell<Account, Boolean>>() {
            @Override
            public TableCell<Account, Boolean> call(TableColumn<Account, Boolean> p) {
                CheckBoxTableCell<Account, Boolean> cell = new CheckBoxTableCell<Account, Boolean>();
                cell.setAlignment(Pos.CENTER);
                return cell;
            }
        });


        usersTable.setItems(userObservableList);

        usersTable.setEditable(true);
    }

    public void initRequestTable() {
        // requestTable data
        List<AccountConference> requestList = AccountConferenceDAO.getInstance().getAll();
        System.out.println("Request list size = : " + requestList.size());
        requestObservableList = FXCollections.observableList(requestList);

        reqRequestIdTableColumn.setCellValueFactory(new PropertyValueFactory<AccountConference, Integer>("id"));
        reqUserIdTableColumn.setCellValueFactory(new PropertyValueFactory<AccountConference, Integer>("idAccount"));
        reqConferenceIdTableColumn.setCellValueFactory(new PropertyValueFactory<AccountConference, Integer>("idConference"));

        // ====== set editable combobox for request status
        reqStatusTableColumn.setCellValueFactory(new PropertyValueFactory<AccountConference, requestStatus>("status"));
        reqStatusTableColumn.setEditable(true);
        ObservableList<requestStatus> requestStatusObservableList = FXCollections.observableArrayList(requestStatus.values());
        reqStatusTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<AccountConference, requestStatus>, ObservableValue<requestStatus>>() {

            @Override
            public ObservableValue<requestStatus> call(TableColumn.CellDataFeatures<AccountConference, requestStatus> param) {
                AccountConference request = param.getValue();
                // 0, 1, 2
                int reqStatusCode = request.getStatus();
                requestStatus reqStatus = requestStatus.getRequestStatusByCode(reqStatusCode);
                return new SimpleObjectProperty<requestStatus>(reqStatus);
            }
        });

        reqStatusTableColumn.setCellFactory(ComboBoxTableCell.forTableColumn(requestStatusObservableList));

        reqStatusTableColumn.setOnEditCommit((TableColumn.CellEditEvent<AccountConference, requestStatus> event) -> {
            TablePosition<AccountConference, requestStatus> pos = event.getTablePosition();

            requestStatus newRequestStatus = event.getNewValue();

            int row = pos.getRow();
            AccountConference request = event.getTableView().getItems().get(row);

            request.setStatus(newRequestStatus.getCode());

            // update DB
//            AccountDAO.getInstance().update(account.getId(), account);
            AccountConferenceDAO.getInstance().update(request.getId(),request);
        });
        //====================================

        requestTable.setItems(requestObservableList);

        ObservableList<AccountConference> selectedItems = requestTable.getSelectionModel().getSelectedItems();

        selectedItems.addListener(new ListChangeListener<AccountConference>() {
            @Override
            public void onChanged(Change<? extends AccountConference> change) {
                if (change.getList().size() > 0) {
                    System.out.println("Selection changed: " + change.getList());
                    AccountConference selectedInfo = change.getList().get(0);
                    setRequestInfoFromSelectedRow(selectedInfo);

                    // them ham load row detail ở đây

                }
            }

            private void setRequestInfoFromSelectedRow(AccountConference selectedInfo) {
                Account account = AccountDAO.getInstance().getById(selectedInfo.getIdAccount());
                Conference conference = ConferenceDAO.getInstance().getById(selectedInfo.getIdConference());

                reqUserDisplayNameTextField.setText(account.getDisplayName());
                reqConferenceNameTextField.setText(conference.getName());
            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initConferenceTable();

        initUserTable();

        initRequestTable();
    }

    public void setConferenceInfoFromSelectedRow(Conference conference) {
        this.conNameTextField.setText(conference.getName());


        Location location = LocationDAO.getInstance().getById(conference.getLocation());
        this.conLocationComboBox.getSelectionModel().select(location.getId());

        this.conTimeTextField.setText(conference.getTime().toString());
        this.conImageURLTextField.setText(conference.getImgUrl());

//        this.conCapacityTextField.setText(String.valueOf(conference.getCapacity()));
        this.conNumOfParticipantsTextField.setText(conference.getNumOfParticipants().toString());

        this.conShortDescriptionTextField.setText(conference.getShortDescription());
        this.conDetailDescriptionTextArea.setText(conference.getDetailDescription());
    }

    public void goDetailConference(ActionEvent actionEvent) {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Default/ConferenceDetail.fxml"));
        // set controller
        Conference selectedItem = conferenceViewTable.getSelectionModel().getSelectedItem();
        loader.setController(new ConferenceDetailController(selectedItem));

        try {
            Parent conferenceDetailParent = loader.load();
            Scene scene = new Scene(conferenceDetailParent);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateConference (ActionEvent actionEvent) throws ParseException, IOException {
        Conference selectedItem = conferenceViewTable.getSelectionModel().getSelectedItem();

        // Chọn trên table: để update - dùng SP
        if (selectedItem != null) {
            Conference conferenceToUpdate = new Conference();
            conferenceToUpdate.setId(selectedItem.getId());

            conferenceToUpdate.setName(conNameTextField.getText());
            conferenceToUpdate.setShortDescription(conShortDescriptionTextField.getText());
            conferenceToUpdate.setDetailDescription(conDetailDescriptionTextArea.getText());

            Location location = LocationDAO.getInstance().getLocationByName(this.conLocationComboBox.getSelectionModel().getSelectedItem());
//            Location location = this.conLocationComboBox.getSelectionModel().getSelectedItem();
            conferenceToUpdate.setLocation(location.getId());

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date date1 = dateFormat.parse(conTimeTextField.getText());
            Timestamp dateTimeStamp = new Timestamp(date1.getTime());
            conferenceToUpdate.setTime(dateTimeStamp);

            conferenceToUpdate.setImgUrl(conImageURLTextField.getText());
            conferenceToUpdate.setNumOfParticipants(Integer.parseInt(conNumOfParticipantsTextField.getText()));
//            conferenceToUpdate.setCapacity(Integer.parseInt(conCapacityTextField.getText()));

            System.out.println("Created a conference with updated info !");
            ConferenceDAO.getInstance().update(selectedItem.getId(), conferenceToUpdate);

            // update tableview
            conferenceObservableList.remove(conferenceViewTable.getSelectionModel().getSelectedItem());
            conferenceObservableList.add(conferenceToUpdate);
            conferenceViewTable.getSelectionModel().select(conferenceToUpdate);
        } else if (selectedItem == null) {
            // Không chọn trên table: Tạo mới một hội nghị
            Window owner = updateConferenceButton.getScene().getWindow();

            // Kiểm tra các field đã được nhập đủ chưa?
            if (conNameTextField.getText().trim().isEmpty()) {
                Controller.showAlert(Alert.AlertType.ERROR, owner, "Thông tin chưa hợp lệ",
                        "Vui lòng điền tên hội nghị!");
                return;
            }
            if (conLocationComboBox.getSelectionModel().getSelectedItem() == null) {
                Controller.showAlert(Alert.AlertType.ERROR, owner, "Thông tin chưa hợp lệ",
                        "Vui lòng nhập địa chỉ!");
                return;
            }
            if (conTimeTextField.getText().trim().isEmpty()) {
                Controller.showAlert(Alert.AlertType.ERROR, owner, "Thông tin chưa hợp lệ",
                        "Vui lòng nhập vào thời gian theo định dạng \"YYYY-MM-DD HH:MM:SS\"!");
                return;
            }
            if (conNumOfParticipantsTextField.getText().trim().isEmpty()) {
                Controller.showAlert(Alert.AlertType.ERROR, owner, "Thông tin chưa hợp lệ",
                        "Vui lòng nhập vào số lượng người tham dự tối đa của hội nghị!");
                return;
            }
            if (conImageURLTextField.getText().trim().isEmpty()) {
                Controller.showAlert(Alert.AlertType.ERROR, owner, "Thông tin chưa hợp lệ",
                        "Vui lòng nhập vào URL hình ảnh minh họa cho hội nghị!");
                return;
            }
            if (conShortDescriptionTextField.getText().trim().isEmpty()) {
                Controller.showAlert(Alert.AlertType.ERROR, owner, "Thông tin chưa hợp lệ",
                        "Vui lòng nhập vào mô tả (ngắn) cho hội nghị!");
                return;
            }
            if (conDetailDescriptionTextArea.getText().trim().isEmpty()) {
                Controller.showAlert(Alert.AlertType.ERROR, owner, "Thông tin chưa hợp lệ",
                        "Vui lòng nhập vào mô tả chi tiết cho hội nghị!");
                return;
            }

            // create TimeStamp instance from String
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date date1 = dateFormat.parse(conTimeTextField.getText());
            Timestamp dateTimeStamp = new Timestamp(date1.getTime());

            Location location = LocationDAO.getInstance().getLocationByName(this.conLocationComboBox.getSelectionModel().getSelectedItem());

            Conference newConference = new Conference(
                    ConferenceDAO.getInstance().getMaxId() + 1,
                    conNameTextField.getText(),
                    conShortDescriptionTextField.getText(),
                    conDetailDescriptionTextArea.getText(),
                    conImageURLTextField.getText(),
                    dateTimeStamp,
                    location.getId(),
                    0
            );

            // insert to DB
            ConferenceDAO.getInstance().insert(newConference);

            conferenceViewTable.getItems().add(newConference);
//        }
        }
    }

    public void logOut (ActionEvent actionEvent) throws IOException {
        Session.getInstance().cleanSession();

        FXMLLoader loginFormLoader = new FXMLLoader(getClass().getResource("/FXML/Default/LoginForm.fxml"));
        loginFormLoader.setController(new LoginController());
        Parent loginFormParent = loginFormLoader.load();;
        Stage loginStage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        Scene loginScene = new Scene(loginFormParent);
        loginStage.setScene(loginScene);
        loginStage.show();
    }



    // Clear selected conference in fields to create new conference
    public void clearConferenceSelection(ActionEvent actionEvent) {
        if (conferenceViewTable.getSelectionModel().getSelectedItem() != null) {
            conferenceViewTable.getSelectionModel().clearSelection();

            conNameTextField.clear();
            conLocationComboBox.getSelectionModel().clearSelection();
            conTimeTextField.clear();
//            conCapacityTextField.clear();
            conImageURLTextField.clear();
            conShortDescriptionTextField.clear();
            conDetailDescriptionTextArea.clear();

            //thay doi trang thai cac button
            goDetailConferenceButton.setDisable(true);
            updateConferenceButton.setText("Thêm");
        }
    }
}
