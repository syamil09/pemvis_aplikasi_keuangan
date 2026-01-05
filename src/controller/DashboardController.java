/*
 * Controller untuk Dashboard - mengambil data summary untuk halaman dashboard
 */
package controller;

import connection.DBConnection;
import model.JournalEntry;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import helper.CurrencyHelper;

/**
 * Controller untuk mengambil data dashboard
 * 
 * @author Leonovo
 */
public class DashboardController {
    
    private final Connection connection;
    private PreparedStatement ps;
    private ResultSet rs;
    
    public DashboardController() {
        this.connection = DBConnection.getConnection();
    }
    
    /**
     * Get count of active projects
     * @return jumlah proyek yang sedang berjalan
     */
    public int getActiveProjectCount() {
        try {
            String sql = "SELECT COUNT(*) as count FROM projects WHERE status = 'ongoing'";
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                return rs.getInt("count");
            }
        } catch (SQLException e) {
            System.out.println("Error getting active project count: " + e.getMessage());
        }
        return 0;
    }
    
    /**
     * Get total income for current month
     * Includes: invoices (paid) + other_receipts
     * @return total pemasukan bulan ini
     */
    public double getCurrentMonthIncome() {
        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH) + 1;
        int year = cal.get(Calendar.YEAR);
        return getMonthlyIncome(month, year);
    }
    
    /**
     * Get total income for last month
     * @return total pemasukan bulan lalu
     */
    public double getLastMonthIncome() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        int month = cal.get(Calendar.MONTH) + 1;
        int year = cal.get(Calendar.YEAR);
        return getMonthlyIncome(month, year);
    }
    
    /**
     * Get monthly income from invoices + other_receipts
     */
    private double getMonthlyIncome(int month, int year) {
        double total = 0;
        
        try {
            // invoices paid dalam bulan tersebut
            String sqlInvoice = "SELECT COALESCE(SUM(total_amount), 0) as total FROM invoices " +
                               "WHERE status = 'paid' AND MONTH(invoice_date) = ? AND YEAR(invoice_date) = ?";
            ps = connection.prepareStatement(sqlInvoice);
            ps.setInt(1, month);
            ps.setInt(2, year);
            rs = ps.executeQuery();
            if (rs.next()) {
                total += rs.getDouble("total");
            }
            
            // other_receipts dalam bulan tersebut
            String sqlReceipt = "SELECT COALESCE(SUM(amount), 0) as total FROM other_receipts " +
                               "WHERE MONTH(receipt_date) = ? AND YEAR(receipt_date) = ?";
            ps = connection.prepareStatement(sqlReceipt);
            ps.setInt(1, month);
            ps.setInt(2, year);
            rs = ps.executeQuery();
            if (rs.next()) {
                total += rs.getDouble("total");
            }
            
        } catch (SQLException e) {
            System.out.println("Error getting monthly income: " + e.getMessage());
        }
        
        return total;
    }
    
    /**
     * Get total expense for current month
     * Includes: expenses + payrolls (paid)
     * @return total pengeluaran bulan ini
     */
    public double getCurrentMonthExpense() {
        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH) + 1;
        int year = cal.get(Calendar.YEAR);
        return getMonthlyExpense(month, year);
    }
    
    /**
     * Get total expense for last month
     * @return total pengeluaran bulan lalu
     */
    public double getLastMonthExpense() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        int month = cal.get(Calendar.MONTH) + 1;
        int year = cal.get(Calendar.YEAR);
        return getMonthlyExpense(month, year);
    }
    
    /**
     * Get monthly expense from expenses + payrolls
     */
    private double getMonthlyExpense(int month, int year) {
        double total = 0;
        
        try {
            // expenses dalam bulan tersebut
            String sqlExpense = "SELECT COALESCE(SUM(amount), 0) as total FROM expenses " +
                               "WHERE MONTH(expense_date) = ? AND YEAR(expense_date) = ?";
            ps = connection.prepareStatement(sqlExpense);
            ps.setInt(1, month);
            ps.setInt(2, year);
            rs = ps.executeQuery();
            if (rs.next()) {
                total += rs.getDouble("total");
            }
            
            // payrolls paid dalam bulan tersebut
            String sqlPayroll = "SELECT COALESCE(SUM(total_gaji), 0) as total FROM payrolls " +
                               "WHERE status = 'paid' AND period_month = ? AND period_year = ?";
            ps = connection.prepareStatement(sqlPayroll);
            ps.setInt(1, month);
            ps.setInt(2, year);
            rs = ps.executeQuery();
            if (rs.next()) {
                total += rs.getDouble("total");
            }
            
        } catch (SQLException e) {
            System.out.println("Error getting monthly expense: " + e.getMessage());
        }
        
        return total;
    }
    
    /**
     * Calculate percentage change between current and last month
     * @param current nilai bulan ini
     * @param last nilai bulan lalu
     * @return persentase perubahan (positif = naik, negatif = turun)
     */
    public double calculatePercentageChange(double current, double last) {
        if (last == 0) {
            return current > 0 ? 100 : 0;
        }
        return ((current - last) / last) * 100;
    }
    
    /**
     * Get recent transactions from journal entries
     * @param limit jumlah transaksi yang diambil
     * @return list of recent journal entries
     */
    public List<JournalEntry> getRecentTransactions(int limit) {
        List<JournalEntry> transactions = new ArrayList<>();
        
        try {
            String sql = "SELECT je.*, tc.name as category_name " +
                        "FROM journal_entries je " +
                        "LEFT JOIN transaction_categories tc ON je.category_id = tc.category_id " +
                        "ORDER BY je.entry_date DESC, je.created_at DESC " +
                        "LIMIT ?";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, limit);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                JournalEntry entry = new JournalEntry();
                entry.setEntryId(rs.getString("entry_id"));
                entry.setTransactionType(rs.getString("transaction_type"));
                entry.setEntryDate(rs.getString("entry_date"));
                entry.setDescription(rs.getString("description"));
                entry.setAmount(rs.getDouble("amount"));
                entry.setCategoryName(rs.getString("category_name"));
                transactions.add(entry);
            }
            
        } catch (SQLException e) {
            System.out.println("Error getting recent transactions: " + e.getMessage());
        }
        
        return transactions;
    }
    
    /**
     * Main method untuk testing
     */
    public static void main(String[] args) {
        DashboardController controller = new DashboardController();
        
        System.out.println("===== TESTING DASHBOARD CONTROLLER =====\n");
        
        System.out.println("Active Projects: " + controller.getActiveProjectCount());
        System.out.println("Current Month Income: " + CurrencyHelper.formatToRupiahWithoutDecimal(controller.getCurrentMonthIncome()));
        System.out.println("Last Month Income: " + CurrencyHelper.formatToRupiahWithoutDecimal(controller.getLastMonthIncome()));
        System.out.println("Current Month Expense: " + CurrencyHelper.formatToRupiahWithoutDecimal(controller.getCurrentMonthExpense()));
        System.out.println("Last Month Expense: " + CurrencyHelper.formatToRupiahWithoutDecimal(controller.getLastMonthExpense()));
        
        System.out.println("\nRecent Transactions:");
        List<JournalEntry> transactions = controller.getRecentTransactions(5);
        for (JournalEntry entry : transactions) {
            System.out.println("- " + entry.getEntryDate() + " | " + entry.getDescription() + " | " + entry.getFormattedAmount());
        }
        
        System.out.println("\n===== TESTING COMPLETED =====");
    }
}
