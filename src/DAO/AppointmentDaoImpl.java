package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Appointment;
import models.Customer;
import models.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO class for Appointments
 */
public class AppointmentDaoImpl {
    public static void create(Appointment apt) throws SQLException {
        DBConnection.makeConnection();
        String title = apt.getTitle();
        String desc = apt.getDescription();
        String location = apt.getLocation();
        String type = apt.getType();
        Timestamp start = apt.getTimeStart();
        Timestamp end = apt.getTimeEnd();
        int custID = apt.getCustID();
        int userID = apt.getUserID();
        int contID = apt.getContactID();
        String sqlStatement = "INSERT INTO appointments (Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID) "
                            + "VALUES ('" + title + "', '" + desc + "', '" + location+ "', '" + type + "', '" + start
                            +  "', '" + end + "', '" + custID + "', '" + userID + "', '" + contID + "');";
        Query.makeQuery(sqlStatement);
        DBConnection.closeConnection();
    }

    public static void update(Appointment apt) throws SQLException {
        DBConnection.makeConnection();
        int ID = apt.getID();
        String title = apt.getTitle();
        String desc = apt.getDescription();
        String location = apt.getLocation();
        String type = apt.getType();
        Timestamp start = apt.getTimeStart();
        Timestamp end = apt.getTimeEnd();
        int custID = apt.getCustID();
        int userID = apt.getUserID();
        int contID = apt.getContactID();
        String sqlStatement = "UPDATE appointments SET "
                + "Title = '" + title + "', "
                + "Description = '" + desc + "', "
                + "Location = '" + location + "', "
                + "Type = '" + type + "', "
                + "Start = '" + start + "', "
                + "End = '" + end + "', "
                + "Customer_ID = '" + custID + "', "
                + "User_ID = '" + userID + "', "
                + "Contact_ID = '" + contID + "' "
                + "WHERE Appointment_ID = '" + ID + "';";
        System.out.println(sqlStatement);
        Query.makeQuery(sqlStatement);
        DBConnection.closeConnection();
    }


    public static List<Appointment> getAppoitmentsByUser(User user) throws SQLException {
        List<Appointment> appointments = new ArrayList<Appointment>();
        DBConnection.makeConnection();
        int UserID = user.getUID();
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
            Timestamp timeStart = result.getTimestamp("Start");
            Timestamp timeEnd = result.getTimestamp("End");
            Appointment apptResult = new Appointment(apptID, title, desc, location, type, timeStart, timeEnd, userID,  custID, contID);
            appointments.add(apptResult);
        }
        DBConnection.closeConnection();
        return appointments;
    }

    public static List<Appointment> getAppointmentsByCustomer(Customer customer) throws SQLException {
        List<Appointment> appointments = new ArrayList<Appointment>();
        DBConnection.makeConnection();
        int ID = customer.getID();
        String sqlStatement="select * FROM appointments where Customer_ID = " + ID;
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
            Timestamp timeStart = result.getTimestamp("Start");
            Timestamp timeEnd = result.getTimestamp("End");
            Appointment apptResult = new Appointment(apptID, title, desc, location, type, timeStart, timeEnd, userID,  custID, contID);
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
            int apptID = result.getInt("Appointment_ID");
            String title = result.getString("Title");
            String desc = result.getString("Description");
            String location = result.getString("Location");
            String type = result.getString("Type");
            int userID = result.getInt("User_ID");
            int custID = result.getInt("Customer_ID");
            int contID = result.getInt("Contact_ID");
            Timestamp timeStart = result.getTimestamp("Start");
            Timestamp timeEnd = result.getTimestamp("End");
            apptResult = new Appointment(apptID, title, desc, location, type, timeStart, timeEnd, userID,  custID, contID);
            return apptResult;
        }
        DBConnection.closeConnection();
        return null;
    }

    public static void deleteMany(List<Appointment> appointments) throws SQLException {
        String IDs = "";
        for (Appointment appointment : appointments) {
            IDs += "Appointment_ID LIKE " + String.valueOf(appointment.getID()) + " OR ";
        }
        IDs = IDs.substring(0, IDs.length() - 4 );
        String sqlStatement = "DELETE FROM appointments WHERE " + IDs;
        System.out.println(IDs);

        DBConnection.makeConnection();
        Query.makeQuery(sqlStatement);
        DBConnection.closeConnection();
    }

    public static void delete(Appointment appointment) throws SQLException {
        String sqlStatement = "DELETE FROM appointments WHERE Appointment_ID = '" + appointment.getID() + "';";
        DBConnection.makeConnection();
        Query.makeQuery(sqlStatement);
        DBConnection.closeConnection();
    }

}
