/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import components.CustomTable;
import controller.ProjectController;
import helper.CurrencyHelper;
import java.awt.Container;
import java.awt.event.KeyEvent;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import model.Project;

/**
 *
 * @author Leonovo
 */
public class MasterProjectPage extends javax.swing.JFrame {

    ProjectController projectCtr;
    DefaultTableModel tableModel;
    /**
     * Creates new form MasterUserForm
     */
    public MasterProjectPage() {
        projectCtr = new ProjectController();
        tableModel = new DefaultTableModel();
        
        initComponents();
        
        loadDataTable();
    }
    
    public JPanel getMainPanel() {
        return mainPanel;
    }
    
    private void loadDataTable() {
        tableModel = new DefaultTableModel();
        try {
            String cariData = txtCari.getText();
            List<Project> listproject = projectCtr.getData(cariData);
            Object[] colums = {"Project ID", "Name", "Budget", "Status", "Aksi"};
            tableModel.setColumnIdentifiers(colums);
            
            
            
            
            for (Project project : listproject) {
                tableModel.addRow(new Object[]{
                    project.getProject_id(), 
                    project.getName(),
                    CurrencyHelper.formatForTable(project.getBudget(), true),
                    project.getStatus(),
                    "", // tambahkan 1 value kosong untuk kolom button action (edit dan delete)
                });
            }
            customtable1.setModel(tableModel);
            
            CustomTable.ColumnConfig[] configs = {
                new CustomTable.ColumnConfig(120, CustomTable.ALIGN_CENTER),    //Project ID
                new CustomTable.ColumnConfig(280, CustomTable.ALIGN_LEFT),     // Name  
                new CustomTable.ColumnConfig(200, CustomTable.ALIGN_RIGHT),     // Budget
                new CustomTable.ColumnConfig(120, CustomTable.ALIGN_CENTER),   // Status
                new CustomTable.ColumnConfig(120, CustomTable.ALIGN_CENTER)    // Aksi
            };
            customtable1.setColumnConfigs(configs);
            
            customtable1.setShowActionButtons(true);
            customtable1.setActionButtonListener(new CustomTable.ActionButtonListener() {
                @Override
                public void onEdit(int row, Object[] rowData) {
                    String Project_id = rowData[0].toString();
                    showDataToForm(Project_id);
                }

                @Override
                public void onDelete(int row, Object[] rowData) {
                    int result = JOptionPane.showConfirmDialog(null, 
                        "Delete Project " + rowData[2] + "?", 
                        "Confirm", 
                        JOptionPane.YES_NO_OPTION);
                    if (result == JOptionPane.YES_OPTION) {
                        customtable1.removeRow(row);
                    }
                }
            });
        
        } catch (Exception e) {
            System.out.println(e);
            JOptionPane.showMessageDialog(null, "data project gagal dipanggil "+e);
        
        } 
    }
    
    private void showDataToForm(String Project_id) {
        try {
            Project project = projectCtr.getProjectById(Project_id);
            
            lblTitleForm.setText("Form Edit Data Project");
            
            btnSimpan.setText("Update");
            txtProjectId.setText(project.getProject_id());
            txtClientId.setText(project.getClient_id());
            txtClientName.setText(project.getClient_name());
            txtName.setText(project.getName());
            txtStartDate.setDateFromSQLString(project.getStart_date());
            txtEndDate.setDateFromSQLString(project.getEnd_date());
            numberBudget.setDoubleValue(project.getBudget());
            cbStatus.setSelectedItem(project.getStatus());
            
            txtProjectId.setEnabled(false);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "data user gagal dipanggil "+e);
        }
    }
    
    private void save() {
        Project project = new Project();
        project.setProject_id(txtProjectId.getText());
        project.setClient_id(txtClientId.getText());
        project.setName(txtName.getText());
        project.setStart_date(txtStartDate.getDateSQLString());
        project.setEnd_date(txtEndDate.getDateSQLString());
        project.setBudget(numberBudget.getDoubleValue());
        project.setStatus(cbStatus.getSelectedItem().toString());
        
        
        boolean isCreateUser = btnSimpan.getText().equals("Tambah");
        
        if (isCreateUser) {
            String create = projectCtr.createProject(project);
            JOptionPane.showMessageDialog(null, create);
        } else {
            String update = projectCtr.updateProject(project);
            JOptionPane.showMessageDialog(null, update);
        }


        loadDataTable();
       
    }
    
    private void resetForm() {
        txtProjectId.setText("");
        txtProjectId.setEnabled(true);
        txtClientId.setText("");
        txtClientName.setText("");
        txtName.setText("");
        txtStartDate.setDate(new Date());
        txtStartDate.setDateFromString("");
        cbStatus.setSelectedIndex(0);
        txtCari.setText("");
        txtEndDate.setDate(new Date());
        txtEndDate.setDateFromString("");
        numberBudget.setText("");
        btnSimpan.setText("Tambah");
        lblTitleForm.setText("Form Tambah Data Project");
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        roundedPanel1 = new components.RoundedPanel();
        lblTitleForm = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtClientId = new components.RoundedTextField();
        txtName = new components.RoundedTextField();
        cbStatus = new components.RoundedComboBox();
        roundedButton2 = new components.RoundedButton();
        btnSimpan = new components.RoundedButton();
        jLabel7 = new javax.swing.JLabel();
        txtProjectId = new components.RoundedTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        btnSimpan1 = new components.RoundedButton();
        txtClientName = new components.RoundedTextField();
        txtStartDate = new components.CustomCalendar();
        txtEndDate = new components.CustomCalendar();
        numberBudget = new components.RoundedCurrencyField();
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 32767));
        roundedPanel2 = new components.RoundedPanel();
        customtable1 = new components.CustomTable();
        txtCari = new components.RoundedTextField();
        btnCari = new components.RoundedButton();
        jLabel6 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        mainPanel.setOpaque(false);

        roundedPanel1.setCornerRadius(20);
        roundedPanel1.setCustomHasBorder(false);
        roundedPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblTitleForm.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblTitleForm.setText("Form Tambah Data Proyek");
        roundedPanel1.add(lblTitleForm, new org.netbeans.lib.awtextra.AbsoluteConstraints(23, 20, -1, -1));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("Client ID : ");
        roundedPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(23, 171, -1, -1));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setText("Project Name : ");
        roundedPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(23, 123, -1, -1));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setText("Start Date : ");
        roundedPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(479, 76, -1, -1));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setText("End Date : ");
        roundedPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(479, 118, -1, -1));

        txtClientId.setCornerRadius(12);
        txtClientId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtClientIdActionPerformed(evt);
            }
        });
        roundedPanel1.add(txtClientId, new org.netbeans.lib.awtextra.AbsoluteConstraints(128, 166, 227, -1));

        txtName.setCornerRadius(12);
        roundedPanel1.add(txtName, new org.netbeans.lib.awtextra.AbsoluteConstraints(128, 118, 281, -1));

        cbStatus.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "completed", "ongoing", "planning" }));
        cbStatus.setCornerRadius(12);
        cbStatus.setDoubleBuffered(true);
        roundedPanel1.add(cbStatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(558, 212, 302, -1));

        roundedButton2.setText("⟳ reset form");
        roundedButton2.setAutoscrolls(true);
        roundedButton2.setCustomColorScheme(components.RoundedButton.ColorScheme.SECONDARY);
        roundedButton2.setCustomCornerRadius(20);
        roundedButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                roundedButton2ActionPerformed(evt);
            }
        });
        roundedPanel1.add(roundedButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(729, 20, -1, 33));

        btnSimpan.setText("Tambah");
        btnSimpan.setCustomCornerRadius(12);
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });
        roundedPanel1.add(btnSimpan, new org.netbeans.lib.awtextra.AbsoluteConstraints(128, 267, 281, 39));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel7.setText("Project ID :");
        roundedPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(23, 76, -1, -1));

        txtProjectId.setCornerRadius(12);
        txtProjectId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtProjectIdActionPerformed(evt);
            }
        });
        roundedPanel1.add(txtProjectId, new org.netbeans.lib.awtextra.AbsoluteConstraints(128, 71, 281, -1));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel8.setText("Budget : ");
        roundedPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(479, 165, -1, -1));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel9.setText("Status : ");
        roundedPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(479, 219, -1, -1));

        btnSimpan1.setText("🔍");
        btnSimpan1.setCustomColorScheme(components.RoundedButton.ColorScheme.PRIMARY);
        btnSimpan1.setCustomCornerRadius(12);
        btnSimpan1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpan1ActionPerformed(evt);
            }
        });
        roundedPanel1.add(btnSimpan1, new org.netbeans.lib.awtextra.AbsoluteConstraints(361, 165, 48, 33));

        txtClientName.setEditable(false);
        txtClientName.setCornerRadius(12);
        txtClientName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtClientNameActionPerformed(evt);
            }
        });
        roundedPanel1.add(txtClientName, new org.netbeans.lib.awtextra.AbsoluteConstraints(128, 212, 281, -1));

        txtStartDate.setDateFormat("dd MMMM yyyy");
        txtStartDate.setPlaceholder("Input tanggal mulai");
        roundedPanel1.add(txtStartDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(558, 71, 302, -1));

        txtEndDate.setDateFormat("dd MMMM yyyy");
        txtEndDate.setPlaceholder("Input tanggal selesai");
        roundedPanel1.add(txtEndDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(558, 118, 302, -1));
        roundedPanel1.add(numberBudget, new org.netbeans.lib.awtextra.AbsoluteConstraints(558, 165, 302, 35));
        roundedPanel1.add(filler2, new org.netbeans.lib.awtextra.AbsoluteConstraints(413, 71, 60, 255));

        roundedPanel2.setCornerRadius(20);
        roundedPanel2.setHasBorder(false);

        customtable1.setBackground(new java.awt.Color(236, 240, 241));
        customtable1.setHeaderBackgroundColor(new java.awt.Color(236, 240, 241));
        customtable1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                customtable1PropertyChange(evt);
            }
        });

        txtCari.setCornerRadius(12);
        txtCari.setPlaceholder("Cari data ...");
        txtCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCariActionPerformed(evt);
            }
        });
        txtCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCariKeyPressed(evt);
            }
        });

        btnCari.setText("🔍 Cari");
        btnCari.setCustomCornerRadius(12);
        btnCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCariActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel6.setText("Data Proyek");

        javax.swing.GroupLayout roundedPanel2Layout = new javax.swing.GroupLayout(roundedPanel2);
        roundedPanel2.setLayout(roundedPanel2Layout);
        roundedPanel2Layout.setHorizontalGroup(
            roundedPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundedPanel2Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(roundedPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(customtable1, javax.swing.GroupLayout.DEFAULT_SIZE, 819, Short.MAX_VALUE)
                    .addGroup(roundedPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnCari, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(34, 34, 34))
        );
        roundedPanel2Layout.setVerticalGroup(
            roundedPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundedPanel2Layout.createSequentialGroup()
                .addContainerGap(15, Short.MAX_VALUE)
                .addGroup(roundedPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(roundedPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(roundedPanel2Layout.createSequentialGroup()
                            .addGap(6, 6, 6)
                            .addComponent(jLabel6))
                        .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnCari, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(customtable1, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37))
        );

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(roundedPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(roundedPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(roundedPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(roundedPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(42, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(mainPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 540, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtClientIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtClientIdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtClientIdActionPerformed

    private void customtable1PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_customtable1PropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_customtable1PropertyChange

    private void roundedButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_roundedButton2ActionPerformed
        // TODO add your handling code here:
        resetForm();
    }//GEN-LAST:event_roundedButton2ActionPerformed

    private void txtCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCariActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCariActionPerformed

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        // TODO add your handling code here:
        save();
        resetForm();
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void txtProjectIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtProjectIdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtProjectIdActionPerformed

    private void btnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariActionPerformed
        // TODO add your handling code here:
        loadDataTable();
    }//GEN-LAST:event_btnCariActionPerformed

    private void txtCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCariKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            loadDataTable();
        }
    }//GEN-LAST:event_txtCariKeyPressed

    private void btnSimpan1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpan1ActionPerformed
        // TODO add your handling code here:
        PopupChooseClient.show(this, client -> {
            txtClientId.setText(client.getClientId());
            txtClientName.setText(client.getName());
        });
    }//GEN-LAST:event_btnSimpan1ActionPerformed

    private void txtClientNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtClientNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtClientNameActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MasterProjectPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MasterProjectPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MasterProjectPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MasterProjectPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MasterProjectPage().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private components.RoundedButton btnCari;
    private components.RoundedButton btnSimpan;
    private components.RoundedButton btnSimpan1;
    private components.RoundedComboBox cbStatus;
    private components.CustomTable customtable1;
    private javax.swing.Box.Filler filler2;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel lblTitleForm;
    private javax.swing.JPanel mainPanel;
    private components.RoundedCurrencyField numberBudget;
    private components.RoundedButton roundedButton2;
    private components.RoundedPanel roundedPanel1;
    private components.RoundedPanel roundedPanel2;
    private components.RoundedTextField txtCari;
    private components.RoundedTextField txtClientId;
    private components.RoundedTextField txtClientName;
    private components.CustomCalendar txtEndDate;
    private components.RoundedTextField txtName;
    private components.RoundedTextField txtProjectId;
    private components.CustomCalendar txtStartDate;
    // End of variables declaration//GEN-END:variables
}
