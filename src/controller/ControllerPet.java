/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.*;
import model.*;
import util.*;
import views.*;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author User
 */
public class ControllerPet 
{

    private final PanelPets view;
    private final PetDAO petDAO;
    private final CustomerDAO customerDAO;

    // The pets currently shown in the table same order as the rows
    private List<Pet> shownPets = new ArrayList<>();
    // The pet currently selected in the table, or null.
    private Pet selectedPet = null;
    // The customers inside the owner combo box combo index 1 = owners.get(0), index 0 is "--Select--"
    private List<Customer> owners = new ArrayList<>();

    public ControllerPet(PanelPets view) 
    {
        this.view = view;
        this.petDAO = new PetDAO();
        this.customerDAO = new CustomerDAO();

        view.addSearchListener(e -> search());
        view.addShowAllListener(e -> showAll());
        view.addPetListener(e -> addPet());
        view.addUpdateListener(e -> updatePet());
        view.addDeleteListener(e -> deletePet());
        view.addClearListener(e -> clear());
        

        // Fill the form when a row is selected.
        view.getPetsTable().getSelectionModel().addListSelectionListener(e -> 
        {
            if (!e.getValueIsAdjusting()) 
            {
                onRowSelected();
            }
        });

        refreshOwners();
        showAll();
    }

    
    // Loads all customer names into the combo box. Call this again if customers change.
    public void refreshOwners() 
    {
        //getting all customers from the database
        owners = customerDAO.findAll();

        //creating a list of names in the same order as the owners list
        List<String> names = new ArrayList<>();
        for (Customer c : owners) 
        {
            names.add(c.getName());
        }
        view.loadOwners(names);
    }

    // Reloads the pets table 
    public void refresh() 
    {
        refreshOwners();
        showAll();
    }
    
    
    private void showAll() 
    {
        fillTable(petDAO.findAll());
        
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
            fillTable(petDAO.search(keyword));
        }
      
    }

    private void fillTable(List<Pet> pets) 
    {
        shownPets = pets;
        selectedPet = null;

        DefaultTableModel model = (DefaultTableModel) view.getPetsTable().getModel();
        model.setRowCount(0);
        for (Pet p : pets) 
        {
            model.addRow(new Object[]
            {
                p.getId(),
                p.getName(),
                p.getSpecies(),
                p.getBreed(),
                p.getOwnerName()
            });
        }
    }



    private void onRowSelected() 
    {
        int row = view.getPetsTable().getSelectedRow();
        if (row < 0 || row >= shownPets.size()) 
        {
            selectedPet = null;
            return;
        }
        selectedPet = shownPets.get(row);

        //finding the owner of this pet inside the combo box (0 = "--Select--")
        int ownerIndex = 0;
        for (int i = 0; i < owners.size(); i++) 
        {
            if (owners.get(i).getId() == selectedPet.getOwnerId()) 
            {
                ownerIndex = i + 1;
            }
        }
        view.showPet(selectedPet, ownerIndex);
    }

    

    private void addPet() 
    {
        Pet p = readForm();
        //form was invalid, the error was already shown
        if (p == null) 
        {
            return;
        }

        //if the insert fails the DAO has already shown the database error
        if (petDAO.insert(p))
        {
            showAll();
            view.clearForm();
            JOptionPane.showMessageDialog(null, "Pet added",
                "Pet Added", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void updatePet() 
    {
        try 
        {
            if (selectedPet == null) 
            {
                throw new CustomException("Select a pet from the table first");
            }

            Pet p = readForm();
            //form was invalid, the error was already shown
            if (p == null) 
            {
                return;
            }
            p.setId(selectedPet.getId());

            //if the update fails the DAO has already shown the database error
            if (petDAO.update(p))
            {
                showAll();
                view.clearForm();
                JOptionPane.showMessageDialog(null, "Pet updated",
                    "Pet Updated", JOptionPane.INFORMATION_MESSAGE);
            }
        } 
        catch (CustomException ex) 
        {
            showError(ex);
        }
    }

    private void deletePet() 
    {
        try 
        {
            if (selectedPet == null) 
            {
                throw new CustomException("Select a pet from the table first");
            }

            int choice = JOptionPane.showConfirmDialog(null, "Delete " + selectedPet.getName() + "?",
                "Confirm Delete", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (choice != JOptionPane.YES_OPTION) 
            {
                return;
            }

            //if the delete fails the DAO has already shown the database error
            if (petDAO.delete(selectedPet.getId()))
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
        selectedPet = null;
    }

    // Reads and validates the form. Returns a Pet (without id), or null if the form is invalid.
    private Pet readForm() 
    {
        try 
        {
            String name = view.getPetName().trim();
            String species = view.getSpecies();
            String breed = view.getBreed().trim();
            java.util.Date birthday = view.getBirthday();
            String weightText = view.getWeight().trim();
            int ownerIndex = view.getOwnerIndex();

            if (name.isEmpty() || breed.isEmpty() || weightText.isEmpty()) 
            {
                throw new CustomException("Please fill in all fields");
            }
            //"--Select--" is index 0
            if (ownerIndex <= 0 || ownerIndex > owners.size()) 
            {
                throw new CustomException("Please select an owner");
            }

            //weight must be a number
            double weight = 0;
            try 
            {
                weight = Double.parseDouble(weightText);
            } 
            catch (NumberFormatException ex) 
            {
                throw new CustomException("Weight must be a number");
            }
            if (weight <= 0) 
            {
                throw new CustomException("Weight must be more than zero");
            }

            Pet p = new Pet();
            p.setName(name);
            p.setSpecies(species);
            p.setBreed(breed);
            
            //view gives util birthdat so convert it to sql date
            p.setBirthday(new java.sql.Date(birthday.getTime()));
            
            p.setWeight(weight);
            //the owner list is one place behind the combo box because of "--Select--"
            p.setOwnerId(owners.get(ownerIndex - 1).getId());
            return p;
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
