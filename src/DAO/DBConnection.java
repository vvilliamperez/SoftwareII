package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Project: DAODemo2021
 * Package: sample.DAO
 * <p>
 * User: carolyn.sher
 * Date: 9/15/2021
 * Time: 9:52 AM
 * <p>
 * Created with IntelliJ IDEA
 * To change this template use File | Settings | File Templates.
 */

// TODO: CHANGE CODE
public class DBConnection {
    private static final String databaseName="client_schedule";
    private static final String DB_URL="jdbc:mysql://127.0.0.1:3306/"+databaseName;
    private static final String username="sqlUser";
    private static final String password="Passw0rd!";
    static Connection conn;
    public static void makeConnection() throws ClassNotFoundException, SQLException, Exception{
        conn=(Connection) DriverManager.getConnection(DB_URL,username,password);
    }
    public static void closeConnection() throws ClassNotFoundException,SQLException, Exception{
        conn.close();
    }

}
