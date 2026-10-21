/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;
import java.sql.*;
/**
 *
 * @author User
 */
public class DBConnection {                                    // Singleton
    private static DBConnection instance;
    private static final String URL = "jdbc:mysql://localhost:3306/pawlish";
    private static final String USER = "root";
    private static final String PASS = "";

    public static synchronized DBConnection getInstance() {
        if (instance == null) //if there is no DBconnection created
        {
            instance = new DBConnection();
        }
        return instance;
    }
    public Connection getConnection(){
        try
        {
            Connection con = DriverManager.getConnection(URL, USER, PASS);
            return con;
        }
        catch(SQLException e)
        {
            e.printStackTrace();
           
        }
        return null;
    }
}
