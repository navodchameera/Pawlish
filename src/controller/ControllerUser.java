package controller;

import dao.UserDAO;
import model.User;
import util.CustomException;
import util.SessionManager;
import views.PanelEmployees;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

/**
 * Connects PanelEmployees (the view) to UserDAO (the database).
 * Handles search, show all, add, update, delete and clear for employees.
 *
 * @author User
 */
public class ControllerUser {

    // PanelEmployees has no password field, so new employees get this password.
    private static final String DEFAULT_PASSWORD = "staff123";

    private final PanelEmployees view;
    private final UserDAO userDAO;

    // The users currently shown in the table (same order as the rows).
    private List<User> shownUsers = new ArrayList<>();
    // The user currently selected in the table, or null.
    private User selectedUser = null;

    public ControllerUser(PanelEmployees view) {
        this.view = view;
        this.userDAO = new UserDAO();

        view.addSearchListener(e -> search());
        view.addShowAllListener(e -> showAll());
        view.addEmployeeListener(e -> addEmployee());
        view.addUpdateListener(e -> updateEmployee());
        view.addDeleteListener(e -> deleteEmployee());
        view.addClearListener(e -> clear());

        // Fill the form when a row is selected.
        view.getEmployeesTable().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                onRowSelected();
            }
        });

        showAll();
    }

    // ---------- Loading the table ----------

    private void showAll() 
    {
        fillTable(userDAO.findAll());
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
            fillTable(userDAO.search(keyword));
        }
    }

    private void fillTable(List<User> users) 
    {
        shownUsers = users;
        selectedUser = null;

        DefaultTableModel model = (DefaultTableModel) view.getEmployeesTable().getModel();
        model.setRowCount(0);
        for (User u : users) 
        {
            model.addRow(new Object[]
            {
                u.getId(),
                u.getUsername(),
                u.getFirstName() + " " + u.getLastName(),
                u.getRole()
            });
        }
    }

    

    private void onRowSelected() 
    {
        int row = view.getEmployeesTable().getSelectedRow();
        if (row < 0 || row >= shownUsers.size()) {
            selectedUser = null;
            return;
        }
        selectedUser = shownUsers.get(row);
        view.showUser(selectedUser);
    }

    

    private void addEmployee() {
        try {
            User u = readForm();

            if (userDAO.usernameExists(u.getUsername())) 
            {
                throw new CustomException("That username is already taken");
            }

            u.setPassword(DEFAULT_PASSWORD);

            if (!userDAO.insert(u)) 
            {
                throw new CustomException("Could not add the employee");
            }

            showAll();
            view.clearForm();
            JOptionPane.showMessageDialog(null,
                "Employee added. Their password is: " + DEFAULT_PASSWORD,
                "Employee Added", JOptionPane.INFORMATION_MESSAGE);

        } catch (CustomException ex) {
            showError(ex);
        }
    }

    private void updateEmployee() {
        try {
            if (selectedUser == null) {
                throw new CustomException("Select an employee from the table first");
            }

            User u = readForm();
            u.setId(selectedUser.getId());

            //only check for duplicates if the username was actually changed
            //(equalsIgnoreCase because MySQL usually treats usernames as case-insensitive)
            boolean usernameChanged = !u.getUsername().equalsIgnoreCase(selectedUser.getUsername());

            if (usernameChanged && userDAO.usernameExists(u.getUsername()))
            {
                throw new CustomException("That username is already taken");
            }

            if (!userDAO.update(u)) {
                throw new CustomException("Could not update the employee");
            }

            showAll();
            view.clearForm();
            JOptionPane.showMessageDialog(null, "Employee updated",
                "Employee Updated", JOptionPane.INFORMATION_MESSAGE);

        } catch (CustomException ex) {
            showError(ex);
        }
    }

    private void deleteEmployee() {
        try 
        {
            if (selectedUser == null) {
                throw new CustomException("Select an employee from the table first");
            }

            // Stop the logged-in admin from deleting their own account.
            User current = SessionManager.getCurrentUser();
            if (current != null && current.getId() == selectedUser.getId()) 
            {
                throw new CustomException("You cannot delete the account you are logged in with");
            }

            int choice = JOptionPane.showConfirmDialog(null, "Delete " + selectedUser.getFirstName() + " " + selectedUser.getLastName() + "?","Confirm Delete", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (choice != JOptionPane.YES_OPTION) 
            {
                return;
            }

            if (!userDAO.delete(selectedUser.getId())) 
            {
                throw new CustomException("Could not delete the employee");
            }

            showAll();
            view.clearForm();

        } 
        catch (CustomException ex) 
        {
            showError(ex);
        }
    }

    private void clear() 
    {
        view.clearForm();
        selectedUser = null;
    }

    // Reads and validates the form. Returns a User (without id or password).
    private User readForm()
    {
        try
        {
            String username  = view.getUsername().trim();
            String firstName = view.getFirstName().trim();
            String lastName  = view.getLastName().trim();
            String phone     = view.getPhone().trim();
            String address   = view.getAddress().trim();
            String role      = view.getRole();
            if (username.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || phone.isEmpty() || address.isEmpty()) 
            {
                throw new CustomException("Please fill in all fields");
            }
            if (phone.length() != 10) 
            {
                throw new CustomException("Please enter a valid phone number");
            }

            User u = new User();
            u.setUsername(username);
            u.setFirstName(firstName);
            u.setLastName(lastName);
            u.setPhone(phone);
            u.setAddress(address);
            u.setRole(role);   
            return u;
        }
        catch(Exception ex)
        {
            
        }
        return null;
    }

    private void showError(CustomException ex) 
    {
        JOptionPane.showMessageDialog(null, ex.getMessage());
    }
}

