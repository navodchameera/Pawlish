/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author User
 */


public class DashboardDAO
{
    private Connection con;

    public DashboardDAO()
    {
        con = DBConnection.getInstance().getConnection();
    }

    //positions: 0 = appointments, 1 = groomed, 2 = revenue, 3 = pending
    public double[] getCounts(Date today)
    {
        double[] result = new double[4];
        PreparedStatement ps = null;
        ResultSet rs = null;

        //Done = grooming finished AND paid
        String sql = "SELECT "
                   + "(SELECT COUNT(*) FROM appointment "
                   + " WHERE appointment_date = ? AND status <> 'Cancelled') AS total, "
                   + "(SELECT COUNT(*) FROM appointment "
                   + " WHERE appointment_date = ? AND status = 'Done') AS groomed, "
                   + "(SELECT COALESCE(SUM(s.price), 0) FROM appointment a "
                   + " JOIN service s ON a.service_id = s.id "
                   + " WHERE a.appointment_date = ? AND a.status = 'Done') AS revenue, "
                   + "(SELECT COALESCE(SUM(s.price), 0) FROM appointment a "
                   + " JOIN service s ON a.service_id = s.id "
                   + " WHERE a.appointment_date = ? "
                   + " AND a.status IN ('Booked', 'In progress')) AS pending";

        if (con == null)
        {
            JOptionPane.showMessageDialog(null, "Cannot connect to database");
            return null;
        }

        try
        {
            ps = con.prepareStatement(sql);
            ps.setDate(1, today);
            ps.setDate(2, today);
            ps.setDate(3, today);
            ps.setDate(4, today);

            rs = ps.executeQuery();

            if (rs.next())
            {
                result[0] = rs.getInt("total");
                result[1] = rs.getInt("groomed");
                result[2] = rs.getDouble("revenue");
                result[3] = rs.getDouble("pending");
            }

            return result;
        }
        catch (SQLException ex)
        {
            JOptionPane.showMessageDialog(null, "Could not load counts: " + ex.getMessage());
            return null;
        }
        finally
        {
            //closing statement also closes its result set
            try
            {
                if (ps != null)
                {
                    ps.close();
                }
            }
            catch (SQLException ex)
            {
                ex.printStackTrace();
            }
        }
    }

    //updating status of selected appointment
    public boolean updateStatus(int id, String oldStatus, String newStatus)
    {
        PreparedStatement ps = null;

        if (con == null)
        {
            JOptionPane.showMessageDialog(null, "Cannot connect to database");
            return false;
        }

        //checking old status also before updating
        String sql = "UPDATE appointment SET status = ? WHERE id = ? AND status = ?";

        try
        {
            ps = con.prepareStatement(sql);
            ps.setString(1, newStatus);
            ps.setInt(2, id);
            ps.setString(3, oldStatus);

            int rows = ps.executeUpdate();

            //returns true if one appointment updated
            if (rows == 1)
            {
                return true;
            }

            JOptionPane.showMessageDialog(null, "Appointment was changed or deleted. Please refresh and select it again.");
            return false;
        }
        catch (SQLException ex)
        {
            JOptionPane.showMessageDialog(null, "Could not update appointment: " + ex.getMessage());
            return false;
        }
        finally
        {
            //closing statement after using
            try
            {
                if (ps != null)
                {
                    ps.close();
                }
            }
            catch (SQLException ex)
            {
                ex.printStackTrace();
            }
        }
    }

    public List<Object[]> getTodaySchedule(Date date)
    {
        //each object array holds one appointment row
        List<Object[]> appointments = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT a.id, a.appointment_time, "
                   + "p.name AS petName, "
                   + "c.name AS ownerName, "
                   + "s.name AS serviceName, "
                   + "CONCAT_WS(' ', u.firstName, u.lastName) AS groomerName, "
                   + "a.status "
                   + "FROM appointment a "
                   + "LEFT JOIN pet p ON a.pet_id = p.id "
                   + "LEFT JOIN customer c ON p.owner_id = c.id "
                   + "LEFT JOIN service s ON a.service_id = s.id "
                   + "LEFT JOIN `user` u ON a.groomer_id = u.id "
                   + "WHERE a.appointment_date = ? "
                   + "AND a.status <> 'Cancelled' "
                   + "ORDER BY a.appointment_time, a.id";

        if (con == null)
        {
            JOptionPane.showMessageDialog(null, "Cannot connect to database");
            return null;
        }

        try
        {
            ps = con.prepareStatement(sql);
            ps.setDate(1, date);

            rs = ps.executeQuery();

            //reading todays appointments one by one
            while (rs.next())
            {
                appointments.add(new Object[]
                {
                    rs.getTime("appointment_time"),
                    rs.getString("petName"),
                    rs.getString("ownerName"),
                    rs.getString("serviceName"),
                    rs.getString("groomerName"),
                    rs.getString("status"),
                    rs.getInt("id")
                });
            }

            return appointments;
        }
        catch (SQLException ex)
        {
            JOptionPane.showMessageDialog(null, "Could not load appointments: " + ex.getMessage());
            return null;
        }
        finally
        {
            //closing statement also closes its result set
            try
            {
                if (ps != null)
                {
                    ps.close();
                }
            }
            catch (SQLException ex)
            {
                ex.printStackTrace();
            }
        }
    }
}