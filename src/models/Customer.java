package models;
import javafx.beans.value.ObservableValue;

public class Customer {
    private int ID, division;
    private String name, address, phone, postal;

    public Customer(int custID, String name, String address, String postal, String phone, int divisionID) {
        this.ID = custID;
        this.name = name;
        this.address = address;
        this.postal = postal;
        this.phone = phone;
        this.division = divisionID;
    }


    public int getID() {
        return ID;
    }

    public String getPostal() {
        return postal;
    }

    public int getDivision() {
        return division;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }
}
