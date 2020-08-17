package ConferenceManagementSystem;

import ConferenceManagementSystem.Controllers.Default.HomeController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Admin/ConferenceManagement.fxml"));
//        loader.setController(new ConferenceManagementController());
//        Parent root = loader.load();
//        primaryStage.setTitle("Hệ thống quản lý tổ chức hội nghị");
//        primaryStage.setScene(new Scene(root));
//        primaryStage.show();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Default/HomeScreen.fxml"));
        loader.setController(new HomeController());
        Parent root = loader.load();
        primaryStage.setTitle("Hệ thống quản lý tổ chức hội nghị");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

}
