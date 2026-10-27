/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package views;

import controller.*;
import model.Appointment;
import java.util.List;
import java.util.Locale;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author User
 */
public class PanelAppointments extends javax.swing.JPanel {

    /**
     * Creates new form PanelAppointments
     */
    private ControllerAppointment controller;

    public PanelAppointments()
    {
        initComponents();
        controller = new ControllerAppointment(this);

        //getting latest records when opening panel
        addComponentListener(new java.awt.event.ComponentAdapter()
        {
            @Override
            public void componentShown(java.awt.event.ComponentEvent evt)
            {
                controller.refresh();
            }
        });
    }


    //getting appointment search text
    public String getSearchText()
    {
        return txtSearchAppoinmets.getText();
    }

    //getting pet search text
    public String getPetSearchText()
    {
        return txtSearchPets.getText();
    }

    public int getServiceIndex()
    {
        return cmbService.getSelectedIndex();
    }

    public int getGroomerIndex()
    {
        return cmbGroomer.getSelectedIndex();
    }

    public java.util.Date getAppointmentDate()
            throws java.text.ParseException
    {
        //save typed date before getting value
        spnDate.commitEdit();

        return (java.util.Date) spnDate.getValue();
    }

    public String getAppointmentTime()
    {
        return (String) cmbTime.getSelectedItem();
    }

    public String getAppointmentStatus()
    {
        return (String) cmbStatus.getSelectedItem();
    }


    public javax.swing.JTable getAppointmentsTable()
    {
        return tblAppointments1;
    }

    public javax.swing.JTable getPetsTable()
    {
        return tblPets;
    }

    //showing selected pet name in textbox
    public void setPetName(String name)
    {
        txtPetName.setText(name);
    }

    public void clearPetSearch()
    {
        txtSearchPets.setText("");
    }

    public void clearAppointmentSearch()
    {
        txtSearchAppoinmets.setText("");
    }


    public void addSearchListener(java.awt.event.ActionListener l)
    {
        btnSearchAppointment.addActionListener(l);

        //also search when pressing enter
        txtSearchAppoinmets.addActionListener(l);
    }

    public void addShowAllListener(java.awt.event.ActionListener l)
    {
        btnShowAllAppoimnets.addActionListener(l);
    }



    public void addPetSearchListener(java.awt.event.ActionListener l)
    {
        btnSearchPets.addActionListener(l);

        //also search when pressing enter
        txtSearchPets.addActionListener(l);
    }

    public void addShowAllPetsListener(java.awt.event.ActionListener l)
    {
        btnShowAll.addActionListener(l);
    }



    public void addAppointmentListener(java.awt.event.ActionListener l)
    {
        jButton3.addActionListener(l);
    }

    public void addUpdateListener(java.awt.event.ActionListener l)
    {
        jButton4.addActionListener(l);
    }

    public void addDeleteListener(java.awt.event.ActionListener l)
    {
        jButton5.addActionListener(l);
    }

    public void addClearListener(java.awt.event.ActionListener l)
    {
        btnClear.addActionListener(l);
    }

    //loading services and groomers from database
    public void loadChoices(List<String> services, List<String> groomers)
    {
        cmbService.removeAllItems();
        cmbGroomer.removeAllItems();

        cmbService.addItem("--Select--");
        cmbGroomer.addItem("--Select--");

        for (String name : services)
        {
            cmbService.addItem(name);
        }

        for (String name : groomers)
        {
            cmbGroomer.addItem(name);
        }
    }


    public void showAppointment(Appointment a,int serviceIndex,int groomerIndex)
    {
        txtPetName.setText(a.getPetName());

        cmbService.setSelectedIndex(serviceIndex);
        cmbGroomer.setSelectedIndex(groomerIndex);

        spnDate.setValue(new java.util.Date(a.getDate().getTime()));

        DateTimeFormatter format =
            DateTimeFormatter.ofPattern("hh:mm a", Locale.ENGLISH);

        String time = a.getTime().toLocalTime().format(format);

        boolean found = false;

        for (int i = 0; i < cmbTime.getItemCount(); i++)
        {
            //matching time even if properties used a dot
            String item = cmbTime.getItemAt(i).trim().replace('.', ':');

            if (time.equals(item))
            {
                cmbTime.setSelectedIndex(i);
                found = true;
                break;
            }
        }

        //show saved time if it is not in current options
        if (!found)
        {
            cmbTime.addItem(time);
            cmbTime.setSelectedItem(time);
        }

        cmbStatus.setSelectedItem(a.getStatus());
    }
    //clearing appointment fields
    public void clearForm()
    {
        txtPetName.setText("");
        cmbService.setSelectedIndex(0);
        cmbGroomer.setSelectedIndex(0);
        spnDate.setValue(new java.util.Date());
        cmbTime.setSelectedIndex(0);
        cmbStatus.setSelectedIndex(0);
        
    }
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        txtSearchAppoinmets = new javax.swing.JTextField();
        btnSearchAppointment = new javax.swing.JButton();
        btnShowAllAppoimnets = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblAppointments1 = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        cmbService = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        spnDate = new javax.swing.JSpinner();
        jLabel7 = new javax.swing.JLabel();
        cmbTime = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        cmbGroomer = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        cmbStatus = new javax.swing.JComboBox<>();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblPets = new javax.swing.JTable();
        txtSearchPets = new javax.swing.JTextField();
        btnShowAll = new javax.swing.JButton();
        btnSearchPets = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtPetName = new javax.swing.JTextField();

        setBackground(new java.awt.Color(255, 248, 240));
        setPreferredSize(new java.awt.Dimension(910, 680));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(35, 54, 61));
        jLabel1.setText("Appointments");
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(28, 20, 400, 36));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(191, 211, 208)));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtSearchAppoinmets.setBackground(new java.awt.Color(227, 244, 241));
        txtSearchAppoinmets.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jPanel1.add(txtSearchAppoinmets, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 16, 262, 32));

        btnSearchAppointment.setBackground(new java.awt.Color(227, 244, 241));
        btnSearchAppointment.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        btnSearchAppointment.setForeground(new java.awt.Color(23, 120, 122));
        btnSearchAppointment.setText("Search");
        btnSearchAppointment.setMargin(new java.awt.Insets(2, 2, 2, 2));
        jPanel1.add(btnSearchAppointment, new org.netbeans.lib.awtextra.AbsoluteConstraints(286, 16, 80, 32));

        btnShowAllAppoimnets.setBackground(new java.awt.Color(227, 244, 241));
        btnShowAllAppoimnets.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        btnShowAllAppoimnets.setForeground(new java.awt.Color(23, 120, 122));
        btnShowAllAppoimnets.setText("Show all");
        btnShowAllAppoimnets.setMargin(new java.awt.Insets(2, 2, 2, 2));
        jPanel1.add(btnShowAllAppoimnets, new org.netbeans.lib.awtextra.AbsoluteConstraints(372, 16, 82, 32));

        jLabel2.setForeground(new java.awt.Color(91, 111, 117));
        jLabel2.setText("Click a row to load it into the form.");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 556, 438, 20));

        tblAppointments1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tblAppointments1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Date", "Pet", "Service", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblAppointments1.setFillsViewportHeight(true);
        tblAppointments1.setRowHeight(26);
        tblAppointments1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane2.setViewportView(tblAppointments1);
        if (tblAppointments1.getColumnModel().getColumnCount() > 0) {
            tblAppointments1.getColumnModel().getColumn(3).setHeaderValue("Service");
            tblAppointments1.getColumnModel().getColumn(4).setHeaderValue("Status");
        }

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 64, 438, 480));

        add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(28, 70, 470, 590));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(191, 211, 208)));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(35, 54, 61));
        jLabel4.setText("Pet");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 56, 160, 20));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(35, 54, 61));
        jLabel5.setText("Service");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(184, 56, 160, 20));

        cmbService.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cmbService.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--Select--" }));
        jPanel2.add(cmbService, new org.netbeans.lib.awtextra.AbsoluteConstraints(184, 78, 160, 32));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(35, 54, 61));
        jLabel6.setText("Date");
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 130, 160, 20));

        spnDate.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        spnDate.setModel(new javax.swing.SpinnerDateModel());
        spnDate.setEditor(new javax.swing.JSpinner.DateEditor(spnDate, "dd MMM yyyy"));
        jPanel2.add(spnDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 152, 160, 32));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(35, 54, 61));
        jLabel7.setText("Time");
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(184, 130, 160, 20));

        cmbTime.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cmbTime.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "09:00 AM", "09:30 AM", "10:00 AM", "10:30 AM", "12:00 PM", "12.30 PM", "01:00 PM", "01:30 PM", "02:00 PM", "03:00 PM", "03:30 PM" }));
        jPanel2.add(cmbTime, new org.netbeans.lib.awtextra.AbsoluteConstraints(184, 152, 160, 32));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(35, 54, 61));
        jLabel8.setText("Groomer");
        jPanel2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 204, 160, 20));

        cmbGroomer.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cmbGroomer.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--Select--" }));
        jPanel2.add(cmbGroomer, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 226, 160, 32));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(35, 54, 61));
        jLabel9.setText("Status");
        jPanel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(184, 204, 160, 20));

        cmbStatus.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cmbStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Booked", "In progress", "Done", "Cancelled" }));
        jPanel2.add(cmbStatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(184, 226, 160, 32));

        jButton3.setBackground(new java.awt.Color(255, 138, 101));
        jButton3.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        jButton3.setForeground(new java.awt.Color(35, 54, 61));
        jButton3.setMargin(new java.awt.Insets(2, 2, 2, 2));
        jButton3.setText("Add");
        jPanel2.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 530, 78, 40));

        jButton4.setBackground(new java.awt.Color(227, 244, 241));
        jButton4.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        jButton4.setForeground(new java.awt.Color(23, 120, 122));
        jButton4.setMargin(new java.awt.Insets(2, 2, 2, 2));
        jButton4.setText("Update");
        jPanel2.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(102, 530, 78, 40));

        jButton5.setBackground(new java.awt.Color(255, 227, 216));
        jButton5.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        jButton5.setForeground(new java.awt.Color(138, 58, 30));
        jButton5.setMargin(new java.awt.Insets(2, 2, 2, 2));
        jButton5.setText("Delete");
        jPanel2.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(188, 530, 78, 40));

        btnClear.setBackground(new java.awt.Color(227, 244, 241));
        btnClear.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        btnClear.setForeground(new java.awt.Color(23, 120, 122));
        btnClear.setMargin(new java.awt.Insets(2, 2, 2, 2));
        btnClear.setText("Clear");
        btnClear.addActionListener(this::btnClearActionPerformed);
        jPanel2.add(btnClear, new org.netbeans.lib.awtextra.AbsoluteConstraints(274, 530, 78, 40));

        tblPets.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tblPets.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Pet ID", "Pet Name", "Owner Name", "Phone"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblPets.setFillsViewportHeight(true);
        tblPets.setRowHeight(26);
        tblPets.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(tblPets);

        jPanel2.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 350, 330, 160));

        txtSearchPets.setBackground(new java.awt.Color(227, 244, 241));
        txtSearchPets.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jPanel2.add(txtSearchPets, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 300, 140, 32));

        btnShowAll.setBackground(new java.awt.Color(227, 244, 241));
        btnShowAll.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        btnShowAll.setForeground(new java.awt.Color(23, 120, 122));
        btnShowAll.setText("Show all");
        btnShowAll.setMargin(new java.awt.Insets(2, 2, 2, 2));
        jPanel2.add(btnShowAll, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 300, 82, 32));

        btnSearchPets.setBackground(new java.awt.Color(227, 244, 241));
        btnSearchPets.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        btnSearchPets.setForeground(new java.awt.Color(23, 120, 122));
        btnSearchPets.setText("Search");
        btnSearchPets.setMargin(new java.awt.Insets(2, 2, 2, 2));
        jPanel2.add(btnSearchPets, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 300, 80, 32));

        jLabel10.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 16)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(23, 120, 122));
        jLabel10.setText("Appointment details");
        jPanel2.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 16, 336, 24));

        jLabel3.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 16)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(23, 120, 122));
        jLabel3.setText("Search pets to add");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 270, 336, 24));

        txtPetName.setEditable(false);
        txtPetName.setBackground(new java.awt.Color(227, 244, 241));
        txtPetName.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jPanel2.add(txtPetName, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 78, 160, 32));

        add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(514, 70, 368, 590));
    }// </editor-fold>//GEN-END:initComponents

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        
    }//GEN-LAST:event_btnClearActionPerformed

    /**
     * @param args the command line arguments
     */
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnSearchAppointment;
    private javax.swing.JButton btnSearchPets;
    private javax.swing.JButton btnShowAll;
    private javax.swing.JButton btnShowAllAppoimnets;
    private javax.swing.JComboBox<String> cmbGroomer;
    private javax.swing.JComboBox<String> cmbService;
    private javax.swing.JComboBox<String> cmbStatus;
    private javax.swing.JComboBox<String> cmbTime;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSpinner spnDate;
    private javax.swing.JTable tblAppointments1;
    private javax.swing.JTable tblPets;
    private javax.swing.JTextField txtPetName;
    private javax.swing.JTextField txtSearchAppoinmets;
    private javax.swing.JTextField txtSearchPets;
    // End of variables declaration//GEN-END:variables
}
