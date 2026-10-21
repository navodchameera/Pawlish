/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import views.*;
import dao.*;
import model.*;

/**
 *
 * @author User
 */

public class ControllerUser {
    
    private UserDAO userDAO;
    private ViewLogin view;
     
    public ControllerUser(ViewLogin view) {
        this.view = view;
        view.addLoginListener(e -> login());
    }
     
    public void login()
    {
        try {
            String username = view.getUsername();
            String password = view.getPassword();

            // validation
            Validator.requireNotEmpty(username, "Username");
            Validator.requireNotEmpty(password, "Password");

            //
            User user = dao.findByUsername(username.trim());
            if (user == null || !user.getPassword().equals(password))
                throw new AuthenticationException("Invalid username or password");

            SessionManager.setCurrentUser(user);
            view.dispose();
            new DashboardView().setVisible(true);

        } catch (InvalidInputException | AuthenticationException ex) {
            view.showError(ex.getMessage());
        } catch (SQLException ex) {
            view.showError("System error. Please try again later.");
        }
    }
    }
    
}
