package controllers;
import DAO.ApptDaoImpl;
import DAO.ContactDaoImpl;
import DAO.CustDaoImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import models.Appointment;
import models.Contact;
import models.Customer;
import models.Session;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class AppointmentScreen extends BasicScreen {

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnConfirm;

    @FXML
    private ComboBox<String> cmbContact;

    @FXML
    private ComboBox<String> cmbCustomerID;

    @FXML
    private ComboBox<String> cmbEndHr;

    @FXML
    private ComboBox<String> cmbEndMin;

    @FXML
    private ComboBox<String> cmbStartHr;

    @FXML
    private ComboBox<String> cmbStartMin;

    @FXML
    private DatePicker dateEnd;

    @FXML
    private DatePicker dateStart;

    @FXML
    private TextArea fcDescription;

    @FXML
    private TextField tfApptID;

    @FXML
    private TextField tfLocation;

    @FXML
    private TextField tfTitle;

    @FXML
    private TextField tfType;

    @FXML
    private TextField tfUserID;

    @FXML
    private Label titleSection;

    @FXML
    private Font x1;

    @FXML
    private Color x2;


    private Appointment currentApt;

    ObservableList<String> hours = FXCollections.observableArrayList();
    ObservableList<String> mins = FXCollections.observableArrayList();
    ObservableList<Contact> contacts;
    ObservableList<String> contactStrings = FXCollections.observableArrayList();
    ObservableList<Customer> customers;
    ObservableList<String> customerStrings = FXCollections.observableArrayList();

    @FXML
    public void initialize(){
        fillTimeSlots();
        setListeners();


    }

    private void setListeners() {

        btnConfirm.setOnAction( e-> {
            if(formFilled()) {
                sendToDatabase();
                Stage stage = (Stage) btnConfirm.getScene().getWindow();
                stage.close();
            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR, currentSession.getString("formNotFilled"));
                alert.showAndWait();
            }
        });

        btnCancel.setOnAction(e -> {
            Stage stage = (Stage) btnCancel.getScene().getWindow();
            stage.close();
        });




    }

    private void sendToDatabase() {
        int apptID = 9999;
        if(!tfApptID.getText().isEmpty())
            apptID = Integer.parseInt(tfApptID.getText());
        String title = tfTitle.getText();
        String desc = fcDescription.getText();
        String location = tfLocation.getText();
        String type = tfType.getText();

        ZonedDateTime localDateTimeStart, localDateTimeEnd;
        LocalDateTime serverDateTimeStart, serverDateTimeEnd;
        localDateTimeStart = dateStart.getValue().atTime(Integer.parseInt(cmbStartHr.getValue()), Integer.parseInt(cmbStartMin.getValue())).atZone(ZoneId.systemDefault());
        localDateTimeEnd = dateEnd.getValue().atTime(Integer.parseInt(cmbEndHr.getValue()), Integer.parseInt(cmbEndMin.getValue())).atZone(ZoneId.systemDefault());

        serverDateTimeStart = localDateTimeStart.toLocalDateTime();
        serverDateTimeEnd = localDateTimeEnd.toLocalDateTime();

        int custID = customers.get(cmbCustomerID.getSelectionModel().getSelectedIndex()).getID();
        int contID = contacts.get(cmbContact.getSelectionModel().getSelectedIndex()).getID();
        int userID = Integer.parseInt(tfUserID.getText());

        Appointment appointment = new Appointment(
                apptID,title,desc,location,type,
                Timestamp.valueOf(serverDateTimeStart),Timestamp.valueOf(serverDateTimeEnd),
                custID,userID,contID);

        try {
            if (currentApt == null) //new record
                ApptDaoImpl.create(appointment);
            else //update appoinment
                ApptDaoImpl.update(appointment);
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
            alert.show();
            e.printStackTrace();
        }
        return;
    }

    private boolean formFilled() {
        return  ( !tfTitle.getText().isEmpty() &&
                !tfUserID.getText().isEmpty() &&
                !tfLocation.getText().isEmpty() &&
                !tfType.getText().isEmpty() &&
                !fcDescription.getText().isEmpty() &&
                !(dateStart.getValue() == null) &&
                !(dateEnd.getValue() == null) &&
                !cmbStartHr.getSelectionModel().isEmpty() &&
                !cmbStartMin.getSelectionModel().isEmpty() &&
                !cmbEndHr.getSelectionModel().isEmpty()  &&
                !cmbEndMin.getSelectionModel().isEmpty()&&
                !cmbContact.getSelectionModel().isEmpty() &&
                !cmbCustomerID.getSelectionModel().isEmpty());
    }

    private void getCustomerIDData() {
        try {
            customers = CustDaoImpl.getAllCustomers();
            for (Customer customer: customers){
                customerStrings.add(customer.getName());
            }
            cmbCustomerID.setItems(customerStrings);
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
            alert.show();
            e.printStackTrace();
        }
    }

    private void getContactData() {
        try {
            contacts = ContactDaoImpl.getAllContacts();
            for (Contact contact: contacts){
                contactStrings.add(contact.getName());
            }
            cmbContact.setItems(contactStrings);
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
            alert.show();
            e.printStackTrace();
        }


    }

    private void fillTimeSlots() {
        hours.addAll("00", "01", "02", "03", "04", "05",
                          "06", "07", "08", "09", "10", "11",
                          "12", "13", "14", "15", "16", "17",
                          "18", "19", "20", "21", "22", "23");
        mins.addAll("00", "15", "30", "45");
        cmbStartHr.setItems(hours);
        cmbEndHr.setItems(hours);
        cmbStartMin.setItems(mins);
        cmbEndMin.setItems(mins);
    }

    @Override
    public void update() {
        this.currentApt = currentSession.getCurrentAppointment();
        tfUserID.setText(String.valueOf(currentSession.getCurrentUser().getUID()));
        getCustomerIDData();
        getContactData();
        if (currentApt != null) populateAptData();
        setLocale();
    }

    private void populateAptData() {
        Appointment apt = currentApt;
        tfApptID.setText(String.valueOf(apt.getID()));
        tfTitle.setText(apt.getTitle());
        tfLocation.setText(apt.getLocation());
        tfType.setText(apt.getType());
        tfUserID.setText(String.valueOf(apt.getUserID()));
        fcDescription.setText(apt.getDescription());

        ZonedDateTime dateTimeStart = apt.getLdtStart().atZone(ZoneId.systemDefault());
        ZonedDateTime dateTimeEnd = apt.getLdtEnd().atZone(ZoneId.systemDefault());

        dateStart.setValue(dateTimeStart.toLocalDate());
        dateEnd.setValue(dateTimeEnd.toLocalDate());
        String starthr, startmin, endhr, endmin;
        starthr = String.format("%02d", dateTimeStart.getHour());
        startmin = String.format("%02d", dateTimeStart.getMinute());
        endhr = String.format("%02d", dateTimeEnd.getHour());
        endmin = String.format("%02d", dateTimeEnd.getMinute());

        cmbStartHr.getSelectionModel().select(starthr);
        cmbStartMin.getSelectionModel().select(startmin);
        cmbEndHr.getSelectionModel().select(endhr);
        cmbEndMin.getSelectionModel().select(endmin);

        int custID = apt.getCustID();
        String custName = customers.filtered(c -> c.getID() == custID).get(0).getName();
        cmbCustomerID.getSelectionModel().select(custName);

        int contactID = apt.getContactID();
        String contName = contacts.filtered(c -> c.getID() == contactID).get(0).getName();
        cmbContact.getSelectionModel().select(contName);


    }

    @Override
    protected void setLocale() {
        Session s = currentSession;
        titleSection.setText(s.getString("appointment"));
        tfTitle.setPromptText(s.getString("title"));
        tfLocation.setPromptText(s.getString("location"));
        tfType.setPromptText(s.getString("type"));
        fcDescription.setPromptText(s.getString("description"));
        tfApptID.setPromptText(s.getString("appointment") + " " + s.getString("ID"));
        tfUserID.setPromptText(s.getString("user") + " " + s.getString("ID"));
        dateStart.setPromptText(s.getString("start"));
        dateEnd.setPromptText(s.getString("end"));
        cmbContact.setPromptText(s.getString("contact"));
        cmbCustomerID.setPromptText(s.getString("customer"));
        btnCancel.setText(s.getString("cancel"));
        btnConfirm.setText(s.getString("submit"));


    }
}
