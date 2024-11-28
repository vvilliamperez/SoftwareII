package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Appointment;
import models.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO class for Customers
 * Handles all SQL queries for customers
 * @see models.Customer
 */
public class CustomerDaoImpl {
    public static List<Customer> getAllCustomers() throws SQLException {
        DBConnection.makeConnection();
        String sqlStatement="select * FROM customers";
        Query.makeQuery(sqlStatement);
        Customer customer;
        ResultSet result=Query.getResult();
        List<Customer> customers = new ArrayList<Customer>();
        while(result.next()){
            int custID = result.getInt("Customer_ID");
            String name = result.getString("Customer_Name");
            String address = result.getString("Address");
            String postal = result.getString("Postal_Code");
            String phone = result.getString("Phone");
            int divisionID = result.getInt("Division_ID");
            customer = new Customer(custID, name, address, postal, phone, divisionID);
            customers.add(customer);
        }
        DBConnection.closeConnection();
        return customers;
    }

    public static Customer getCustomerByID(int ID) throws SQLException {
        DBConnection.makeConnection();
        String sqlStatement="select * FROM customers WHERE Customer_ID = " + ID + ";";
        Query.makeQuery(sqlStatement);
        Customer customer = null;
        ResultSet result=Query.getResult();
        while(result.next()){
            int custID = result.getInt("Customer_ID");
            String name = result.getString("Customer_Name");
            String address = result.getString("Address");
            String postal = result.getString("Postal_Code");
            String phone = result.getString("Phone");
            int divisionID = result.getInt("Division_ID");
            customer = new Customer(custID, name, address, postal, phone, divisionID);
        }
        DBConnection.closeConnection();
        return customer;
    }

    public static Customer getCustomerByName(String name) throws SQLException {
        DBConnection.makeConnection();
        String sqlStatement="select * FROM customers WHERE Customer_Name = '" + name + "';";
        Query.makeQuery(sqlStatement);
        Customer customer = null;
        ResultSet result=Query.getResult();
        while(result.next()){
            int custID = result.getInt("Customer_ID");
            String address = result.getString("Address");
            String postal = result.getString("Postal_Code");
            String phone = result.getString("Phone");
            int divisionID = result.getInt("Division_ID");
            customer = new Customer(custID, name, address, postal, phone, divisionID);
        }
        DBConnection.closeConnection();
        return customer;
    }

    public static void create(Customer customer) throws SQLException {
        DBConnection.makeConnection();
        //TODO:handle ID
        String name = customer.getName();
        String addr = customer.getAddress();
        String post = customer.getPostal();
        String phone = customer.getPhone();
        int div = customer.getDivision();
        String sqlStatement="INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Division_ID) "
                           +"VALUES ('" + name + "', '" + addr + "', '" + post + "', '" + phone + "', '" + div + "');";

        Query.makeQuery(sqlStatement);
        DBConnection.closeConnection();
    }

    public static void update(Customer customer) throws SQLException {
        DBConnection.makeConnection();
        int ID = customer.getID();
        String name = customer.getName();
        String addr = customer.getAddress();
        String post = customer.getPostal();
        String phone = customer.getPhone();
        int div = customer.getDivision();
        String sqlStatement =
                "UPDATE customers SET "
                + " Customer_Name = '" + name + "',"
                + " Address = '" + addr + "',"
                + " Postal_Code = '" + post + "',"
                + " Phone = '" + phone + "',"
                + " Division_ID = '" + div + "' "
                + " WHERE Customer_ID = '" + ID + "';";
        Query.makeQuery(sqlStatement);
        System.out.println(sqlStatement);
        DBConnection.closeConnection();
    }

    public static void delete(Customer customer) throws SQLException {
        //first delete any appointments with customer ID
        List<Appointment> appointments = AppointmentDaoImpl.getAppointmentsByCustomer(customer);
        if (!appointments.isEmpty()) AppointmentDaoImpl.deleteMany(appointments);
        //Then delete Customer
        DBConnection.makeConnection();
        int ID = customer.getID();
        String sqlStatement = "DELETE FROM customers WHERE Customer_ID = " + ID + ";";
        System.out.println(sqlStatement);
        Query.makeQuery(sqlStatement);
        DBConnection.closeConnection();
    }


}
