/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import components.CustomTable;
import controller.ExpenseController;
import controller.ProjectController;
import helper.CurrencyHelper;
import java.awt.Container;
import java.awt.event.KeyEvent;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import model.Expense;
import model.Project;

/**
 *
 * @author Leonovo
 */
public class TransactionExpensePage extends javax.swing.JFrame {

    ExpenseController expenseCtr;
    DefaultTableModel tableModel;
    /**
     * Creates new form MasterUserForm
     */
    public TransactionExpensePage() {
        expenseCtr = new ExpenseController();
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
            List<Expense> listExpense = expenseCtr.getData(cariData);
            Object[] colums = {"ID", "Description", "Jumlah", "Tanggal", "Aksi"};
            tableModel.setColumnIdentifiers(colums);
            
            
            
            for (Expense project : listExpense) {
                tableModel.addRow(new Object[]{
                    project.getExpenseId(), 
                    project.getDescription(),
                    CurrencyHelper.formatForTable(project.getAmount(), true),
                    project.getExpenseDate(),
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
                        "Hapus Pengeluaran " + rowData[0] + "?", 
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
    
    private void showDataToForm(String expenseId) {
        try {
            Expense expense = expenseCtr.getExpenseById(expenseId);
            
            lblTitleForm.setText("Form Edit Data Project");
            
            btnSimpan.setText("Update");
            txtExpenseId.setText(expense.getExpenseId());
            txtCategoryTranId.setText(expense.getCategoryId());
            txtCategoryTranName.setText(expense.getCategoryName());
            txtExpenseDate.setDateFromSQLString(expense.getExpenseDate());
            txtReceiptNumber.setText(expense.getReceiptNumber());
            txtDesc.setText(expense.getDescription());
            numberAmount.setDoubleValue(expense.getAmount());
            
            txtExpenseId.setEnabled(false);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "data user gagal dipanggil "+e);
        }
    }
    
    private void save() {
        Expense expense = new Expense();
        expense.setExpenseId(txtExpenseId.getText());
        expense.setCategoryId(txtCategoryTranId.getText());
        expense.setDescription(txtDesc.getText());
        expense.setProjectId(txtProjectId.getText());
        expense.setAmount(numberAmount.getDoubleValue());
        expense.setExpenseDate(txtExpenseDate.getDateSQLString());
        expense.setReceiptNumber(txtReceiptNumber.getText());
        expense.setCreatedBy("");
        
        
        boolean isCreateUser = btnSimpan.getText().equals("Tambah");
        
        if (isCreateUser) {
            String create = expenseCtr.createExpense(expense);
            JOptionPane.showMessageDialog(null, create);
        } else {
            String update = expenseCtr.updateExpense(expense);
            JOptionPane.showMessageDialog(null, update);
        }


        loadDataTable();
       
    }
    
    private void resetForm() {
        txtExpenseId.setText("");
        txtExpenseId.setEnabled(true);
        txtCategoryTranId.setText("");
        txtCategoryTranName.setText("");
        txtExpenseDate.setToday();
        txtCari.setText("");
        txtExpenseDate.setDateFromString("");
        numberAmount.setText("");
        btnSimpan.setText("Tambah");
        lblTitleForm.setText("Form Tambah Data Pengeluaran");
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
        txtCategoryTranId = new components.RoundedTextField();
        roundedButton2 = new components.RoundedButton();
        btnSimpan = new components.RoundedButton();
        jLabel7 = new javax.swing.JLabel();
        txtExpenseId = new components.RoundedTextField();
        btnSearchCategorytran = new components.RoundedButton();
        txtCategoryTranName = new components.RoundedTextField();
        numberAmount = new components.RoundedCurrencyField();
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 32767));
        jLabel11 = new javax.swing.JLabel();
        txtProjectId = new components.RoundedTextField();
        txtProjectName = new components.RoundedTextField();
        btnSearchProject = new components.RoundedButton();
        filler3 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 32767));
        jLabel9 = new javax.swing.JLabel();
        txtDesc = new components.RoundedTextArea();
        txtReceiptNumber = new components.RoundedTextField();
        txtExpenseDate = new components.CustomCalendar();
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
        lblTitleForm.setText("Form Tambah Data Pengeluaran");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("Kategori ");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setText("Tanggal");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setText("Nomor Bukti :");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setText("Jumlah : ");

        txtCategoryTranId.setCornerRadius(12);
        txtCategoryTranId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCategoryTranIdActionPerformed(evt);
            }
        });

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
        jLabel7.setText("Expense ID :");

        txtExpenseId.setCornerRadius(12);
        txtExpenseId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtExpenseIdActionPerformed(evt);
            }
        });

        btnSearchCategorytran.setText("🔍");
        btnSearchCategorytran.setCustomColorScheme(components.RoundedButton.ColorScheme.PRIMARY);
        btnSearchCategorytran.setCustomCornerRadius(12);
        btnSearchCategorytran.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchCategorytranActionPerformed(evt);
            }
        });

        txtCategoryTranName.setEditable(false);
        txtCategoryTranName.setCornerRadius(12);
        txtCategoryTranName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCategoryTranNameActionPerformed(evt);
            }
        });

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

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel9.setText("Deskripsi");

        txtDesc.setBorderThickness(1);

        txtReceiptNumber.setCornerRadius(12);
        txtReceiptNumber.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtReceiptNumberActionPerformed(evt);
            }
        });

        txtExpenseDate.setDateFormat("dd MMMM yyyy");
        txtExpenseDate.setPlaceholder("tgl pengeluaran");

        javax.swing.GroupLayout roundedPanel1Layout = new javax.swing.GroupLayout(roundedPanel1);
        roundedPanel1.setLayout(roundedPanel1Layout);
        roundedPanel1Layout.setHorizontalGroup(
            roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundedPanel1Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(roundedPanel1Layout.createSequentialGroup()
                        .addComponent(lblTitleForm)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(roundedButton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(roundedPanel1Layout.createSequentialGroup()
                        .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2)
                            .addComponent(jLabel9))
                        .addGap(18, 18, 18)
                        .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtCategoryTranName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundedPanel1Layout.createSequentialGroup()
                                .addComponent(txtCategoryTranId, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnSearchCategorytran, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtExpenseId, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtExpenseDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtDesc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(filler2, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, roundedPanel1Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jLabel4))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, roundedPanel1Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel11)
                                    .addComponent(jLabel5))
                                .addGap(31, 31, 31)
                                .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(roundedPanel1Layout.createSequentialGroup()
                                        .addComponent(txtProjectId, javax.swing.GroupLayout.PREFERRED_SIZE, 314, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnSearchProject, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addComponent(numberAmount, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtReceiptNumber, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnSimpan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(roundedPanel1Layout.createSequentialGroup()
                                .addGap(120, 120, 120)
                                .addComponent(txtProjectName, javax.swing.GroupLayout.PREFERRED_SIZE, 368, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(54, 54, 54)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(filler3, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        roundedPanel1Layout.setVerticalGroup(
            roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundedPanel1Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTitleForm)
                    .addComponent(roundedButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(roundedPanel1Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(filler3, javax.swing.GroupLayout.DEFAULT_SIZE, 206, Short.MAX_VALUE)
                                .addComponent(filler2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(roundedPanel1Layout.createSequentialGroup()
                                .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtReceiptNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel5)
                                    .addComponent(numberAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtProjectId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel11)
                                    .addComponent(btnSearchProject, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(20, 20, 20)
                                .addComponent(txtProjectName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(roundedPanel1Layout.createSequentialGroup()
                        .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(roundedPanel1Layout.createSequentialGroup()
                                .addGap(31, 31, 31)
                                .addComponent(jLabel7)
                                .addGap(27, 27, 27)
                                .addComponent(jLabel3)
                                .addGap(28, 28, 28)
                                .addComponent(jLabel2))
                            .addGroup(roundedPanel1Layout.createSequentialGroup()
                                .addGap(28, 28, 28)
                                .addComponent(txtExpenseId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtExpenseDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtCategoryTranId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnSearchCategorytran, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(13, 13, 13)
                        .addComponent(txtCategoryTranName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(22, 22, 22)
                        .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtDesc, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9))))
                .addContainerGap(19, Short.MAX_VALUE))
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
        jLabel6.setText("Data Pengeluaran");

        javax.swing.GroupLayout roundedPanel2Layout = new javax.swing.GroupLayout(roundedPanel2);
        roundedPanel2.setLayout(roundedPanel2Layout);
        roundedPanel2Layout.setHorizontalGroup(
            roundedPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundedPanel2Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(roundedPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(roundedPanel2Layout.createSequentialGroup()
                        .addComponent(customtable1, javax.swing.GroupLayout.PREFERRED_SIZE, 882, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(roundedPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnCari, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34))))
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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(roundedPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(roundedPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(163, 163, 163))
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(roundedPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(roundedPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(42, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(mainPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 925, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 135, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(mainPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 326, Short.MAX_VALUE))
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

    private void txtCategoryTranNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCategoryTranNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCategoryTranNameActionPerformed

    private void btnSearchCategorytranActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchCategorytranActionPerformed
        // TODO add your handling code here:
        PopupChooseTransCategory.show(this, category -> {
            txtCategoryTranId.setText(category.getCategoryId());
            txtCategoryTranName.setText(category.getNama());
        });
    }//GEN-LAST:event_btnSearchCategorytranActionPerformed

    private void txtExpenseIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtExpenseIdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtExpenseIdActionPerformed

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        // TODO add your handling code here:
        save();
        resetForm();
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void roundedButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_roundedButton2ActionPerformed
        // TODO add your handling code here:
        resetForm();
    }//GEN-LAST:event_roundedButton2ActionPerformed

    private void txtCategoryTranIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCategoryTranIdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCategoryTranIdActionPerformed

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

    private void txtReceiptNumberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtReceiptNumberActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtReceiptNumberActionPerformed

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
            java.util.logging.Logger.getLogger(TransactionExpensePage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TransactionExpensePage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TransactionExpensePage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TransactionExpensePage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TransactionExpensePage().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private components.RoundedButton btnCari;
    private components.RoundedButton btnSearchCategorytran;
    private components.RoundedButton btnSearchProject;
    private components.RoundedButton btnSimpan;
    private components.CustomTable customtable1;
    private javax.swing.Box.Filler filler2;
    private javax.swing.Box.Filler filler3;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel lblTitleForm;
    private javax.swing.JPanel mainPanel;
    private components.RoundedCurrencyField numberAmount;
    private components.RoundedButton roundedButton2;
    private components.RoundedPanel roundedPanel1;
    private components.RoundedPanel roundedPanel2;
    private components.RoundedTextField txtCari;
    private components.RoundedTextField txtCategoryTranId;
    private components.RoundedTextField txtCategoryTranName;
    private components.RoundedTextArea txtDesc;
    private components.CustomCalendar txtExpenseDate;
    private components.RoundedTextField txtExpenseId;
    private components.RoundedTextField txtProjectId;
    private components.RoundedTextField txtProjectName;
    private components.RoundedTextField txtReceiptNumber;
    // End of variables declaration//GEN-END:variables
}
