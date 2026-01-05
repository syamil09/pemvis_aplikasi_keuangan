/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import connection.DBConnection;
import model.OtherReceipt;
import model.JournalEntry;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller untuk mengelola operasi CRUD pada tabel other_receipts
 * 
 * @author Leonovo
 * @version 1.0
 */
public class OtherReceiptController {
    
    private final Connection connection;
    private PreparedStatement ps;
    private ResultSet rs;
    private final JournalEntryController journalCtr;
    
    public OtherReceiptController() {
        this.connection = DBConnection.getConnection();
        this.journalCtr = new JournalEntryController();
    }
    
    /**
     * READ - Get all other receipts with search functionality
     * @param searchItem kata kunci pencarian (opsional, null untuk semua data)
     * @return List<OtherReceipt>
     */
    public List<OtherReceipt> getData(String searchItem) {
        List<OtherReceipt> listData = new ArrayList<>();
        System.out.println("---- Getting other receipt data -----");
        
        try {
            String sql;
            
            if (searchItem != null && !searchItem.trim().isEmpty()) {
                sql = "SELECT r.receipt_id, r.category_id, r.description, " +
                      "r.amount, r.receipt_date, r.source, r.created_by, r.created_at, " +
                      "tc.name as category_name, " +
                      "u.fullname as created_by_name " +
                      "FROM other_receipts r " +
                      "LEFT JOIN transaction_categories tc ON r.category_id = tc.category_id " +
                      "LEFT JOIN users u ON r.created_by = u.user_id " +
                      "WHERE r.receipt_id LIKE ? OR " +
                      "r.description LIKE ? OR " +
                      "r.source LIKE ? OR " +
                      "tc.name LIKE ? " +
                      "ORDER BY r.receipt_date DESC, r.created_at DESC";
                ps = connection.prepareStatement(sql);
                
                String searchPattern = "%" + searchItem.trim() + "%";
                ps.setString(1, searchPattern);
                ps.setString(2, searchPattern);
                ps.setString(3, searchPattern);
                ps.setString(4, searchPattern);
                
                System.out.println("Searching for: " + searchItem);
            } else {
                sql = "SELECT r.receipt_id, r.category_id, r.description, " +
                      "r.amount, r.receipt_date, r.source, r.created_by, r.created_at, " +
                      "tc.name as category_name, " +
                      "u.fullname as created_by_name " +
                      "FROM other_receipts r " +
                      "LEFT JOIN transaction_categories tc ON r.category_id = tc.category_id " +
                      "LEFT JOIN users u ON r.created_by = u.user_id " +
                      "ORDER BY r.receipt_date DESC, r.created_at DESC";
                ps = connection.prepareStatement(sql);
                System.out.println("Getting all other receipts");
            }
            
            rs = ps.executeQuery();
            
            while (rs.next()) {
                OtherReceipt receipt = new OtherReceipt();
                receipt.setReceiptId(rs.getString("receipt_id"));
                receipt.setCategoryId(rs.getString("category_id"));
                receipt.setDescription(rs.getString("description"));
                receipt.setAmount(rs.getDouble("amount"));
                receipt.setReceiptDate(rs.getString("receipt_date"));
                receipt.setSource(rs.getString("source"));
                receipt.setCreatedBy(rs.getString("created_by"));
                receipt.setCreatedAt(rs.getString("created_at"));
                
                // Field tambahan dari JOIN
                receipt.setCategoryName(rs.getString("category_name"));
                receipt.setCreatedByName(rs.getString("created_by_name"));
                
                listData.add(receipt);
                System.out.println("    Receipt ID: " + rs.getString("receipt_id"));
            }
            
            System.out.println("Found " + listData.size() + " other receipts");
            
        } catch (SQLException e) {
            System.out.println("Error getting other receipt data: " + e.getMessage());
            e.printStackTrace();
        }
        
        return listData;
    }
    
    /**
     * Ambil other receipt berdasarkan ID
     * @param receiptId
     * @return OtherReceipt object atau null jika tidak ditemukan
     */
    public OtherReceipt getReceiptById(String receiptId) {
        System.out.println("---- Getting other receipt by ID: " + receiptId + " -----");
        
        String sql = "SELECT r.receipt_id, r.category_id, r.description, " +
                     "r.amount, r.receipt_date, r.source, r.created_by, r.created_at, " +
                     "tc.name as category_name, " +
                     "u.fullname as created_by_name " +
                     "FROM other_receipts r " +
                     "LEFT JOIN transaction_categories tc ON r.category_id = tc.category_id " +
                     "LEFT JOIN users u ON r.created_by = u.user_id " +
                     "WHERE r.receipt_id = ?";
        
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, receiptId);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                OtherReceipt receipt = new OtherReceipt();
                receipt.setReceiptId(rs.getString("receipt_id"));
                receipt.setCategoryId(rs.getString("category_id"));
                receipt.setDescription(rs.getString("description"));
                receipt.setAmount(rs.getDouble("amount"));
                receipt.setReceiptDate(rs.getString("receipt_date"));
                receipt.setSource(rs.getString("source"));
                receipt.setCreatedBy(rs.getString("created_by"));
                receipt.setCreatedAt(rs.getString("created_at"));
                
                receipt.setCategoryName(rs.getString("category_name"));
                receipt.setCreatedByName(rs.getString("created_by_name"));
                
                System.out.println("Other receipt found: " + receipt.getDescription());
                return receipt;
            } else {
                System.out.println("Other receipt not found with ID: " + receiptId);
            }
            
        } catch (SQLException e) {
            System.out.println("Error getting other receipt by ID: " + e.getMessage());
            e.printStackTrace();
        }
        
        return null;
    }
    
    /**
     * CREATE - Tambah other receipt baru
     * @param receipt
     * @return String pesan hasil operasi
     */
    public String createReceipt(OtherReceipt receipt) {
        System.out.println("---- Creating new other receipt -----");
        
        if (!receipt.isValid()) {
            return "Data penerimaan lain tidak valid";
        }
        
        try {
            String sql = "INSERT INTO other_receipts (receipt_id, category_id, description, " +
                        "amount, receipt_date, source, created_by, created_at) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, NOW())";
            ps = connection.prepareStatement(sql);
            
            // Generate ID jika belum ada
            if (receipt.getReceiptId() == null || receipt.getReceiptId().isEmpty()) {
                receipt.setReceiptId(generateReceiptId());
            }
            
            ps.setString(1, receipt.getReceiptId());
            ps.setString(2, receipt.getCategoryId());
            ps.setString(3, receipt.getDescription());
            ps.setDouble(4, receipt.getAmount());
            ps.setString(5, receipt.getReceiptDate());
            ps.setString(6, receipt.getSource());
            ps.setString(7, receipt.getCreatedBy());
            
            int result = ps.executeUpdate();
            if (result > 0) {
                // auto-create journal entry
                journalCtr.createFromReceipt(
                    receipt.getReceiptId(),
                    receipt.getCategoryId(),
                    receipt.getDescription(),
                    receipt.getAmount(),
                    receipt.getReceiptDate(),
                    receipt.getSource(),
                    receipt.getCreatedBy()
                );
                return "Data penerimaan lain berhasil ditambah : " + receipt.getReceiptId();
            } else {
                return "Data penerimaan lain gagal ditambah";
            }
            
        } catch (SQLException e) {
            return "Terjadi error : " + e.getMessage();
        }
    }
    
    /**
     * UPDATE - Update other receipt yang sudah ada
     * @param receipt
     * @return String pesan hasil operasi
     */
    public String updateReceipt(OtherReceipt receipt) {
        System.out.println("---- Updating other receipt: " + receipt.getReceiptId() + " -----");
        
        if (!receipt.isValid()) {
            return "Data penerimaan lain tidak valid";
        }
        
        try {
            String sql = "UPDATE other_receipts " +
                        "SET category_id = ?, description = ?, " +
                        "amount = ?, receipt_date = ?, source = ? " +
                        "WHERE receipt_id = ?";
            ps = connection.prepareStatement(sql);
            
            ps.setString(1, receipt.getCategoryId());
            ps.setString(2, receipt.getDescription());
            ps.setDouble(3, receipt.getAmount());
            ps.setString(4, receipt.getReceiptDate());
            ps.setString(5, receipt.getSource());
            ps.setString(6, receipt.getReceiptId());
            
            int result = ps.executeUpdate();
            if (result > 0) {
                return "Data penerimaan lain berhasil diupdate : " + receipt.getReceiptId();
            } else {
                return "Data penerimaan lain gagal diupdate - ID tidak ditemukan";
            }
            
        } catch (SQLException e) {
            return "Terjadi error : " + e.getMessage();
        }
    }
    
    /**
     * DELETE - Hapus other receipt berdasarkan ID
     * @param receiptId
     * @return String pesan hasil operasi
     */
    public String deleteReceipt(String receiptId) {
        System.out.println("---- Deleting other receipt: " + receiptId + " -----");
        
        try {
            // delete journal entry first
            journalCtr.deleteByTransactionId(receiptId, JournalEntry.TYPE_RECEIPT);
            
            String sql = "DELETE FROM other_receipts WHERE receipt_id = ?";
            ps = connection.prepareStatement(sql);
            ps.setString(1, receiptId);
            
            int result = ps.executeUpdate();
            if (result > 0) {
                return "Data penerimaan lain berhasil dihapus : " + receiptId;
            } else {
                return "Data penerimaan lain gagal dihapus - ID tidak ditemukan";
            }
            
        } catch (SQLException e) {
            return "Terjadi error : " + e.getMessage();
        }
    }
    
    /**
     * UTILITY - Generate next receipt ID
     * @return String receipt ID
     */
    private String generateReceiptId() {
        try {
            String sql = "SELECT receipt_id FROM other_receipts WHERE receipt_id LIKE 'RCP%' ORDER BY receipt_id DESC LIMIT 1";
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                String lastId = rs.getString("receipt_id");
                // Extract nomor dari RCP001 -> 001
                String numericPart = lastId.substring(3);
                int nextNumber = Integer.parseInt(numericPart) + 1;
                return String.format("RCP%03d", nextNumber);
            } else {
                return "RCP001"; // ID pertama
            }
            
        } catch (SQLException | NumberFormatException e) {
            System.out.println("Error generate receipt ID: " + e.getMessage());
            return "RCP001";
        }
    }
    
    /**
     * Get total receipts berdasarkan periode
     * @param startDate format: yyyy-MM-dd
     * @param endDate format: yyyy-MM-dd
     * @return double total amount
     */
    public double getTotalReceiptsByPeriod(String startDate, String endDate) {
        try {
            String sql = "SELECT SUM(amount) as total FROM other_receipts WHERE receipt_date BETWEEN ? AND ?";
            ps = connection.prepareStatement(sql);
            ps.setString(1, startDate);
            ps.setString(2, endDate);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                return rs.getDouble("total");
            }
            
        } catch (SQLException e) {
            System.out.println("Error get total receipts: " + e.getMessage());
            e.printStackTrace();
        }
        
        return 0.0;
    }
    
    /**
     * Main method untuk testing CRUD operations
     */
    public static void main(String[] args) {
        OtherReceiptController controller = new OtherReceiptController();
        
        System.out.println("\n===== TESTING OTHER RECEIPT CONTROLLER =====\n");
        
        // Test 1: Get all data
        System.out.println("Test 1: Get all other receipts");
        List<OtherReceipt> receipts = controller.getData(null);
        System.out.println("Total receipts: " + receipts.size());
        
        // Test 2: Search
        System.out.println("\nTest 2: Search 'Bank'");
        receipts = controller.getData("Bank");
        System.out.println("Found: " + receipts.size() + " receipts");
        
        // Test 3: Get by ID
        System.out.println("\nTest 3: Get receipt by ID 'RCP001'");
        OtherReceipt receipt = controller.getReceiptById("RCP001");
        if (receipt != null) {
            System.out.println("Receipt: " + receipt.getDescription());
            System.out.println("Amount: " + receipt.getFormattedAmount());
            System.out.println("Source: " + receipt.getSource());
        }
        
        // Test 4: Create new receipt (uncomment to test)
        /*
        System.out.println("\nTest 4: Create new receipt");
        OtherReceipt newReceipt = new OtherReceipt();
        newReceipt.setCategoryId("TRC006");
        newReceipt.setDescription("Test Penerimaan Lain");
        newReceipt.setAmount(1000000.0);
        newReceipt.setReceiptDate("2025-12-25");
        newReceipt.setSource("Testing Source");
        newReceipt.setCreatedBy("USR001");
        
        String result = controller.createReceipt(newReceipt);
        System.out.println("Result: " + result);
        */
        
        // Test 5: Get total by period
        System.out.println("\nTest 5: Get total receipts for Jan-Feb 2025");
        double total = controller.getTotalReceiptsByPeriod("2025-01-01", "2025-02-28");
        System.out.println("Total: Rp " + String.format("%,.2f", total));
        
        System.out.println("\n===== TESTING COMPLETED =====");
    }
}
