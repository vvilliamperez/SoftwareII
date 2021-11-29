package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Customer;
import java.sql.ResultSet;
import java.sql.SQLException;


public class CustDaoImpl {
    public static ObservableList<Customer> getAllCustomers() throws SQLException {
            DBConnection.makeConnection();
            String sqlStatement="select * FROM Customers";
            Query.makeQuery(sqlStatement);
            Customer customer;
            ResultSet result=Query.getResult();
            ObservableList<Customer> customers = FXCollections.observableArrayList();
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
}
