package DAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Country;
import models.Division;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO class for Divisions
 */
public class DivisionDaoImpl {
    public static List<Division> getAllDivisions() throws SQLException {
        List<Division> divisions = new ArrayList<Division>();
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

    public static List<Division> getDivisionsByCountry(Country country) throws SQLException {
        int countryID = country.getID();
        List<Division> divisions = new ArrayList<Division>();
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

    public static List<Division> getDivisionsByCountryName(String countryName) throws SQLException{
        String sqlStatement = "SELECT Country_ID from countries WHERE Country LIKE '" + countryName + "'";
        DBConnection.makeConnection();
        Query.makeQuery(sqlStatement);
        Query.getResult().next();
        int countryID = Query.getResult().getInt("Country_ID");
        sqlStatement = "SELECT * FROM first_level_divisions WHERE COUNTRY_ID = " + String.valueOf(countryID);
        Query.makeQuery(sqlStatement);
        ResultSet countResults = Query.getResult();
        List<Division> divisions = new ArrayList<Division>();
        while (countResults.next()){
            int ID = countResults.getInt("Division_ID");
            String division = countResults.getString("Division");
            int FK = countResults.getInt("COUNTRY_ID");
            divisions.add(new Division(ID, division, FK));
        }
        DBConnection.closeConnection();
        return divisions;

    }

    public static Division getDivisionByID(int ID) throws SQLException {
        DBConnection.makeConnection();
        String sqlStatement = "SELECT * FROM first_level_divisions WHERE Division_ID = " + String.valueOf(ID);
        Query.makeQuery(sqlStatement);
        Query.getResult().next();
        int id = Query.getResult().getInt("Division_ID");
        String divisionName = Query.getResult().getString("Division");
        int countryId = Query.getResult().getInt("COUNTRY_ID");
        Division division = new Division(id, divisionName, countryId);
        DBConnection.closeConnection();
        return division;
    }
}

