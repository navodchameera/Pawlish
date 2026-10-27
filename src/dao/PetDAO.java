/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import model.Pet;
import util.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author User
 */
public class PetDAO 
{

    private Connection con;

    public PetDAO() 
    {
        con = DBConnection.getInstance().getConnection();
    }

    // All pets with their owner names, oldest first.
    public List<Pet> findAll() 
    {
        //creating an array list to get all pets
        List<Pet> pets = new ArrayList<>();
        String sql = "SELECT p.*, c.name AS ownerName FROM `pet` p "
                   + "JOIN `customer` c ON p.owner_id = c.id ORDER BY p.id";

        try 
        {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            //read all pets line by line until it ends
            while (rs.next()) 
            {
                //creating a pet
                Pet pet = new Pet(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("species"),
                    rs.getString("breed"),
                    rs.getDate("birthday"),
                    rs.getDouble("weight"),
                    rs.getInt("owner_id"),
                    rs.getString("ownerName")
                );
                //add created pet to the array list
                pets.add(pet);
            }
        } 
        catch (SQLException ex) 
        {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return pets;
    }

    // Pets whose name, species, breed or owner name contains the keyword.
    public List<Pet> search(String keyword) 
    {
        //creating array list to store search results
        List<Pet> pets = new ArrayList<>();

        try 
        {
            String sql = "SELECT p.*, c.name AS ownerName FROM `pet` p "
                       + "JOIN `customer` c ON p.owner_id = c.id "
                       + "WHERE p.name LIKE ? OR p.species LIKE ? OR p.breed LIKE ? OR c.name LIKE ? "
                       + "ORDER BY p.id";
            PreparedStatement ps = con.prepareStatement(sql);
            String like = "%" + keyword + "%";
            ps.setString(1, like);
            ps.setString(2, like);
            ps.setString(3, like);
            ps.setString(4, like);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) 
            {
                //create a pet object to store the search result
                Pet pet = new Pet(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("species"),
                    rs.getString("breed"),
                    rs.getDate("birthday"),
                    rs.getDouble("weight"),
                    rs.getInt("owner_id"),
                    rs.getString("ownerName")
                );
                //add created pet to the array list
                pets.add(pet);
            }
        } 
        catch (SQLException ex) 
        {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return pets;
    }

    public boolean insert(Pet p) 
    {
        try 
        {
            String sql = "INSERT INTO `pet` (name, species, breed, birthday, weight, owner_id) VALUES (?, ?, ?, ?, ?, ?)";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, p.getName());
            ps.setString(2, p.getSpecies());
            ps.setString(3, p.getBreed());
            ps.setDate(4, p.getBirthday());
            ps.setDouble(5, p.getWeight());
            ps.setInt(6, p.getOwnerId());

            int rowsAffected = ps.executeUpdate();

            //if one or more rows were inserted the pet was saved
            if (rowsAffected > 0) 
            {
                return true;
            }
            //pet was not saved
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

    public boolean update(Pet p) 
    {
        try 
        {
            String sql = "UPDATE `pet` SET name = ?, species = ?, breed = ?, birthday = ?, weight = ?, owner_id = ? WHERE id = ?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, p.getName());
            ps.setString(2, p.getSpecies());
            ps.setString(3, p.getBreed());
            ps.setDate(4, p.getBirthday());
            ps.setDouble(5, p.getWeight());
            ps.setInt(6, p.getOwnerId());
            ps.setInt(7, p.getId());

            int rowsAffected = ps.executeUpdate();

            //if one or more rows were updated the pet details were saved
            if (rowsAffected > 0) 
            {
                return true;
            }
            //pet was not updated
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
            String sql = "DELETE FROM `pet` WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);

            int rowsAffected = ps.executeUpdate();

            //if one or more rows were deleted the pet was removed
            if (rowsAffected > 0) 
            {
                return true;
            }
            //pet was not deleted
            else
            {
                //no pet exists with this id
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
