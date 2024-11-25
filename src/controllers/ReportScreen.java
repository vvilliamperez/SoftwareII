package controllers;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.*;

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
    private TableView<ObservableList<String>> tableReport;

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
     * Adds columns to the table view
     * @param tableView The table view to add columns to
     * @param columnNames The names of the columns
     */
    public void addColumns(TableView<ObservableList<String>> tableView, ObservableList<String> columnNames) {
        // Clear existing columns
        tableView.getColumns().clear();

        // Create a TableColumn for each column name
        for (int i = 0; i < columnNames.size(); i++) {
            final int columnIndex = i; // Final index for use in the lambda

            TableColumn<ObservableList<String>, String> column = new TableColumn<>(columnNames.get(i));
            column.setCellValueFactory(cellData -> {
                ObservableList<String> row = cellData.getValue();
                // Return the value at the current column index
                return new SimpleStringProperty(row.size() > columnIndex ? row.get(columnIndex) : "");
            });

            tableView.getColumns().add(column);
        }
    }


    /**
     * Populates the appointment data
     */
    private void populateTableData() {

        // If the report data is null, return
        if (reportData == null) {
            return;
        }

        // make a column for each field in the report
        // reportData is a resultSet
        ObservableList<ObservableList<String>> data = reportData.getReportData();
        ObservableList<String> columnNames = reportData.getColumnNames();
        addColumns(tableReport, columnNames);
        tableReport.setItems(FXCollections.observableArrayList(data));

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
