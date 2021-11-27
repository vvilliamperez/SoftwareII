package DAO;

import java.sql.Statement;

import java.sql.ResultSet;

import static DAO.DBConnection.conn;

/**
 * Project: DAODemo2021
 * Package: sample.DAO
 * <p>
 * User: carolyn.sher
 * Date: 9/15/2021
 * Time: 9:53 AM
 * <p>
 * Created with IntelliJ IDEA
 * To change this template use File | Settings | File Templates.
 */



// TODO: CHANGE CODE
public class Query {
    private static String query;
    private static Statement stmt;
    private static ResultSet result;

    public static void makeQuery(String q){
        query =q;
        try{
            stmt=conn.createStatement();
            // determine query execution
            if(query.toLowerCase().startsWith("select"))
                result=stmt.executeQuery(q);
            if(query.toLowerCase().startsWith("delete")||query.toLowerCase().startsWith("insert")||query.toLowerCase().startsWith("update"))
                stmt.executeUpdate(q);

        }
        catch(Exception ex){
            System.out.println("Error: "+ex.getMessage());
        }
    }
    public static ResultSet getResult(){
        return result;
    }
}
