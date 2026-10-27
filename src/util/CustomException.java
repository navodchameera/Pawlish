/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import javax.swing.JOptionPane;


/**
 *
 * @author User
 */
public class CustomException extends Exception{
    
    
    public CustomException(String error){
        super(error);
        //JOptionPane.showMessageDialog(null, error, errorTitle, JOptionPane.ERROR_MESSAGE);
    }
    
    
}
