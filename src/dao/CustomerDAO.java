/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import model.Customer;
import util.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 * @author User
 */
public class CustomerDAO 
{

    private Connection con;

    public CustomerDAO() 
    {
        con = DBConnection.getInstance().getConnection();
    }

    public Customer findById(int id) 
    {
        try 
        {
            String sql = "SELECT * FROM customer WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            //move the cursor to the result row (if there is one)
            if (rs.next()) 
            {
                //a customer with this id exists, build the object from the row
                return new Customer(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("phone"),
                    rs.getString("email"),
                    rs.getString("address")
                );
            }
        } 
        catch (SQLException ex) 
        {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        //no customer found with this id (or an error occurred)
        return null;
    }

    // All customers, oldest first.
    public List<Customer> findAll() 
    {
        //creating an array list to get all customers
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT * FROM customer ORDER BY id";

        try 
        {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            //read all customers line by line until it ends
            while (rs.next()) 
            {
                //creating a customer
                Customer customer = new Customer(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("phone"),
                    rs.getString("email"),
                    rs.getString("address")
                );
                //add created customer to the array list
                customers.add(customer);
            }
        } 
        catch (SQLException ex) 
        {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return customers;
    }

    // Customers whose name, phone or email contains the keyword.
    public List<Customer> search(String keyword) 
    {
        //creating array list to store search results
        List<Customer> customers = new ArrayList<>();

        try 
        {
            String sql = "SELECT * FROM customer WHERE name LIKE ? OR phone LIKE ? OR email LIKE ? ORDER BY id";
            PreparedStatement ps = con.prepareStatement(sql);
            String like = "%" + keyword + "%";
            ps.setString(1, like);
            ps.setString(2, like);
            ps.setString(3, like);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) 
            {
                //create a customer object to store the search result
                Customer customer = new Customer(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("phone"),
                    rs.getString("email"),
                    rs.getString("address")
                );
                //add created customer to the array list
                customers.add(customer);
            }
        } 
        catch (SQLException ex) 
        {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return customers;
    }

    public boolean insert(Customer c) 
    {
        try 
        {
            String sql = "INSERT INTO customer (name, phone, email, address) VALUES (?, ?, ?, ?)";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, c.getName());
            ps.setString(2, c.getPhone());
            ps.setString(3, c.getEmail());
            ps.setString(4, c.getAddress());

            int rowsAffected = ps.executeUpdate();

            //if one or more rows were inserted the customer was saved
            if (rowsAffected > 0) 
            {
                return true;
            }
            //customer was not saved
            else
            {
                //no rows were inserted
                return false;
            }
        } 
        catch (SQLException ex) 
        {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return false;
    }

    public boolean update(Customer c) 
    {
        try 
        {
            String sql = "UPDATE customer SET name = ?, phone = ?, email = ?, address = ? WHERE id = ?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, c.getName());
            ps.setString(2, c.getPhone());
            ps.setString(3, c.getEmail());
            ps.setString(4, c.getAddress());
            ps.setInt(5, c.getId());

            int rowsAffected = ps.executeUpdate();

            //if one or more rows were updated the customer details were saved
            if (rowsAffected > 0) 
            {
                return true;
            }
            //customer was not updated
            else
            {
                //no row matched this id, or the values were identical to what was already stored
                return false;
            }
        } 
        catch (SQLException ex) 
        {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return false;
    }

    public boolean delete(int id) 
    {
        try 
        {
            String sql = "DELETE FROM customer WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);

            int rowsAffected = ps.executeUpdate();

            //if one or more rows were deleted the customer was removed
            if (rowsAffected > 0) 
            {
                return true;
            }
            //customer was not deleted
            else
            {
                //no customer exists with this id
                return false;
            }
        } 
        catch (SQLException ex) 
        {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return false;
    }
}
