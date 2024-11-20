package controllers;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import models.Session;
import java.io.IOException;


/**
 * Abstract class for screens to prevent code duplication
 * passes data using initData() by sending PrimaryStage and session object
 * requires use of an Update() method
 * handles opening new windows based on a String.
 */
public abstract class BasicScreen {

    protected Stage primaryStage;
    protected Session currentSession;

    /**
     * Updates the screen
     */
    public abstract void update();

    /**
     * Sets the locale
     */
    protected abstract void setLocale();

    /**
     * Initializes the screen
     * @param primaryStage The primary stage
     * @param session The session object
     */
    public void initData(Stage primaryStage, Session session){
        this.primaryStage = primaryStage;
        this.currentSession = session;
        update();
    }

    /**
     * Opens a new window
     * @param resourceName The resource name
     */
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
