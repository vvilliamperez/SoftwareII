package controllers;
import DAO.AppointmentDaoImpl;
import DAO.ContactDaoImpl;
import DAO.CustomerDaoImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

/**
 * Class for the Appointment Screen
 * Handles all the appointment updates and additions
 * Autopopulates fields and sets locale
 * Validates appointment times and checks for conflicts
 * @see Appointment
 */
public class ReportScreen extends BasicScreen {

    @FXML
    private Button btnClose;

    @FXML
    private TableView<Object> tableReport;

    @FXML
    private Text textTitle;


    private Report reportData;


    /**
     * Initializes the Appointment Screen
     */
    @FXML
    public void initialize(){
        setListeners();
    }

    /**
     * Sets the listeners for the Appointment Screen
     */
    private void setListeners() {

        btnClose.setOnAction(e -> {
            Stage stage = (Stage) btnClose.getScene().getWindow();
            stage.close();
        });
    }


    /**
     * Updates the screen
     */
    @Override
    public void update() {
        this.reportData = currentSession.getCurrentReport();
        populateTableData();
        setLocale();
    }

    /**
     * Populates the appointment data
     */
    private void populateTableData() {


    }


    /**
     * Sets the locale
     */
    @Override
    protected void setLocale() {
        Session s = currentSession;
        btnClose.setText(s.getString("close"));
        textTitle.setText(s.getString("report"));

    }
}
