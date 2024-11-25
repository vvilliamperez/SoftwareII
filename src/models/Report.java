package models;

import javafx.collections.ObservableList;

public class Report {

    private ObservableList<ObservableList<String>> reportData;
    private ObservableList<String> columnNames;

    public Report(ObservableList<ObservableList<String>> reportData, ObservableList<String> metaData) {
        this.reportData = reportData;
        this.columnNames = metaData;
    }

    public ObservableList<ObservableList<String>> getReportData() {
        return reportData;
    }

    public void setReportData(ObservableList<ObservableList<String>> reportData) {
        this.reportData = reportData;
    }

    public ObservableList<String> getColumnNames() {
        return columnNames;
    }

}


