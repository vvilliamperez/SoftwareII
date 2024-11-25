package models;

import java.sql.ResultSet;

public class Report {

    private ResultSet reportData;

    public Report(ResultSet reportData) {
        this.reportData = reportData;
    }

    public ResultSet getReportData() {
        return reportData;
    }

    public void setReportData(ResultSet reportData) {
        this.reportData = reportData;
    }

}


