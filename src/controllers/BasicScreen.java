package controllers;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import models.Session;
import java.io.IOException;

public abstract class BasicScreen {

    protected Stage primaryStage;
    protected Session currentSession;

    public abstract void update();

    protected abstract void setLocale();

    public void initData(Stage primaryStage, Session session){
        this.primaryStage = primaryStage;
        this.currentSession = session;
        update();
    }

    protected void openWindow(String resourceName){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/" + resourceName + ".fxml"));
        try {
            Parent root = loader.load();
            BasicScreen screenController = loader.getController();
            screenController.initData(primaryStage, currentSession);
            Stage newWindow = new Stage();
            newWindow.addEventFilter(WindowEvent.ANY, e-> this.update() );
            newWindow.setScene(new Scene(root));
            newWindow.initOwner(primaryStage);
            newWindow.initModality(Modality.WINDOW_MODAL);
            newWindow.show();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
