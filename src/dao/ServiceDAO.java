/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import model.Service;
import util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author User
 */
public class ServiceDAO
{
    private Connection con;

    public ServiceDAO()
    {
        con = DBConnection.getInstance().getConnection();
    }

    public List<Service> findAll()
    {
        //empty search gets all services
        return search("");
    }

    public List<Service> search(String keyword)
    {
        //creating array list to store services
        List<Service> services = new ArrayList<>();

        String sql = "SELECT * FROM service "
                   + "WHERE name LIKE ? OR description LIKE ? "
                   + "ORDER BY id";

        try (PreparedStatement ps = con.prepareStatement(sql))
        {
            String searchText = "%" + keyword + "%";

            ps.setString(1, searchText);
            ps.setString(2, searchText);

            try (ResultSet rs = ps.executeQuery())
            {
                //read services one by one
                while (rs.next())
                {
                    Service s = new Service(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getDouble("price"),
                        rs.getInt("duration")
                    );

                    //adding service to the list
                    services.add(s);
                }
            }
        }
        catch (SQLException ex)
        {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }

        return services;
    }

    public boolean insert(Service s)
    {
        String sql = "INSERT INTO service "
                   + "(name, description, price, duration) "
                   + "VALUES (?, ?, ?, ?)";

        try (PreparedStatement ps = con.prepareStatement(sql))
        {
            ps.setString(1, s.getName());
            ps.setString(2, s.getDescription());
            ps.setDouble(3, s.getPrice());
            ps.setInt(4, s.getDuration());

            int rowsAffected = ps.executeUpdate();

            //if row inserted service was saved
            if (rowsAffected > 0)
            {
                return true;
            }
        }
        catch (SQLException ex)
        {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }

        return false;
    }

    public boolean update(Service s)
    {
        String sql = "UPDATE service SET name = ?, description = ?, "
                   + "price = ?, duration = ? WHERE id = ?";

        try (PreparedStatement ps = con.prepareStatement(sql))
        {
            ps.setString(1, s.getName());
            ps.setString(2, s.getDescription());
            ps.setDouble(3, s.getPrice());
            ps.setInt(4, s.getDuration());
            ps.setInt(5, s.getId());

            int rowsAffected = ps.executeUpdate();

            //if row updated service was saved
            if (rowsAffected > 0)
            {
                return true;
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
        String sql = "DELETE FROM service WHERE id = ?";

        try (PreparedStatement ps = con.prepareStatement(sql))
        {
            ps.setInt(1, id);

            int rowsAffected = ps.executeUpdate();

            //if row deleted return true
            if (rowsAffected > 0)
            {
                return true;
            }
        }
        catch (SQLException ex)
        {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }

        return false;
    }
}
