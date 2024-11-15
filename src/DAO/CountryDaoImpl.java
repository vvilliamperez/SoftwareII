package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Country;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO class for Countries
 */
public class CountryDaoImpl {
    public static List<Country> getAllCountries() throws SQLException {
        List<Country> countries = new ArrayList<Country>();
        DBConnection.makeConnection();
        String sqlStatement = "SELECT * FROM countries";
        Query.makeQuery(sqlStatement);
        ResultSet countResults = Query.getResult();
        while (countResults.next()){
            int ID = countResults.getInt("Country_ID");
            String country = countResults.getString("Country");
            countries.add(new Country(ID, country));
        }
        return countries;
    }

    public static Country getCountryByID(int countryID) throws SQLException {
        DBConnection.makeConnection();
        String sqlStatement = "SELECT * FROM countries WHERE Country_ID = " + String.valueOf(countryID);
        Query.makeQuery(sqlStatement);
        ResultSet countResults = Query.getResult();
        countResults.next();
        int ID = countResults.getInt("Country_ID");
        String countryName= countResults.getString("Country");
        return new Country(ID, countryName);
    }

}
