import controllers.BasicScreen;
import controllers.LoginScreen;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import models.Session;
/**
 * Appointment System - APTSYS
 * Runs in English and French
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("./views/LoginScreen.fxml"));
        Parent root = loader.load();
        LoginScreen controller = loader.getController();
        controller.initData(primaryStage, new Session());
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
