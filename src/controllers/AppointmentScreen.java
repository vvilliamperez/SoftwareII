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
import javafx.stage.Stage;
import models.Appointment;
import models.Contact;
import models.Customer;
import models.Session;
import utils.AppointmentHelper;

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
 * @see models.Appointment
 */
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

    /**
     * Initializes the Appointment Screen
     */
    @FXML
    public void initialize(){
        fillTimeSlots();
        setListeners();
    }

    /**
     * Sets the listeners for the Appointment Screen
     */
    private void setListeners() {

        btnConfirm.setOnAction( e-> {
            if(formFilled() && validAppointmentTime() && notConflictingAppointment()) {
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

    /**
     * Checks if the appointment time is valid
     * @return True if the appointment time is valid
     */
    private boolean notConflictingAppointment() {
        // Check if appointment time for customer is already taken
        ZonedDateTime zonedDateTimeStart, zonedDateTimeEnd;
        zonedDateTimeStart = dateStart.getValue().atTime(Integer.parseInt(cmbStartHr.getValue()), Integer.parseInt(cmbStartMin.getValue())).atZone(ZoneId.systemDefault());
        zonedDateTimeEnd = dateEnd.getValue().atTime(Integer.parseInt(cmbEndHr.getValue()), Integer.parseInt(cmbEndMin.getValue())).atZone(ZoneId.systemDefault());
        ZonedDateTime utcStart = zonedDateTimeStart.withZoneSameInstant(ZoneId.of("UTC"));
        ZonedDateTime utcEnd = zonedDateTimeEnd.withZoneSameInstant(ZoneId.of("UTC"));


        // Check if appointment time for customer is already taken
        LocalDateTime currentStart = utcStart.toLocalDateTime();
        LocalDateTime currentEnd = utcEnd.toLocalDateTime();
        Customer customer = customers.get(cmbCustomerID.getSelectionModel().getSelectedIndex());
        try {
            List<Appointment> appointments_for_customer = AppointmentDaoImpl.getAppointmentsByCustomer(customer);
            for (Appointment existing_appointment: appointments_for_customer){
                if (currentApt != null && existing_appointment.getID() == currentApt.getID())
                    continue;
                // If the start or end time is within an existing appointment, it's invalid
                LocalDateTime start_and_end[];
                start_and_end = AppointmentHelper.getUTCLocalDateTimeStartAndEnd(existing_appointment);
                LocalDateTime existing_start = start_and_end[0];
                LocalDateTime existing_end = start_and_end[1];

                boolean time_is_the_same = (currentStart.equals(existing_start) || currentEnd.equals(existing_end));
                boolean time_is_within = (currentStart.isAfter(existing_start) && currentStart.isBefore(existing_end)) ||
                        (currentEnd.isAfter(existing_start) && currentEnd.isBefore(existing_end));

                if (time_is_within || time_is_the_same )
                {
                    Alert alert = new Alert(Alert.AlertType.ERROR, currentSession.getString("appointmentConflictCustomer"));
                    alert.showAndWait();
                    return false;
                }
            }

            // Check if appointment time for user is already taken
            List<Appointment> appointments_for_user = AppointmentDaoImpl.getAppoitmentsByUser(currentSession.getCurrentUser());
            for (Appointment existing_appoitnemnt: appointments_for_user ){
                LocalDateTime start_and_end[];
                start_and_end = AppointmentHelper.getUTCLocalDateTimeStartAndEnd(existing_appoitnemnt);
                LocalDateTime existing_start = start_and_end[0];
                LocalDateTime existing_end = start_and_end[1];

                if (currentApt != null && existing_appoitnemnt.getID() == currentApt.getID())
                    continue;

                boolean time_is_the_same = (currentStart.equals(existing_start) || currentEnd.equals(existing_end));
                boolean time_is_within = (currentStart.isAfter(existing_start) && currentStart.isBefore(existing_end)) ||
                        (currentEnd.isAfter(existing_start) && currentEnd.isBefore(existing_end));

                if (time_is_within || time_is_the_same ) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, currentSession.getString("appointmentConflictUser"));
                    alert.showAndWait();
                    return false;
                }

            }
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
            alert.show();
            e.printStackTrace();
        }
        return true;
    }

    /**
     * Checks if the appointment time is valid
     * @return True if the appointment time is valid
     */
    private boolean validAppointmentTime() {
        // Time must be within the business hours of 8am to 10pm EST
        ZonedDateTime zonedDateTimeStart, zonedDateTimeEnd;

        zonedDateTimeStart = dateStart.getValue().atTime(Integer.parseInt(cmbStartHr.getValue()), Integer.parseInt(cmbStartMin.getValue())).atZone(ZoneId.systemDefault());
        zonedDateTimeEnd = dateEnd.getValue().atTime(Integer.parseInt(cmbEndHr.getValue()), Integer.parseInt(cmbEndMin.getValue())).atZone(ZoneId.systemDefault());
        ZonedDateTime utcStart = zonedDateTimeStart.withZoneSameInstant(ZoneId.of("UTC"));
        ZonedDateTime utcEnd = zonedDateTimeEnd.withZoneSameInstant(ZoneId.of("UTC"));

        if (utcStart.getHour() < 12 || utcEnd.getHour() > 22) {
            // 12 UTC is 8am EST, 22 UTC is 10pm EST
            Alert alert = new Alert(Alert.AlertType.ERROR, currentSession.getString("appointmentOutsideBusinessHours"));
            alert.showAndWait();
            return false;
        }


        return true;
    }

    /**
     * Sends the appointment data to the database
     */
    private void sendToDatabase() {
        int apptID = 9999;
        if(!tfApptID.getText().isEmpty())
            apptID = Integer.parseInt(tfApptID.getText());
        String title = tfTitle.getText();
        String desc = fcDescription.getText();
        String location = tfLocation.getText();
        String type = tfType.getText();


        // All writes to the database must be in UTC time
        ZonedDateTime zonedDateTimeStart, zonedDateTimeEnd;
        zonedDateTimeStart = dateStart.getValue().atTime(Integer.parseInt(cmbStartHr.getValue()), Integer.parseInt(cmbStartMin.getValue())).atZone(ZoneId.systemDefault());
        zonedDateTimeEnd = dateEnd.getValue().atTime(Integer.parseInt(cmbEndHr.getValue()), Integer.parseInt(cmbEndMin.getValue())).atZone(ZoneId.systemDefault());
        // Log out zonedDateTimeStart and zonedDateTimeEnd
        //System.out.println("Start: " + zonedDateTimeStart.toString());
        //System.out.println("End: " + zonedDateTimeEnd.toString());


        ZonedDateTime utcStart = zonedDateTimeStart.withZoneSameInstant(ZoneId.of("UTC"));
        ZonedDateTime utcEnd = zonedDateTimeEnd.withZoneSameInstant(ZoneId.of("UTC"));

        int custID = customers.get(cmbCustomerID.getSelectionModel().getSelectedIndex()).getID();
        int contID = contacts.get(cmbContact.getSelectionModel().getSelectedIndex()).getID();
        int userID = Integer.parseInt(tfUserID.getText());

        Appointment appointment = new Appointment(
                apptID,title,desc,location,type,
                Timestamp.valueOf(utcStart.toLocalDateTime()),Timestamp.valueOf(utcEnd.toLocalDateTime()),
                custID,userID,contID);

        try {
            if (currentApt == null) //new record
                AppointmentDaoImpl.create(appointment);
            else //update appoinment
                AppointmentDaoImpl.update(appointment);
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
            alert.show();
            e.printStackTrace();
        }
    }

    /**
     * Checks if the form is filled
     * @return boolean
     */
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

    /**
     * Gets the customer ID data
     */
    private void getCustomerIDData() {
        try {
            List<Customer> customersData = CustomerDaoImpl.getAllCustomers();
            customers = FXCollections.observableArrayList(customersData);
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

    /**
     * Gets the contact data
     */
    private void getContactData() {
        try {
            List<Contact> contactsData = ContactDaoImpl.getAllContacts();
            contacts = FXCollections.observableArrayList(contactsData);
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

    /**
     * Fills the time slots
     */
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

    /**
     * Updates the screen
     */
    @Override
    public void update() {
        this.currentApt = currentSession.getCurrentAppointment();
        tfUserID.setText(String.valueOf(currentSession.getCurrentUser().getUID()));
        getCustomerIDData();
        getContactData();
        if (currentApt != null) populateAptData();
        setLocale();
    }

    /**
     * Populates the appointment data
     */
    private void populateAptData() {
        Appointment apt = currentApt;
        tfApptID.setText(String.valueOf(apt.getID()));
        tfTitle.setText(apt.getTitle());
        tfLocation.setText(apt.getLocation());
        tfType.setText(apt.getType());
        tfUserID.setText(String.valueOf(apt.getUserID()));
        fcDescription.setText(apt.getDescription());

        // Data from apt is a Timestamp in UTC. Read it in as a ZonedDateTime and offset it to the local time zone
        ZonedDateTime dateTimeStart = apt.getTimeStart().toLocalDateTime().atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.systemDefault());
        ZonedDateTime dateTimeEnd = apt.getTimeEnd().toLocalDateTime().atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.systemDefault());

        // Fill in the date pickers
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




    /**
     * Sets the locale
     */
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
