/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import components.CustomTable;
import controller.AccountController;
import controller.ClientController;
import controller.ProjectController;
import helper.CurrencyHelper;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import model.Account;
import model.Client;
import model.Project;

/**
 *
 * @author Leonovo
 */
public class PopupChooseProject extends javax.swing.JFrame {

    ProjectController projectCtr;
    DefaultTableModel tableModel;
    /**
     * Creates new form PopupChooseAccount
     */
    public PopupChooseProject() {
        projectCtr = new ProjectController();
        tableModel = new DefaultTableModel();
        
        initComponents();
        
        loadDataTable();
    }
    
    // Simple callback interface
    public interface OnDataSelectedCallback {
        void onDataSelected(Project project);
    }
    
    // Callback instance
    private OnDataSelectedCallback callback;
    
    /**
     * Constructor untuk reusable popup
     * @param callback function yang akan dipanggil saat account dipilih
     */
    public PopupChooseProject(OnDataSelectedCallback callback) {
        this.callback = callback;
        
        projectCtr = new ProjectController();
        tableModel = new DefaultTableModel();
        
        initComponents();
        setupFrame();
        loadDataTable();
    }
    
        /**
     * Static method untuk kemudahan penggunaan - cara paling simple
     * @param callback function yang akan dipanggil saat account dipilih
     * @return instance PopupChooseAccount
     */
    public static PopupChooseProject show(OnDataSelectedCallback callback) {
        PopupChooseProject popup = new PopupChooseProject(callback);
        popup.setVisible(true);
        return popup;
    }
    
    /**
     * Static method dengan parent frame untuk positioning
     * @param parent parent frame untuk posisi
     * @param callback function yang akan dipanggil saat account dipilih
     * @return instance PopupChooseAccount
     */
    public static PopupChooseProject show(JFrame parent, OnDataSelectedCallback callback) {
        PopupChooseProject popup = new PopupChooseProject(callback);
        popup.setLocationRelativeTo(parent);
        popup.setVisible(true);
        return popup;
    }
    
    private void setupFrame() {
        setTitle("Pilih Klien");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(850, 400);
        setLocationRelativeTo(null);
        setResizable(true);
        
        // Optional: Handle window closing
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Frame akan otomatis dispose
            }
        });
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
            
            setupTableButtons();
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "data project gagal dipanggil "+e);
        
        } 
    }
    
    private void setupTableButtons() {
        // Hide edit and delete buttons, show only custom action button
        customtable1.setShowActionButtons(false);
        customtable1.setShowCustomActionButton(true);
        customtable1.setCustomActionButtonText("Pilih");
        customtable1.setCustomActionButtonBackgroundColor(new Color(40, 167, 69)); // Green
        customtable1.setCustomActionButtonTextColor(Color.WHITE);
        
        // Set custom action button listener
        customtable1.setCustomActionButtonListener(new CustomTable.CustomActionButtonListener() {
            @Override
            public void onCustomAction(int row, Object[] rowData) {
                selectAccount(row, rowData);
            }
        });
    }
    
    private void selectAccount(int row, Object[] rowData) {
        try {
            String projectId = rowData[0].toString();
            Project project = projectCtr.getProjectById(projectId);
            
            if (project != null) {
                // Call the callback function
                if (callback != null) {
                    callback.onDataSelected(project);
                }
                
                // Close this popup
                dispose();
                
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Account tidak ditemukan!", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (Exception e) {
            System.out.println("Error selecting account: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, 
                "Error saat memilih account: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
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
        roundedPanel2 = new components.RoundedPanel();
        customtable1 = new components.CustomTable();
        txtCari = new components.RoundedTextField();
        btnCari = new components.RoundedButton();
        jLabel6 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setAlwaysOnTop(true);
        setBackground(new java.awt.Color(255, 255, 255));

        mainPanel.setOpaque(false);

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
        jLabel6.setText("Pilih Akun");

        javax.swing.GroupLayout roundedPanel2Layout = new javax.swing.GroupLayout(roundedPanel2);
        roundedPanel2.setLayout(roundedPanel2Layout);
        roundedPanel2Layout.setHorizontalGroup(
            roundedPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundedPanel2Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(roundedPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(customtable1, javax.swing.GroupLayout.DEFAULT_SIZE, 777, Short.MAX_VALUE)
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
                .addContainerGap(12, Short.MAX_VALUE)
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
                .addContainerGap()
                .addComponent(roundedPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(roundedPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(8, Short.MAX_VALUE))
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
            .addComponent(mainPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void customtable1PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_customtable1PropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_customtable1PropertyChange

    private void txtCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCariActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCariActionPerformed

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
            java.util.logging.Logger.getLogger(PopupChooseProject.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PopupChooseProject.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PopupChooseProject.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PopupChooseProject.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
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
                new PopupChooseProject().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private components.RoundedButton btnCari;
    private components.CustomTable customtable1;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel mainPanel;
    private components.RoundedPanel roundedPanel2;
    private components.RoundedTextField txtCari;
    // End of variables declaration//GEN-END:variables
}
