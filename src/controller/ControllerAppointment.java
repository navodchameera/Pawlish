/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.*;
import model.*;
import views.PanelAppointments;
import util.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author User
 */
public class ControllerAppointment
{
    private final PanelAppointments view;
    private final AppointmentDAO appointmentDAO;
    private final PetDAO petDAO;
    private final ServiceDAO serviceDAO;
    private final UserDAO userDAO;

    //records currently showing in tables
    private List<Appointment> shownAppointments = new ArrayList<>();
    private List<Pet> shownPets = new ArrayList<>();

    //records used for combo boxes
    private List<Service> services = new ArrayList<>();
    private List<User> groomers = new ArrayList<>();

    private Appointment selectedAppointment = null;

    //zero means no pet selected
    private int selectedPetId = 0;

    public ControllerAppointment(PanelAppointments view)
    {
        this.view = view;
        this.appointmentDAO = new AppointmentDAO();
        this.petDAO = new PetDAO();
        this.serviceDAO = new ServiceDAO();
        this.userDAO = new UserDAO();

        view.addSearchListener(e -> search());
        view.addShowAllListener(e -> showAll());

        view.addPetSearchListener(e -> searchPets());
        view.addShowAllPetsListener(e -> showAllPets());

        view.addAppointmentListener(e -> addAppointment());
        view.addUpdateListener(e -> updateAppointment());
        view.addDeleteListener(e -> deleteAppointment());
        view.addClearListener(e -> clear());

        //when appointment row selected fill the form
        view.getAppointmentsTable().getSelectionModel()
            .addListSelectionListener(e ->
            {
                if (!e.getValueIsAdjusting())
                {
                    onRowSelected();
                }
            });

        //when pet row selected fill pet name
        view.getPetsTable().getSelectionModel()
            .addListSelectionListener(e ->
            {
                if (!e.getValueIsAdjusting())
                {
                    onPetSelected();
                }
            });

        refresh();
    }

    public void refresh()
    {
        clear();
        loadChoices();
        showAllPets();
        showAll();
    }

    private void loadChoices()
    {
        //getting services and employees from database
        services = serviceDAO.findAll();
        groomers = new ArrayList<>();

        List<String> serviceNames = new ArrayList<>();
        List<String> groomerNames = new ArrayList<>();

        for (Service s : services)
        {
            serviceNames.add(
                s.getName() + " (#" + s.getId() + ")"
            );
        }
        
        
        //adding all employees including admins
        for (User u : userDAO.findAll())
        {
            groomers.add(u);

            groomerNames.add(
                u.getFirstName() + " " + u.getLastName()
                + " (#" + u.getId() + ")"
            );
        }
        
        view.loadChoices(serviceNames, groomerNames);
    }

    private void showAllPets()
    {
        view.clearPetSearch();

        //empty search gets all pets
        fillPets(petDAO.searchForAppointment(""));
    }

    private void searchPets()
    {
        String keyword = view.getPetSearchText().trim();

        fillPets(petDAO.searchForAppointment(keyword));
    }

    private void fillPets(List<Pet> pets)
    {
        //clear table highlight before replacing rows
        view.getPetsTable().clearSelection();

        shownPets = pets;

        DefaultTableModel model =
            (DefaultTableModel) view.getPetsTable().getModel();

        model.setRowCount(0);

        for (Pet p : pets)
        {
            model.addRow(new Object[]
            {
                p.getId(),
                p.getName(),
                p.getOwnerName(),
                p.getOwnerPhone()
            });
        }

        //searching doesnt remove pet already chosen in form
    }

    private void onPetSelected()
    {
        int row = view.getPetsTable().getSelectedRow();

        if (row < 0)
        {
            return;
        }

        row = view.getPetsTable().convertRowIndexToModel(row);

        if (row >= shownPets.size())
        {
            return;
        }

        Pet p = shownPets.get(row);

        //keeping id for saving and showing name in textbox
        selectedPetId = p.getId();
        view.setPetName(p.getName());
    }

    private void showAll()
    {
        view.clearAppointmentSearch();
        fillTable(appointmentDAO.findAll());
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
            fillTable(appointmentDAO.search(keyword));
        }
    }

    private void fillTable(List<Appointment> appointments)
    {
        clear();
        shownAppointments = appointments;

        DefaultTableModel model =
            (DefaultTableModel) view.getAppointmentsTable().getModel();

        model.setRowCount(0);

        for (Appointment a : appointments)
        {
            model.addRow(new Object[]
            {
                a.getId(),
                a.getDate(),
                a.getPetName(),
                a.getServiceName(),
                a.getStatus()
            });
        }
    }

    private void onRowSelected()
    {
        int row = view.getAppointmentsTable().getSelectedRow();

        if (row < 0)
        {
            selectedAppointment = null;
            return;
        }

        row = view.getAppointmentsTable().convertRowIndexToModel(row);

        if (row >= shownAppointments.size())
        {
            selectedAppointment = null;
            return;
        }

        selectedAppointment = shownAppointments.get(row);

        //getting pet directly from saved appointment
        selectedPetId = selectedAppointment.getPetId();

        int serviceIndex = 0;
        int groomerIndex = 0;

        for (int i = 0; i < services.size(); i++)
        {
            if (services.get(i).getId()
                    == selectedAppointment.getServiceId())
            {
                serviceIndex = i + 1;
                break;
            }
        }

        for (int i = 0; i < groomers.size(); i++)
        {
            if (groomers.get(i).getId()
                    == selectedAppointment.getGroomerId())
            {
                groomerIndex = i + 1;
                break;
            }
        }

        //remove previous pet highlight
        view.getPetsTable().clearSelection();

        //highlight appointment pet if it is in current pet results
        for (int i = 0; i < shownPets.size(); i++)
        {
            if (shownPets.get(i).getId() == selectedPetId)
            {
                int petRow =
                    view.getPetsTable().convertRowIndexToView(i);

                if (petRow >= 0)
                {
                    view.getPetsTable().setRowSelectionInterval(
                        petRow, petRow
                    );
                }

                break;
            }
        }

        view.showAppointment(
            selectedAppointment, serviceIndex, groomerIndex
        );
    }

    private void addAppointment()
    {
        Appointment a = readForm(false);

        if (a == null)
        {
            return;
        }

        if (appointmentDAO.insert(a))
        {
            showAll();

            JOptionPane.showMessageDialog(
                view, "Appointment added",
                "Appointment Added",
                JOptionPane.INFORMATION_MESSAGE
            );
        }
    }

    private void updateAppointment()
    {
        if (selectedAppointment == null)
        {
            JOptionPane.showMessageDialog(
                view, "Select an appointment from the table first"
            );
            return;
        }

        Appointment a = readForm(true);

        if (a == null)
        {
            return;
        }

        a.setId(selectedAppointment.getId());

        if (appointmentDAO.update(a))
        {
            showAll();

            JOptionPane.showMessageDialog(
                view, "Appointment updated",
                "Appointment Updated",
                JOptionPane.INFORMATION_MESSAGE
            );
        }
    }

    private void deleteAppointment()
    {
        if (selectedAppointment == null)
        {
            JOptionPane.showMessageDialog(
                view, "Select an appointment from the table first"
            );
            return;
        }

        int choice = JOptionPane.showConfirmDialog(
            view,
            "Delete selected appointment?",
            "Confirm Delete",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );

        if (choice != JOptionPane.YES_OPTION)
        {
            return;
        }

        if (appointmentDAO.delete(selectedAppointment.getId()))
        {
            showAll();

            JOptionPane.showMessageDialog(
                view, "Appointment deleted"
            );
        }
    }

    private void clear()
    {
        view.getAppointmentsTable().clearSelection();
        view.getPetsTable().clearSelection();

        selectedAppointment = null;
        selectedPetId = 0;

        view.clearForm();
    }

    private Appointment readForm(boolean updating)
    {
        try
        {
            int serviceIndex = view.getServiceIndex();
            int groomerIndex = view.getGroomerIndex();
            String status = view.getAppointmentStatus();

            if (selectedPetId == 0)
            {
                throw new CustomException(
                    "Please select a pet from the pet table"
                );
            }

            if (serviceIndex <= 0 || serviceIndex > services.size())
            {
                throw new CustomException("Please select a service");
            }

            if (groomerIndex <= 0 || groomerIndex > groomers.size())
            {
                throw new CustomException("Please select a groomer");
            }

            if (status == null || status.trim().isEmpty()
                    || status.equals("--Select--"))
            {
                throw new CustomException("Please select a status");
            }

            if (status.length() > 20)
            {
                throw new CustomException("Status is too long");
            }

            java.util.Date date;

            //getting typed date from spinner
            try
            {
                date = view.getAppointmentDate();
            }
            catch (java.text.ParseException ex)
            {
                throw new CustomException("Please enter a valid date");
            }

            String timeText = view.getAppointmentTime();

            if (timeText == null || timeText.equals("--Select--"))
            {
                throw new CustomException("Please select a time");
            }

            //handles 12.30 PM from current combo properties
            timeText = timeText.trim().replace('.', ':');

            DateTimeFormatter format =
                DateTimeFormatter.ofPattern("hh:mm a", Locale.ENGLISH);

            LocalTime time;

            try
            {
                time = LocalTime.parse(timeText, format);
            }
            catch (java.time.format.DateTimeParseException ex)
            {
                throw new CustomException("Please select a valid time");
            }

            java.sql.Date sqlDate = new java.sql.Date(date.getTime());

            LocalDateTime appointmentDateTime =
                LocalDateTime.of(sqlDate.toLocalDate(), time);

            boolean checkDate = true;

            if (updating)
            {
                //allow status update for old appointment
                //changed date or time must be in future
                checkDate =
                    !sqlDate.toLocalDate().equals(
                        selectedAppointment.getDate().toLocalDate()
                    )
                    || !time.equals(
                        selectedAppointment.getTime().toLocalTime()
                    );
            }

            if (checkDate
                    && appointmentDateTime.isBefore(LocalDateTime.now()))
            {
                throw new CustomException(
                    "Appointment date and time cannot be in the past"
                );
            }

            Appointment a = new Appointment();

            //using selected pet id not the textbox name
            a.setPetId(selectedPetId);
            a.setServiceId(services.get(serviceIndex - 1).getId());
            a.setGroomerId(groomers.get(groomerIndex - 1).getId());
            a.setDate(sqlDate);
            a.setTime(java.sql.Time.valueOf(time));
            a.setStatus(status);

            return a;
        }
        catch (CustomException ex)
        {
            JOptionPane.showMessageDialog(view, ex.getMessage());
        }

        return null;
    }
}
