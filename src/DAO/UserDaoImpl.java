package DAO;

import models.User;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * DAO class for User
 * Handles all SQL queries for users
 * @see models.User
 *
 */
public class UserDaoImpl {
    public static User getUserByUserName(String userName) throws SQLException {
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
