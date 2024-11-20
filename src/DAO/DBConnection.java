package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Database connection class
 * Holds strings for connection
 * Connects to the database
 * Closes the connection
 */
public class DBConnection {
    private static final String databaseName="client_schedule";
    private static final String DB_URL="jdbc:mysql://127.0.0.1:3306/"+databaseName;
    private static final String username="sqlUser";
    private static final String password="Passw0rd!";
    static Connection conn;
    public static void makeConnection() throws SQLException {
        conn= DriverManager.getConnection(DB_URL,username,password);
    }
    public static void closeConnection() throws SQLException {
        conn.close();
    }

}
