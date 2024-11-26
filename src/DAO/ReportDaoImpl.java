package DAO;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Report;
import utils.TimeHelper;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;

public class ReportDaoImpl {

    public static Report appointmentReport() throws SQLException {
        String sql_statement = ("SELECT \n" +
                "    type,\n" +
                "    DATE_FORMAT(start, '%Y-%m') AS month,\n" +
                "    COUNT(*) AS total_appointments\n" +
                "FROM \n" +
                "    appointments\n" +
                "GROUP BY \n" +
                "    type, \n" +
                "    DATE_FORMAT(start, '%Y-%m')\n" +
                "ORDER BY \n" +
                "    month, \n" +
                "    type;\n");
        DBConnection.makeConnection();
        Query.makeQuery(sql_statement);
        ResultSet countResults = Query.getResult();
        ResultSetMetaData metaData = countResults.getMetaData();


        // Add data to ObservableList
        int columnCount = metaData.getColumnCount();
        ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();
        while (countResults.next()) {
            ObservableList<String> row = FXCollections.observableArrayList();
            for (int i = 1; i <= columnCount; i++) {
                row.add(countResults.getString(i));
            }
            data.add(row);
        }

        // Add column names to ObservableList
        ObservableList<String> columnNames = FXCollections.observableArrayList();
        int columnNameCount = metaData.getColumnCount();
        for (int i = 1; i <= columnNameCount; i++) {
            columnNames.add(metaData.getColumnName(i));
        }
        Report report = new Report(data, columnNames);
        DBConnection.closeConnection();
        return report;
    }

    public static Report contactScheduleReport() throws SQLException {
        String sql_statement = ("SELECT \n" +
                "    c.Contact_Name AS Contact_Name,\n" +
                "    a.Appointment_ID,\n" +
                "    a.Title,\n" +
                "    a.Type,\n" +
                "    a.Description,\n" +
                "    a.Start,\n" +
                "    a.End,\n" +
                "    a.Customer_ID\n" +
                "FROM \n" +
                "    appointments a\n" +
                "JOIN \n" +
                "    contacts c\n" +
                "ON \n" +
                "    a.Contact_ID = c.Contact_ID\n" +
                "ORDER BY \n" +
                "    c.Contact_Name,        -- Groups by contact name\n" +
                "    a.Start;  -- Sorts each contact's schedule by start time\n");
        DBConnection.makeConnection();
        Query.makeQuery(sql_statement);
        ResultSet scheduleResults = Query.getResult();
        ResultSetMetaData metaData = scheduleResults.getMetaData();


        // Add data to ObservableList
        int columnCount = metaData.getColumnCount();
        ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();
        while (scheduleResults.next()) {
            // Convert UTC Timestamps to local Timestamps
            ObservableList<String> row = FXCollections.observableArrayList();
            for (int i = 1; i <= columnCount; i++) {
                String cell_data = scheduleResults.getString(i);
                if (i == 6 || i == 7) {
                    // Row 6 and 7 are the start and end timestamps
                    Timestamp utcTimestamp = scheduleResults.getTimestamp(i);
                    Timestamp localTimestamp = TimeHelper.utcTimestampToLocalTimestamp(utcTimestamp);
                    cell_data = localTimestamp.toString();
                }
                row.add(cell_data);
            }
            data.add(row);
        }

        // Add column names to ObservableList
        ObservableList<String> columnNames = FXCollections.observableArrayList();
        int columnNameCount = metaData.getColumnCount();
        for (int i = 1; i <= columnNameCount; i++) {
            columnNames.add(metaData.getColumnName(i));
        }
        Report report = new Report(data, columnNames);
        DBConnection.closeConnection();
        return report;
    }



    public static Report customerOverviewReport() throws SQLException {
        String sql_statement = ("SELECT \n" +
                "    c.Customer_ID,\n" +
                "    c.Customer_Name,\n" +
                "    c.Address,\n" +
                "    c.Postal_Code,\n" +
                "    c.Phone,\n" +
                "    d.Division AS Division_Name,\n" +
                "    co.Country AS Country_Name\n" +
                "FROM \n" +
                "    customers c\n" +
                "JOIN \n" +
                "    first_level_divisions d\n" +
                "ON \n" +
                "    c.Division_ID = d.Division_ID\n" +
                "JOIN \n" +
                "    countries co\n" +
                "ON \n" +
                "    d.COUNTRY_ID = co.Country_ID\n" +
                "ORDER BY \n" +
                "    co.Country, d.Division, c.Customer_Name;\n");
        DBConnection.makeConnection();
        Query.makeQuery(sql_statement);
        ResultSet customerResults = Query.getResult();
        ResultSetMetaData metaData = customerResults.getMetaData();

        // Add data to ObservableList
        int columnCount = metaData.getColumnCount();
        ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();
        while (customerResults.next()) {
            ObservableList<String> row = FXCollections.observableArrayList();
            for (int i = 1; i <= columnCount; i++) {
                row.add(customerResults.getString(i));
            }
            data.add(row);
        }

        // Add column names to ObservableList
        ObservableList<String> columnNames = FXCollections.observableArrayList();
        int columnNameCount = metaData.getColumnCount();
        for (int i = 1; i <= columnNameCount; i++) {
            columnNames.add(metaData.getColumnName(i));
        }
        Report report = new Report(data, columnNames);
        DBConnection.closeConnection();
        return report;
    }
}
