package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.User;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * User is read only, no create update or delete
 */
// TODO: CHANGE CODE
public class UserDaoImpl {
    public static User getUser(String userName) throws SQLException {
        // type is name or phone, value is the name or the phone #
        DBConnection.makeConnection();
        String sqlStatement="select * FROM users WHERE User_Name  = '" + userName+ "'";
        //  String sqlStatement="select FROM address";
        Query.makeQuery(sqlStatement);
        User userResult;
        ResultSet result=Query.getResult();
        if(result.next()){
            int userid=result.getInt("User_ID");
            String usergName=result.getString("User_Name");
            String password=result.getString("Password");
            userResult= new User(userid, usergName, password);
            return userResult;
        }
        DBConnection.closeConnection();
        return null;
    }
}