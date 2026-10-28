/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.DashboardDAO;
import views.PanelDashboard;
import java.sql.Date;
import java.sql.Time;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author User
 */
public class ControllerDashboard
{
    private final PanelDashboard view;
    private final DashboardDAO dashboardDAO;

    //keeping appointments in same order as table
    private List<Object[]> shownAppointments = new ArrayList<>();

    public ControllerDashboard(PanelDashboard view)
    {
        this.view = view;
        this.dashboardDAO = new DashboardDAO();

        view.addRefreshListener(e -> refresh());
        view.addProcessListener(e -> updateStatus("In progress"));
        view.addDoneListener(e -> updateStatus("Done"));

        //allowing only one appointment to select
        view.getScheduleTable().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        //making table read only
        view.getScheduleTable().setDefaultEditor(Object.class, null);

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

            DefaultTableModel model = (DefaultTableModel) view.getScheduleTable().getModel();

            view.getScheduleTable().clearSelection();
            model.setRowCount(0);

            shownAppointments = appointments;

            DateTimeFormatter format = DateTimeFormatter.ofPattern("hh:mm a", Locale.ENGLISH);

            for (Object[] row : shownAppointments)
            {
                //converting database time to readable time
                Time time = (Time) row[0];
                String timeText = "";

                if (time != null)
                {
                    timeText = time.toLocalTime().format(format);
                }

                //id is kept in list but not showing in table
                model.addRow(new Object[] {timeText, row[1], row[2], row[3], row[4], row[5]});
            }
        }
        catch (Exception ex)
        {
            //clearing old data when loading failed
            shownAppointments = new ArrayList<>();
            view.showLoadError();

            JOptionPane.showMessageDialog(view, "Could not load dashboard: " + ex.getMessage());
        }
    }

    private void updateStatus(String newStatus)
    {
        int selectedRow = view.getScheduleTable().getSelectedRow();

        //checking appointment is selected
        if (selectedRow == -1)
        {
            JOptionPane.showMessageDialog(view, "Please select an appointment first");
            return;
        }

        //getting correct row even when table is sorted
        int rowIndex = view.getScheduleTable().convertRowIndexToModel(selectedRow);

        Object[] appointment = shownAppointments.get(rowIndex);

        int appointmentId = (Integer) appointment[6];
        String oldStatus = (String) appointment[5];
        String petName = (String) appointment[1];

        //checking status is already same
        if (newStatus.equals(oldStatus))
        {
            JOptionPane.showMessageDialog(view, "This appointment is already " + newStatus);
            return;
        }

        //asking before updating appointment
        int answer = JOptionPane.showConfirmDialog(view, "Appointment #" + appointmentId + "\nPet: " 
                + petName + "\n\nChange status from " + oldStatus + " to " + newStatus + 
                "?", "Confirm status update", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (answer != JOptionPane.YES_OPTION)
        {
            return;
        }

        try
        {
            boolean updated = dashboardDAO.updateStatus(appointmentId, oldStatus, newStatus);

            if (updated)
            {
                JOptionPane.showMessageDialog(view, "Appointment status updated");
            }
            else
            {
                JOptionPane.showMessageDialog(view, "Appointment was changed or deleted.\nPlease select it again after refreshing.");
            }

            //loading latest table and counts
            refresh();
        }
        catch (Exception ex)
        {
            JOptionPane.showMessageDialog(view, "Could not update appointment: " + ex.getMessage());
        }
    }
}