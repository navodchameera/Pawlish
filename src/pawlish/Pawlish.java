/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package pawlish;

import views.*;
import controller.*;

/**
 *
 * @author User
 */
public class Pawlish {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        ViewLogin view = new ViewLogin();
        ControllerUser controller = new ControllerUser(view);
        view.setVisible(true);
    }
    
}
