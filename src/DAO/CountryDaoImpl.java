package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Country;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * DAO class for Countries
 */
public class CountryDaoImpl {
    public static ObservableList<Country> getAllCountries() throws SQLException {
        ObservableList<Country> countries = FXCollections.observableArrayList();
        DBConnection.makeConnection();
        String sqlStatement = "SELECT * FROM Countries";
        Query.makeQuery(sqlStatement);
        ResultSet countResults = Query.getResult();
        while (countResults.next()){
            int ID = countResults.getInt("Country_ID");
            String country = countResults.getString("Country");
            countries.add(new Country(ID, country));
        }
        return countries;
    }

}
