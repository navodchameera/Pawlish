package dao;

import util.DBConnection;
import java.sql.*;
import javax.swing.JOptionPane;

public class SettingsDAO
{
    public boolean updateUsername(int id, String username)
    {
        Connection con = null;
        PreparedStatement ps = null;

        try
        {
            con = DBConnection.getInstance().getConnection();

            if (con == null)
            {
                JOptionPane.showMessageDialog(null, "Cannot connect to database");
                return false;
            }

            //checking another user already has this username
            String sql = "SELECT id FROM `user` WHERE username = ? AND id <> ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, username);
            ps.setInt(2, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next())
            {
                JOptionPane.showMessageDialog(null, "This username is already taken");
                return false;
            }

            ps.close();

            //updating only signed in user
            sql = "UPDATE `user` SET username = ? WHERE id = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, username);
            ps.setInt(2, id);

            if (ps.executeUpdate() == 1)
            {
                return true;
            }

            JOptionPane.showMessageDialog(null, "Account was not found. Please sign in again.");
        }
        catch (SQLException ex)
        {
            if (ex.getErrorCode() == 1062)
            {
                JOptionPane.showMessageDialog(null, "This username is already taken");
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Could not update username: " + ex.getMessage());
            }
        }
        finally
        {
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

            try
            {
                if (con != null)
                {
                    con.close();
                }
            }
            catch (SQLException ex)
            {
                ex.printStackTrace();
            }
        }

        return false;
    }

    public boolean updatePassword(int id, String currentPassword, String newPassword)
    {
        Connection con = null;
        PreparedStatement ps = null;

        try
        {
            con = DBConnection.getInstance().getConnection();

            if (con == null)
            {
                JOptionPane.showMessageDialog(null, "Cannot connect to database");
                return false;
            }

            //checking exact current password before saving new password
            //binary comparison keeps password case sensitive
            String sql = "UPDATE `user` SET password = ? WHERE id = ? AND CAST(password AS BINARY) = CAST(? AS BINARY)";
            ps = con.prepareStatement(sql);
            ps.setString(1, newPassword);
            ps.setInt(2, id);
            ps.setString(3, currentPassword);

            if (ps.executeUpdate() == 1)
            {
                return true;
            }

            JOptionPane.showMessageDialog(null, "Current password is incorrect or account no longer exists");
        }
        catch (SQLException ex)
        {
            JOptionPane.showMessageDialog(null, "Could not update password: " + ex.getMessage());
        }
        finally
        {
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

            try
            {
                if (con != null)
                {
                    con.close();
                }
            }
            catch (SQLException ex)
            {
                ex.printStackTrace();
            }
        }

        return false;
    }
}
