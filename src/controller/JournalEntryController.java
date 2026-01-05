/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import connection.DBConnection;
import model.JournalEntry;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Calendar;

/**
 * Controller untuk mengelola operasi pada tabel journal_entries
 * Termasuk auto-create dari transaksi lain
 * 
 * @author Leonovo
 * @version 1.0
 */
public class JournalEntryController {
    
    private final Connection connection;
    private PreparedStatement ps;
    private ResultSet rs;
    
    // Hardcode category untuk payroll
    public static final String PAYROLL_CATEGORY_ID = "TRC101";
    
    public JournalEntryController() {
        this.connection = DBConnection.getConnection();
    }
    
    /**
     * CREATE - Tambah journal entry baru
     * @param entry JournalEntry object
     * @return String pesan hasil operasi
     */
    public String createEntry(JournalEntry entry) {
        System.out.println("---- Creating new journal entry -----");
        
        if (!entry.isValid()) {
            System.out.println("Journal entry not valid - check fields");
            return "Data journal entry tidak valid";
        }
        
        try {
            // Generate ID and entry number BEFORE preparing statement
            // to avoid overwriting ps variable
            if (entry.getEntryId() == null || entry.getEntryId().isEmpty()) {
                entry.setEntryId(generateNextId());
            }
            if (entry.getEntryNumber() == null || entry.getEntryNumber().isEmpty()) {
                entry.setEntryNumber(generateEntryNumber());
            }
            
            String sql = "INSERT INTO journal_entries (entry_id, entry_number, transaction_type, " +
                        "transaction_id, category_id, entry_date, description, debit_account_id, " +
                        "credit_account_id, amount, reference_number, created_by, created_at) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NOW())";
            ps = connection.prepareStatement(sql);
            
            ps.setString(1, entry.getEntryId());
            ps.setString(2, entry.getEntryNumber());
            ps.setString(3, entry.getTransactionType());
            ps.setString(4, entry.getTransactionId());
            ps.setString(5, entry.getCategoryId());
            ps.setString(6, entry.getEntryDate());
            ps.setString(7, entry.getDescription());
            ps.setString(8, entry.getDebitAccountId());
            ps.setString(9, entry.getCreditAccountId());
            ps.setDouble(10, entry.getAmount());
            ps.setString(11, entry.getReferenceNumber());
            ps.setString(12, entry.getCreatedBy());
            
            int result = ps.executeUpdate();
            if (result > 0) {
                System.out.println("Journal entry created: " + entry.getEntryId());
                return "Journal entry berhasil dibuat: " + entry.getEntryId();
            } else {
                return "Journal entry gagal dibuat";
            }
            
        } catch (SQLException e) {
            System.out.println("Error creating journal entry: " + e.getMessage());
            return "Terjadi error: " + e.getMessage();
        }
    }
    
    /**
     * DELETE journal entry by transaction ID and type
     * Digunakan saat transaksi dihapus
     * @param transactionId ID transaksi (EXP001, RCP001, INV001, PAY001)
     * @param transactionType tipe transaksi
     * @return true jika berhasil
     */
    public boolean deleteByTransactionId(String transactionId, String transactionType) {
        System.out.println("---- Deleting journal entry for transaction: " + transactionId + " -----");
        
        try {
            String sql = "DELETE FROM journal_entries WHERE transaction_id = ? AND transaction_type = ?";
            ps = connection.prepareStatement(sql);
            ps.setString(1, transactionId);
            ps.setString(2, transactionType);
            
            int result = ps.executeUpdate();
            System.out.println("Deleted " + result + " journal entries");
            return result > 0;
            
        } catch (SQLException e) {
            System.out.println("Error deleting journal entry: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Get debit and credit account IDs from transaction category
     * @param categoryId ID kategori transaksi
     * @return String[] with [0]=debitAccountId, [1]=creditAccountId, or null if not found
     */
    public String[] getAccountsFromCategory(String categoryId) {
        System.out.println("Getting accounts for category: " + categoryId);
        
        try {
            String sql = "SELECT debit_account_id, credit_account_id FROM transaction_categories WHERE category_id = ?";
            ps = connection.prepareStatement(sql);
            ps.setString(1, categoryId);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                String[] accounts = new String[2];
                accounts[0] = rs.getString("debit_account_id");
                accounts[1] = rs.getString("credit_account_id");
                System.out.println("Found: debit=" + accounts[0] + ", credit=" + accounts[1]);
                return accounts;
            }
            
        } catch (SQLException e) {
            System.out.println("Error getting accounts from category: " + e.getMessage());
        }
        
        return null;
    }
    
    /**
     * Create journal entry from expense transaction
     * @param expenseId ID expense
     * @param categoryId ID kategori
     * @param description deskripsi
     * @param amount jumlah
     * @param expenseDate tanggal
     * @param receiptNumber nomor bukti
     * @param createdBy user yang buat
     * @return true jika berhasil
     */
    public boolean createFromExpense(String expenseId, String categoryId, String description,
                                     Double amount, String expenseDate, String receiptNumber, String createdBy) {
        
        System.out.println("[DEBUG createFromExpense] expenseId=" + expenseId + ", categoryId=" + categoryId);
        System.out.println("[DEBUG createFromExpense] amount=" + amount + ", expenseDate=" + expenseDate);
        
        String[] accounts = getAccountsFromCategory(categoryId);
        if (accounts == null) {
            System.out.println("[DEBUG createFromExpense] Category not found: " + categoryId);
            return false;
        }
        
        System.out.println("[DEBUG createFromExpense] accounts[0]=" + accounts[0] + ", accounts[1]=" + accounts[1]);
        
        JournalEntry entry = new JournalEntry();
        entry.setTransactionType(JournalEntry.TYPE_EXPENSE);
        entry.setTransactionId(expenseId);
        entry.setCategoryId(categoryId);
        entry.setEntryDate(expenseDate);
        entry.setDescription(description);
        entry.setDebitAccountId(accounts[0]);
        entry.setCreditAccountId(accounts[1]);
        entry.setAmount(amount);
        entry.setReferenceNumber(receiptNumber);
        entry.setCreatedBy(createdBy);
        
        System.out.println("[DEBUG createFromExpense] entry.isValid()=" + entry.isValid());
        
        String result = createEntry(entry);
        System.out.println("[DEBUG createFromExpense] result=" + result);
        return result.contains("berhasil");
    }
    
    /**
     * Create journal entry from other receipt transaction
     * @param receiptId ID receipt
     * @param categoryId ID kategori
     * @param description deskripsi
     * @param amount jumlah
     * @param receiptDate tanggal
     * @param source sumber penerimaan
     * @param createdBy user yang buat
     * @return true jika berhasil
     */
    public boolean createFromReceipt(String receiptId, String categoryId, String description,
                                     Double amount, String receiptDate, String source, String createdBy) {
        
        String[] accounts = getAccountsFromCategory(categoryId);
        if (accounts == null) {
            System.out.println("Category not found: " + categoryId);
            return false;
        }
        
        JournalEntry entry = new JournalEntry();
        entry.setTransactionType(JournalEntry.TYPE_RECEIPT);
        entry.setTransactionId(receiptId);
        entry.setCategoryId(categoryId);
        entry.setEntryDate(receiptDate);
        entry.setDescription(description);
        entry.setDebitAccountId(accounts[0]);
        entry.setCreditAccountId(accounts[1]);
        entry.setAmount(amount);
        entry.setReferenceNumber(source);
        entry.setCreatedBy(createdBy);
        
        String result = createEntry(entry);
        return result.contains("berhasil");
    }
    
    /**
     * Create journal entry from payroll transaction
     * Uses hardcoded category TRC101
     * @param payrollId ID payroll
     * @param employeeName nama karyawan
     * @param totalGaji total gaji
     * @param paymentDate tanggal pembayaran
     * @param periodMonth bulan periode
     * @param periodYear tahun periode
     * @param createdBy user yang buat
     * @return true jika berhasil
     */
    public boolean createFromPayroll(String payrollId, String employeeName, Double totalGaji,
                                     String paymentDate, int periodMonth, int periodYear, String createdBy) {
        
        String[] accounts = getAccountsFromCategory(PAYROLL_CATEGORY_ID);
        if (accounts == null) {
            System.out.println("Payroll category not found: " + PAYROLL_CATEGORY_ID);
            return false;
        }
        
        String description = "Gaji " + employeeName + " - " + getMonthName(periodMonth) + " " + periodYear;
        
        JournalEntry entry = new JournalEntry();
        entry.setTransactionType(JournalEntry.TYPE_PAYROLL);
        entry.setTransactionId(payrollId);
        entry.setCategoryId(PAYROLL_CATEGORY_ID);
        entry.setEntryDate(paymentDate);
        entry.setDescription(description);
        entry.setDebitAccountId(accounts[0]); // 5101 - Beban Gaji
        entry.setCreditAccountId(accounts[1]); // 1103 - Bank Mandiri Payroll
        entry.setAmount(totalGaji);
        entry.setReferenceNumber(payrollId);
        entry.setCreatedBy(createdBy);
        
        String result = createEntry(entry);
        return result.contains("berhasil");
    }
    
    /**
     * Create journal entry from invoice transaction
     * @param invoiceId ID invoice
     * @param categoryId ID kategori (optional, bisa null)
     * @param description deskripsi
     * @param totalAmount total amount
     * @param invoiceDate tanggal invoice
     * @param invoiceNumber nomor invoice
     * @param createdBy user yang buat
     * @return true jika berhasil
     */
    public boolean createFromInvoice(String invoiceId, String categoryId, String description,
                                     Double totalAmount, String invoiceDate, String invoiceNumber, String createdBy) {
        
        System.out.println("[DEBUG createFromInvoice] invoiceId=" + invoiceId);
        System.out.println("[DEBUG createFromInvoice] totalAmount=" + totalAmount + ", invoiceDate=" + invoiceDate);
        
        // skip jika amount 0 atau null (invoice draft tanpa item)
        if (totalAmount == null || totalAmount <= 0) {
            System.out.println("Skipping journal entry - invoice amount is 0 or null");
            return true; // return true karena ini bukan error, hanya skip
        }
        
        // Untuk invoice, jika ada category gunakan dari category
        // jika tidak, default: debit Piutang (1111), credit Pendapatan Jasa (4101)
        String debitAccount = "1111"; // Piutang Usaha - Klien
        String creditAccount = "4101"; // Pendapatan Jasa Pembuatan Software
        
        if (categoryId != null && !categoryId.isEmpty()) {
            String[] accounts = getAccountsFromCategory(categoryId);
            if (accounts != null) {
                debitAccount = accounts[0];
                creditAccount = accounts[1];
            }
        }
        
        JournalEntry entry = new JournalEntry();
        entry.setTransactionType(JournalEntry.TYPE_INVOICE);
        entry.setTransactionId(invoiceId);
        entry.setCategoryId(categoryId);
        entry.setEntryDate(invoiceDate);
        entry.setDescription(description);
        entry.setDebitAccountId(debitAccount);
        entry.setCreditAccountId(creditAccount);
        entry.setAmount(totalAmount);
        entry.setReferenceNumber(invoiceNumber);
        entry.setCreatedBy(createdBy);
        
        System.out.println("[DEBUG createFromInvoice] isValid=" + entry.isValid());
        
        String result = createEntry(entry);
        return result.contains("berhasil");
    }
    
    /**
     * Generate next entry ID (JE001, JE002, ...)
     */
    private String generateNextId() {
        try {
            String sql = "SELECT entry_id FROM journal_entries WHERE entry_id LIKE 'JE%' ORDER BY entry_id DESC LIMIT 1";
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                String lastId = rs.getString("entry_id");
                String numericPart = lastId.substring(2);
                int nextNumber = Integer.parseInt(numericPart) + 1;
                return String.format("JE%03d", nextNumber);
            } else {
                return "JE001";
            }
            
        } catch (SQLException | NumberFormatException e) {
            System.out.println("Error generating entry ID: " + e.getMessage());
            return "JE001";
        }
    }
    
    /**
     * Generate entry number format JE-YYYY-NNN
     */
    private String generateEntryNumber() {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        
        try {
            String sql = "SELECT entry_number FROM journal_entries WHERE entry_number LIKE ? ORDER BY entry_number DESC LIMIT 1";
            ps = connection.prepareStatement(sql);
            ps.setString(1, "JE-" + year + "-%");
            rs = ps.executeQuery();
            
            if (rs.next()) {
                String lastNumber = rs.getString("entry_number");
                // Format: JE-2025-001
                String[] parts = lastNumber.split("-");
                int nextNum = Integer.parseInt(parts[2]) + 1;
                return String.format("JE-%d-%03d", year, nextNum);
            } else {
                return String.format("JE-%d-001", year);
            }
            
        } catch (SQLException | NumberFormatException e) {
            System.out.println("Error generating entry number: " + e.getMessage());
            return String.format("JE-%d-001", year);
        }
    }
    
    /**
     * Helper: Get month name in Indonesian
     */
    private String getMonthName(int month) {
        String[] months = {"", "Januari", "Februari", "Maret", "April", "Mei", "Juni",
                          "Juli", "Agustus", "September", "Oktober", "November", "Desember"};
        if (month >= 1 && month <= 12) {
            return months[month];
        }
        return "Bulan " + month;
    }
    
    /**
     * READ - Get all journal entries with search
     */
    public List<JournalEntry> getData(String searchItem) {
        List<JournalEntry> listData = new ArrayList<>();
        System.out.println("---- Getting journal entries -----");
        
        try {
            String sql;
            
            if (searchItem != null && !searchItem.trim().isEmpty()) {
                sql = "SELECT je.*, " +
                      "da.name as debit_account_name, " +
                      "ca.name as credit_account_name, " +
                      "tc.name as category_name, " +
                      "u.fullname as created_by_name " +
                      "FROM journal_entries je " +
                      "LEFT JOIN accounts da ON je.debit_account_id = da.account_id " +
                      "LEFT JOIN accounts ca ON je.credit_account_id = ca.account_id " +
                      "LEFT JOIN transaction_categories tc ON je.category_id = tc.category_id " +
                      "LEFT JOIN users u ON je.created_by = u.user_id " +
                      "WHERE je.entry_id LIKE ? OR je.entry_number LIKE ? OR je.description LIKE ? " +
                      "ORDER BY je.entry_date DESC, je.created_at DESC";
                ps = connection.prepareStatement(sql);
                String searchPattern = "%" + searchItem.trim() + "%";
                ps.setString(1, searchPattern);
                ps.setString(2, searchPattern);
                ps.setString(3, searchPattern);
            } else {
                sql = "SELECT je.*, " +
                      "da.name as debit_account_name, " +
                      "ca.name as credit_account_name, " +
                      "tc.name as category_name, " +
                      "u.fullname as created_by_name " +
                      "FROM journal_entries je " +
                      "LEFT JOIN accounts da ON je.debit_account_id = da.account_id " +
                      "LEFT JOIN accounts ca ON je.credit_account_id = ca.account_id " +
                      "LEFT JOIN transaction_categories tc ON je.category_id = tc.category_id " +
                      "LEFT JOIN users u ON je.created_by = u.user_id " +
                      "ORDER BY je.entry_date DESC, je.created_at DESC";
                ps = connection.prepareStatement(sql);
            }
            
            rs = ps.executeQuery();
            
            while (rs.next()) {
                JournalEntry entry = mapResultSetToEntry(rs);
                listData.add(entry);
            }
            
            System.out.println("Found " + listData.size() + " journal entries");
            
        } catch (SQLException e) {
            System.out.println("Error getting journal entries: " + e.getMessage());
            e.printStackTrace();
        }
        
        return listData;
    }
    
    /**
     * Helper: Map ResultSet to JournalEntry
     */
    private JournalEntry mapResultSetToEntry(ResultSet rs) throws SQLException {
        JournalEntry entry = new JournalEntry();
        entry.setEntryId(rs.getString("entry_id"));
        entry.setEntryNumber(rs.getString("entry_number"));
        entry.setTransactionType(rs.getString("transaction_type"));
        entry.setTransactionId(rs.getString("transaction_id"));
        entry.setCategoryId(rs.getString("category_id"));
        entry.setEntryDate(rs.getString("entry_date"));
        entry.setDescription(rs.getString("description"));
        entry.setDebitAccountId(rs.getString("debit_account_id"));
        entry.setCreditAccountId(rs.getString("credit_account_id"));
        entry.setAmount(rs.getDouble("amount"));
        entry.setReferenceNumber(rs.getString("reference_number"));
        entry.setCreatedBy(rs.getString("created_by"));
        entry.setCreatedAt(rs.getString("created_at"));
        
        // UI fields from JOINs
        try {
            entry.setDebitAccountName(rs.getString("debit_account_name"));
            entry.setCreditAccountName(rs.getString("credit_account_name"));
            entry.setCategoryName(rs.getString("category_name"));
            entry.setCreatedByName(rs.getString("created_by_name"));
        } catch (SQLException e) {
            // columns might not exist in all queries
        }
        
        return entry;
    }
    
    /**
     * Main method untuk testing
     */
    public static void main(String[] args) {
        JournalEntryController controller = new JournalEntryController();
        
        System.out.println("\n===== TESTING JOURNAL ENTRY CONTROLLER =====\n");
        
        // Test get accounts from category
        System.out.println("Test 1: Get accounts from TRC101");
        String[] accounts = controller.getAccountsFromCategory("TRC101");
        if (accounts != null) {
            System.out.println("Debit: " + accounts[0] + ", Credit: " + accounts[1]);
        }
        
        // Test get all data
        System.out.println("\nTest 2: Get all journal entries");
        List<JournalEntry> entries = controller.getData(null);
        System.out.println("Total entries: " + entries.size());
        
        System.out.println("\n===== TESTING COMPLETED =====");
    }
}
