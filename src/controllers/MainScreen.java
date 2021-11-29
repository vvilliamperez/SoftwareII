package controllers;

import DAO.ApptDaoImpl;
import DAO.CustDaoImpl;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Callback;
import models.Appointment;
import models.Customer;
import models.Session;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.sql.Time;

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
    private Label titleSection1;

    @FXML
    private Label titleSection2;

    @FXML
    private Label titleSection3;

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
    private Color x61;

    private ObservableList<Appointment> appointments;
    private ObservableList<Customer> customers;



    @Override
    public void update() {
        setLocale();

        int userID = currentSession.getCurrentUser().getUID();
        try {
            appointments = ApptDaoImpl.getAppoitmentsByUserID(userID);
            tableApts.setItems(appointments);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            customers = CustDaoImpl.getAllCustomers();
            tableCustomers.setItems(customers);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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

        textLocale.setText(s.getString("locale"));
        textStatus.setText(s.getString("status") + s.getString("connected"));
    }

    @FXML
    public void initialize(){
        setListeners();
        setFactories();
    }

    private void setFactories() {
        colAptId.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getID()));
        colTitle.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getTitle()));
        colDesc.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getDescription()));
        colLocation.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getLocation()));
        colContact.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getContact()));
        colType.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getType()));
        colCustID.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getCustID()));
        colUserId.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getUserID()));

        col2Id.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getID()));
        col2Name.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getName()));
        col2Address.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getAddress()));
        col2Postal.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getPostal()));
        col2Phone.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getPhone()));
        col2Division.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getDivision()));
    }

    private void setListeners() {

    }


}
