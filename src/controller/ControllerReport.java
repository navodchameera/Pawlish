/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.ReportDAO;
import views.PanelReports;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author User
 */
public class ControllerReport
{
    private final PanelReports view;
    private final ReportDAO reportDAO;

    //keeping selected filter for each table
    private String employeePeriod = "All";
    private String servicePeriod = "All";

    public ControllerReport(PanelReports view)
    {
        this.view = view;
        this.reportDAO = new ReportDAO();

        //employee table buttons
        view.addEmployeeAllListener(e -> loadEmployees("All"));
        view.addEmployeeMonthListener(e -> loadEmployees("Month"));
        view.addEmployeeWeekListener(e -> loadEmployees("Week"));
        view.addEmployeeTodayListener(e -> loadEmployees("Today"));

        //service table buttons
        view.addServiceAllListener(e -> loadServices("All"));
        view.addServiceMonthListener(e -> loadServices("Month"));
        view.addServiceWeekListener(e -> loadServices("Week"));
        view.addServiceTodayListener(e -> loadServices("Today"));
    }

    public void refresh()
    {
        //loading both tables with their selected filters
        loadEmployees(employeePeriod);
        loadServices(servicePeriod);
    }

    private void loadEmployees(String period)
    {
        employeePeriod = period;

        LocalDate today = LocalDate.now();
        Date fromDate = null;
        Date toDate = Date.valueOf(today);

        //null from date means showing all records
        if (period.equals("Month"))
        {
            fromDate = Date.valueOf(today.withDayOfMonth(1));
        }
        else if (period.equals("Week"))
        {
            //getting monday of this week
            fromDate = Date.valueOf(today.minusDays(today.getDayOfWeek().getValue() - 1));
        }
        else if (period.equals("Today"))
        {
            fromDate = Date.valueOf(today);
        }

        List<Object[]> employees = reportDAO.getEmployeeSummary(fromDate, toDate);

        DefaultTableModel model = (DefaultTableModel) view.getEmployeeTable().getModel();

        //clearing old rows before loading
        model.setRowCount(0);

        if (employees == null)
        {
            view.setEmployeeTitle("Employee Summary - Unavailable");
            return;
        }

        view.setEmployeeTitle("Employee Summary - " + period);

        for (Object[] row : employees)
        {
            //showing revenue with two decimal places
            String revenue = String.format(Locale.ENGLISH, "%,.2f", (Double) row[2]);

            model.addRow(new Object[] {row[0], row[1], revenue});
        }
    }

    private void loadServices(String period)
    {
        servicePeriod = period;

        LocalDate today = LocalDate.now();
        Date fromDate = null;
        Date toDate = Date.valueOf(today);

        //null from date means showing all records
        if (period.equals("Month"))
        {
            fromDate = Date.valueOf(today.withDayOfMonth(1));
        }
        else if (period.equals("Week"))
        {
            //getting monday of this week
            fromDate = Date.valueOf(today.minusDays(today.getDayOfWeek().getValue() - 1));
        }
        else if (period.equals("Today"))
        {
            fromDate = Date.valueOf(today);
        }

        List<Object[]> services = reportDAO.getServiceSummary(fromDate, toDate);

        DefaultTableModel model = (DefaultTableModel) view.getServiceTable().getModel();

        //clearing old rows before loading
        model.setRowCount(0);

        if (services == null)
        {
            view.setServiceTitle("Service Summary - Unavailable");
            return;
        }

        view.setServiceTitle("Service Summary - " + period);

        for (Object[] row : services)
        {
            //showing revenue with two decimal places
            String revenue = String.format(Locale.ENGLISH, "%,.2f", (Double) row[2]);

            model.addRow(new Object[] {row[0], row[1], revenue});
        }
    }
}
