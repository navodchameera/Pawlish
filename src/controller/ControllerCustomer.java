/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.CustomerDAO;
import model.*;
import util.CustomException;
import views.*;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author User
 */
public class ControllerCustomer 
{

    private final PanelCustomers view;
    private final CustomerDAO customerDAO;

    // The customers currently shown in the table (same order as the rows).
    private List<Customer> shownCustomers = new ArrayList<>();
    // The customer currently selected in the table, or null.
    private Customer selectedCustomer = null;

    public ControllerCustomer(PanelCustomers view) 
    {
        this.view = view;
        this.customerDAO = new CustomerDAO();

        view.addSearchListener(e ->search());
        view.addShowAllListener(e -> showAll());
        view.addCustomerListener(e -> addCustomer());
        view.addUpdateListener(e -> updateCustomer());
        view.addDeleteListener(e ->deleteCustomer());
        view.addClearListener(e -> clear());
        

        // Fill the form when a row is selected.
        view.getCustomersTable().getSelectionModel().addListSelectionListener(e -> 
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
        fillTable(customerDAO.findAll());
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
            fillTable(customerDAO.search(keyword));
        }
    }

    private void fillTable(List<Customer> customers) 
    {
        shownCustomers = customers;
        selectedCustomer = null;

        DefaultTableModel model = (DefaultTableModel) view.getCustomersTable().getModel();
        model.setRowCount(0);
        for (Customer c : customers) 
        {
            model.addRow(new Object[]
            {
                c.getId(),
                c.getName(),
                c.getPhone(),
                c.getEmail()
            });
        }
    }


    private void onRowSelected() 
    {
        int row = view.getCustomersTable().getSelectedRow();
        //if its a invalid selection
        if (row < 0 || row >= shownCustomers.size()) 
        {
            selectedCustomer = null;
            return;
        }
        //getting Customer from the array list
        selectedCustomer = shownCustomers.get(row);
        view.showCustomer(selectedCustomer);
    }


    private void addCustomer() 
    {
        try 
        {
            Customer c = readForm();
            //form was invalid, the error was already shown
            if (c == null) 
            {
                return;
            }

            //if the insert fails the DAO has already shown the database error
            if (customerDAO.insert(c))
            {
                showAll();
                view.clearForm();
                JOptionPane.showMessageDialog(null, "Customer added",
                    "Customer Added", JOptionPane.INFORMATION_MESSAGE);
            }
        } 
        catch (Exception ex) 
        {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }
    
    

    private void updateCustomer() 
    {
        try 
        {
            if (selectedCustomer == null) 
            {
                throw new CustomException("Select a customer from the table first");
            }

            Customer c = readForm();
            //form was invalid, the error was already shown
            if (c == null) 
            {
                return;
            }
            c.setId(selectedCustomer.getId());

            //if the update fails the DAO has already shown the database error
            if (customerDAO.update(c))
            {
                showAll();
                view.clearForm();
                JOptionPane.showMessageDialog(null, "Customer updated",
                    "Customer Updated", JOptionPane.INFORMATION_MESSAGE);
            }
        } 
        catch (CustomException ex) 
        {
            showError(ex);
        }
    }

    private void deleteCustomer() 
    {
        try 
        {
            if (selectedCustomer == null) 
            {
                throw new CustomException("Select a customer from the table first");
            }

            int choice = JOptionPane.showConfirmDialog(null, "Delete " + selectedCustomer.getName() + "?",
                "Confirm Delete", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (choice != JOptionPane.YES_OPTION) 
            {
                return;
            }

            //if the delete fails the DAO has already shown the database error
            if (customerDAO.delete(selectedCustomer.getId()))
            {
                showAll();
                view.clearForm();
            }
        } 
        catch (CustomException ex) 
        {
            showError(ex);
        }
    }

    private void clear() 
    {
        view.clearForm();
        selectedCustomer = null;
    }

    // Reads and validates the form. Returns a Customer (without id), or null if the form is invalid.
    private Customer readForm() 
    {
        try 
        {
            String name    = view.getCustomerName().trim();
            String phone   = view.getPhone().trim();
            String email   = view.getEmail().trim();
            String address = view.getAddress().trim();

            if (name.isEmpty() || phone.isEmpty() || email.isEmpty() || address.isEmpty()) 
            {
                throw new CustomException("Please fill in all fields");
            }
            if (phone.length() != 10) 
            {
                throw new CustomException("Please enter a valid phone number");
            }
            if (!email.contains("@") || !email.contains(".")) 
            {
                throw new CustomException("Please enter a valid email address");
            }

            Customer c = new Customer();
            c.setName(name);
            c.setPhone(phone);
            c.setEmail(email);
            c.setAddress(address);
            return c;
        } 
        catch (CustomException ex) 
        {
            //show the validation message once and tell the caller the form is invalid
            showError(ex);
        }
        return null;
    }

    private void showError(CustomException ex) 
    {
        JOptionPane.showMessageDialog(null, ex.getMessage());
    }
}
