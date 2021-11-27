package controllers;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import models.Session;

public class loginscreen {
    @FXML
    private TextField tfUsername;

    @FXML
    private TextField tfPassword;

    @FXML
    private Text textLocale;

    @FXML
    private Button btnConnect;

    @FXML
    public void initialize(){
        Session session = new Session() ;

        tfUsername.setPromptText(session.getString("unamePrompt"));
        tfPassword.setPromptText(session.getString("passPrompt"));
        textLocale.setText(session.getString("locale"));


        btnConnect.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    session.logUserIn(tfUsername.getText(), tfPassword.getText());
                } catch (Exception e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
                    alert.show();
                    e.printStackTrace();
                }

            }
        });

    }

}
