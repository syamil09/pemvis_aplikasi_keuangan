/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

/**
 *
 * @author Leonovo
 */
import connection.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import model.Invoice;

/**
 * Controller class untuk operasi yang berkaitan dengan database dan invoice
 * @author YourName
 */
public class InvoiceController {
    private final Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;
    
    public InvoiceController() {
        conn = DBConnection.getConnection();
    }
    
    // CREATE - Add new invoice
    public String createInvoice(Invoice invoice) {
        System.out.println("---- Creating new invoice -----");
        try {
            String sql = "INSERT INTO invoices (invoice_id, project_id, client_id, invoice_number, invoice_date, due_date, subtotal, tax_amount, total_amount, paid_amount, status, created_by, created_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, invoice.getInvoiceId());
            ps.setString(2, invoice.getProjectId());
            ps.setString(3, invoice.getClientId());
            ps.setString(4, invoice.getInvoiceNumber());
            ps.setString(5, invoice.getInvoiceDate());
            ps.setString(6, invoice.getDueDate());
            ps.setDouble(7, invoice.getSubtotal());
            ps.setDouble(8, invoice.getTaxAmount());
            ps.setDouble(9, invoice.getTotalAmount());
            ps.setDouble(10, invoice.getPaidAmount());
            ps.setString(11, invoice.getStatus());
            ps.setString(12, invoice.getCreatedBy());
            ps.setTimestamp(13, invoice.getCreatedAt());
            
            int result = ps.executeUpdate();
            if (result > 0) {
                return "Data invoice berhasil ditambah: " + invoice.getInvoiceId();
            } else {
                return "Data invoice gagal ditambah";
            }
        } catch (SQLException e) {
            return "Terjadi error: " + e.getMessage();
        }
    }
    
    // READ - Get all invoices with search functionality
    public List<Invoice> getData(String searchItem) {
        List<Invoice> listData = new ArrayList<>();
        System.out.println("---- Getting invoice data -----");
        
        try {
            String sql;
            
            if (searchItem != null && !searchItem.trim().isEmpty()) {
                sql = "SELECT * FROM invoices WHERE " +
                      "invoice_id LIKE ? OR " +
                      "invoice_number LIKE ? OR " +
                      "client_id LIKE ? OR " +
                      "project_id LIKE ? OR " +
                      "status LIKE ? " +
                      "ORDER BY created_at DESC";
                ps = conn.prepareStatement(sql);
                
                String searchPattern = "%" + searchItem.trim() + "%";
                ps.setString(1, searchPattern);
                ps.setString(2, searchPattern);
                ps.setString(3, searchPattern);
                ps.setString(4, searchPattern);
                ps.setString(5, searchPattern);
                
                System.out.println("Searching for: " + searchItem);
            } else {
                sql = "SELECT * FROM invoices ORDER BY created_at DESC";
                ps = conn.prepareStatement(sql);
                System.out.println("Getting all invoices");
            }
            
            rs = ps.executeQuery();
            
            while (rs.next()) {
                Invoice invoice = new Invoice();
                invoice.setInvoiceId(rs.getString("invoice_id"));
                invoice.setProjectId(rs.getString("project_id"));
                invoice.setClientId(rs.getString("client_id"));
                invoice.setInvoiceNumber(rs.getString("invoice_number"));
                invoice.setInvoiceDate(rs.getString("invoice_date"));
                invoice.setDueDate(rs.getString("due_date"));
                invoice.setSubtotal(rs.getDouble("subtotal"));
                invoice.setTaxAmount(rs.getDouble("tax_amount"));
                invoice.setTotalAmount(rs.getDouble("total_amount"));
                invoice.setPaidAmount(rs.getDouble("paid_amount"));
                invoice.setStatus(rs.getString("status"));
                invoice.setCreatedBy(rs.getString("created_by"));
                invoice.setCreatedAt(rs.getTimestamp("created_at"));
                
                listData.add(invoice);
                System.out.println("    Invoice ID: " + rs.getString("invoice_id"));
            }
            
            System.out.println("Found " + listData.size() + " invoices");
            
        } catch (SQLException e) {
            System.out.println("Error getting invoice data: " + e.getMessage());
            e.printStackTrace();
        }
        
        return listData;
    }
    
    // READ - Get invoice by ID
    public Invoice getInvoiceById(String invoiceId) {
        System.out.println("---- Getting invoice by ID: " + invoiceId + " -----");
        
        try {
            String sql = "SELECT * FROM invoices WHERE invoice_id = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, invoiceId);
            
            rs = ps.executeQuery();
            
            if (rs.next()) {
                Invoice invoice = new Invoice();
                invoice.setInvoiceId(rs.getString("invoice_id"));
                invoice.setProjectId(rs.getString("project_id"));
                invoice.setClientId(rs.getString("client_id"));
                invoice.setInvoiceNumber(rs.getString("invoice_number"));
                invoice.setInvoiceDate(rs.getString("invoice_date"));
                invoice.setDueDate(rs.getString("due_date"));
                invoice.setSubtotal(rs.getDouble("subtotal"));
                invoice.setTaxAmount(rs.getDouble("tax_amount"));
                invoice.setTotalAmount(rs.getDouble("total_amount"));
                invoice.setPaidAmount(rs.getDouble("paid_amount"));
                invoice.setStatus(rs.getString("status"));
                invoice.setCreatedBy(rs.getString("created_by"));
                invoice.setCreatedAt(rs.getTimestamp("created_at"));
                
                System.out.println("Invoice found: " + invoice.getInvoiceId());
                return invoice;
            } else {
                System.out.println("Invoice not found with ID: " + invoiceId);
            }
            
        } catch (SQLException e) {
            System.out.println("Error getting invoice by ID: " + e.getMessage());
        }
        
        return null;
    }
    
    // UPDATE - Update existing invoice
    public String updateInvoice(Invoice invoice) {
        System.out.println("---- Updating invoice: " + invoice.getInvoiceId() + " -----");
        
        try {
            String sql = "UPDATE invoices SET project_id = ?, client_id = ?, invoice_number = ?, invoice_date = ?, due_date = ?, subtotal = ?, tax_amount = ?, total_amount = ?, paid_amount = ?, status = ?, created_by = ? WHERE invoice_id = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, invoice.getProjectId());
            ps.setString(2, invoice.getClientId());
            ps.setString(3, invoice.getInvoiceNumber());
            ps.setString(4, invoice.getInvoiceDate());
            ps.setString(5, invoice.getDueDate());
            ps.setDouble(6, invoice.getSubtotal());
            ps.setDouble(7, invoice.getTaxAmount());
            ps.setDouble(8, invoice.getTotalAmount());
            ps.setDouble(9, invoice.getPaidAmount());
            ps.setString(10, invoice.getStatus());
            ps.setString(11, invoice.getCreatedBy());
            ps.setString(12, invoice.getInvoiceId());
            
            int result = ps.executeUpdate();
            if (result > 0) {
                return "Invoice updated successfully: " + invoice.getInvoiceId();
            } else {
                return "No invoice found with ID: " + invoice.getInvoiceId();
            }
        } catch (SQLException e) {
            return "Error updating invoice: " + e.getMessage();
        }
    }
    
    // DELETE - Delete invoice by ID
    public boolean deleteInvoice(String invoiceId) {
        System.out.println("---- Deleting invoice: " + invoiceId + " -----");
        
        try {
            String sql = "DELETE FROM invoices WHERE invoice_id = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, invoiceId);
            
            int result = ps.executeUpdate();
            if (result > 0) {
                System.out.println("Invoice deleted successfully: " + invoiceId);
                return true;
            } else {
                System.out.println("No invoice found with ID: " + invoiceId);
            }
        } catch (SQLException e) {
            System.out.println("Error deleting invoice: " + e.getMessage());
        }
        return false;
    }
    
    // ADDITIONAL METHODS - Business specific methods
    
    // Get invoices by client ID
    public List<Invoice> getInvoicesByClientId(String clientId) {
        List<Invoice> listData = new ArrayList<>();
        System.out.println("---- Getting invoices by client ID: " + clientId + " -----");
        
        try {
            String sql = "SELECT * FROM invoices WHERE client_id = ? ORDER BY created_at DESC";
            ps = conn.prepareStatement(sql);
            ps.setString(1, clientId);
            
            rs = ps.executeQuery();
            
            while (rs.next()) {
                Invoice invoice = new Invoice();
                invoice.setInvoiceId(rs.getString("invoice_id"));
                invoice.setProjectId(rs.getString("project_id"));
                invoice.setClientId(rs.getString("client_id"));
                invoice.setInvoiceNumber(rs.getString("invoice_number"));
                invoice.setInvoiceDate(rs.getString("invoice_date"));
                invoice.setDueDate(rs.getString("due_date"));
                invoice.setSubtotal(rs.getDouble("subtotal"));
                invoice.setTaxAmount(rs.getDouble("tax_amount"));
                invoice.setTotalAmount(rs.getDouble("total_amount"));
                invoice.setPaidAmount(rs.getDouble("paid_amount"));
                invoice.setStatus(rs.getString("status"));
                invoice.setCreatedBy(rs.getString("created_by"));
                invoice.setCreatedAt(rs.getTimestamp("created_at"));
                
                listData.add(invoice);
            }
            
            System.out.println("Found " + listData.size() + " invoices for client: " + clientId);
            
        } catch (SQLException e) {
            System.out.println("Error getting invoices by client ID: " + e.getMessage());
        }
        
        return listData;
    }
    
    // Get invoices by status
    public List<Invoice> getInvoicesByStatus(String status) {
        List<Invoice> listData = new ArrayList<>();
        System.out.println("---- Getting invoices by status: " + status + " -----");
        
        try {
            String sql = "SELECT * FROM invoices WHERE status = ? ORDER BY created_at DESC";
            ps = conn.prepareStatement(sql);
            ps.setString(1, status);
            
            rs = ps.executeQuery();
            
            while (rs.next()) {
                Invoice invoice = new Invoice();
                invoice.setInvoiceId(rs.getString("invoice_id"));
                invoice.setProjectId(rs.getString("project_id"));
                invoice.setClientId(rs.getString("client_id"));
                invoice.setInvoiceNumber(rs.getString("invoice_number"));
                invoice.setInvoiceDate(rs.getString("invoice_date"));
                invoice.setDueDate(rs.getString("due_date"));
                invoice.setSubtotal(rs.getDouble("subtotal"));
                invoice.setTaxAmount(rs.getDouble("tax_amount"));
                invoice.setTotalAmount(rs.getDouble("total_amount"));
                invoice.setPaidAmount(rs.getDouble("paid_amount"));
                invoice.setStatus(rs.getString("status"));
                invoice.setCreatedBy(rs.getString("created_by"));
                invoice.setCreatedAt(rs.getTimestamp("created_at"));
                
                listData.add(invoice);
            }
            
            System.out.println("Found " + listData.size() + " invoices with status: " + status);
            
        } catch (SQLException e) {
            System.out.println("Error getting invoices by status: " + e.getMessage());
        }
        
        return listData;
    }
    
    // Update invoice status
    public String updateInvoiceStatus(String invoiceId, String newStatus) {
        System.out.println("---- Updating invoice status: " + invoiceId + " to " + newStatus + " -----");
        
        try {
            String sql = "UPDATE invoices SET status = ? WHERE invoice_id = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, newStatus);
            ps.setString(2, invoiceId);
            
            int result = ps.executeUpdate();
            if (result > 0) {
                return "Invoice status updated successfully: " + invoiceId;
            } else {
                return "No invoice found with ID: " + invoiceId;
            }
        } catch (SQLException e) {
            return "Error updating invoice status: " + e.getMessage();
        }
    }
    
    // Update payment amount
    public String updatePaymentAmount(String invoiceId, double paidAmount) {
        System.out.println("---- Updating payment amount for invoice: " + invoiceId + " -----");
        
        try {
            String sql = "UPDATE invoices SET paid_amount = ? WHERE invoice_id = ?";
            ps = conn.prepareStatement(sql);
            ps.setDouble(1, paidAmount);
            ps.setString(2, invoiceId);
            
            int result = ps.executeUpdate();
            if (result > 0) {
                return "Payment amount updated successfully: " + invoiceId;
            } else {
                return "No invoice found with ID: " + invoiceId;
            }
        } catch (SQLException e) {
            return "Error updating payment amount: " + e.getMessage();
        }
    }
    
    // Generate next invoice number
    public String generateNextInvoiceNumber() {
        try {
            String sql = "SELECT MAX(CAST(SUBSTRING(invoice_number, 10) AS UNSIGNED)) as max_num FROM invoices WHERE invoice_number LIKE 'INV/2025/%'";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                int maxNum = rs.getInt("max_num");
                int nextNum = maxNum + 1;
                return String.format("INV/2025/%03d", nextNum);
            } else {
                return "INV/2025/001";
            }
        } catch (SQLException e) {
            System.out.println("Error generating invoice number: " + e.getMessage());
            return "INV/2025/001";
        }
    }
    
    // TESTING - Test all CRUD operations
    public static void main(String[] args) {
        InvoiceController controller = new InvoiceController();
        
        // Test CREATE
        System.out.println("=== TESTING CREATE ===");
        Invoice newInvoice = new Invoice();
        newInvoice.setInvoiceId("INV999");
        newInvoice.setProjectId("PRJ001");
        newInvoice.setClientId("CLI001");
        newInvoice.setInvoiceNumber("INV/2025/999");
        newInvoice.setInvoiceDate("2025-07-10");
        newInvoice.setDueDate("2025-08-09");
        newInvoice.setSubtotal(100000000.0);
        newInvoice.setTaxAmount(11000000.0);
        newInvoice.setTotalAmount(111000000.0);
        newInvoice.setPaidAmount(0.0);
        newInvoice.setStatus("draft");
        newInvoice.setCreatedBy("USR001");
        newInvoice.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        
        String created = controller.createInvoice(newInvoice);
        System.out.println("Create result: " + created);
        
        // Test READ
        System.out.println("\n=== TESTING READ ===");
        List<Invoice> invoices = controller.getData("");
        System.out.println("Total invoices: " + invoices.size());
        
        // Test READ BY ID
        System.out.println("\n=== TESTING READ BY ID ===");
        Invoice foundInvoice = controller.getInvoiceById("INV999");
        if (foundInvoice != null) {
            System.out.println("Found invoice: " + foundInvoice.getInvoiceNumber());
        }
        
        // Test UPDATE
        System.out.println("\n=== TESTING UPDATE ===");
        if (foundInvoice != null) {
            foundInvoice.setStatus("sent");
            String updated = controller.updateInvoice(foundInvoice);
            System.out.println("Update result: " + updated);
        }
        
        // Test additional methods
        System.out.println("\n=== TESTING ADDITIONAL METHODS ===");
        String nextNumber = controller.generateNextInvoiceNumber();
        System.out.println("Next invoice number: " + nextNumber);
        
        // Test DELETE
        System.out.println("\n=== TESTING DELETE ===");
        boolean deleted = controller.deleteInvoice("INV999");
        System.out.println("Delete result: " + deleted);
    }
}