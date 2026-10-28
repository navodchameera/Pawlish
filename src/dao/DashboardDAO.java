/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

    public int[] getCounts(Date date) throws SQLException
    {
        //positions are total, waiting, in progress, completed
        int[] counts = new int[4];

        String sql = "SELECT COUNT(*) AS total, "
                   + "COALESCE(SUM(status = 'Booked'), 0) AS waiting, "
                   + "COALESCE(SUM(status = 'In progress'), 0) AS inProgress, "
                   + "COALESCE(SUM(status = 'Done'), 0) AS completed "
                   + "FROM appointment "
                   + "WHERE appointment_date = ? "
                   + "AND status <> 'Cancelled'";

        if (con == null)
        {
            throw new SQLException("Cannot connect to database");
        }

        try (PreparedStatement ps = con.prepareStatement(sql))
        {
            ps.setDate(1, date);

            try (ResultSet rs = ps.executeQuery())
            {
                if (rs.next())
                {
                    counts[0] = rs.getInt("total");
                    counts[1] = rs.getInt("waiting");
                    counts[2] = rs.getInt("inProgress");
                    counts[3] = rs.getInt("completed");
                }
            }
        }

        return counts;
    }

    public List<Object[]> getTodaySchedule(Date date) throws SQLException
    {
        //each object array holds one appointment row
        List<Object[]> appointments = new ArrayList<>();

        String sql = "SELECT a.appointment_time, "
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
            throw new SQLException("Cannot connect to database");
        }

        try (PreparedStatement ps = con.prepareStatement(sql))
        {
            ps.setDate(1, date);

            try (ResultSet rs = ps.executeQuery())
            {
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
                        rs.getString("status")
                    });
                }
            }
        }

        return appointments;
    }
}