package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Appointment;
import models.User;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * In this class we will have the Create, Read, Update and Delete files for the appointment table
 */
// TODO: CHANGE CODE
public class ApptDaoImpl {
    public static ObservableList<Appointment> getAppoitmentsByUserID(int UserID) throws SQLException {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        DBConnection.makeConnection();
        String sqlStatement="select * FROM appointments where User_ID = " + UserID;
        Query.makeQuery(sqlStatement);
        ResultSet result=Query.getResult();
        while(result.next()){
            int apptID=result.getInt("Appointment_ID");
            String title = result.getString("Title");
            String desc = result.getString("Description");
            String location = result.getString("Location");
            String type = result.getString("Type");
            int userID = result.getInt("User_ID");
            int custID = result.getInt("Customer_ID");
            int contID = result.getInt("Contact_ID");
            //TODO: TIME START AND END
            Appointment apptResult = new Appointment(apptID, title, desc, location, type, userID, custID, contID);
            appointments.add(apptResult);
        }
        DBConnection.closeConnection();
        return appointments;

    }
    public static Appointment getApptByID(int ApptID) throws SQLException {
        // type is name or phone, value is the name or the phone #

        DBConnection.makeConnection();
        String sqlStatement="select * FROM appointments WHERE Appointment_ID  = " + ApptID;
        Query.makeQuery(sqlStatement);
        Appointment apptResult;
        ResultSet result=Query.getResult();
        if(result.next()){
            int apptID=result.getInt("Appointment_ID");
            String title = result.getString("Title");
            String desc = result.getString("Description");
            String location = result.getString("Location");
            String type = result.getString("Type");
            //TODO: TIME START AND END
            int userID = result.getInt("User_ID");
            int custID = result.getInt("Customer_ID");
            int contID = result.getInt("Contact_ID");
            apptResult = new Appointment(apptID, title, desc, location, type, userID, custID, contID);
            return apptResult;
        }
        DBConnection.closeConnection();
        return null;
    }

}
