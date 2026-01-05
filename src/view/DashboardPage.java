/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import components.CustomTable;
import controller.DashboardController;
import helper.CurrencyHelper;
import model.JournalEntry;
import java.awt.Container;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Leonovo
 */
public class DashboardPage extends javax.swing.JFrame {

    private DashboardController dashboardController;
    
    /**
     * Creates new form MasterUserForm
     */
    public DashboardPage() {
        dashboardController = new DashboardController();
        initComponents();
        loadDashboardData();
    }
    
    /**
     * Load real data to dashboard cards and table
     */
    private void loadDashboardData() {
        System.out.println("===== DEBUG LOAD DASHBOARD DATA =====");
        
        // load active project count
        int projectCount = dashboardController.getActiveProjectCount();
        lblCoutProject.setText(String.valueOf(projectCount));
        System.out.println("[DEBUG] Active projects: " + projectCount);
        
        // load current month income
        double currentIncome = dashboardController.getCurrentMonthIncome();
        double lastIncome = dashboardController.getLastMonthIncome();
        lblTotalIncome.setText(CurrencyHelper.formatToRupiahWithoutDecimal(currentIncome));
        System.out.println("[DEBUG] Current month income: " + currentIncome);
        System.out.println("[DEBUG] Last month income: " + lastIncome);
        
        // calculate income change percentage
        double incomeChange = dashboardController.calculatePercentageChange(currentIncome, lastIncome);
        System.out.println("[DEBUG] Income change %: " + incomeChange);
        if (incomeChange >= 0) {
            lblStatusIncome.setText(String.format("Naik %.0f%% dari bulan lalu", incomeChange));
            iconStatusIncome.setImageIconPath("images/icon_up.png");
        } else {
            lblStatusIncome.setText(String.format("Turun %.0f%% dari bulan lalu", Math.abs(incomeChange)));
            iconStatusIncome.setImageIconPath("images/icon_down.png");
        }
        
        // load current month expense
        double currentExpense = dashboardController.getCurrentMonthExpense();
        double lastExpense = dashboardController.getLastMonthExpense();
        lblTotalOutcome.setText(CurrencyHelper.formatToRupiahWithoutDecimal(currentExpense));
        System.out.println("[DEBUG] Current month expense: " + currentExpense);
        System.out.println("[DEBUG] Last month expense: " + lastExpense);
        
        // calculate expense change percentage
        double expenseChange = dashboardController.calculatePercentageChange(currentExpense, lastExpense);
        System.out.println("[DEBUG] Expense change %: " + expenseChange);
        if (expenseChange >= 0) {
            lblStatusOutcome.setText(String.format("Naik %.0f%% dari bulan lalu", expenseChange));
            iconStatusOutcome.setImageIconPath("images/icon_up.png");
        } else {
            lblStatusOutcome.setText(String.format("Turun %.0f%% dari bulan lalu", Math.abs(expenseChange)));
            iconStatusOutcome.setImageIconPath("images/icon_down.png");
        }
        
        // calculate net profit (income - expense)
        double currentProfit = currentIncome - currentExpense;
        double lastProfit = lastIncome - lastExpense;
        lblTotalProfit.setText(CurrencyHelper.formatToRupiahWithoutDecimal(currentProfit));
        System.out.println("[DEBUG] Current month profit: " + currentProfit);
        System.out.println("[DEBUG] Last month profit: " + lastProfit);
        
        // calculate profit change percentage
        double profitChange = dashboardController.calculatePercentageChange(currentProfit, lastProfit);
        System.out.println("[DEBUG] Profit change %: " + profitChange);
        if (profitChange >= 0) {
            lblStatusProfit.setText(String.format("Naik %.0f%% dari bulan lalu", profitChange));
            iconStatusProfit.setImageIconPath("images/icon_up.png");
        } else {
            lblStatusProfit.setText(String.format("Turun %.0f%% dari bulan lalu", Math.abs(profitChange)));
            iconStatusProfit.setImageIconPath("images/icon_down.png");
        }
        
        // set profit color based on positive/negative
        if (currentProfit >= 0) {
            lblTotalProfit.setForeground(new java.awt.Color(46, 204, 113)); // green
        } else {
            lblTotalProfit.setForeground(new java.awt.Color(231, 76, 60)); // red
        }
        
        System.out.println("===== END DEBUG =====");
        
        // load recent transactions to table
        loadRecentTransactions();
    }
    
    /**
     * Load recent transactions to table
     */
    private void loadRecentTransactions() {
        DefaultTableModel tableModel = new DefaultTableModel();
        Object[] columns = {"Tipe", "Tanggal", "Deskripsi", "Jumlah"};
        tableModel.setColumnIdentifiers(columns);
        
        List<JournalEntry> transactions = dashboardController.getRecentTransactions(10);
        for (JournalEntry entry : transactions) {
            tableModel.addRow(new Object[]{
                entry.getTransactionTypeLabel(),
                entry.getEntryDate(),
                entry.getDescription(),
                CurrencyHelper.formatForTable(entry.getAmount(), true)
            });
        }
        
        customTable1.setModel(tableModel);
        
        // setup column widths
        CustomTable.ColumnConfig[] configs = {
            new CustomTable.ColumnConfig(100, CustomTable.ALIGN_CENTER),
            new CustomTable.ColumnConfig(100, CustomTable.ALIGN_CENTER),
            new CustomTable.ColumnConfig(300, CustomTable.ALIGN_LEFT),
            new CustomTable.ColumnConfig(120, CustomTable.ALIGN_RIGHT)
        };
        customTable1.setColumnConfigs(configs);
        customTable1.setShowActionButtons(false);
    }
    
    public JPanel getMainPanel() {
        return mainPanel;
    }
    
    public JPanel extractMainPanel() {
        Container parent = roundedPanel1.getParent();
        if (parent != null) {
            parent.remove(roundedPanel1);
        }
        this.setVisible(false);
        return roundedPanel1;
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
        cardItem1 = new components.RoundedPanel();
        lblPemasukan = new javax.swing.JLabel();
        lblTotalIncome = new javax.swing.JLabel();
        customImageIcon2 = new components.CustomImageIcon();
        lblStatusIncome = new javax.swing.JLabel();
        iconStatusIncome = new components.CustomImageIcon();
        cardItem2 = new components.RoundedPanel();
        jLabel3 = new javax.swing.JLabel();
        lblTotalOutcome = new javax.swing.JLabel();
        customImageIcon4 = new components.CustomImageIcon();
        lblStatusOutcome = new javax.swing.JLabel();
        iconStatusOutcome = new components.CustomImageIcon();
        cardItem = new components.RoundedPanel();
        jLabel1 = new javax.swing.JLabel();
        customImageIcon1 = new components.CustomImageIcon();
        lblCoutProject = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        panelHighlight1 = new components.RoundedPanel();
        panelHighlightText1 = new javax.swing.JPanel();
        lblPantauKeuangan1 = new javax.swing.JLabel();
        lblKelolaSemua1 = new javax.swing.JLabel();
        lblChartIcon1 = new javax.swing.JLabel();
        cardItem3 = new components.RoundedPanel();
        jLabel4 = new javax.swing.JLabel();
        lblTotalProfit = new javax.swing.JLabel();
        customImageIcon5 = new components.CustomImageIcon();
        lblStatusProfit = new javax.swing.JLabel();
        iconStatusProfit = new components.CustomImageIcon();
        roundedPanel2 = new components.RoundedPanel();
        customTable1 = new components.CustomTable();
        roundedTextField1 = new components.RoundedTextField();
        roundedButton3 = new components.RoundedButton();
        jLabel6 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        mainPanel.setBackground(new java.awt.Color(236, 240, 241));

        roundedPanel1.setBackground(new java.awt.Color(236, 240, 241));
        roundedPanel1.setBackgroundColor(new java.awt.Color(236, 240, 241));
        roundedPanel1.setCornerRadius(20);
        roundedPanel1.setCustomHasBorder(false);
        roundedPanel1.setOpaque(true);
        roundedPanel1.setPreferredSize(new java.awt.Dimension(836, 373));

        cardItem1.setCornerRadius(20);
        cardItem1.setCustomHasBorder(false);

        lblPemasukan.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblPemasukan.setText("<html>Pemasukan<br>Bulan Ini</html>");

        lblTotalIncome.setFont(new java.awt.Font("Segoe UI", 1, 22)); // NOI18N
        lblTotalIncome.setForeground(new java.awt.Color(53, 222, 222));
        lblTotalIncome.setText("Rp53.420.000");

        customImageIcon2.setImageIconPath("images/icon_income.png");
        customImageIcon2.setInheritsPopupMenu(false);

        lblStatusIncome.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblStatusIncome.setText("Naik 8% dari bulan lalu");

        iconStatusIncome.setImageIconHeight(15);
        iconStatusIncome.setImageIconPath("images/icon_up.png");
        iconStatusIncome.setImageIconWidth(15);
        iconStatusIncome.setInheritsPopupMenu(false);

        javax.swing.GroupLayout cardItem1Layout = new javax.swing.GroupLayout(cardItem1);
        cardItem1.setLayout(cardItem1Layout);
        cardItem1Layout.setHorizontalGroup(
            cardItem1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cardItem1Layout.createSequentialGroup()
                .addGroup(cardItem1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(cardItem1Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(customImageIcon2, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblPemasukan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(cardItem1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(cardItem1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTotalIncome)
                            .addGroup(cardItem1Layout.createSequentialGroup()
                                .addComponent(iconStatusIncome, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblStatusIncome)))))
                .addGap(0, 21, Short.MAX_VALUE))
        );
        cardItem1Layout.setVerticalGroup(
            cardItem1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cardItem1Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(cardItem1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(customImageIcon2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblPemasukan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 43, Short.MAX_VALUE)
                .addComponent(lblTotalIncome)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(cardItem1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblStatusIncome)
                    .addComponent(iconStatusIncome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(13, 13, 13))
        );

        cardItem2.setCornerRadius(20);
        cardItem2.setCustomHasBorder(false);

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel3.setText("<html>Pengeluaran<br>Bulan Ini</html>");

        lblTotalOutcome.setFont(new java.awt.Font("Segoe UI", 1, 22)); // NOI18N
        lblTotalOutcome.setForeground(new java.awt.Color(156, 39, 176));
        lblTotalOutcome.setText("Rp13.340.000");

        customImageIcon4.setImageIconPath("images/icon_expense.png");
        customImageIcon4.setInheritsPopupMenu(false);

        lblStatusOutcome.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblStatusOutcome.setText("Turun 3% dari bulan lalu");

        iconStatusOutcome.setImageIconHeight(15);
        iconStatusOutcome.setImageIconPath("images/icon_down.png");
        iconStatusOutcome.setImageIconWidth(15);
        iconStatusOutcome.setInheritsPopupMenu(false);

        javax.swing.GroupLayout cardItem2Layout = new javax.swing.GroupLayout(cardItem2);
        cardItem2.setLayout(cardItem2Layout);
        cardItem2Layout.setHorizontalGroup(
            cardItem2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cardItem2Layout.createSequentialGroup()
                .addGroup(cardItem2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(cardItem2Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(customImageIcon4, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(cardItem2Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(cardItem2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(cardItem2Layout.createSequentialGroup()
                                .addComponent(iconStatusOutcome, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblStatusOutcome))
                            .addComponent(lblTotalOutcome))))
                .addGap(0, 28, Short.MAX_VALUE))
        );
        cardItem2Layout.setVerticalGroup(
            cardItem2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cardItem2Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(cardItem2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(customImageIcon4, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(42, 42, 42)
                .addComponent(lblTotalOutcome)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(cardItem2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblStatusOutcome)
                    .addComponent(iconStatusOutcome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 12, Short.MAX_VALUE))
        );

        cardItem.setCornerRadius(20);
        cardItem.setCustomHasBorder(false);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("<html>Proyek<br>Berjalan</html>");

        customImageIcon1.setImageIconPath("images/icon_project.png");
        customImageIcon1.setInheritsPopupMenu(false);

        lblCoutProject.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblCoutProject.setForeground(new java.awt.Color(46, 204, 113));
        lblCoutProject.setText("8");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(46, 204, 113));
        jLabel7.setText("proyek");

        javax.swing.GroupLayout cardItemLayout = new javax.swing.GroupLayout(cardItem);
        cardItem.setLayout(cardItemLayout);
        cardItemLayout.setHorizontalGroup(
            cardItemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cardItemLayout.createSequentialGroup()
                .addGroup(cardItemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(cardItemLayout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(lblCoutProject)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel7))
                    .addGroup(cardItemLayout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(customImageIcon1, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 24, Short.MAX_VALUE))
        );
        cardItemLayout.setVerticalGroup(
            cardItemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cardItemLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(cardItemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(customImageIcon1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addGroup(cardItemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCoutProject)
                    .addComponent(jLabel7))
                .addGap(0, 60, Short.MAX_VALUE))
        );

        panelHighlight1.setBorder(javax.swing.BorderFactory.createCompoundBorder(null, javax.swing.BorderFactory.createEmptyBorder(20, 20, 20, 20)));
        panelHighlight1.setBackgroundColor(new java.awt.Color(240, 255, 240));
        panelHighlight1.setCornerRadius(20);
        panelHighlight1.setCustomHasBorder(false);
        panelHighlight1.setPreferredSize(new java.awt.Dimension(816, 115));
        panelHighlight1.setLayout(new java.awt.BorderLayout(20, 0));

        panelHighlightText1.setOpaque(false);
        panelHighlightText1.setLayout(new javax.swing.BoxLayout(panelHighlightText1, javax.swing.BoxLayout.Y_AXIS));

        lblPantauKeuangan1.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        lblPantauKeuangan1.setForeground(new java.awt.Color(46, 204, 113));
        lblPantauKeuangan1.setText("Pantau Keuangan Perusahaan dengan Mudah!");
        lblPantauKeuangan1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 5, 1));
        panelHighlightText1.add(lblPantauKeuangan1);

        lblKelolaSemua1.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblKelolaSemua1.setText("Kelola semua transaksi, laporan, dan data master dalam satu dashboard yang cerah, bersih, dan menyenangkan.");
        panelHighlightText1.add(lblKelolaSemua1);

        panelHighlight1.add(panelHighlightText1, java.awt.BorderLayout.CENTER);

        lblChartIcon1.setFont(new java.awt.Font("Segoe UI Emoji", 0, 48)); // NOI18N
        lblChartIcon1.setForeground(new java.awt.Color(46, 204, 113));
        lblChartIcon1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblChartIcon1.setText("📊");
        lblChartIcon1.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 20));
        panelHighlight1.add(lblChartIcon1, java.awt.BorderLayout.EAST);

        cardItem3.setCornerRadius(20);
        cardItem3.setCustomHasBorder(false);

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel4.setText("<html>Net Profit<br>Bulan Ini</html>");

        lblTotalProfit.setFont(new java.awt.Font("Segoe UI", 1, 22)); // NOI18N
        lblTotalProfit.setForeground(new java.awt.Color(46, 204, 113));
        lblTotalProfit.setText("Rp13.340.000");

        customImageIcon5.setImageIconPath("images/icon_profit.png");
        customImageIcon5.setInheritsPopupMenu(false);

        lblStatusProfit.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblStatusProfit.setText("Turun 3% dari bulan lalu");

        iconStatusProfit.setImageIconHeight(15);
        iconStatusProfit.setImageIconPath("images/icon_down.png");
        iconStatusProfit.setImageIconWidth(15);
        iconStatusProfit.setInheritsPopupMenu(false);

        javax.swing.GroupLayout cardItem3Layout = new javax.swing.GroupLayout(cardItem3);
        cardItem3.setLayout(cardItem3Layout);
        cardItem3Layout.setHorizontalGroup(
            cardItem3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cardItem3Layout.createSequentialGroup()
                .addGroup(cardItem3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(cardItem3Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(customImageIcon5, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(cardItem3Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(cardItem3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(cardItem3Layout.createSequentialGroup()
                                .addComponent(iconStatusProfit, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblStatusProfit))
                            .addComponent(lblTotalProfit))))
                .addGap(0, 26, Short.MAX_VALUE))
        );
        cardItem3Layout.setVerticalGroup(
            cardItem3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cardItem3Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(cardItem3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(customImageIcon5, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(43, 43, 43)
                .addComponent(lblTotalProfit)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(cardItem3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblStatusProfit)
                    .addComponent(iconStatusProfit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 13, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout roundedPanel1Layout = new javax.swing.GroupLayout(roundedPanel1);
        roundedPanel1.setLayout(roundedPanel1Layout);
        roundedPanel1Layout.setHorizontalGroup(
            roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundedPanel1Layout.createSequentialGroup()
                .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panelHighlight1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(roundedPanel1Layout.createSequentialGroup()
                        .addComponent(cardItem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(cardItem1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(cardItem2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                        .addComponent(cardItem3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        roundedPanel1Layout.setVerticalGroup(
            roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundedPanel1Layout.createSequentialGroup()
                .addComponent(panelHighlight1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(roundedPanel1Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(roundedPanel1Layout.createSequentialGroup()
                                .addComponent(cardItem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(cardItem3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundedPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cardItem2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cardItem1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );

        roundedPanel2.setCornerRadius(20);
        roundedPanel2.setHasBorder(false);

        customTable1.setColumnNamesString("Kategori,Tanggal,Jumlah");
        customTable1.setHeaderBackgroundColor(new java.awt.Color(236, 240, 241));
        customTable1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                customTable1PropertyChange(evt);
            }
        });

        roundedTextField1.setCornerRadius(12);
        roundedTextField1.setPlaceholder("Cari data ...");
        roundedTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                roundedTextField1ActionPerformed(evt);
            }
        });

        roundedButton3.setText("🔍 Cari");
        roundedButton3.setCustomCornerRadius(12);

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel6.setText("Transaksi terakhir");

        javax.swing.GroupLayout roundedPanel2Layout = new javax.swing.GroupLayout(roundedPanel2);
        roundedPanel2.setLayout(roundedPanel2Layout);
        roundedPanel2Layout.setHorizontalGroup(
            roundedPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundedPanel2Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(roundedPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(customTable1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(roundedPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(roundedTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(roundedButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(22, 22, 22))
        );
        roundedPanel2Layout.setVerticalGroup(
            roundedPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundedPanel2Layout.createSequentialGroup()
                .addContainerGap(25, Short.MAX_VALUE)
                .addGroup(roundedPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(roundedPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(roundedPanel2Layout.createSequentialGroup()
                            .addGap(6, 6, 6)
                            .addComponent(jLabel6))
                        .addComponent(roundedTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(roundedButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(customTable1, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37))
        );

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(roundedPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 897, Short.MAX_VALUE)
                    .addComponent(roundedPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(18, Short.MAX_VALUE))
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(roundedPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 339, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(roundedPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(mainPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 12, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void customTable1PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_customTable1PropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_customTable1PropertyChange

    private void roundedTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_roundedTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_roundedTextField1ActionPerformed

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
            java.util.logging.Logger.getLogger(DashboardPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DashboardPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DashboardPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DashboardPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DashboardPage().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private components.RoundedPanel cardItem;
    private components.RoundedPanel cardItem1;
    private components.RoundedPanel cardItem2;
    private components.RoundedPanel cardItem3;
    private components.CustomImageIcon customImageIcon1;
    private components.CustomImageIcon customImageIcon2;
    private components.CustomImageIcon customImageIcon4;
    private components.CustomImageIcon customImageIcon5;
    private components.CustomTable customTable1;
    private components.CustomImageIcon iconStatusIncome;
    private components.CustomImageIcon iconStatusOutcome;
    private components.CustomImageIcon iconStatusProfit;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel lblChartIcon1;
    private javax.swing.JLabel lblCoutProject;
    private javax.swing.JLabel lblKelolaSemua1;
    private javax.swing.JLabel lblPantauKeuangan1;
    private javax.swing.JLabel lblPemasukan;
    private javax.swing.JLabel lblStatusIncome;
    private javax.swing.JLabel lblStatusOutcome;
    private javax.swing.JLabel lblStatusProfit;
    private javax.swing.JLabel lblTotalIncome;
    private javax.swing.JLabel lblTotalOutcome;
    private javax.swing.JLabel lblTotalProfit;
    private javax.swing.JPanel mainPanel;
    private components.RoundedPanel panelHighlight1;
    private javax.swing.JPanel panelHighlightText1;
    private components.RoundedButton roundedButton3;
    private components.RoundedPanel roundedPanel1;
    private components.RoundedPanel roundedPanel2;
    private components.RoundedTextField roundedTextField1;
    // End of variables declaration//GEN-END:variables
}
