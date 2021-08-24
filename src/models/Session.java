package models;
import utils.Constants;

import java.sql.*;
import java.util.Properties;

public class Session {
    private final Constants strings = new Constants();
    private enum ConnectionStatus  {
        DISCONNECTED, CONNECTED, ERROR;
    }

    private static ConnectionStatus status;
    private static User currentUser;
    private static Connection connection;
    private static Properties connectionProperties;
    private static Statement statement;

    public Session(){
        status = ConnectionStatus.DISCONNECTED;
        connectionProperties = new Properties();
        connectionProperties.put("user", strings.user);
        connectionProperties.put("password", strings.pass);
    }


    //User Login/Logout
    public boolean LogUserIn(String user, String pass) throws SQLException, ClassNotFoundException {
        if(status != ConnectionStatus.CONNECTED) connectSql();
        statement = connection.createStatement();
        String sql = "SELECT * FROM Users WHERE username like " + strings.user;
        ResultSet rs = statement.executeQuery(sql);
        if (rs.next()){
            currentUser = new User(rs.getInt("UID"));
            return true;
        } else {
            return false;
        }
    }

    public boolean LogUserOut() {
        try {
            disconnectSQL();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    //SQL
    private void connectSql() throws SQLException, ClassNotFoundException {
            Class.forName(strings.driver);
            connection = DriverManager.getConnection(strings.url, connectionProperties);
            status = ConnectionStatus.CONNECTED;
    }

    private void disconnectSQL() throws SQLException {
            connection.close();
            status = ConnectionStatus.DISCONNECTED;
    }




    //CRUD for Users
    private void createUser(){

    }
    private void readUser(){

    }
    private void updateUser(){

    }
    private void deleteUser(){

    }

    //CRUD for Appointments
    private void createAppointment(){

    }
    private void readAppointment(){

    }
    private void updateAppointment(){

    }
    private void deleteAppointment(){

    }


}
