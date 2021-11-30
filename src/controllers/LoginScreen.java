package controllers;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import models.Session;

/**
 * Class for the Login Screen
 * Handles username and password verification
 */
public class LoginScreen extends BasicScreen {
    @FXML
    private TextField tfUsername;

    @FXML
    private PasswordField tfPassword;

    @FXML
    private Text textLocale;

    @FXML
    private Button btnConnect;

    @FXML
    public void initialize(){

        btnConnect.setOnAction(event -> {
            try {
                currentSession.logUserIn(tfUsername.getText(), tfPassword.getText());
                openWindow("MainScreen");
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
                alert.show();
                e.printStackTrace();
            }
        });

    }

    @Override
    public void update() {
        tfUsername.clear();
        tfPassword.clear();
        setLocale();
    }

    protected void setLocale() {
        Session s = currentSession;
        primaryStage.setTitle(s.getString("loginTitle"));
        tfUsername.setPromptText(s.getString("unamePrompt"));
        tfPassword.setPromptText(s.getString("passPrompt"));
        textLocale.setText(s.getString("locale"));
        btnConnect.setText(s.getString("connect"));
    }


}
