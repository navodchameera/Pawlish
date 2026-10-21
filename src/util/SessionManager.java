/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;
import model.User;

/**
 *
 * @author User
 */
public class SessionManager {
    
    private static User currentUser;

    

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User user) {
        currentUser = user;
    }

    //check admin or not
    public static boolean isAdmin()
    {
        if(currentUser.getRole().equals("ADMIN"))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    //logging out 
    public static void logout()
    {
        //making current user null
        currentUser = null;
    }
    
}
