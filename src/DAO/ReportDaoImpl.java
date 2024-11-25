package DAO;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Report;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class ReportDaoImpl {

    public static Report appointmentReport() throws SQLException {
        DBConnection.makeConnection();
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
        Query.makeQuery(sql_statement);
        ResultSet countResults = Query.getResult();
        ResultSetMetaData metaData = countResults.getMetaData();

        int columnCount = metaData.getColumnCount();
        ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();
        while (countResults.next()) {
            ObservableList<String> row = FXCollections.observableArrayList();
            for (int i = 1; i <= columnCount; i++) {
                row.add(countResults.getString(i));
            }
            data.add(row);
        }
        ObservableList<String> columnNames = FXCollections.observableArrayList();
        int columnNameCount = metaData.getColumnCount();
        for (int i = 1; i <= columnNameCount; i++) {
            columnNames.add(metaData.getColumnName(i));
        }

        DBConnection.closeConnection();
        Report report = new Report(data, columnNames);
        return report;
    }

    public static void customerReport() throws SQLException {

    }

    public static void specialReport() throws SQLException {


    }







}
