package DAO;


import models.Report;

import java.sql.ResultSet;
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
        Report report = new Report(countResults);
        DBConnection.closeConnection();
        return report;
    }

    public static void customerReport() throws SQLException {

    }

    public static void specialReport() throws SQLException {


    }







}
