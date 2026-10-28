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

public class ReportDAO
{
    private Connection con;

    public ReportDAO()
    {
        con = DBConnection.getInstance().getConnection();
    }

    public List<Object[]> getEmployeeSummary(Date fromDate, Date toDate)
    {
        List<Object[]> employees = new ArrayList<>();
        PreparedStatement ps = null;

        if (con == null)
        {
            JOptionPane.showMessageDialog(null, "Cannot connect to database");
            return null;
        }

        //getting all employees including admins
        String sql = "SELECT CONCAT_WS(' ', u.firstName, u.lastName) AS employeeName, "
                   + "COUNT(a.id) AS completed, "
                   + "COALESCE(SUM(s.price), 0) AS revenue "
                   + "FROM `user` u "
                   + "LEFT JOIN appointment a ON a.groomer_id = u.id "
                   + "AND a.status = 'Done' ";

        //adding date filter only when not showing all
        if (fromDate != null)
        {
            sql += "AND a.appointment_date BETWEEN ? AND ? ";
        }

        sql += "LEFT JOIN service s ON a.service_id = s.id "
             + "GROUP BY u.id, u.firstName, u.lastName "
             + "ORDER BY completed DESC, employeeName";

        try
        {
            ps = con.prepareStatement(sql);

            if (fromDate != null)
            {
                ps.setDate(1, fromDate);
                ps.setDate(2, toDate);
            }

            ResultSet rs = ps.executeQuery();

            //reading employees one by one
            while (rs.next())
            {
                employees.add(new Object[]
                {
                    rs.getString("employeeName"),
                    rs.getInt("completed"),
                    rs.getDouble("revenue")
                });
            }
        }
        catch (SQLException ex)
        {
            JOptionPane.showMessageDialog(null, "Could not load employee summary: " + ex.getMessage());
            return null;
        }
        finally
        {
            //closing statement also closes result set
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

        return employees;
    }

    public List<Object[]> getServiceSummary(Date fromDate, Date toDate)
    {
        List<Object[]> services = new ArrayList<>();
        PreparedStatement ps = null;

        if (con == null)
        {
            JOptionPane.showMessageDialog(null, "Cannot connect to database");
            return null;
        }

        //getting completed appointments for each service
        String sql = "SELECT s.name AS serviceName, "
                   + "COUNT(a.id) AS completed, "
                   + "COALESCE(SUM(CASE WHEN a.id IS NOT NULL THEN s.price ELSE 0 END), 0) AS revenue "
                   + "FROM service s "
                   + "LEFT JOIN appointment a ON a.service_id = s.id "
                   + "AND a.status = 'Done' ";

        //adding date filter only when not showing all
        if (fromDate != null)
        {
            sql += "AND a.appointment_date BETWEEN ? AND ? ";
        }

        sql += "GROUP BY s.id, s.name "
             + "ORDER BY completed DESC, s.name";

        try
        {
            ps = con.prepareStatement(sql);

            if (fromDate != null)
            {
                ps.setDate(1, fromDate);
                ps.setDate(2, toDate);
            }

            ResultSet rs = ps.executeQuery();

            //reading services one by one
            while (rs.next())
            {
                services.add(new Object[]
                {
                    rs.getString("serviceName"),
                    rs.getInt("completed"),
                    rs.getDouble("revenue")
                });
            }
        }
        catch (SQLException ex)
        {
            JOptionPane.showMessageDialog(null, "Could not load service summary: " + ex.getMessage());
            return null;
        }
        finally
        {
            //closing statement also closes result set
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

        return services;
    }
}
