package controllers;

import DAO.CountryDaoImpl;
import DAO.CustDaoImpl;
import DAO.DivisionDaoImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.Country;
import models.Customer;
import models.Division;

import java.sql.SQLException;

public class CustomerScreen extends BasicScreen {

    @FXML
    private ComboBox<String> cmbCountry;

    @FXML
    private ComboBox<String> cmbState;

    @FXML
    private TextField tfAddress;

    @FXML
    private TextField tfCustID;

    @FXML
    private TextField tfName;

    @FXML
    private TextField tfPhone;

    @FXML
    private TextField tfPostal;

    @FXML
    private Text titleSection;

    @FXML
    private Button btnConfirm;

    private Customer currentCustomer;

    ObservableList<String> countriesStrings;
    ObservableList<Country> countriesList;

    ObservableList<Division> divisionsList;

    @FXML
    public void initialize(){
        getLocationData();
        setListeners();
    }

    private void setListeners(){
        cmbCountry.getSelectionModel().selectedItemProperty().addListener(
                (options, oldValue, newValue) -> {getDivisionData(newValue);});

        btnConfirm.setOnAction(e ->
        {
            if (formFilled()) {
                sendToDatabase();
                Stage stage = (Stage) btnConfirm.getScene().getWindow();
                stage.close();
            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR, currentSession.getString("formNotFilled"));
                alert.showAndWait();
            }

        });
    }



    private void sendToDatabase() {
        String name = tfName.getText();
        String addr = tfAddress.getText();
        String post = tfPostal.getText();
        String phone = tfPhone.getText();
        int customerID = 9999;
        if (!tfCustID.getText().isEmpty())
            customerID = Integer.parseInt(tfCustID.getText());
        int divID = divisionsList.get(cmbState.getSelectionModel().getSelectedIndex()).getID();

        Customer customer = new Customer(customerID, name, addr, post, phone, divID);
        try {
        if (currentCustomer == null) //new record
            CustDaoImpl.create(customer);
        else  //update existing record
            CustDaoImpl.update(customer);
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
            alert.show();
            e.printStackTrace();
        }
    }

    private boolean formFilled() {
        return (!tfAddress.getText().isEmpty() &&
                !tfPostal.getText().isEmpty() &&
                !tfPhone.getText().isEmpty() &&
                !tfName.getText().isEmpty() &&
                !cmbCountry.getSelectionModel().isEmpty() &&
                !cmbState.getSelectionModel().isEmpty() );
    }

    private void getDivisionData(String countryName) {
        try {
            divisionsList = DivisionDaoImpl.getDivisionsByCountryName(countryName);
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
            alert.show();
            e.printStackTrace();
        }
        ObservableList<String> divisionStrings = FXCollections.observableArrayList();
        for (Division division : divisionsList) {
            divisionStrings.add(division.getName());
        }
        cmbState.setItems(divisionStrings);
        cmbState.setDisable(false);
    }


    @Override
    public void update() {
        this.currentCustomer = currentSession.getCurrentCustomer();
        if (currentCustomer != null) populateCustomerData();
    }

    private void populateCustomerData() {
        Customer cc = currentCustomer;
        tfAddress.setText(cc.getAddress());
        tfCustID.setText(String.valueOf(cc.getID()));
        tfName.setText(cc.getName());
        tfPhone.setText(cc.getPhone());
        tfPostal.setText(cc.getPostal());

        //Auto fill customer location data
    }

    private void getLocationData() {
        try {
            countriesList = CountryDaoImpl.getAllCountries();
            countriesStrings = FXCollections.observableArrayList();
            for (Country country: countriesList){
                countriesStrings.add(country.getCountry());
            }
            cmbCountry.setItems(countriesStrings);
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
            alert.show();
            e.printStackTrace();
        }
    }

    @Override
    protected void setLocale() {

    }
}