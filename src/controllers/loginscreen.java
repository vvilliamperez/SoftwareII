package controllers;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import models.Session;

public class loginscreen {
    @FXML
    private TextField username;

    @FXML
    private TextField password;

    @FXML
    private Button btnLogin;

    @FXML
    private Button btnSignUp;

    
    @FXML
    public void initialize(){

        Session session = new Session() ;

        btnLogin.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //boolean loginSucceeded = session.Login(username.getText(), password.getText());

               /* if (loginSucceeded) {

                } else {*/


            }
        });

        btnSignUp.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });
    }

}
