/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.DashboardDAO;
import views.*;
import java.sql.Date;
import java.sql.Time;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author User
 */

public class ControllerDashboard
{
    private final PanelDashboard view;
    private final DashboardDAO dashboardDAO;

    public ControllerDashboard(PanelDashboard view)
    {
        this.view = view;
        this.dashboardDAO = new DashboardDAO();

        view.addRefreshListener(e -> refresh());
        

        //panel calls refresh when it becomes visible
    }

    public void refresh()
    {
        try
        {
            //getting todays date from computer
            Date today = Date.valueOf(LocalDate.now());

            //getting both results before updating screen
            double[] counts = dashboardDAO.getCounts(today);
            List<Object[]> appointments = dashboardDAO.getTodaySchedule(today);

            view.showCounts((int) counts[0], (int) counts[1], counts[2], counts[3]);

            DefaultTableModel model =
                (DefaultTableModel) view.getScheduleTable().getModel();

            model.setRowCount(0);

            DateTimeFormatter format =
                DateTimeFormatter.ofPattern("hh:mm a", Locale.ENGLISH);

            for (Object[] row : appointments)
            {
                //converting database time to readable time
                Time time = (Time) row[0];

                if (time != null)
                {
                    row[0] = time.toLocalTime().format(format);
                }
                else
                {
                    row[0] = "";
                }

                model.addRow(row);
            }
        }
        catch (SQLException ex)
        {
            //show unavailable instead of old or incorrect counts
            view.showLoadError();

            JOptionPane.showMessageDialog(
                view,
                "Could not load dashboard: " + ex.getMessage()
            );
        }
    }
}