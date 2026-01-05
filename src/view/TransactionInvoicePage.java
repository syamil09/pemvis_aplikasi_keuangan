/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import components.CustomTable;
import controller.ClientController;
import controller.InvoiceController;
import controller.ProjectController;
import helper.CurrencyHelper;
import java.awt.Container;
import java.awt.event.KeyEvent;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import model.Client;
import model.Invoice;
import model.Project;

/**
 *
 * @author Leonovo
 */
public class TransactionInvoicePage extends javax.swing.JFrame {

    InvoiceController invoiceController;
    ClientController clientController;
    ProjectController projectController;
    DefaultTableModel tableModel;
    /**
     * Creates new form MasterUserForm
     */
    public TransactionInvoicePage() {
        invoiceController = new InvoiceController();
        clientController = new ClientController();
        projectController = new ProjectController();
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
            List<Invoice> listInvoice = invoiceController.getData(cariData);
            Object[] columns = {"Invoice ID", "Invoice Number", "Client ID", "Project ID", "Invoice Date", "Due Date", "Total Amount", "Status", "Aksi"};
            tableModel.setColumnIdentifiers(columns);
            
            for (Invoice invoice : listInvoice) {
                tableModel.addRow(new Object[]{
                    invoice.getInvoiceId(),
                    invoice.getInvoiceNumber(),
                    invoice.getClientId(),
                    invoice.getProjectId() != null ? invoice.getProjectId() : "-",
                    invoice.getFormattedInvoiceDate(),
                    invoice.getFormattedDueDate(),
                    CurrencyHelper.formatForTable(invoice.getTotalAmount(), true),
                    invoice.getStatusDisplay(),
                    "" // kolom aksi
                });
            }
            
            customtable1.setModel(tableModel);
            
            // Setup column configurations
            CustomTable.ColumnConfig[] configs = {
                new CustomTable.ColumnConfig(100, CustomTable.ALIGN_CENTER),    // Invoice ID
                new CustomTable.ColumnConfig(140, CustomTable.ALIGN_CENTER),    // Invoice Number
                new CustomTable.ColumnConfig(80, CustomTable.ALIGN_CENTER),     // Client ID
                new CustomTable.ColumnConfig(80, CustomTable.ALIGN_CENTER),     // Project ID
                new CustomTable.ColumnConfig(100, CustomTable.ALIGN_CENTER),    // Invoice Date
                new CustomTable.ColumnConfig(100, CustomTable.ALIGN_CENTER),    // Due Date
                new CustomTable.ColumnConfig(120, CustomTable.ALIGN_RIGHT),     // Total Amount
                new CustomTable.ColumnConfig(100, CustomTable.ALIGN_CENTER),    // Status
                new CustomTable.ColumnConfig(120, CustomTable.ALIGN_CENTER)     // Aksi
            };
            customtable1.setColumnConfigs(configs);
            
            // Setup action buttons
            customtable1.setShowActionButtons(true);
            customtable1.setActionButtonListener(new CustomTable.ActionButtonListener() {
                @Override
                public void onEdit(int row, Object[] rowData) {
                    String invoiceId = rowData[0].toString();
                    showDataToForm(invoiceId);
                }

                @Override
                public void onDelete(int row, Object[] rowData) {
                    int result = JOptionPane.showConfirmDialog(null, 
                        "Delete Invoice " + rowData[1] + "?", 
                        "Confirm Delete", 
                        JOptionPane.YES_NO_OPTION);
                    if (result == JOptionPane.YES_OPTION) {
                        String invoiceId = rowData[0].toString();
                        boolean deleted = invoiceController.deleteInvoice(invoiceId);
                        if (deleted) {
                            JOptionPane.showMessageDialog(null, "Invoice berhasil dihapus");
                            loadDataTable();
                        } else {
                            JOptionPane.showMessageDialog(null, "Gagal menghapus invoice");
                        }
                    }
                }
            });
        
        } catch (Exception e) {
            System.out.println("Error loading data: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Data invoice gagal dipanggil: " + e.getMessage());
        } 
    }
    
    /**
     * Show selected invoice data to form for editing
     */
    private void showDataToForm(String invoiceId) {
        try {
            Invoice invoice = invoiceController.getInvoiceById(invoiceId);
            
            if (invoice != null) {
                lblTitleForm.setText("Form Edit Data Invoice");
                btnSimpan.setText("Update");
                
                // Fill form fields
                txtInvoiceId.setText(invoice.getInvoiceId());
                txtInvoiceNumber.setText(invoice.getInvoiceNumber());
                txtClientId.setText(invoice.getClientId());
                txtProjectId.setText(invoice.getProjectId());
                
                // Load client name
                if (invoice.getClientId() != null) {
                    Client client = clientController.getClientById(invoice.getClientId());
                    if (client != null) {
                        txtClientName.setText(client.getName());
                    }
                }
                
                // Load project name
                if (invoice.getProjectId() != null) {
                    Project project = projectController.getProjectById(invoice.getProjectId());
                    if (project != null) {
                        txtProjectName.setText(project.getName());
                    }
                }
                
                // Set dates
                txtStartDate.setDateFromSQLString(invoice.getInvoiceDate());
                txtEndDate.setDateFromSQLString(invoice.getDueDate());
                
                // Set amounts
                numberSubTotal.setDoubleValue(invoice.getSubtotal());
//                numberTaxAmount.setDoubleValue(invoice.getTaxAmount());
//                numberTotalAmount.setDoubleValue(invoice.getTotalAmount());
//                numberPaidAmount.setDoubleValue(invoice.getPaidAmount());
                
                // Set status
                cbStatus.setSelectedItem(invoice.getStatus());
                
                // Disable invoice ID field
                txtInvoiceId.setEnabled(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error showing data to form: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Data invoice gagal dipanggil: " + e.getMessage());
        }
    }
    
    /**
     * Save invoice data (create or update)
     */
    private void save() {
        try {
            // Validation
            if (txtInvoiceId.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Invoice ID tidak boleh kosong");
                return;
            }
            
            if (txtInvoiceNumber.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Invoice Number tidak boleh kosong");
                return;
            }
            
            if (txtClientId.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Client ID tidak boleh kosong");
                return;
            }
            
            if (txtStartDate.getDateSQLString() == null || txtStartDate.getDateSQLString().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Tanggal invoice tidak boleh kosong");
                return;
            }
            
            if (txtEndDate.getDateSQLString() == null || txtEndDate.getDateSQLString().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Tanggal jatuh tempo tidak boleh kosong");
                return;
            }
            
            // Create invoice object
            Invoice invoice = new Invoice();
            invoice.setInvoiceId(txtInvoiceId.getText().trim());
            invoice.setInvoiceNumber(txtInvoiceNumber.getText().trim());
            invoice.setClientId(txtClientId.getText().trim());
            invoice.setProjectId(txtProjectId.getText().trim().isEmpty() ? null : txtProjectId.getText().trim());
            invoice.setInvoiceDate(txtStartDate.getDateSQLString());
            invoice.setDueDate(txtEndDate.getDateSQLString());
            invoice.setSubtotal(numberSubTotal.getDoubleValue());
            // totalAmount = subtotal karena field tax tidak digunakan
            invoice.setTotalAmount(numberSubTotal.getDoubleValue());
            invoice.setTaxAmount(0.0);
            invoice.setPaidAmount(0.0);
            invoice.setStatus(cbStatus.getSelectedItem().toString());
            invoice.setCreatedBy("USR001"); // TODO: Get from session
            invoice.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            
            // Calculate totals automatically
//            calculateTotals();
            
            // Check if create or update
            boolean isCreate = btnSimpan.getText().equals("Tambah");
            
            String result;
            if (isCreate) {
                result = invoiceController.createInvoice(invoice);
            } else {
                result = invoiceController.updateInvoice(invoice);
            }
            
            JOptionPane.showMessageDialog(null, result);
            
            // Refresh table
            loadDataTable();
            
        } catch (Exception e) {
            System.out.println("Error saving invoice: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Error menyimpan invoice: " + e.getMessage());
        }
    }
    
    private void resetForm() {
        txtInvoiceId.setText("");
        txtInvoiceId.setEnabled(true);
        txtClientId.setText("");
        txtClientName.setText("");
        txtInvoiceNumber.setText("");
        txtStartDate.setDate(new Date());
        txtStartDate.setDateFromString("");
        cbStatus.setSelectedIndex(0);
        txtCari.setText("");
        txtEndDate.setDate(new Date());
        txtEndDate.setDateFromString("");
        numberSubTotal.setText("");
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
        txtInvoiceNumber = new components.RoundedTextField();
        cbStatus = new components.RoundedComboBox();
        roundedButton2 = new components.RoundedButton();
        btnSimpan = new components.RoundedButton();
        jLabel7 = new javax.swing.JLabel();
        txtInvoiceId = new components.RoundedTextField();
        jLabel8 = new javax.swing.JLabel();
        btnSearchClient = new components.RoundedButton();
        txtClientName = new components.RoundedTextField();
        txtStartDate = new components.CustomCalendar();
        txtEndDate = new components.CustomCalendar();
        numberSubTotal = new components.RoundedCurrencyField();
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 32767));
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtProjectId = new components.RoundedTextField();
        txtProjectName = new components.RoundedTextField();
        btnSearchProject = new components.RoundedButton();
        filler3 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 32767));
        roundedPanel2 = new components.RoundedPanel();
        customtable1 = new components.CustomTable();
        txtCari = new components.RoundedTextField();
        btnCari = new components.RoundedButton();
        jLabel6 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        mainPanel.setOpaque(false);

        roundedPanel1.setCornerRadius(20);
        roundedPanel1.setCustomHasBorder(false);

        lblTitleForm.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblTitleForm.setText("Form Tambah Data Faktur");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("Client ID : ");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setText("Invoice Number");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setText("Dibuat :");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setText("Jatuh Tempo : ");

        txtClientId.setCornerRadius(12);
        txtClientId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtClientIdActionPerformed(evt);
            }
        });

        txtInvoiceNumber.setCornerRadius(12);

        cbStatus.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "draft", "sent", "overdue", "paid" }));
        cbStatus.setCornerRadius(12);
        cbStatus.setDoubleBuffered(true);

        roundedButton2.setText("⟳ reset form");
        roundedButton2.setAutoscrolls(true);
        roundedButton2.setCustomColorScheme(components.RoundedButton.ColorScheme.SECONDARY);
        roundedButton2.setCustomCornerRadius(20);
        roundedButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                roundedButton2ActionPerformed(evt);
            }
        });

        btnSimpan.setText("Tambah");
        btnSimpan.setCustomCornerRadius(12);
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel7.setText("Invoice ID :");

        txtInvoiceId.setCornerRadius(12);
        txtInvoiceId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtInvoiceIdActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel8.setText("Subtotal : ");

        btnSearchClient.setText("🔍");
        btnSearchClient.setCustomColorScheme(components.RoundedButton.ColorScheme.PRIMARY);
        btnSearchClient.setCustomCornerRadius(12);
        btnSearchClient.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchClientActionPerformed(evt);
            }
        });

        txtClientName.setEditable(false);
        txtClientName.setCornerRadius(12);
        txtClientName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtClientNameActionPerformed(evt);
            }
        });

        txtStartDate.setDateFormat("dd MMMM yyyy");
        txtStartDate.setPlaceholder("tanggal dibuat");

        txtEndDate.setDateFormat("dd MMMM yyyy");
        txtEndDate.setPlaceholder("tgl jatuh tempo");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel10.setText("Status : ");

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel11.setText("Proyek ID : ");

        txtProjectId.setCornerRadius(12);
        txtProjectId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtProjectIdActionPerformed(evt);
            }
        });

        txtProjectName.setEditable(false);
        txtProjectName.setCornerRadius(12);
        txtProjectName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtProjectNameActionPerformed(evt);
            }
        });

        btnSearchProject.setText("🔍");
        btnSearchProject.setCustomColorScheme(components.RoundedButton.ColorScheme.PRIMARY);
        btnSearchProject.setCustomCornerRadius(12);
        btnSearchProject.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchProjectActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout roundedPanel1Layout = new javax.swing.GroupLayout(roundedPanel1);
        roundedPanel1.setLayout(roundedPanel1Layout);
        roundedPanel1Layout.setHorizontalGroup(
            roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundedPanel1Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(roundedPanel1Layout.createSequentialGroup()
                        .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2))
                        .addGap(8, 8, 8)
                        .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(roundedPanel1Layout.createSequentialGroup()
                                .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtClientName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtInvoiceId, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtInvoiceNumber, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundedPanel1Layout.createSequentialGroup()
                                        .addComponent(txtClientId, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnSearchClient, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(filler2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(roundedPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel11)
                                        .addGap(25, 25, 25)
                                        .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundedPanel1Layout.createSequentialGroup()
                                                .addComponent(txtProjectId, javax.swing.GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(btnSearchProject, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(txtProjectName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(filler3, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(numberSubTotal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addGroup(roundedPanel1Layout.createSequentialGroup()
                                                .addComponent(jLabel8)
                                                .addGap(0, 135, Short.MAX_VALUE))))
                                    .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(roundedButton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(roundedPanel1Layout.createSequentialGroup()
                                            .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jLabel4)
                                                .addComponent(jLabel5))
                                            .addGap(6, 6, 6)
                                            .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(txtEndDate, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(txtStartDate, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGap(30, 30, 30)
                                            .addComponent(jLabel10)
                                            .addGap(18, 18, 18)
                                            .addComponent(cbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                        .addContainerGap(15, Short.MAX_VALUE))
                    .addGroup(roundedPanel1Layout.createSequentialGroup()
                        .addComponent(lblTitleForm)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        roundedPanel1Layout.setVerticalGroup(
            roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundedPanel1Layout.createSequentialGroup()
                .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(roundedPanel1Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(lblTitleForm)
                        .addGap(14, 14, 14))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundedPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(roundedButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(roundedPanel1Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(jLabel7)
                        .addGap(27, 27, 27)
                        .addComponent(jLabel3)
                        .addGap(28, 28, 28)
                        .addComponent(jLabel2))
                    .addGroup(roundedPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(txtInvoiceId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(14, 14, 14)
                        .addComponent(txtInvoiceNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(14, 14, 14)
                        .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtClientId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnSearchClient, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(13, 13, 13)
                        .addComponent(txtClientName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(roundedPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(filler3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(roundedPanel1Layout.createSequentialGroup()
                                .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(roundedPanel1Layout.createSequentialGroup()
                                        .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel4)
                                            .addComponent(txtStartDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(roundedPanel1Layout.createSequentialGroup()
                                                .addComponent(jLabel5)
                                                .addGap(15, 15, 15))
                                            .addComponent(txtEndDate, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(cbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel10)))
                                .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(roundedPanel1Layout.createSequentialGroup()
                                        .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(roundedPanel1Layout.createSequentialGroup()
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                    .addComponent(txtProjectId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(btnSearchProject, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                            .addGroup(roundedPanel1Layout.createSequentialGroup()
                                                .addGap(17, 17, 17)
                                                .addComponent(jLabel11)))
                                        .addGap(18, 18, 18)
                                        .addComponent(txtProjectName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundedPanel1Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel8)
                                        .addGap(18, 18, 18)
                                        .addComponent(numberSubTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addComponent(filler2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );

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
        jLabel6.setText("Data Faktur");

        javax.swing.GroupLayout roundedPanel2Layout = new javax.swing.GroupLayout(roundedPanel2);
        roundedPanel2.setLayout(roundedPanel2Layout);
        roundedPanel2Layout.setHorizontalGroup(
            roundedPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundedPanel2Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(roundedPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(customtable1, javax.swing.GroupLayout.DEFAULT_SIZE, 894, Short.MAX_VALUE)
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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(roundedPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(123, 123, 123))
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(roundedPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addComponent(roundedPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addComponent(roundedPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(42, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(mainPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 960, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 116, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(mainPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 358, Short.MAX_VALUE))
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

    private void txtClientNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtClientNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtClientNameActionPerformed

    private void btnSearchClientActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchClientActionPerformed
        // TODO add your handling code here:
        PopupChooseClient.show(this, client -> {
            txtClientId.setText(client.getClientId());
            txtClientName.setText(client.getName());
        });
    }//GEN-LAST:event_btnSearchClientActionPerformed

    private void txtInvoiceIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtInvoiceIdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtInvoiceIdActionPerformed

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        // TODO add your handling code here:
        save();
        resetForm();
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void roundedButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_roundedButton2ActionPerformed
        // TODO add your handling code here:
        resetForm();
    }//GEN-LAST:event_roundedButton2ActionPerformed

    private void txtClientIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtClientIdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtClientIdActionPerformed

    private void txtProjectIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtProjectIdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtProjectIdActionPerformed

    private void txtProjectNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtProjectNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtProjectNameActionPerformed

    private void btnSearchProjectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchProjectActionPerformed
        // TODO add your handling code here:
        PopupChooseProject.show(this, project -> {
            txtProjectId.setText(project.getProject_id());
            txtProjectName.setText(project.getName());
        });
        
    }//GEN-LAST:event_btnSearchProjectActionPerformed

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
            java.util.logging.Logger.getLogger(TransactionInvoicePage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TransactionInvoicePage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TransactionInvoicePage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TransactionInvoicePage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TransactionInvoicePage().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private components.RoundedButton btnCari;
    private components.RoundedButton btnSearchClient;
    private components.RoundedButton btnSearchProject;
    private components.RoundedButton btnSimpan;
    private components.RoundedComboBox cbStatus;
    private components.CustomTable customtable1;
    private javax.swing.Box.Filler filler2;
    private javax.swing.Box.Filler filler3;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel lblTitleForm;
    private javax.swing.JPanel mainPanel;
    private components.RoundedCurrencyField numberSubTotal;
    private components.RoundedButton roundedButton2;
    private components.RoundedPanel roundedPanel1;
    private components.RoundedPanel roundedPanel2;
    private components.RoundedTextField txtCari;
    private components.RoundedTextField txtClientId;
    private components.RoundedTextField txtClientName;
    private components.CustomCalendar txtEndDate;
    private components.RoundedTextField txtInvoiceId;
    private components.RoundedTextField txtInvoiceNumber;
    private components.RoundedTextField txtProjectId;
    private components.RoundedTextField txtProjectName;
    private components.CustomCalendar txtStartDate;
    // End of variables declaration//GEN-END:variables
}
