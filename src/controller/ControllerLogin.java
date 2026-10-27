/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import views.*;
import dao.*;
import model.*;
import javax.swing.*;
import util.*;

/**
 *
 * @author User
 */

public class ControllerLogin {
    
    private UserDAO userDAO;
    private ViewLogin view;
     
    public ControllerLogin(ViewLogin view) {
        this.view = view;
        //creating a object for userDAO
        this.userDAO = new UserDAO();
        view.addLoginListener(e -> login());
    }
     
    public void login()
    {
        try {
            String username = view.getUsername().trim();
            String password = view.getPassword().trim();

            //usermname or password is empty
            if(username.equals("") || password.equals(""))
            {
                throw new CustomException("Login Failed!\nPlease fill in all fields");
            }
            //username and password is not empty 
            else
            {
                User user = userDAO.findByUsername(username.trim());
                //if there is not a user or password does not match
                if (user == null || !user.getPassword().equals(password))
                {
                    throw new CustomException("Login Failed!\nInvalid username or password");
                }
                //if threr is a user and password deos match
                else
                {
                    SessionManager.setCurrentUser(user);
                    view.dispose();
                    new ViewMain().setVisible(true);
                    JOptionPane.showMessageDialog(null,"Welcome back, " + user.getFirstName() + "!","Welcome to Pawlish",JOptionPane.INFORMATION_MESSAGE);

                }
            }
            
        } 
        catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }
    
}
