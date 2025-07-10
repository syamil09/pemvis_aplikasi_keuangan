/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import connection.DBConnection;
import model.Expense;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import java.sql.SQLException;

/**
 * Controller untuk mengelola operasi CRUD pada tabel expenses
 * 
 * @author Leonovo
 * @version 1.0
 */
public class ExpenseController {
    
    private final Connection connection;
    private PreparedStatement ps;
    private ResultSet rs;
    
    public ExpenseController() {
        this.connection = DBConnection.getConnection();
    }
    
    /**
     * READ - Get all expenses with search functionality
     * @param searchItem kata kunci pencarian (opsional, null untuk semua data)
     * @return List<Expense>
     */
    public List<Expense> getData(String searchItem) {
        List<Expense> listData = new ArrayList<>();
        System.out.println("---- Getting expense data -----");
        
        try {
            String sql;
            
            if (searchItem != null && !searchItem.trim().isEmpty()) {
                sql = "SELECT e.expense_id, e.project_id, e.category_id, e.description, " +
                      "e.amount, e.expense_date, e.receipt_number, e.created_by, e.created_at, " +
                      "COALESCE(p.name, 'Umum') as project_name, " +
                      "tc.name as category_name, " +
                      "u.fullname as created_by_name " +
                      "FROM expenses e " +
                      "LEFT JOIN projects p ON e.project_id = p.project_id " +
                      "LEFT JOIN transaction_categories tc ON e.category_id = tc.category_id " +
                      "LEFT JOIN users u ON e.created_by = u.user_id " +
                      "WHERE e.expense_id LIKE ? OR " +
                      "e.description LIKE ? OR " +
                      "e.receipt_number LIKE ? OR " +
                      "p.name LIKE ? OR " +
                      "tc.name LIKE ? " +
                      "ORDER BY e.expense_date DESC, e.created_at DESC";
                ps = connection.prepareStatement(sql);
                
                String searchPattern = "%" + searchItem.trim() + "%";
                ps.setString(1, searchPattern);
                ps.setString(2, searchPattern);
                ps.setString(3, searchPattern);
                ps.setString(4, searchPattern);
                ps.setString(5, searchPattern);
                
                System.out.println("Searching for: " + searchItem);
            } else {
                sql = "SELECT e.expense_id, e.project_id, e.category_id, e.description, " +
                      "e.amount, e.expense_date, e.receipt_number, e.created_by, e.created_at, " +
                      "COALESCE(p.name, 'Umum') as project_name, " +
                      "tc.name as category_name, " +
                      "u.fullname as created_by_name " +
                      "FROM expenses e " +
                      "LEFT JOIN projects p ON e.project_id = p.project_id " +
                      "LEFT JOIN transaction_categories tc ON e.category_id = tc.category_id " +
                      "LEFT JOIN users u ON e.created_by = u.user_id " +
                      "ORDER BY e.expense_date DESC, e.created_at DESC";
                ps = connection.prepareStatement(sql);
                System.out.println("Getting all expenses");
            }
            
            rs = ps.executeQuery();
            
            while (rs.next()) {
                Expense expense = new Expense();
                expense.setExpenseId(rs.getString("expense_id"));
                expense.setProjectId(rs.getString("project_id"));
                expense.setCategoryId(rs.getString("category_id"));
                expense.setDescription(rs.getString("description"));
                expense.setAmount(rs.getDouble("amount"));
                expense.setExpenseDate(rs.getString("expense_date"));
                expense.setReceiptNumber(rs.getString("receipt_number"));
                expense.setCreatedBy(rs.getString("created_by"));
                expense.setCreatedAt(rs.getString("created_at"));
                
                // Field tambahan dari JOIN
                expense.setProjectName(rs.getString("project_name"));
                expense.setCategoryName(rs.getString("category_name"));
                expense.setCreatedByName(rs.getString("created_by_name"));
                expense.setStatus("approved"); // default status
                
                listData.add(expense);
                System.out.println("    Expense ID: " + rs.getString("expense_id"));
            }
            
            System.out.println("Found " + listData.size() + " expenses");
            
        } catch (SQLException e) {
            System.out.println("Error getting expense data: " + e.getMessage());
            e.printStackTrace();
        }
        
        return listData;
    }
    
    /**
     * Ambil expense berdasarkan ID
     * @param expenseId
     * @return Expense object atau null jika tidak ditemukan
     */
    public Expense getExpenseById(String expenseId) {
        System.out.println("---- Getting expense by ID: " + expenseId + " -----");
        
        String sql = "SELECT e.expense_id, e.project_id, e.category_id, e.description, " +
                     "e.amount, e.expense_date, e.receipt_number, e.created_by, e.created_at, " +
                     "COALESCE(p.name, 'Umum') as project_name, " +
                     "tc.name as category_name, " +
                     "u.fullname as created_by_name " +
                     "FROM expenses e " +
                     "LEFT JOIN projects p ON e.project_id = p.project_id " +
                     "LEFT JOIN transaction_categories tc ON e.category_id = tc.category_id " +
                     "LEFT JOIN users u ON e.created_by = u.user_id " +
                     "WHERE e.expense_id = ?";
        
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, expenseId);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                Expense expense = new Expense();
                expense.setExpenseId(rs.getString("expense_id"));
                expense.setProjectId(rs.getString("project_id"));
                expense.setCategoryId(rs.getString("category_id"));
                expense.setDescription(rs.getString("description"));
                expense.setAmount(rs.getDouble("amount"));
                expense.setExpenseDate(rs.getString("expense_date"));
                expense.setReceiptNumber(rs.getString("receipt_number"));
                expense.setCreatedBy(rs.getString("created_by"));
                expense.setCreatedAt(rs.getString("created_at"));
                
                expense.setProjectName(rs.getString("project_name"));
                expense.setCategoryName(rs.getString("category_name"));
                expense.setCreatedByName(rs.getString("created_by_name"));
                expense.setStatus("approved");
                
                System.out.println("Expense found: " + expense.getDescription());
                return expense;
            } else {
                System.out.println("Expense not found with ID: " + expenseId);
            }
            
        } catch (SQLException e) {
            System.out.println("Error getting expense by ID: " + e.getMessage());
            e.printStackTrace();
        }
        
        return null;
    }
    
    /**
     * CREATE - Tambah expense baru
     * @param expense
     * @return String pesan hasil operasi
     */
    public String createExpense(Expense expense) {
        System.out.println("---- Creating new expense -----");
        
        if (!expense.isValid()) {
            return "Data expense tidak valid";
        }
        
        try {
            String sql = "INSERT INTO expenses (expense_id, project_id, category_id, description, " +
                        "amount, expense_date, receipt_number, created_by, created_at) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, NOW())";
            ps = connection.prepareStatement(sql);
            
            // Generate ID jika belum ada
            if (expense.getExpenseId() == null || expense.getExpenseId().isEmpty()) {
                expense.setExpenseId(generateExpenseId());
            }
            
            ps.setString(1, expense.getExpenseId());
            ps.setString(2, expense.getProjectId());
            ps.setString(3, expense.getCategoryId());
            ps.setString(4, expense.getDescription());
            ps.setDouble(5, expense.getAmount());
            ps.setString(6, expense.getExpenseDate());
            ps.setString(7, expense.getReceiptNumber());
            ps.setString(8, expense.getCreatedBy());
            
            int result = ps.executeUpdate();
            if (result > 0) {
                return "Data expense berhasil ditambah : " + expense.getExpenseId();
            } else {
                return "Data expense gagal ditambah";
            }
            
        } catch (SQLException e) {
            return "Terjadi error : " + e.getMessage();
        }
    }
    
    /**
     * UPDATE - Update expense yang sudah ada
     * @param expense
     * @return String pesan hasil operasi
     */
    public String updateExpense(Expense expense) {
        System.out.println("---- Updating expense: " + expense.getExpenseId() + " -----");
        
        if (!expense.isValid()) {
            return "Data expense tidak valid";
        }
        
        try {
            String sql = "UPDATE expenses " +
                        "SET project_id = ?, category_id = ?, description = ?, " +
                        "amount = ?, expense_date = ?, receipt_number = ? " +
                        "WHERE expense_id = ?";
            ps = connection.prepareStatement(sql);
            
            ps.setString(1, expense.getProjectId());
            ps.setString(2, expense.getCategoryId());
            ps.setString(3, expense.getDescription());
            ps.setDouble(4, expense.getAmount());
            ps.setString(5, expense.getExpenseDate());
            ps.setString(6, expense.getReceiptNumber());
            ps.setString(7, expense.getExpenseId());
            
            int result = ps.executeUpdate();
            if (result > 0) {
                return "Data expense berhasil diupdate : " + expense.getExpenseId();
            } else {
                return "Data expense gagal diupdate - ID tidak ditemukan";
            }
            
        } catch (SQLException e) {
            return "Terjadi error : " + e.getMessage();
        }
    }
    
    /**
     * DELETE - Hapus expense berdasarkan ID
     * @param expenseId
     * @return String pesan hasil operasi
     */
    public String deleteExpense(String expenseId) {
        System.out.println("---- Deleting expense: " + expenseId + " -----");
        
        try {
            String sql = "DELETE FROM expenses WHERE expense_id = ?";
            ps = connection.prepareStatement(sql);
            ps.setString(1, expenseId);
            
            int result = ps.executeUpdate();
            if (result > 0) {
                return "Data expense berhasil dihapus : " + expenseId;
            } else {
                return "Data expense gagal dihapus - ID tidak ditemukan";
            }
            
        } catch (SQLException e) {
            return "Terjadi error : " + e.getMessage();
        }
    }
    
    /**
     * UTILITY - Generate next expense ID
     * @return String expense ID
     */
    private String generateExpenseId() {
        try {
            String sql = "SELECT expense_id FROM expenses WHERE expense_id LIKE 'EXP%' ORDER BY expense_id DESC LIMIT 1";
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                String lastId = rs.getString("expense_id");
                // Extract nomor dari EXP001 -> 001
                String numericPart = lastId.substring(3);
                int nextNumber = Integer.parseInt(numericPart) + 1;
                return String.format("EXP%03d", nextNumber);
            } else {
                return "EXP001"; // ID pertama
            }
            
        } catch (SQLException | NumberFormatException e) {
            System.out.println("Error generate expense ID: " + e.getMessage());
            return "EXP001";
        }
    }
    
    /**
     * Get total expenses berdasarkan periode
     * @param startDate format: yyyy-MM-dd
     * @param endDate format: yyyy-MM-dd
     * @return double total amount
     */
    public double getTotalExpensesByPeriod(String startDate, String endDate) {
        try {
            String sql = "SELECT SUM(amount) as total FROM expenses WHERE expense_date BETWEEN ? AND ?";
            ps = connection.prepareStatement(sql);
            ps.setString(1, startDate);
            ps.setString(2, endDate);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                return rs.getDouble("total");
            }
            
        } catch (SQLException e) {
            System.out.println("Error get total expenses: " + e.getMessage());
            e.printStackTrace();
        }
        
        return 0.0;
    }
}