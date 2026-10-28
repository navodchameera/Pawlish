/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import model.Appointment;
import util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author User
 */
public class AppointmentDAO
{
    private Connection con;

    public AppointmentDAO()
    {
        con = DBConnection.getInstance().getConnection();
    }

    public List<Appointment> findAll()
    {
        //empty search gets all appointments
        return search("");
    }

    public List<Appointment> search(String keyword)
    {
        //creating list to store appointments
        List<Appointment> appointments = new ArrayList<>();

        try 
        {
            String sql = "SELECT a.*, p.name AS petName, "
                   + "s.name AS serviceName "
                   + "FROM appointment a "
                   + "JOIN pet p ON a.pet_id = p.id "
                   + "JOIN service s ON a.service_id = s.id "
                   + "WHERE p.name LIKE ? OR s.name LIKE ? "
                   + "OR a.status LIKE ? "
                   + "ORDER BY a.appointment_date DESC, "
                   + "a.appointment_time DESC, a.id DESC";
            
            PreparedStatement ps = con.prepareStatement(sql);
            
            String searchText = "%" + keyword + "%";

            ps.setString(1, searchText);
            ps.setString(2, searchText);
            ps.setString(3, searchText);

            try (ResultSet rs = ps.executeQuery())
            {
                //reading appointments one by one
                while (rs.next())
                {
                    Appointment a = new Appointment();

                    a.setId(rs.getInt("id"));
                    a.setPetId(rs.getInt("pet_id"));
                    a.setServiceId(rs.getInt("service_id"));
                    a.setGroomerId(rs.getInt("groomer_id"));
                    a.setDate(rs.getDate("appointment_date"));
                    a.setTime(rs.getTime("appointment_time"));
                    a.setStatus(rs.getString("status"));
                    a.setPetName(rs.getString("petName"));
                    a.setServiceName(rs.getString("serviceName"));

                    //adding appointment to the list
                    appointments.add(a);
                }
            }
        }
        catch (SQLException ex)
        {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }

        return appointments;
        
    }

    public boolean insert(Appointment a)
    {
        

        try 
        {
            String sql = "INSERT INTO appointment "
                   + "(pet_id, service_id, groomer_id, "
                   + "appointment_date, appointment_time, status) "
                   + "VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, a.getPetId());
            ps.setInt(2, a.getServiceId());
            ps.setInt(3, a.getGroomerId());
            ps.setDate(4, a.getDate());
            ps.setTime(5, a.getTime());
            ps.setString(6, a.getStatus());

            int rowsAffected = ps.executeUpdate();

            //if row inserted appointment was saved
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

    public boolean update(Appointment a)
    {
        

        try 
        {
            String sql = "UPDATE appointment SET pet_id = ?, "
                   + "service_id = ?, groomer_id = ?, "
                   + "appointment_date = ?, appointment_time = ?, "
                   + "status = ? WHERE id = ?";
            
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, a.getPetId());
            ps.setInt(2, a.getServiceId());
            ps.setInt(3, a.getGroomerId());
            ps.setDate(4, a.getDate());
            ps.setTime(5, a.getTime());
            ps.setString(6, a.getStatus());
            ps.setInt(7, a.getId());

            int rowsAffected = ps.executeUpdate();

            //if row updated appointment was saved
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
        

        try 
        {
            String sql = "DELETE FROM appointment WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
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
