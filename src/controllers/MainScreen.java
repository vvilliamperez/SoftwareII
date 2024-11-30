package controllers;

import DAO.AppointmentDaoImpl;
import DAO.CustomerDaoImpl;
import DAO.ReportDaoImpl;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import models.Appointment;
import models.Customer;
import models.Session;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


import static utils.AppointmentHelper.filterAppointmentsByDateWindow;
import static utils.SelectionWindowCalculator.calculateSelectionWindow;


/**
 * Class for the Main Screen
 * Displays appointmens by weekly or monthly selections in a table to select and edit or delete
 * Displays customers in a separate table to select and edit or delete
 * Allows user to select weekly or monthly view
 * Allows user to navigate through weeks or months
 */
public class MainScreen extends BasicScreen {

    @FXML
    private Button btnDelApt;

    @FXML
    private Button btnDeleteCustomer;

    @FXML
    private Button btnEditApt;

    @FXML
    private Button btnEditCustomer;

    @FXML
    private Button btnNewApt;

    @FXML
    private Button btnNewCustomer;

    @FXML
    private TableColumn<Customer, String> col2Address;

    @FXML
    private TableColumn<Customer, Integer> col2Division;

    @FXML
    private TableColumn<Customer, Integer> col2Id;

    @FXML
    private TableColumn<Customer, String> col2Name;

    @FXML
    private TableColumn<Customer, String> col2Postal;

    @FXML
    private TableColumn<Customer, String> col2Phone;

    @FXML
    private TableColumn<Appointment, Integer> colAptId;

    @FXML
    private TableColumn<Appointment, Integer> colContact;

    @FXML
    private TableColumn<Appointment, Integer> colCustID;

    @FXML
    private TableColumn<Appointment, String> colDesc;

    @FXML
    private TableColumn<Appointment, DateCell> colEnd;

    @FXML
    private TableColumn<Appointment, String> colLocation;

    @FXML
    private TableColumn<Appointment, Time> colStart;

    @FXML
    private TableColumn<Appointment, String> colTitle;

    @FXML
    private TableColumn<Appointment, String> colType;

    @FXML
    private TableColumn<Appointment, Integer> colUserId;

    @FXML
    private RadioButton radioBtnMonthly;

    @FXML
    private RadioButton radioBtnWeekly;

    @FXML
    private TableView<Appointment> tableApts;

    @FXML
    private TableView<Customer> tableCustomers;

    @FXML
    private Label textLocale;

    @FXML
    private Label textStatus;

    @FXML
    private Text timeclock;

    @FXML
    private Button btnNext;

    @FXML
    private Button btnPrev;

    @FXML
    private Label titleSection1;

    @FXML
    private Label titleSection2;

    @FXML
    private Label titleSection3;

    @FXML
    private Label titleSection4;

    @FXML
    private Label titleSelection5;

    @FXML
    private Font x1;

    @FXML
    private Color x2;

    @FXML
    private Font x3;

    @FXML
    private Color x4;

    @FXML
    private Font x5;

    @FXML
    private Font x51;

    @FXML
    private Color x6;

    @FXML
    private Text textOffset;

    @FXML
    private Text textSelectionWindowStart;

    @FXML
    private Text textSelectionWindowEnd;

    @FXML
    private Color x61;

    @FXML
    private ComboBox<String> cmbReportType;

    @FXML
    private Button btnRunReport;

    private ObservableList<Appointment> appointments;
    private ObservableList<Customer> customers;

    private int dateOffset = 0;

    private boolean checkedOnLogin = false;

    private Timestamp[] selectionWindow = new Timestamp[2];


    /**
     * Updates the screen
     */
    @Override
    public void update() {
        setLocale();
        textOffset.setText(String.valueOf(dateOffset));

        List<Appointment> appointments_data;
        List<Appointment> appointments_data_filtered;
        // Get all appointments and filter them by week or month
        try {
            appointments_data = AppointmentDaoImpl.getAppoitmentsByUser(currentSession.getCurrentUser());
            // Get current timestamp in UTC

            Timestamp nowTimestamp = Timestamp.from(Instant.now());
            String period_type = radioBtnWeekly.isSelected() ? "Week" : "Month";
            selectionWindow = calculateSelectionWindow(nowTimestamp, period_type, dateOffset);



            appointments_data_filtered = filterAppointmentsByDateWindow(appointments_data, selectionWindow);

            appointments = FXCollections.observableArrayList(appointments_data_filtered);
            tableApts.setItems(appointments);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Update selection windows
        LocalDateTime localDateTimeWindowStart = selectionWindow[0].toLocalDateTime();
        LocalDateTime localDateTimeWindowEnd = selectionWindow[1].toLocalDateTime();
        // Define the desired date format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        // Format the date
        String formattedWindowStart = localDateTimeWindowStart.format(formatter);
        String formattedWindowEnd = localDateTimeWindowEnd.format(formatter);

        textSelectionWindowStart.setText(formattedWindowStart);
        textSelectionWindowEnd .setText(formattedWindowEnd);


        // Get all customers
        try {
            List<Customer> customers_data = CustomerDaoImpl.getAllCustomers();
            customers = FXCollections.observableArrayList(customers_data);
            tableCustomers.setItems(customers);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!checkedOnLogin){
            checkAlarm();
            checkedOnLogin = true;
        }

    }

    /**
     * Checks for appointments within the next 15 minutes and displays an alert if any are found
     */
    private void checkAlarm() {

        ZonedDateTime today = LocalDateTime.now().atZone(ZoneId.systemDefault());

        boolean alarmed = false;
        List<Appointment> appointments_data;
        try {
            appointments_data = AppointmentDaoImpl.getAppoitmentsByUser(currentSession.getCurrentUser());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        for (Appointment e: appointments_data){
            // appointment data is in UTC. Convert to local time
            ZonedDateTime start = e.getTimeStart().toLocalDateTime().atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.systemDefault());

            if (start.isAfter(today) && start.isBefore(today.plusMinutes(15))){
                Alert alert = new Alert(Alert.AlertType.INFORMATION, currentSession.getString("alarmText") + "\n\n"
                        + String.valueOf(e.getTitle()) + "\n" + e.getTimeStart().toLocalDateTime().atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.systemDefault()) );
                alert.showAndWait();
                alarmed = true;
            }
        }
        if (!alarmed) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, currentSession.getString("noAppointments"));
            alert.showAndWait();
        }
    }

    /**
     * Sets the locale for the screen
     */
    @Override
    protected void setLocale() {
        Session s = currentSession;
        titleSection1.setText(s.getString("section1"));
        titleSection2.setText(s.getString("section2"));
        titleSection3.setText(s.getString("section3"));

        btnNewApt.setText(s.getString("new") + " " + s.getString("appointment"));
        btnEditApt.setText(s.getString("edit") + " " + s.getString("appointment"));
        btnDelApt.setText(s.getString("delete") + " " + s.getString("appointment"));

        radioBtnMonthly.setText(s.getString("monthly"));
        radioBtnWeekly.setText(s.getString("weekly"));

        btnNewCustomer.setText(s.getString("new") + " " + s.getString("customer"));
        btnEditCustomer.setText(s.getString("edit") + " " + s.getString("customer"));
        btnDeleteCustomer.setText(s.getString("delete") + " " + s.getString("customer"));

        colAptId.setText(s.getString("ID"));
        colTitle.setText(s.getString("title"));
        colDesc.setText(s.getString("description"));
        colLocation.setText(s.getString("location"));
        colContact.setText(s.getString("contact"));
        colType.setText(s.getString("type"));
        colStart.setText(s.getString("start"));
        colEnd.setText(s.getString("end"));
        colCustID.setText(s.getString("customer") + " " + s.getString("ID"));
        colUserId.setText(s.getString("user") + " " + s.getString("ID"));

        col2Id.setText(s.getString("ID"));
        col2Name.setText(s.getString("name"));
        col2Address.setText(s.getString("address"));
        col2Postal.setText(s.getString("postal"));
        col2Division.setText(s.getString("division"));

        textLocale.setText(s.getString("language"));
        textStatus.setText(s.getString("status") + s.getString("connected"));

        ArrayList<String> reportTypes = new ArrayList<>();
        reportTypes.add(currentSession.getString("appointmentReport"));
        reportTypes.add(currentSession.getString("contactReport"));
        reportTypes.add(currentSession.getString("customerReport"));

        cmbReportType.setItems(FXCollections.observableArrayList(reportTypes));
    }

    /**
     * Initializes the screen
     */
    @FXML
    public void initialize(){
        setListeners();
        setFactories();
        radioBtnWeekly.setSelected(true);
        radioBtnMonthly.setSelected(false);




    }

    /**
     * Sets the factories for the table columns
     * This uses a number of lambda expressions to set the cell Value Factories
     * acting on a cellData object, the lambda expressions return the value of the cell with the appropriate data type
     * and formatting.
     */
    private void setFactories() {
        colAptId.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getID()));
        colTitle.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getTitle()));
        colDesc.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getDescription()));
        colLocation.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getLocation()));
        colContact.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getContactID()));
        colType.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getType()));
        colCustID.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getCustID()));
        colUserId.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getUserID()));


        // Display the time in the local time zone
        // The time is stored in UTC

        colStart.setCellValueFactory(cellData -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            // Assuming the database stores UTC timestamps
            ZonedDateTime utcTime = cellData.getValue().getTimeStart().toLocalDateTime().atZone(ZoneId.of("UTC"));

            // Convert from UTC to local system time
            ZonedDateTime localTime = utcTime.withZoneSameInstant(ZoneId.systemDefault());

            // Format the local time for display
            String formattedTime = localTime.format(formatter);

            return new ReadOnlyObjectWrapper(formattedTime);
        });


        colEnd.setCellValueFactory(cellData -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            // Assuming the database stores UTC timestamps
            ZonedDateTime utcTime = cellData.getValue().getTimeEnd().toLocalDateTime().atZone(ZoneId.of("UTC"));

            // Convert from UTC to local system time
            ZonedDateTime localTime = utcTime.withZoneSameInstant(ZoneId.systemDefault());

            // Format the local time for display
            String formattedTime = localTime.format(formatter);

            return new ReadOnlyObjectWrapper(formattedTime);
        });

        col2Id.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getID()));
        col2Name.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getName()));
        col2Address.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getAddress()));
        col2Postal.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getPostal()));
        col2Phone.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getPhone()));
        col2Division.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getDivision()));
    }


    /**
     * Sets the listeners for the screen
     */
    private void setListeners() {

        btnNext.setOnAction(e -> {
            dateOffset++;
            update();
        });

        btnPrev.setOnAction(e -> {
            dateOffset--;
            update();
        });

        radioBtnMonthly.setOnAction(e -> {
            radioBtnMonthly.setSelected(true);
            radioBtnWeekly.setSelected(false);
            update();
        });

        radioBtnWeekly.setOnAction(e -> {
            radioBtnWeekly.setSelected(true);
            radioBtnMonthly.setSelected(false);
            update();
        });

        btnEditApt.setOnAction(actionEvent -> {
           currentSession.setCurrentAppointment(tableApts.getSelectionModel().getSelectedItem());
           openWindow("AppointmentScreen");
        });

        btnNewApt.setOnAction(actionEvent -> {
            currentSession.setCurrentAppointment(null);
            openWindow("AppointmentScreen");
        });

        btnDelApt.setOnAction(actionEvent -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.showAndWait();
            if (alert.getResult().equals(ButtonType.OK)){
                try {
                    AppointmentDaoImpl.delete(tableApts.getSelectionModel().getSelectedItem());
                    update();
                    alert = new Alert(Alert.AlertType.INFORMATION, currentSession.getString("deletionSuccessful"));
                    alert.showAndWait();
                }
                catch (SQLException e) {
                    alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
                    alert.showAndWait();
                    e.printStackTrace();
                }
            }
        });



        btnEditCustomer.setOnAction(actionEvent -> {
            currentSession.setCurrentCustomer(tableCustomers.getSelectionModel().getSelectedItem());
            openWindow("CustomerScreen");
        });

        btnNewCustomer.setOnAction(actionEvent -> {
            currentSession.setCurrentCustomer(null);
            openWindow("CustomerScreen");
        });

        btnDeleteCustomer.setOnAction(actionEvent -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.showAndWait();
            if (alert.getResult().equals(ButtonType.OK)){
                try {
                    CustomerDaoImpl.delete(tableCustomers.getSelectionModel().getSelectedItem());
                    update();
                    alert = new Alert(Alert.AlertType.INFORMATION, currentSession.getString("deletionSuccessful"));
                    alert.showAndWait();
                } catch (SQLException e) {
                    alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
                    alert.showAndWait();
                    e.printStackTrace();
                }
            }
        });
        
        btnRunReport.setOnAction(actionEvent -> {
            if (cmbReportType.getSelectionModel().getSelectedItem().equals(currentSession.getString("appointmentReport"))){
                runAppointmentReport();
            }
            else if (cmbReportType.getSelectionModel().getSelectedItem().equals(currentSession.getString("contactReport"))){
                runContactScheduleReport();
            }
            else if (cmbReportType.getSelectionModel().getSelectedItem().equals(currentSession.getString("customerReport"))){
                runCustomerOverviewReport();
            }
        });



    }

    /**
     * Runs report and creates dialogue box displaying the results
     */
    private void runAppointmentReport() {
        try {
            currentSession.setCurrentReport(ReportDaoImpl.appointmentReport());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        openWindow("ReportScreen");
    }

    private void runContactScheduleReport() {
        try {
            currentSession.setCurrentReport(ReportDaoImpl.contactScheduleReport());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        openWindow("ReportScreen");
    }


    private void runCustomerOverviewReport() {
        try {
            currentSession.setCurrentReport(ReportDaoImpl.customerOverviewReport());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        openWindow("ReportScreen");

    }

}
