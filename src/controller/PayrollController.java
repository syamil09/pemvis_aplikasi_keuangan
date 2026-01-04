/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import connection.DBConnection;
import model.Payroll;
import model.Employee;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

/**
 * Controller untuk mengelola operasi CRUD pada tabel payrolls
 * 
 * @author Leonovo
 * @version 1.0
 */
public class PayrollController {
    
    private final Connection connection;
    private PreparedStatement ps;
    private ResultSet rs;
    
    public PayrollController() {
        this.connection = DBConnection.getConnection();
    }
    
    /**
     * READ - Get all payrolls with search functionality
     * @param searchItem kata kunci pencarian (opsional, null untuk semua data)
     * @return List<Payroll>
     */
    public List<Payroll> getData(String searchItem) {
        List<Payroll> listData = new ArrayList<>();
        System.out.println("---- Getting payroll data -----");
        
        try {
            String sql;
            
            if (searchItem != null && !searchItem.trim().isEmpty()) {
                sql = "SELECT * FROM payrolls " +
                      "WHERE payroll_id LIKE ? OR " +
                      "employee_name LIKE ? OR " +
                      "notes LIKE ? " +
                      "ORDER BY period_year DESC, period_month DESC, created_at DESC";
                ps = connection.prepareStatement(sql);
                
                String searchPattern = "%" + searchItem.trim() + "%";
                ps.setString(1, searchPattern);
                ps.setString(2, searchPattern);
                ps.setString(3, searchPattern);
                
                System.out.println("Searching for: " + searchItem);
            } else {
                sql = "SELECT * FROM payrolls ORDER BY period_year DESC, period_month DESC, created_at DESC";
                ps = connection.prepareStatement(sql);
                System.out.println("Getting all payrolls");
            }
            
            rs = ps.executeQuery();
            
            while (rs.next()) {
                Payroll payroll = mapResultSetToPayroll(rs);
                listData.add(payroll);
            }
            
            System.out.println("Found " + listData.size() + " payroll records");
            
        } catch (SQLException e) {
            System.out.println("Error getting payroll data: " + e.getMessage());
            e.printStackTrace();
        }
        
        return listData;
    }
    
    /**
     * Get payroll data by periode
     * @param month
     * @param year
     * @return List<Payroll>
     */
    public List<Payroll> getByPeriod(int month, int year) {
        List<Payroll> listData = new ArrayList<>();
        System.out.println("---- Getting payroll for period: " + month + "/" + year + " -----");
        
        try {
            String sql = "SELECT * FROM payrolls WHERE period_month = ? AND period_year = ? ORDER BY employee_name ASC";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, month);
            ps.setInt(2, year);
            
            rs = ps.executeQuery();
            
            while (rs.next()) {
                Payroll payroll = mapResultSetToPayroll(rs);
                listData.add(payroll);
            }
            
            System.out.println("Found " + listData.size() + " payroll records for period");
            
        } catch (SQLException e) {
            System.out.println("Error getting payroll by period: " + e.getMessage());
            e.printStackTrace();
        }
        
        return listData;
    }
    
    /**
     * Ambil payroll berdasarkan ID
     * @param payrollId
     * @return Payroll object atau null jika tidak ditemukan
     */
    public Payroll getById(String payrollId) {
        System.out.println("---- Getting payroll by ID: " + payrollId + " -----");
        
        String sql = "SELECT * FROM payrolls WHERE payroll_id = ?";
        
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, payrollId);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                Payroll payroll = mapResultSetToPayroll(rs);
                System.out.println("Payroll found: " + payroll.getPayrollId());
                return payroll;
            } else {
                System.out.println("Payroll not found with ID: " + payrollId);
            }
            
        } catch (SQLException e) {
            System.out.println("Error getting payroll by ID: " + e.getMessage());
            e.printStackTrace();
        }
        
        return null;
    }
    
    /**
     * CREATE - Tambah payroll baru
     * @param payroll
     * @return String pesan hasil operasi
     */
    public String create(Payroll payroll) {
        System.out.println("---- Creating new payroll -----");
        
        if (!payroll.isValid()) {
            return "Data payroll tidak valid";
        }
        
        try {
            String sql = "INSERT INTO payrolls (payroll_id, period_month, period_year, payment_date, " +
                        "status, employee_id, employee_name, basic_salary, allowance, tax_pph21, " +
                        "bpjs, other_deductions, total_gaji, notes, created_at) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NOW())";
            ps = connection.prepareStatement(sql);
            
            // Generate ID jika belum ada
            if (payroll.getPayrollId() == null || payroll.getPayrollId().isEmpty()) {
                payroll.setPayrollId(generateNextId());
            }
            
            // Ensure total is calculated
            payroll.calculateTotal();
            
            ps.setString(1, payroll.getPayrollId());
            ps.setInt(2, payroll.getPeriodMonth());
            ps.setInt(3, payroll.getPeriodYear());
            ps.setString(4, payroll.getPaymentDate());
            ps.setString(5, payroll.getStatus());
            ps.setString(6, payroll.getEmployeeId());
            ps.setString(7, payroll.getEmployeeName());
            ps.setDouble(8, payroll.getBasicSalary());
            ps.setDouble(9, payroll.getAllowance());
            ps.setDouble(10, payroll.getTaxPph21());
            ps.setDouble(11, payroll.getBpjs());
            ps.setDouble(12, payroll.getOtherDeductions());
            ps.setDouble(13, payroll.getTotalGaji());
            ps.setString(14, payroll.getNotes());
            
            int result = ps.executeUpdate();
            if (result > 0) {
                return "Data payroll berhasil ditambah : " + payroll.getPayrollId();
            } else {
                return "Data payroll gagal ditambah";
            }
            
        } catch (SQLException e) {
            return "Terjadi error : " + e.getMessage();
        }
    }
    
    /**
     * UPDATE - Update payroll yang sudah ada
     * @param payroll
     * @return String pesan hasil operasi
     */
    public String update(Payroll payroll) {
        System.out.println("---- Updating payroll: " + payroll.getPayrollId() + " -----");
        
        if (!payroll.isValid()) {
            return "Data payroll tidak valid";
        }
        
        try {
            // Check if payroll is already paid
            Payroll existing = getById(payroll.getPayrollId());
            if (existing != null && existing.isPaid()) {
                return "Tidak dapat mengubah payroll yang sudah dibayar";
            }
            
            String sql = "UPDATE payrolls " +
                        "SET period_month = ?, period_year = ?, payment_date = ?, " +
                        "status = ?, employee_id = ?, employee_name = ?, " +
                        "basic_salary = ?, allowance = ?, tax_pph21 = ?, bpjs = ?, " +
                        "other_deductions = ?, total_gaji = ?, notes = ? " +
                        "WHERE payroll_id = ?";
            ps = connection.prepareStatement(sql);
            
            // Ensure total is calculated
            payroll.calculateTotal();
            
            ps.setInt(1, payroll.getPeriodMonth());
            ps.setInt(2, payroll.getPeriodYear());
            ps.setString(3, payroll.getPaymentDate());
            ps.setString(4, payroll.getStatus());
            ps.setString(5, payroll.getEmployeeId());
            ps.setString(6, payroll.getEmployeeName());
            ps.setDouble(7, payroll.getBasicSalary());
            ps.setDouble(8, payroll.getAllowance());
            ps.setDouble(9, payroll.getTaxPph21());
            ps.setDouble(10, payroll.getBpjs());
            ps.setDouble(11, payroll.getOtherDeductions());
            ps.setDouble(12, payroll.getTotalGaji());
            ps.setString(13, payroll.getNotes());
            ps.setString(14, payroll.getPayrollId());
            
            int result = ps.executeUpdate();
            if (result > 0) {
                return "Data payroll berhasil diupdate : " + payroll.getPayrollId();
            } else {
                return "Data payroll gagal diupdate - ID tidak ditemukan";
            }
            
        } catch (SQLException e) {
            return "Terjadi error : " + e.getMessage();
        }
    }
    
    /**
     * DELETE - Hapus payroll berdasarkan ID
     * @param payrollId
     * @return String pesan hasil operasi
     */
    public String delete(String payrollId) {
        System.out.println("---- Deleting payroll: " + payrollId + " -----");
        
        try {
            // Check if payroll is already paid
            Payroll existing = getById(payrollId);
            if (existing != null && existing.isPaid()) {
                return "Tidak dapat menghapus payroll yang sudah dibayar";
            }
            
            String sql = "DELETE FROM payrolls WHERE payroll_id = ?";
            ps = connection.prepareStatement(sql);
            ps.setString(1, payrollId);
            
            int result = ps.executeUpdate();
            if (result > 0) {
                return "Data payroll berhasil dihapus : " + payrollId;
            } else {
                return "Data payroll gagal dihapus - ID tidak ditemukan";
            }
            
        } catch (SQLException e) {
            return "Terjadi error : " + e.getMessage();
        }
    }
    
    /**
     * Generate payroll entries for a period from active employees
     * @param month
     * @param year
     * @return String pesan hasil operasi
     */
    public String generatePayrollForPeriod(int month, int year) {
        System.out.println("---- Generating payroll for: " + month + "/" + year + " -----");
        
        try {
            // Check if payroll already exists for this period
            List<Payroll> existing = getByPeriod(month, year);
            if (!existing.isEmpty()) {
                return "Payroll untuk periode ini sudah ada (" + existing.size() + " records)";
            }
            
            // Get all active employees
            EmployeeController empController = new EmployeeController();
            List<Employee> activeEmployees = empController.getActiveEmployees();
            
            if (activeEmployees.isEmpty()) {
                return "Tidak ada karyawan aktif untuk diproses";
            }
            
            System.out.println("Found " + activeEmployees.size() + " active employees");
            
            // Create payroll entry for each active employee with salary copied from employees
            int successCount = 0;
            StringBuilder errorLog = new StringBuilder();
            
            for (Employee emp : activeEmployees) {
                System.out.println("\nProcessing employee: " + emp.getEmployeeId() + " - " + emp.getFullname());
                
                Payroll payroll = new Payroll(emp.getEmployeeId(), emp.getFullname(), month, year);
                
                // Copy salary from employee master (Option A!)
                payroll.setBasicSalary(emp.getBasicSalary() != null ? emp.getBasicSalary() : 0.0);
                payroll.setAllowance(emp.getAllowance() != null ? emp.getAllowance() : 0.0);
                payroll.setTaxPph21(emp.getTaxPph21() != null ? emp.getTaxPph21() : 0.0);
                payroll.setBpjs(emp.getBpjs() != null ? emp.getBpjs() : 0.0);
                payroll.setOtherDeductions(emp.getOtherDeductions() != null ? emp.getOtherDeductions() : 0.0);
                
                System.out.println("Salary data - Basic: " + payroll.getBasicSalary() + 
                                 ", Allowance: " + payroll.getAllowance() + 
                                 ", Tax: " + payroll.getTaxPph21() +
                                 ", BPJS: " + payroll.getBpjs() +
                                 ", Other: " + payroll.getOtherDeductions());
                
                payroll.calculateTotal();
                System.out.println("Total Gaji calculated: " + payroll.getTotalGaji());
                
                payroll.setNotes("Gaji " + payroll.getFormattedPeriod() + " - Draft");
                
                System.out.println("Validating payroll... isValid: " + payroll.isValid());
                
                String result = create(payroll);
                System.out.println("Create result: " + result);
                
                if (result.contains("berhasil")) {
                    successCount++;
                    System.out.println("SUCCESS for " + emp.getFullname());
                } else {
                    System.out.println("FAILED for " + emp.getFullname() + ": " + result);
                    errorLog.append("\n- ").append(emp.getFullname()).append(": ").append(result);
                }
            }
            
            String finalMessage = "Berhasil generate " + successCount + " dari " + activeEmployees.size() + 
                                " karyawan untuk periode " + month + "/" + year;
            
            if (successCount == 0 && errorLog.length() > 0) {
                finalMessage += "\n\nError details:" + errorLog.toString();
            }
            
            return finalMessage;
            
        } catch (Exception e) {
            e.printStackTrace();
            return "Error generate payroll: " + e.getMessage();
        }
    }
    
    /**
     * Get total summary for a period
     * @param month
     * @param year
     * @return Map with summary data
     */
    public Map<String, Double> getSummaryByPeriod(int month, int year) {
        Map<String, Double> summary = new HashMap<>();
        
        try {
            String sql = "SELECT " +
                        "SUM(basic_salary) as total_basic, " +
                        "SUM(allowance) as total_allowance, " +
                        "SUM(tax_pph21) as total_tax, " +
                        "SUM(bpjs) as total_bpjs, " +
                        "SUM(other_deductions) as total_other, " +
                        "SUM(total_gaji) as total_net " +
                        "FROM payrolls " +
                        "WHERE period_month = ? AND period_year = ?";
            
            ps = connection.prepareStatement(sql);
            ps.setInt(1, month);
            ps.setInt(2, year);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                summary.put("total_basic", rs.getDouble("total_basic"));
                summary.put("total_allowance", rs.getDouble("total_allowance"));
                summary.put("total_tax", rs.getDouble("total_tax"));
                summary.put("total_bpjs", rs.getDouble("total_bpjs"));
                summary.put("total_other", rs.getDouble("total_other"));
                summary.put("total_net", rs.getDouble("total_net"));
            }
            
        } catch (SQLException e) {
            System.out.println("Error getting summary: " + e.getMessage());
        }
        
        return summary;
    }
    
    /**
     * Mark payroll period as paid
     * @param month
     * @param year
     * @param paymentDate
     * @return String pesan hasil operasi
     */
    public String markPeriodAsPaid(int month, int year, String paymentDate) {
        System.out.println("---- Marking period as paid: " + month + "/" + year + " -----");
        
        try {
            String sql = "UPDATE payrolls SET status = 'paid', payment_date = ? " +
                        "WHERE period_month = ? AND period_year = ? AND status = 'draft'";
            ps = connection.prepareStatement(sql);
            ps.setString(1, paymentDate);
            ps.setInt(2, month);
            ps.setInt(3, year);
            
            int result = ps.executeUpdate();
            if (result > 0) {
                return "Berhasil menandai " + result + " payroll sebagai dibayar untuk periode " + month + "/" + year;
            } else {
                return "Tidak ada payroll draft untuk periode ini";
            }
            
        } catch (SQLException e) {
            return "Error marking as paid: " + e.getMessage();
        }
    }
    
    /**
     * UTILITY - Generate next payroll ID
     * @return String payroll ID
     */
    public String generateNextId() {
        PreparedStatement psLocal = null;  // Use local variable to avoid overwriting instance ps
        ResultSet rsLocal = null;
        try {
            String sql = "SELECT payroll_id FROM payrolls WHERE payroll_id LIKE 'PAY%' ORDER BY payroll_id DESC LIMIT 1";
            psLocal = connection.prepareStatement(sql);
            rsLocal = psLocal.executeQuery();
            
            if (rsLocal.next()) {
                String lastId = rsLocal.getString("payroll_id");
                // Extract nomor dari PAY001 -> 001
                String numericPart = lastId.substring(3);
                int nextNumber = Integer.parseInt(numericPart) + 1;
                return String.format("PAY%03d", nextNumber);
            } else {
                return "PAY001"; // ID pertama
            }
            
        } catch (SQLException | NumberFormatException e) {
            System.out.println("Error generate payroll ID: " + e.getMessage());
            return "PAY001";
        } finally {
            // Clean up local resources
            try {
                if (rsLocal != null) rsLocal.close();
                if (psLocal != null) psLocal.close();
            } catch (SQLException e) {
                // Ignore cleanup errors
            }
        }
    }
    
    /**
     * Helper: Map ResultSet to Payroll object
     */
    private Payroll mapResultSetToPayroll(ResultSet rs) throws SQLException {
        Payroll payroll = new Payroll();
        payroll.setPayrollId(rs.getString("payroll_id"));
        payroll.setPeriodMonth(rs.getInt("period_month"));
        payroll.setPeriodYear(rs.getInt("period_year"));
        payroll.setPaymentDate(rs.getString("payment_date"));
        payroll.setStatus(rs.getString("status"));
        payroll.setEmployeeId(rs.getString("employee_id"));
        payroll.setEmployeeName(rs.getString("employee_name"));
        payroll.setBasicSalary(rs.getDouble("basic_salary"));
        payroll.setAllowance(rs.getDouble("allowance"));
        payroll.setTaxPph21(rs.getDouble("tax_pph21"));
        payroll.setBpjs(rs.getDouble("bpjs"));
        payroll.setOtherDeductions(rs.getDouble("other_deductions"));
        payroll.setTotalGaji(rs.getDouble("total_gaji"));
        payroll.setNotes(rs.getString("notes"));
        payroll.setCreatedAt(rs.getString("created_at"));
        return payroll;
    }
    
    /**
     * Main method untuk testing
     */
    public static void main(String[] args) {
        PayrollController controller = new PayrollController();
        
        // Test: Get all payrolls
        System.out.println("\n=== Test Get All Payrolls ===");
        List<Payroll> payrolls = controller.getData(null);
        System.out.println("Total payrolls: " + payrolls.size());
        
        // Test: Get by period
        System.out.println("\n=== Test Get By Period ===");
        List<Payroll> periodPayrolls = controller.getByPeriod(1, 2025);
        System.out.println("Payrolls for Jan 2025: " + periodPayrolls.size());
        
        // Test: Get summary
        System.out.println("\n=== Test Get Summary ===");
        Map<String, Double> summary = controller.getSummaryByPeriod(1, 2025);
        System.out.println("Summary: " + summary);
        
        // Test: Generate next ID
        System.out.println("\n=== Test Generate Next ID ===");
        String nextId = controller.generateNextId();
        System.out.println("Next ID: " + nextId);
    }
}
