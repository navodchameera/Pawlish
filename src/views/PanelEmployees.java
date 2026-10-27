/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package views;
import controller.*;

/**
 * @author User
 */
public class PanelEmployees extends javax.swing.JPanel {
    public PanelEmployees() {
        initComponents();
        //sends the view to the panel
        ControllerUser cu = new ControllerUser(this); 
    }
    
    public String getUsername() {
        return txtUsername.getText();
    }

    public String getFirstName() {
        return txtFirstName.getText();
    }

    public String getLastName() {
        return txtLastName.getText();
    }

    public String getPhone() {
        return txtPhone.getText();
    }

    public String getAddress() {
        return txtAddress.getText();
    }

    public String getSearchText() {
        return txtSearch.getText();
    }

    public String getRole() {
        return (String) cmbRole.getSelectedItem();
    }

    public javax.swing.JTable getEmployeesTable() {
        return tblEmployees;
    }

    public void addSearchListener(java.awt.event.ActionListener l) {
        btnSearch.addActionListener(l);
    }

    public void addShowAllListener(java.awt.event.ActionListener l) {
        btnShowAll.addActionListener(l);
    }

    public void addEmployeeListener(java.awt.event.ActionListener l) {
        btnAdd.addActionListener(l);
    }

    public void addUpdateListener(java.awt.event.ActionListener l) {
        btnUpdate.addActionListener(l);
    }

    public void addDeleteListener(java.awt.event.ActionListener l) {
        btnDelete.addActionListener(l);
    }

    public void addClearListener(java.awt.event.ActionListener l) {
        btnClear.addActionListener(l);
    }
    
    
    public void clearForm() {
        txtUsername.setText("");
        txtFirstName.setText("");
        txtLastName.setText("");
        txtPhone.setText("");
        txtAddress.setText("");
        cmbRole.setSelectedIndex(0);
        tblEmployees.clearSelection();
    }
    
    public void showUser(model.User u) {
        txtUsername.setText(u.getUsername());
        txtFirstName.setText(u.getFirstName());
        txtLastName.setText(u.getLastName());
        txtPhone.setText(u.getPhone());
        txtAddress.setText(u.getAddress());
        for (int i = 0; i < cmbRole.getItemCount(); i++) {
            if (cmbRole.getItemAt(i).toString().equalsIgnoreCase(u.getRole())) {
                cmbRole.setSelectedIndex(i);
                break;
            }
        }
    }
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblHeading = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        txtSearch = new javax.swing.JTextField();
        btnSearch = new javax.swing.JButton();
        btnShowAll = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblEmployees = new javax.swing.JTable();
        lblHint = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        lblDetails = new javax.swing.JLabel();
        lblUsername = new javax.swing.JLabel();
        txtUsername = new javax.swing.JTextField();
        lblFirstName = new javax.swing.JLabel();
        txtFirstName = new javax.swing.JTextField();
        lblLastName = new javax.swing.JLabel();
        txtLastName = new javax.swing.JTextField();
        lblPhone = new javax.swing.JLabel();
        txtPhone = new javax.swing.JTextField();
        lblAddress = new javax.swing.JLabel();
        txtAddress = new javax.swing.JTextField();
        lblRole = new javax.swing.JLabel();
        cmbRole = new javax.swing.JComboBox();
        btnAdd = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 248, 240));
        setPreferredSize(new java.awt.Dimension(910, 680));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblHeading.setText("Employees");
        lblHeading.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 24)); // NOI18N
        lblHeading.setForeground(new java.awt.Color(35, 54, 61));
        add(lblHeading, new org.netbeans.lib.awtextra.AbsoluteConstraints(28, 20, 400, 36));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtSearch.setBackground(new java.awt.Color(227, 244, 241));
        jPanel1.add(txtSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 16, 262, 32));

        btnSearch.setText("Search");
        btnSearch.setBackground(new java.awt.Color(227, 244, 241));
        btnSearch.setForeground(new java.awt.Color(23, 120, 122));
        jPanel1.add(btnSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(286, 16, 80, 32));

        btnShowAll.setText("Show all");
        btnShowAll.setBackground(new java.awt.Color(227, 244, 241));
        btnShowAll.setForeground(new java.awt.Color(23, 120, 122));
        jPanel1.add(btnShowAll, new org.netbeans.lib.awtextra.AbsoluteConstraints(372, 16, 82, 32));

        tblEmployees.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Username", "Name", "Role"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblEmployees.setRowHeight(26);
        jScrollPane1.setViewportView(tblEmployees);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 64, 438, 480));

        lblHint.setText("Select an employee to view their details.");
        jPanel1.add(lblHint, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 556, 438, 20));

        add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(28, 70, 470, 590));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblDetails.setText("Employee details");
        lblDetails.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 16)); // NOI18N
        lblDetails.setForeground(new java.awt.Color(23, 120, 122));
        jPanel2.add(lblDetails, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 16, 336, 24));

        lblUsername.setText("Username");
        jPanel2.add(lblUsername, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 54, 336, 20));

        txtUsername.setBackground(new java.awt.Color(227, 244, 241));
        jPanel2.add(txtUsername, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 76, 336, 32));

        lblFirstName.setText("First name");
        jPanel2.add(lblFirstName, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 122, 336, 20));

        txtFirstName.setBackground(new java.awt.Color(227, 244, 241));
        jPanel2.add(txtFirstName, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 144, 336, 32));

        lblLastName.setText("Last name");
        jPanel2.add(lblLastName, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 190, 336, 20));

        txtLastName.setBackground(new java.awt.Color(227, 244, 241));
        jPanel2.add(txtLastName, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 212, 336, 32));

        lblPhone.setText("Phone");
        jPanel2.add(lblPhone, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 258, 336, 20));

        txtPhone.setBackground(new java.awt.Color(227, 244, 241));
        jPanel2.add(txtPhone, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 280, 336, 32));

        lblAddress.setText("Address");
        jPanel2.add(lblAddress, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 326, 336, 20));

        txtAddress.setBackground(new java.awt.Color(227, 244, 241));
        jPanel2.add(txtAddress, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 348, 336, 32));

        lblRole.setText("Role");
        jPanel2.add(lblRole, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 394, 336, 20));

        cmbRole.setBackground(new java.awt.Color(227, 244, 241));
        cmbRole.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "EMPLOYEE", "ADMIN" }));
        jPanel2.add(cmbRole, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 416, 336, 32));

        btnAdd.setText("Add");
        btnAdd.setBackground(new java.awt.Color(255, 138, 101));
        btnAdd.setForeground(new java.awt.Color(35, 54, 61));
        jPanel2.add(btnAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 530, 78, 40));

        btnUpdate.setText("Update");
        btnUpdate.setBackground(new java.awt.Color(227, 244, 241));
        btnUpdate.setForeground(new java.awt.Color(23, 120, 122));
        jPanel2.add(btnUpdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(102, 530, 78, 40));

        btnDelete.setText("Delete");
        btnDelete.setBackground(new java.awt.Color(255, 227, 216));
        btnDelete.setForeground(new java.awt.Color(138, 58, 30));
        jPanel2.add(btnDelete, new org.netbeans.lib.awtextra.AbsoluteConstraints(188, 530, 78, 40));

        btnClear.setText("Clear");
        btnClear.setBackground(new java.awt.Color(227, 244, 241));
        btnClear.setForeground(new java.awt.Color(23, 120, 122));
        jPanel2.add(btnClear, new org.netbeans.lib.awtextra.AbsoluteConstraints(274, 530, 78, 40));

        add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(514, 70, 368, 590));
    }// </editor-fold>//GEN-END:initComponents

    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnShowAll;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JComboBox cmbRole;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblAddress;
    private javax.swing.JLabel lblDetails;
    private javax.swing.JLabel lblFirstName;
    private javax.swing.JLabel lblHeading;
    private javax.swing.JLabel lblHint;
    private javax.swing.JLabel lblLastName;
    private javax.swing.JLabel lblPhone;
    private javax.swing.JLabel lblRole;
    private javax.swing.JLabel lblUsername;
    private javax.swing.JTable tblEmployees;
    private javax.swing.JTextField txtAddress;
    private javax.swing.JTextField txtFirstName;
    private javax.swing.JTextField txtLastName;
    private javax.swing.JTextField txtPhone;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables
}
