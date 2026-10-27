/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.ServiceDAO;
import model.Service;
import views.PanelServices;
import util.CustomException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author User
 */
public class ControllerService
{
    private final PanelServices view;
    private final ServiceDAO serviceDAO;

    //services currently showing in the table
    private List<Service> shownServices = new ArrayList<>();

    //service selected from the table
    private Service selectedService = null;

    public ControllerService(PanelServices view)
    {
        this.view = view;
        this.serviceDAO = new ServiceDAO();

        view.addSearchListener(e -> search());
        view.addShowAllListener(e -> showAll());
        view.addServiceListener(e -> addService());
        view.addUpdateListener(e -> updateService());
        view.addDeleteListener(e -> deleteService());
        view.addClearListener(e -> clear());

        //when selecting a row fill the form
        view.getServicesTable().getSelectionModel()
            .addListSelectionListener(e ->
            {
                if (!e.getValueIsAdjusting())
                {
                    onRowSelected();
                }
            });

        showAll();
    }

    private void showAll()
    {
        fillTable(serviceDAO.findAll());
    }

    private void search()
    {
        String keyword = view.getSearchText().trim();

        if (keyword.equals(""))
        {
            showAll();
        }
        else
        {
            fillTable(serviceDAO.search(keyword));
        }
    }

    private void fillTable(List<Service> services)
    {
        //remove old selection before changing the list
        clear();
        shownServices = services;

        DefaultTableModel model =
            (DefaultTableModel) view.getServicesTable().getModel();

        model.setRowCount(0);

        //adding services to the table
        for (Service s : services)
        {
            model.addRow(new Object[]
            {
                s.getId(),
                s.getName(),
                s.getPrice(),
                s.getDuration()
            });
        }
    }

    private void onRowSelected()
    {
        int row = view.getServicesTable().getSelectedRow();

        if (row < 0)
        {
            selectedService = null;
            return;
        }

        //getting correct row even if table is sorted
        row = view.getServicesTable().convertRowIndexToModel(row);

        if (row >= shownServices.size())
        {
            selectedService = null;
            return;
        }

        //getting selected service from the list
        selectedService = shownServices.get(row);
        view.showService(selectedService);
    }

    private void addService()
    {
        Service s = readForm();

        //form was invalid error already shown
        if (s == null)
        {
            return;
        }

        if (serviceDAO.insert(s))
        {
            showAll();

            JOptionPane.showMessageDialog(
                view,
                "Service added",
                "Service Added",
                JOptionPane.INFORMATION_MESSAGE
            );
        }
    }

    private void updateService()
    {
        try
        {
            if (selectedService == null)
            {
                throw new CustomException(
                    "Select a service from the table first"
                );
            }

            Service s = readForm();

            if (s == null)
            {
                return;
            }

            //setting id of service we are updating
            s.setId(selectedService.getId());

            if (serviceDAO.update(s))
            {
                showAll();

                JOptionPane.showMessageDialog(
                    view,
                    "Service updated",
                    "Service Updated",
                    JOptionPane.INFORMATION_MESSAGE
                );
            }
        }
        catch (CustomException ex)
        {
            JOptionPane.showMessageDialog(view, ex.getMessage());
        }
    }

    private void deleteService()
    {
        try
        {
            if (selectedService == null)
            {
                throw new CustomException(
                    "Select a service from the table first"
                );
            }

            int choice = JOptionPane.showConfirmDialog(
                view,
                "Delete " + selectedService.getName() + "?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
            );

            //stop if user didnt press yes
            if (choice != JOptionPane.YES_OPTION)
            {
                return;
            }

            if (serviceDAO.delete(selectedService.getId()))
            {
                showAll();

                JOptionPane.showMessageDialog(
                    view, "Service deleted"
                );
            }
        }
        catch (CustomException ex)
        {
            JOptionPane.showMessageDialog(view, ex.getMessage());
        }
    }

    private void clear()
    {
        view.getServicesTable().clearSelection();
        selectedService = null;
        view.clearForm();
    }

    private Service readForm()
    {
        try
        {
            String name = view.getServiceName().trim();
            String description = view.getDescription().trim();
            String priceText = view.getPrice().trim();
            String durationText = view.getDuration().trim();

            //description is optional
            if (name.isEmpty() || priceText.isEmpty()
                    || durationText.isEmpty())
            {
                throw new CustomException(
                    "Please enter service name, price and duration"
                );
            }

            if (name.length() > 100)
            {
                throw new CustomException(
                    "Service name cannot have more than 100 characters"
                );
            }

            if (description.length() > 500)
            {
                throw new CustomException(
                    "Description cannot have more than 500 characters"
                );
            }

            double price;

            //converting price text to double
            try
            {
                price = Double.parseDouble(priceText);
            }
            catch (NumberFormatException ex)
            {
                throw new CustomException(
                    "Please enter a valid price"
                );
            }

            //checking price is a valid positive number
            if (!Double.isFinite(price) || price <= 0)
            {
                throw new CustomException(
                    "Please enter a valid price more than zero"
                );
            }

            if (price > 99999999.99)
            {
                throw new CustomException("Price is too large");
            }

            int duration;

            //duration must be a whole number
            try
            {
                duration = Integer.parseInt(durationText);
            }
            catch (NumberFormatException ex)
            {
                throw new CustomException(
                    "Please enter whole minutes for duration"
                );
            }

            if (duration <= 0)
            {
                throw new CustomException(
                    "Duration must be more than zero"
                );
            }

            //creating service using form details
            Service s = new Service();
            s.setName(name);
            s.setDescription(description);
            s.setPrice(price);
            s.setDuration(duration);

            return s;
        }
        catch (CustomException ex)
        {
            JOptionPane.showMessageDialog(view, ex.getMessage());
        }

        return null;
    }
}
