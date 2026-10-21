/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import views.*;
import dao.*;
import model.*;
import javax.swing.*;

/**
 *
 * @author User
 */

public class ControllerUser {
    
    private UserDAO userDAO;
    private ViewLogin view;
     
    public ControllerUser(ViewLogin view) {
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
                JOptionPane.showMessageDialog(null, "Waradine");
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Harine");
            }
                

            //SessionManager.setCurrentUser(user);
            view.dispose();
            new ViewMain().setVisible(true);
            

        } catch (/*InvalidInputException | AuthenticationException ex*/Exception ex) {
            //view.showError(ex.getMessage());
        //} catch (SQLException ex) {
          //  view.showError("System error. Please try again later.");
        //}
    }
    }
    
}
