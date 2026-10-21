/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import model.User;
import util.DBConnection;
import java.sql.*;

/**
 *
 * @author User
 */
public class UserDAO {
    
    private Connection con;
    
    public UserDAO()  
    {
        con = DBConnection.getInstance().getConnection();
    }
    
public User findByUsername(String username) {
    String sql = "SELECT * FROM `user` WHERE username = ?";

    try {
        
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setString(1, username);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            return new User(
                rs.getInt("id"),
                rs.getString("username"),
                rs.getString("password"),
                rs.getString("firstName"),
                rs.getString("lastName"),
                rs.getString("Phone"),
                rs.getString("Address"),
                rs.getString("role")
                
            );
        }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

        
      
    
}
