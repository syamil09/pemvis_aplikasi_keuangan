package view;

import components.CustomTable;
import components.RoundedButton;
import components.RoundedComboBox;
import components.RoundedCurrencyField;
import components.RoundedPanel;
import components.RoundedTextField;
import controller.EmployeeController;
import controller.PayrollController;
import helper.CurrencyHelper;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.util.Calendar;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.LayoutStyle;
import javax.swing.table.DefaultTableModel;
import model.Employee;
import model.Payroll;

/**
 * Transaction Payroll Page - Programmatic UI Version
 * Halaman untuk manajemen penggajian karyawan
 * 
 * @author Leonovo
 */
public class TransactionPayrollPage2 extends JFrame {
    
    // Controllers
    private PayrollController payrollController;
    private EmployeeController employeeController;
    private DefaultTableModel tableModel;
    
    // Period tracking
    private int selectedPeriodMonth;
    private int selectedPeriodYear;
    
    // UI Components - Main Panel
    private JPanel mainPanel;
    private RoundedPanel formPanel;
    private RoundedPanel tablePanel;
    
    // Form Components
    private JLabel lblTitleForm;
    private JLabel lblPayrollId;
    private JLabel lblPeriodMonth;
    private JLabel lblPeriodYear;
    private JLabel lblEmployeeId;
    private JLabel lblTotalGaji;
    private JLabel lblStatus;
    
    private RoundedTextField txtPayrollId;
    private RoundedComboBox<String> cbPeriodMonth;
    private RoundedComboBox<Integer> cbPeriodYear;
    private RoundedTextField txtEmployeeId;
    private RoundedTextField txtEmployeeName;
    private RoundedCurrencyField txtTotalGaji;
    private RoundedComboBox<String> cbStatus;
    
    private RoundedButton btnSearchEmployee;
    private RoundedButton btnGeneratePayroll;
    private RoundedButton btnSimpan;
    private RoundedButton btnReset;
    
    // Table Components
    private JLabel lblTableTitle;
    private RoundedTextField txtCari;
    private RoundedButton btnCari;
    private CustomTable customTable;
    
    /**
     * Constructor
     */
    public TransactionPayrollPage2() {
        payrollController = new PayrollController();
        employeeController = new EmployeeController();
        tableModel = new DefaultTableModel();
        
        // Set current period as default
        Calendar cal = Calendar.getInstance();
        selectedPeriodMonth = cal.get(Calendar.MONTH) + 1;
        selectedPeriodYear = cal.get(Calendar.YEAR);
        
        initComponents();
        loadDataTable();
    }
    
    /**
     * Get main panel for dashboard integration
     */
    public JPanel getMainPanel() {
        return mainPanel;
    }
    
    /**
     * Initialize all UI components
     */
    private void initComponents() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Transaksi Penggajian");
        
        mainPanel = new JPanel();
        mainPanel.setOpaque(false);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        
        // Create panels
        createFormPanel();
        createTablePanel();
        
        // Add panels to main
        mainPanel.add(formPanel);
        mainPanel.add(javax.swing.Box.createVerticalStrut(10));
        mainPanel.add(tablePanel);
        
        // Set frame layout
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(mainPanel, GroupLayout.DEFAULT_SIZE, 960, Short.MAX_VALUE)
                    .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(mainPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap())
        );
        
        pack();
    }
    
    /**
     * Create form panel for payroll entry
     */
    private void createFormPanel() {
        formPanel = new RoundedPanel();
        formPanel.setCornerRadius(20);
        formPanel.setCustomHasBorder(false);
        formPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 300));
        
        // Title
        lblTitleForm = new JLabel("Form Tambah Data Payroll");
        lblTitleForm.setFont(new Font("Segoe UI", Font.BOLD, 18));
        
        // Labels
        lblPayrollId = new JLabel("Payroll ID:");
        lblPayrollId.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        lblPeriodMonth = new JLabel("Bulan:");
        lblPeriodMonth.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        lblPeriodYear = new JLabel("Tahun:");
        lblPeriodYear.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        lblEmployeeId = new JLabel("Karyawan:");
        lblEmployeeId.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        lblTotalGaji = new JLabel("Total Gaji:");
        lblTotalGaji.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        lblStatus = new JLabel("Status:");
        lblStatus.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        // Input fields
        txtPayrollId = new RoundedTextField();
        txtPayrollId.setCornerRadius(12);
        txtPayrollId.setPreferredSize(new Dimension(200, 33));
        
        // Month combo box
        String[] months = {"Januari", "Februari", "Maret", "April", "Mei", "Juni", 
                          "Juli", "Agustus", "September", "Oktober", "November", "Desember"};
        cbPeriodMonth = new RoundedComboBox<>(months);
        cbPeriodMonth.setCornerRadius(12);
        cbPeriodMonth.setSelectedIndex(selectedPeriodMonth - 1);
        cbPeriodMonth.setPreferredSize(new Dimension(150, 33));
        
        // Year combo box
        Integer[] years = new Integer[5];
        for (int i = 0; i < 5; i++) {
            years[i] = selectedPeriodYear - 2 + i;
        }
        cbPeriodYear = new RoundedComboBox<>(years);
        cbPeriodYear.setCornerRadius(12);
        cbPeriodYear.setSelectedItem(selectedPeriodYear);
        cbPeriodYear.setPreferredSize(new Dimension(100, 33));
        
        txtEmployeeId = new RoundedTextField();
        txtEmployeeId.setCornerRadius(12);
        txtEmployeeId.setPreferredSize(new Dimension(100, 33));
        
        txtEmployeeName = new RoundedTextField();
        txtEmployeeName.setCornerRadius(12);
        txtEmployeeName.setEditable(false);
        txtEmployeeName.setPreferredSize(new Dimension(400, 33));
        
        txtTotalGaji = new RoundedCurrencyField();
        txtTotalGaji.setCornerRadius(12);
        txtTotalGaji.setPreferredSize(new Dimension(200, 33));
        
        String[] statusOptions = {"draft", "pending", "paid"};
        cbStatus = new RoundedComboBox<>(statusOptions);
        cbStatus.setCornerRadius(12);
        cbStatus.setPreferredSize(new Dimension(150, 33));
        
        // Buttons - Custom search button with icon
        btnSearchEmployee = new RoundedButton("") {
            @Override
            protected void paintComponent(java.awt.Graphics g) {
                super.paintComponent(g);
                
                // Draw magnifying glass icon
                java.awt.Graphics2D g2 = (java.awt.Graphics2D) g.create();
                g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, 
                                   java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setRenderingHint(java.awt.RenderingHints.KEY_STROKE_CONTROL,
                                   java.awt.RenderingHints.VALUE_STROKE_PURE);
                
                int w = getWidth();
                int h = getHeight();
                int centerX = w / 2;
                int centerY = h / 2 - 1;
                
                // Circle (lens) - 10px diameter
                int circleSize = 10;
                g2.setColor(Color.WHITE);
                g2.setStroke(new java.awt.BasicStroke(2.0f, java.awt.BasicStroke.CAP_ROUND, 
                                                       java.awt.BasicStroke.JOIN_ROUND));
                g2.drawOval(centerX - circleSize/2, centerY - circleSize/2, circleSize, circleSize);
                
                // Handle (stick) - diagonal line
                int handleStartX = centerX + (int)(circleSize * 0.35);
                int handleStartY = centerY + (int)(circleSize * 0.35);
                int handleEndX = centerX + (int)(circleSize * 0.7);
                int handleEndY = centerY + (int)(circleSize * 0.7);
                g2.drawLine(handleStartX, handleStartY, handleEndX, handleEndY);
                
                g2.dispose();
            }
        };
        btnSearchEmployee.setCustomCornerRadius(12);
        btnSearchEmployee.setCustomColorScheme(RoundedButton.ColorScheme.PRIMARY);
        btnSearchEmployee.setPreferredSize(new Dimension(48, 33));
        btnSearchEmployee.addActionListener(e -> searchEmployee());
        
        btnGeneratePayroll = new RoundedButton("⚡ Generate Payroll");
        btnGeneratePayroll.setCustomCornerRadius(12);
        btnGeneratePayroll.setCustomColorScheme(RoundedButton.ColorScheme.SUCCESS);
        btnGeneratePayroll.setPreferredSize(new Dimension(180, 33));
        btnGeneratePayroll.addActionListener(e -> generatePayrollForPeriod());
        
        btnSimpan = new RoundedButton("Tambah");
        btnSimpan.setCustomCornerRadius(12);
        btnSimpan.setPreferredSize(new Dimension(200, 39));
        btnSimpan.addActionListener(e -> {
            save();
            resetForm();
        });
        
        btnReset = new RoundedButton("⟳ Reset Form");
        btnReset.setCustomCornerRadius(20);
        btnReset.setCustomColorScheme(RoundedButton.ColorScheme.SECONDARY);
        btnReset.addActionListener(e -> resetForm());
        
        // Layout
        GroupLayout formLayout = new GroupLayout(formPanel);
        formPanel.setLayout(formLayout);
        formLayout.setHorizontalGroup(
            formLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(formLayout.createSequentialGroup()
                    .addGap(23, 23, 23)
                    .addGroup(formLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        // Title row
                        .addGroup(formLayout.createSequentialGroup()
                            .addComponent(lblTitleForm)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnReset, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        // Form fields
                        .addGroup(formLayout.createSequentialGroup()
                            .addGroup(formLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(lblPayrollId)
                                .addComponent(lblPeriodMonth)
                                .addComponent(lblEmployeeId))
                            .addGap(20, 20, 20)
                            .addGroup(formLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtPayrollId, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGroup(formLayout.createSequentialGroup()
                                    .addComponent(cbPeriodMonth, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                    .addGap(10, 10, 10)
                                    .addComponent(lblPeriodYear)
                                    .addGap(10, 10, 10)
                                    .addComponent(cbPeriodYear, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                    .addGap(20, 20, 20)
                                    .addComponent(btnGeneratePayroll, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGroup(formLayout.createSequentialGroup()
                                    .addComponent(txtEmployeeId, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                    .addGap(10, 10, 10)
                                    .addComponent(btnSearchEmployee, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addComponent(txtEmployeeName, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE))
                            .addGap(50, 50, 50)
                            .addGroup(formLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(lblTotalGaji)
                                .addComponent(lblStatus))
                            .addGap(20, 20, 20)
                            .addGroup(formLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(txtTotalGaji, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(cbStatus, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnSimpan, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
                    .addGap(23, 23, 23))
        );
        formLayout.setVerticalGroup(
            formLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(formLayout.createSequentialGroup()
                    .addGap(18, 18, 18)
                    .addGroup(formLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(lblTitleForm)
                        .addComponent(btnReset, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE))
                    .addGap(20, 20, 20)
                    .addGroup(formLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(lblPayrollId)
                        .addComponent(txtPayrollId, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblTotalGaji)
                        .addComponent(txtTotalGaji, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addGap(14, 14, 14)
                    .addGroup(formLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(lblPeriodMonth)
                        .addComponent(cbPeriodMonth, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblPeriodYear)
                        .addComponent(cbPeriodYear, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnGeneratePayroll, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblStatus)
                        .addComponent(cbStatus, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addGap(14, 14, 14)
                    .addGroup(formLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(lblEmployeeId)
                        .addComponent(txtEmployeeId, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnSearchEmployee, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addGap(14, 14, 14)
                    .addGroup(formLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(txtEmployeeName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnSimpan, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addGap(18, 18, 18))
        );
    }
    
    /**
     * Create table panel for payroll list
     */
    private void createTablePanel() {
        tablePanel = new RoundedPanel();
        tablePanel.setCornerRadius(20);
        tablePanel.setCustomHasBorder(false);
        tablePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 350));
        
        lblTableTitle = new JLabel("Data Payroll");
        lblTableTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        
        txtCari = new RoundedTextField();
        txtCari.setCornerRadius(12);
        txtCari.setPlaceholder("Cari data...");
        txtCari.setPreferredSize(new Dimension(157, 33));
        txtCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                    loadDataTable();
                }
            }
        });
        
        btnCari = new RoundedButton("🔍 Cari");
        btnCari.setCustomCornerRadius(12);
        btnCari.setPreferredSize(new Dimension(77, 33));
        btnCari.addActionListener(e -> loadDataTable());
        
        customTable = new CustomTable();
        customTable.setBackground(new Color(236, 240, 241));
        customTable.setHeaderBackgroundColor(new Color(236, 240, 241));
        customTable.setPreferredSize(new Dimension(900, 219));
        
        // Layout
        GroupLayout tableLayout = new GroupLayout(tablePanel);
        tablePanel.setLayout(tableLayout);
        tableLayout.setHorizontalGroup(
            tableLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(tableLayout.createSequentialGroup()
                    .addGap(25, 25, 25)
                    .addGroup(tableLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(customTable, GroupLayout.DEFAULT_SIZE, 900, Short.MAX_VALUE)
                        .addGroup(tableLayout.createSequentialGroup()
                            .addComponent(lblTableTitle)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtCari, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addGap(12, 12, 12)
                            .addComponent(btnCari, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                    .addGap(25, 25, 25))
        );
        tableLayout.setVerticalGroup(
            tableLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(GroupLayout.Alignment.TRAILING, tableLayout.createSequentialGroup()
                    .addGap(15, 15, 15)
                    .addGroup(tableLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(lblTableTitle)
                        .addComponent(txtCari, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnCari, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addGap(12, 12, 12)
                    .addComponent(customTable, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addGap(20, 20, 20))
        );
    }
    
    /**
     * Load data to table
     */
    private void loadDataTable() {
        tableModel = new DefaultTableModel();
        try {
            String cariData = txtCari.getText();
            List<Payroll> listPayroll = payrollController.getData(cariData);
            Object[] columns = {"Payroll ID", "Employee", "Period", "Basic Salary", "Allowance", "Deductions", "Total Gaji", "Status", "Aksi"};
            tableModel.setColumnIdentifiers(columns);
            
            for (Payroll payroll : listPayroll) {
                double deductions = (payroll.getTaxPph21() != null ? payroll.getTaxPph21() : 0) +
                                  (payroll.getBpjs() != null ? payroll.getBpjs() : 0) +
                                  (payroll.getOtherDeductions() != null ? payroll.getOtherDeductions() : 0);
                
                // Format period as dd/MM/yyyy (e.g., "01/01/2025")
                String periodFormatted = String.format("01/%02d/%04d", 
                    payroll.getPeriodMonth(), 
                    payroll.getPeriodYear());
                
                tableModel.addRow(new Object[]{
                    payroll.getPayrollId(),
                    payroll.getEmployeeName(),
                    periodFormatted,  // Changed from getFormattedPeriod()
                    CurrencyHelper.formatForTable(payroll.getBasicSalary(), true),
                    CurrencyHelper.formatForTable(payroll.getAllowance(), true),
                    CurrencyHelper.formatForTable(deductions, true),
                    CurrencyHelper.formatForTable(payroll.getTotalGaji(), true),
                    payroll.getStatusDisplay(),
                    ""
                });
            }
            
            customTable.setModel(tableModel);
            
            // Setup column configurations
            CustomTable.ColumnConfig[] configs = {
                new CustomTable.ColumnConfig(100, CustomTable.ALIGN_CENTER),
                new CustomTable.ColumnConfig(150, CustomTable.ALIGN_LEFT),
                new CustomTable.ColumnConfig(120, CustomTable.ALIGN_CENTER),
                new CustomTable.ColumnConfig(120, CustomTable.ALIGN_RIGHT),
                new CustomTable.ColumnConfig(120, CustomTable.ALIGN_RIGHT),
                new CustomTable.ColumnConfig(120, CustomTable.ALIGN_RIGHT),
                new CustomTable.ColumnConfig(130, CustomTable.ALIGN_RIGHT),
                new CustomTable.ColumnConfig(80, CustomTable.ALIGN_CENTER),
                new CustomTable.ColumnConfig(120, CustomTable.ALIGN_CENTER)
            };
            customTable.setColumnConfigs(configs);
            
            customTable.setShowActionButtons(true);
            customTable.setActionButtonListener(new CustomTable.ActionButtonListener() {
                @Override
                public void onEdit(int row, Object[] rowData) {
                    String payrollId = rowData[0].toString();
                    showDataToForm(payrollId);
                }

                @Override
                public void onDelete(int row, Object[] rowData) {
                    int result = JOptionPane.showConfirmDialog(null, 
                        "Hapus Payroll untuk " + rowData[1] + "?", 
                        "Konfirmasi", 
                        JOptionPane.YES_NO_OPTION);
                    if (result == JOptionPane.YES_OPTION) {
                        String payrollId = rowData[0].toString();
                        String message = payrollController.delete(payrollId);
                        JOptionPane.showMessageDialog(null, message);
                        loadDataTable();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error loading data: " + e.getMessage());
        }
    }
    
    /**
     * Show selected payroll data to form
     */
    private void showDataToForm(String payrollId) {
        try {
            Payroll payroll = payrollController.getById(payrollId);
            
            if (payroll != null) {
                lblTitleForm.setText("Form Edit Data Payroll");
                btnSimpan.setText("Update");
                
                txtPayrollId.setText(payroll.getPayrollId());
                cbPeriodMonth.setSelectedIndex(payroll.getPeriodMonth() - 1);
                cbPeriodYear.setSelectedItem(payroll.getPeriodYear());
                
                if (payroll.getEmployeeId() != null) {
                    Employee employee = employeeController.getById(payroll.getEmployeeId());
                    if (employee != null) {
                        txtEmployeeId.setText(employee.getEmployeeId());
                        txtEmployeeName.setText(employee.getFullname());
                    }
                }
                
                txtTotalGaji.setDoubleValue(payroll.getTotalGaji() != null ? payroll.getTotalGaji() : 0.0);
                cbStatus.setSelectedItem(payroll.getStatus());
                
                txtPayrollId.setEnabled(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }
    
    /**
     * Save payroll data
     */
    private void save() {
        try {
            if (txtPayrollId.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Payroll ID tidak boleh kosong");
                return;
            }
            
            if (txtEmployeeId.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Employee ID tidak boleh kosong");
                return;
            }
            
            boolean isCreate = btnSimpan.getText().equals("Tambah");
            
            Payroll payroll;
            if (isCreate) {
                // Create new payroll object from scratch
                payroll = new Payroll();
                payroll.setPayrollId(txtPayrollId.getText().trim());
                payroll.setEmployeeId(txtEmployeeId.getText().trim());
                payroll.setPeriodMonth(cbPeriodMonth.getSelectedIndex() + 1);
                payroll.setPeriodYear((Integer) cbPeriodYear.getSelectedItem());
                payroll.setTotalGaji(txtTotalGaji.getDoubleValue());
                payroll.setStatus((String) cbStatus.getSelectedItem());
            } else {
                // Update: Load existing data first to preserve salary fields
                payroll = payrollController.getById(txtPayrollId.getText().trim());
                if (payroll == null) {
                    JOptionPane.showMessageDialog(null, "Data payroll tidak ditemukan");
                    return;
                }
                
                // Only update editable fields
                payroll.setPeriodMonth(cbPeriodMonth.getSelectedIndex() + 1);
                payroll.setPeriodYear((Integer) cbPeriodYear.getSelectedItem());
                payroll.setStatus((String) cbStatus.getSelectedItem());
                // Note: Salary fields (basic, allowance, tax, bpjs, deductions) remain unchanged
                // They should only be changed from employee master data or regeneration
            }
            
            String result = isCreate ? payrollController.create(payroll) : payrollController.update(payroll);
            
            JOptionPane.showMessageDialog(null, result);
            loadDataTable();
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }
    
    /**
     * Generate payroll for all active employees in selected period
     */
    private void generatePayrollForPeriod() {
        try {
            int month = cbPeriodMonth.getSelectedIndex() + 1;
            int year = (Integer) cbPeriodYear.getSelectedItem();
            
            int confirm = JOptionPane.showConfirmDialog(null,
                "Generate payroll untuk semua karyawan aktif pada periode " + 
                cbPeriodMonth.getSelectedItem() + " " + year + "?",
                "Konfirmasi Generate Payroll",
                JOptionPane.YES_NO_OPTION);
            
            if (confirm == JOptionPane.YES_OPTION) {
                String result = payrollController.generatePayrollForPeriod(month, year);
                JOptionPane.showMessageDialog(null, result);
                loadDataTable();
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }
    
    /**
     * Search employee using popup
     */
    private void searchEmployee() {
//        PopupChooseEmployee.show(this, employee -> {
//            txtEmployeeId.setText(employee.getEmployeeId());
//            txtEmployeeName.setText(employee.getFullname());
//        });
    }
    
    /**
     * Reset form to initial state
     */
    private void resetForm() {
        txtPayrollId.setText("");
        txtPayrollId.setEnabled(true);
        txtEmployeeId.setText("");
        txtEmployeeName.setText("");
        txtTotalGaji.setText("");
        cbPeriodMonth.setSelectedIndex(selectedPeriodMonth - 1);
        cbPeriodYear.setSelectedItem(selectedPeriodYear);
        cbStatus.setSelectedIndex(0);
        txtCari.setText("");
        btnSimpan.setText("Tambah");
        lblTitleForm.setText("Form Tambah Data Payroll");
    }
    
    /**
     * Main method for testing
     */
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            new TransactionPayrollPage2().setVisible(true);
        });
    }
}
