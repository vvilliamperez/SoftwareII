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

import java.util.Locale;
import java.util.TimeZone;

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
    private Text textLanguage;

    @FXML
    private Text textLocation;

    @FXML
    private Text textTimeZone;

    @FXML
    private Button btnConnect;

    /**
     * Initializes the Login Screen
     */
    @FXML
    public void initialize(){

        btnConnect.setOnAction(event -> {
            try {
                currentSession.logUserIn(tfUsername.getText(), tfPassword.getText());
                openWindow("MainScreen");
                this.update();
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
                alert.show();
                e.printStackTrace();
            }
        });


    }

    /**
     * Updates the screen
     */
    @Override
    public void update() {
        tfUsername.clear();
        tfPassword.clear();
        setLocale();
    }

    /**
     * Sets the locale
     */
    protected void setLocale() {
        Session s = currentSession;
        primaryStage.setTitle(s.getString("loginTitle"));
        tfUsername.setPromptText(s.getString("unamePrompt"));
        tfPassword.setPromptText(s.getString("passPrompt"));
        textLanguage.setText(s.getString("language"));

        Locale locale = Locale.getDefault();
        String country = locale.getCountry(); // Country code (e.g., "US")
        String language = locale.getLanguage(); // Language code (e.g., "en")
        String displayName = locale.getDisplayName(); // Human-readable name (e.g., "English (United States)")

        System.out.println("System Country: " + country);
        System.out.println("System Language: " + language);
        System.out.println("Locale Display Name: " + displayName);
        textLocation.setText(displayName);

        TimeZone timeZone = TimeZone.getDefault();
        String timeZoneId = timeZone.getID();

        textTimeZone.setText(timeZoneId);



        btnConnect.setText(s.getString("connect"));
    }


}
