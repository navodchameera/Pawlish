package dao;

import model.User;
import util.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 * @author User
 */
public class UserDAO {

    private Connection con;

    public UserDAO() {
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
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return null;
    }
    
    public User findById(int id) {
        

        try 
        {
            String sql = "SELECT * FROM `user` WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            //move the cursor to the result row (if there is one)
            if (rs.next()) {
                //a user with this id exists, build the object from the row
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
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        //no user found with this id (or an error occurred)
        return null;
    }

    
    
    
    
    // All users, oldest first.
    public List<User> findAll() {
        //creating a array list to get all users 
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM `user` ORDER BY id";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            //read all users line by line untill it ends 
            while (rs.next()) {
                //creating a user
                User user = new User(
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("firstName"),
                    rs.getString("lastName"),
                    rs.getString("Phone"),
                    rs.getString("Address"),
                    rs.getString("role")
                );
                //add created user to a arraylist
                users.add(user);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return users;
    }

    // Users whose username, first name or last name contains the keyword.
    public List<User> search(String keyword) {
        //creating array list to store seach results
        List<User> users = new ArrayList<>();
        

        try {
            String sql = "SELECT * FROM `user` WHERE username LIKE ? OR firstName LIKE ? OR lastName LIKE ? ORDER BY id";
            PreparedStatement ps = con.prepareStatement(sql);
            String like = "%" + keyword + "%";
            ps.setString(1, like);
            ps.setString(2, like);
            ps.setString(3, like);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                //create a user object to store search results 
                User user = new User(
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("firstName"),
                    rs.getString("lastName"),
                    rs.getString("Phone"),
                    rs.getString("Address"),
                    rs.getString("role")
                );
                //add created user to the array list
                users.add(user);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return users;
    }

    //checks if a username already exits or not
    public boolean usernameExists(String username) {
        
        try 
        {
            String sql = "SELECT COUNT(*) AS 'resultCount' FROM `user` WHERE username = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, username.trim());
            ResultSet rs = ps.executeQuery();
           
            
            if (rs.next()) {
                //if the count is more than zero that user name already exists
                if (rs.getInt("resultCount") > 0) {
                    return true;
                }
                //user name is available
                else
                {
                    return false;
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return false;
    }


    public boolean insert(User u) {
        try {
            String sql = "INSERT INTO `user` (username, password, firstName, lastName, Phone, Address, role) "
                       + "VALUES (?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, u.getUsername());
            ps.setString(2, u.getPassword());
            ps.setString(3, u.getFirstName());
            ps.setString(4, u.getLastName());
            ps.setString(5, u.getPhone());
            ps.setString(6, u.getAddress());
            ps.setString(7, u.getRole());

            int rowsAffected = ps.executeUpdate();

            //if one or more rows were inserted the user was saved
            if (rowsAffected > 0) {
                return true;
            }
            //user was not saved
            else
            {
                //no rows were inserted
                return false;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return false;
    }

    // Updates everything except the password.
    public boolean update(User u) 
    {
        try {
            String sql = "UPDATE user SET username = ?, firstName = ?, lastName = ?, Phone = ?, Address = ?, role = ? "
                   + "WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, u.getUsername());
            ps.setString(2, u.getFirstName());
            ps.setString(3, u.getLastName());
            ps.setString(4, u.getPhone());
            ps.setString(5, u.getAddress());
            ps.setString(6, u.getRole());
            ps.setInt(7, u.getId());

            int rowsAffected = ps.executeUpdate();

            //if one or more rows were updated the user details were saved
            if (rowsAffected > 0) {
                return true;
            }
            //user was not updated
            else
            {
                //no row matched this id, or the values were identical to what was already stored
                return false;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return false;
    }

    public boolean delete(int id) {
        
        
        try {
            //id one is system strating admin
            //that should not be able to delete 
            if(id == 1)
            {
                throw new CustomException("The main admin account cannot be deleted.");
            }

            //geting role of user that is being deleted
            String target = findById(id).getRole();
            //getting current user
            User currentUser = SessionManager.getCurrentUser();
            //if some one is deleting a admin account and its not the system main admin
            if(target.equals("ADMIN") && currentUser.getId() != 1)
            {
                throw new CustomException("Only the main admin can delete an admin account.");
            }
            
            //if current user deletes their own account
            if(currentUser.getId() == id)
            {
                throw new CustomException("You cannot delete your own account.");
            }
            
            
            //if every thing is ok
            String sql = "DELETE FROM `user` WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);

            int rowsAffected = ps.executeUpdate();

            //if one or more rows were deleted the user was removed
            if (rowsAffected > 0) {
                return true;
            }
            //user was not deleted
            else
            {
                //no user exists with this id
                return false;
            }
        } 
        catch (SQLException ex) 
        {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        catch (Exception ex)
        {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return false;
        
    }
}