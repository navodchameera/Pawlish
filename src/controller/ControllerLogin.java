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
        this.userDAO = new UserDAO();
        view.addLoginListener(e -> login());
    }
     
    public void login()
    {
        try {
            String username = view.getUsername();
            String password = view.getPassword();

            // validation
            //Validator.requireNotEmpty(username, "Username");
            //Validator.requireNotEmpty(password, "Password");

            //
            User user = userDAO.findByUsername(username.trim());
            if (user == null || !user.getPassword().equals(password))
            {
                //throw new AuthenticationException("Invalid username or password");
                JOptionPane.showMessageDialog(null, "Invalid username or password!", "Login Failed", JOptionPane.ERROR_MESSAGE);
            }
            else
            {
                SessionManager.setCurrentUser(user);
                view.dispose();
                new ViewMain().setVisible(true);
                JOptionPane.showMessageDialog(null,"Welcome back, " + user.getFirstName() + "!","Welcome to Pawlish",JOptionPane.INFORMATION_MESSAGE);

            }
            

        } catch (/*InvalidInputException | AuthenticationException ex*/Exception ex) {
            //view.showError(ex.getMessage());
        //} catch (SQLException ex) {
          //  view.showError("System error. Please try again later.");
        //}
    }
    }
    
}
