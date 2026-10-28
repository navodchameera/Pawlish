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

    // positions: 0 = today's appointments, 1 = pets groomed today 2 = revenue today 3 = pending payments today
    public double[] getCounts(Date today) throws SQLException
    {
        double[] result = new double[4];

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
            throw new SQLException("Cannot connect to database");
        }

        try (PreparedStatement ps = con.prepareStatement(sql))
        {
            ps.setDate(1, today);
            ps.setDate(2, today);
            ps.setDate(3, today);
            ps.setDate(4, today);

            try (ResultSet rs = ps.executeQuery())
            {
                if (rs.next())
                {
                    result[0] = rs.getInt("total");
                    result[1] = rs.getInt("groomed");
                    result[2] = rs.getDouble("revenue");
                    result[3] = rs.getDouble("pending");
                }
            }
        }

        return result;
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