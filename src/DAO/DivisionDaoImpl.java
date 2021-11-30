package DAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Country;
import models.Division;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DivisionDaoImpl {
    public static ObservableList<Division> getAllDivisions() throws SQLException {
        ObservableList<Division> divisions = FXCollections.observableArrayList();
        DBConnection.makeConnection();
        String sqlStatement = "SELECT * FROM first_level_divisions";
        Query.makeQuery(sqlStatement);
        ResultSet countResults = Query.getResult();
        while (countResults.next()){
            int ID = countResults.getInt("Division_ID");
            String division = countResults.getString("Division");
            int FK = countResults.getInt("COUNTRY_ID");
            divisions.add(new Division(ID, division, FK));
        }
        DBConnection.closeConnection();
        return divisions;
    }

    public static ObservableList<Division> getDivisionsByCountry(Country country) throws SQLException {
        int countryID = country.getID();
        ObservableList<Division> divisions = FXCollections.observableArrayList();
        DBConnection.makeConnection();
        String sqlStatement = "SELECT * FROM first_level_divisions WHERE COUNTRY_ID = " + String.valueOf(countryID);
        Query.makeQuery(sqlStatement);
        ResultSet countResults = Query.getResult();
        while (countResults.next()){
            int ID = countResults.getInt("Division_ID");
            String division = countResults.getString("Division");
            int FK = countResults.getInt("COUNTRY_ID");
            divisions.add(new Division(ID, division, FK));
        }
        DBConnection.closeConnection();
        return divisions;
    }

    public static ObservableList<Division> getDivisionsByCountryName(String countryName) throws SQLException{
        String sqlStatement = "SELECT Country_ID from Countries WHERE Country LIKE '" + countryName + "'";
        DBConnection.makeConnection();
        Query.makeQuery(sqlStatement);
        Query.getResult().next();
        int countryID = Query.getResult().getInt("Country_ID");
        sqlStatement = "SELECT * FROM first_level_divisions WHERE COUNTRY_ID = " + String.valueOf(countryID);
        Query.makeQuery(sqlStatement);
        ResultSet countResults = Query.getResult();
        ObservableList<Division> divisions = FXCollections.observableArrayList();
        while (countResults.next()){
            int ID = countResults.getInt("Division_ID");
            String division = countResults.getString("Division");
            int FK = countResults.getInt("COUNTRY_ID");
            divisions.add(new Division(ID, division, FK));
        }
        DBConnection.closeConnection();
        return divisions;

    }
}

