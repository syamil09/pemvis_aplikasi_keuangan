/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import connection.DBConnection;
import model.Employee;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller untuk mengelola operasi CRUD pada tabel employees
 * 
 * @author Leonovo
 * @version 1.0
 */
public class EmployeeController {
    
    private final Connection connection;
    private PreparedStatement ps;
    private ResultSet rs;
    
    public EmployeeController() {
        this.connection = DBConnection.getConnection();
    }
    
    /**
     * RE AD - Get all employees with search functionality
     * @param searchItem kata kunci pencarian (opsional, null untuk semua data)
     * @return List<Employee>
     */
    public List<Employee> getData(String searchItem) {
        List<Employee> listData = new ArrayList<>();
        System.out.println("---- Getting employee data -----");
        
        try {
            String sql;
            
            if (searchItem != null && !searchItem.trim().isEmpty()) {
                sql = "SELECT * FROM employees " +
                      "WHERE employee_id LIKE ? OR " +
                      "nik LIKE ? OR " +
                      "fullname LIKE ? OR " +
                      "position LIKE ? OR " +
                      "contact LIKE ? " +
                      "ORDER BY created_at DESC";
                ps = connection.prepareStatement(sql);
                
                String searchPattern = "%" + searchItem.trim() + "%";
                ps.setString(1, searchPattern);
                ps.setString(2, searchPattern);
                ps.setString(3, searchPattern);
                ps.setString(4, searchPattern);
                ps.setString(5, searchPattern);
                
                System.out.println("Searching for: " + searchItem);
            } else {
                sql = "SELECT * FROM employees ORDER BY created_at DESC";
                ps = connection.prepareStatement(sql);
                System.out.println("Getting all employees");
            }
            
            rs = ps.executeQuery();
            
            while (rs.next()) {
                Employee employee = new Employee();
                employee.setEmployeeId(rs.getString("employee_id"));
                employee.setNik(rs.getString("nik"));
                employee.setFullname(rs.getString("fullname"));
                employee.setPosition(rs.getString("position"));
                employee.setContact(rs.getString("contact"));
                employee.setStatus(rs.getString("status"));
                // Salary fields
                employee.setBasicSalary(rs.getDouble("basic_salary"));
                employee.setAllowance(rs.getDouble("allowance"));
                employee.setTaxPph21(rs.getDouble("tax_pph21"));
                employee.setBpjs(rs.getDouble("bpjs"));
                employee.setOtherDeductions(rs.getDouble("other_deductions"));
                employee.setBankName(rs.getString("bank_name"));
                employee.setBankAccountNumber(rs.getString("bank_account_number"));
                employee.setCreatedAt(rs.getString("created_at"));
                
                listData.add(employee);
                System.out.println("    Employee ID: " + rs.getString("employee_id"));
            }
            
            System.out.println("Found " + listData.size() + " employees");
            
        } catch (SQLException e) {
            System.out.println("Error getting employee data: " + e.getMessage());
            e.printStackTrace();
        }
        
        return listData;
    }
    
    /**
     * Ambil employee berdasarkan ID
     * @param employeeId
     * @return Employee object atau null jika tidak ditemukan
     */
    public Employee getById(String employeeId) {
        System.out.println("---- Getting employee by ID: " + employeeId + " -----");
        
        String sql = "SELECT * FROM employees WHERE employee_id = ?";
        
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, employeeId);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                Employee employee = new Employee();
                employee.setEmployeeId(rs.getString("employee_id"));
                employee.setNik(rs.getString("nik"));
                employee.setFullname(rs.getString("fullname"));
                employee.setPosition(rs.getString("position"));
                employee.setContact(rs.getString("contact"));
                employee.setStatus(rs.getString("status"));
                // Salary fields
                employee.setBasicSalary(rs.getDouble("basic_salary"));
                employee.setAllowance(rs.getDouble("allowance"));
                employee.setTaxPph21(rs.getDouble("tax_pph21"));
                employee.setBpjs(rs.getDouble("bpjs"));
                employee.setOtherDeductions(rs.getDouble("other_deductions"));
                employee.setBankName(rs.getString("bank_name"));
                employee.setBankAccountNumber(rs.getString("bank_account_number"));
                employee.setCreatedAt(rs.getString("created_at"));
                
                System.out.println("Employee found: " + employee.getFullname());
                return employee;
            } else {
                System.out.println("Employee not found with ID: " + employeeId);
            }
            
        } catch (SQLException e) {
            System.out.println("Error getting employee by ID: " + e.getMessage());
            e.printStackTrace();
        }
        
        return null;
    }
    
    /**
     * Get active employees only
     * @return List<Employee>
     */
    public List<Employee> getActiveEmployees() {
        List<Employee> listData = new ArrayList<>();
        System.out.println("---- Getting active employees -----");
        
        try {
            String sql = "SELECT * FROM employees WHERE status = 'active' ORDER BY fullname ASC";
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                Employee employee = new Employee();
                employee.setEmployeeId(rs.getString("employee_id"));
                employee.setNik(rs.getString("nik"));
                employee.setFullname(rs.getString("fullname"));
                employee.setPosition(rs.getString("position"));
                employee.setContact(rs.getString("contact"));
                employee.setStatus(rs.getString("status"));
                // Salary fields
                employee.setBasicSalary(rs.getDouble("basic_salary"));
                employee.setAllowance(rs.getDouble("allowance"));
                employee.setTaxPph21(rs.getDouble("tax_pph21"));
                employee.setBpjs(rs.getDouble("bpjs"));
                employee.setOtherDeductions(rs.getDouble("other_deductions"));
                employee.setBankName(rs.getString("bank_name"));
                employee.setBankAccountNumber(rs.getString("bank_account_number"));
                employee.setCreatedAt(rs.getString("created_at"));
                
                listData.add(employee);
            }
            
            System.out.println("Found " + listData.size() + " active employees");
            
        } catch (SQLException e) {
            System.out.println("Error getting active employees: " + e.getMessage());
            e.printStackTrace();
        }
        
        return listData;
    }
    
    /**
     * CREATE - Tambah employee baru
     * @param employee
     * @return String pesan hasil operasi
     */
    public String create(Employee employee) {
        System.out.println("---- Creating new employee -----");
        
        if (!employee.isValid()) {
            return "Data employee tidak valid";
        }
        
        try {
            // Cek duplikasi NIK
            if (isNikExists(employee.getNik())) {
                return "NIK " + employee.getNik() + " sudah terdaftar";
            }
            
            String sql = "INSERT INTO employees (employee_id, nik, fullname, position, contact, " +
                        "status, basic_salary, allowance, tax_pph21, bpjs, other_deductions, " +
                        "bank_name, bank_account_number, created_at) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NOW())";
            ps = connection.prepareStatement(sql);
            
            // Generate ID jika belum ada
            if (employee.getEmployeeId() == null || employee.getEmployeeId().isEmpty()) {
                employee.setEmployeeId(generateNextId());
            }
            
            ps.setString(1, employee.getEmployeeId());
            ps.setString(2, employee.getNik());
            ps.setString(3, employee.getFullname());
            ps.setString(4, employee.getPosition());
            ps.setString(5, employee.getContact());
            ps.setString(6, employee.getStatus());
            ps.setDouble(7, employee.getBasicSalary() != null ? employee.getBasicSalary() : 0.0);
            ps.setDouble(8, employee.getAllowance() != null ? employee.getAllowance() : 0.0);
            ps.setDouble(9, employee.getTaxPph21() != null ? employee.getTaxPph21() : 0.0);
            ps.setDouble(10, employee.getBpjs() != null ? employee.getBpjs() : 0.0);
            ps.setDouble(11, employee.getOtherDeductions() != null ? employee.getOtherDeductions() : 0.0);
            ps.setString(12, employee.getBankName());
            ps.setString(13, employee.getBankAccountNumber());
            
            int result = ps.executeUpdate();
            if (result > 0) {
                return "Data employee berhasil ditambah : " + employee.getEmployeeId();
            } else {
                return "Data employee gagal ditambah";
            }
            
        } catch (SQLException e) {
            return "Terjadi error : " + e.getMessage();
        }
    }
    
    /**
     * UPDATE - Update employee yang sudah ada
     * @param employee
     * @return String pesan hasil operasi
     */
    public String update(Employee employee) {
        System.out.println("---- Updating employee: " + employee.getEmployeeId() + " -----");
        
        if (!employee.isValid()) {
            return "Data employee tidak valid";
        }
        
        try {
            String sql = "UPDATE employees " +
                        "SET nik = ?, fullname = ?, position = ?, contact = ?, " +
                        "status = ?, basic_salary = ?, allowance = ?, tax_pph21 = ?, " +
                        "bpjs = ?, other_deductions = ?, bank_name = ?, bank_account_number = ? " +
                        "WHERE employee_id = ?";
            ps = connection.prepareStatement(sql);
            
            ps.setString(1, employee.getNik());
            ps.setString(2, employee.getFullname());
            ps.setString(3, employee.getPosition());
            ps.setString(4, employee.getContact());
            ps.setString(5, employee.getStatus());
            ps.setDouble(6, employee.getBasicSalary() != null ? employee.getBasicSalary() : 0.0);
            ps.setDouble(7, employee.getAllowance() != null ? employee.getAllowance() : 0.0);
            ps.setDouble(8, employee.getTaxPph21() != null ? employee.getTaxPph21() : 0.0);
            ps.setDouble(9, employee.getBpjs() != null ? employee.getBpjs() : 0.0);
            ps.setDouble(10, employee.getOtherDeductions() != null ? employee.getOtherDeductions() : 0.0);
            ps.setString(11, employee.getBankName());
            ps.setString(12, employee.getBankAccountNumber());
            ps.setString(13, employee.getEmployeeId());
            
            int result = ps.executeUpdate();
            if (result > 0) {
                return "Data employee berhasil diupdate : " + employee.getEmployeeId();
            } else {
                return "Data employee gagal diupdate - ID tidak ditemukan";
            }
            
        } catch (SQLException e) {
            return "Terjadi error : " + e.getMessage();
        }
    }
    
    /**
     * DELETE - Hapus employee berdasarkan ID
     * @param employeeId
     * @return String pesan hasil operasi
     */
    public String delete(String employeeId) {
        System.out.println("---- Deleting employee: " + employeeId + " -----");
        
        try {
            // Cek apakah employee punya payroll data
            if (hasPayrollData(employeeId)) {
                return "Tidak dapat menghapus employee yang sudah memiliki data payroll";
            }
            
            String sql = "DELETE FROM employees WHERE employee_id = ?";
            ps = connection.prepareStatement(sql);
            ps.setString(1, employeeId);
            
            int result = ps.executeUpdate();
            if (result > 0) {
                return "Data employee berhasil dihapus : " + employeeId;
            } else {
                return "Data employee gagal dihapus - ID tidak ditemukan";
            }
            
        } catch (SQLException e) {
            return "Terjadi error : " + e.getMessage();
        }
    }
    
    /**
     * UTILITY - Generate next employee ID
     * @return String employee ID
     */
    public String generateNextId() {
        try {
            String sql = "SELECT employee_id FROM employees WHERE employee_id LIKE 'EMP%' ORDER BY employee_id DESC LIMIT 1";
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                String lastId = rs.getString("employee_id");
                // Extract nomor dari EMP001 -> 001
                String numericPart = lastId.substring(3);
                int nextNumber = Integer.parseInt(numericPart) + 1;
                return String.format("EMP%03d", nextNumber);
            } else {
                return "EMP001"; // ID pertama
            }
            
        } catch (SQLException | NumberFormatException e) {
            System.out.println("Error generate employee ID: " + e.getMessage());
            return "EMP001";
        }
    }
    
    /**
     * Check apakah NIK sudah terdaftar
     * @param nik
     * @return boolean
     */
    private boolean isNikExists(String nik) {
        try {
            String sql = "SELECT COUNT(*) as count FROM employees WHERE nik = ?";
            ps = connection.prepareStatement(sql);
            ps.setString(1, nik);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                return rs.getInt("count") > 0;
            }
        } catch (SQLException e) {
            System.out.println("Error checking NIK: " + e.getMessage());
        }
        return false;
    }
    
    /**
     * Check apakah employee punya data payroll
     * @param employeeId
     * @return boolean
     */
    private boolean hasPayrollData(String employeeId) {
        try {
            String sql = "SELECT COUNT(*) as count FROM payrolls WHERE employee_id = ?";
            ps = connection.prepareStatement(sql);
            ps.setString(1, employeeId);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                return rs.getInt("count") > 0;
            }
        } catch (SQLException e) {
            System.out.println("Error checking payroll data: " + e.getMessage());
        }
        return false;
    }
    
    /**
     * Main method untuk testing
     */
    public static void main(String[] args) {
        EmployeeController controller = new EmployeeController();
        
        // Test: Get all employees
        System.out.println("\n=== Test Get All Employees ===");
        List<Employee> employees = controller.getData(null);
        for (Employee emp : employees) {
            System.out.println(emp);
        }
        
        // Test: Get active employees only
        System.out.println("\n=== Test Get Active Employees ===");
        List<Employee> activeEmployees = controller.getActiveEmployees();
        System.out.println("Active employees count: " + activeEmployees.size());
        
        // Test: Generate next ID
        System.out.println("\n=== Test Generate Next ID ===");
        String nextId = controller.generateNextId();
        System.out.println("Next ID: " + nextId);
        
        // Test: Get by ID
        System.out.println("\n=== Test Get By ID ===");
        Employee emp = controller.getById("EMP001");
        if (emp != null) {
            System.out.println("Found: " + emp.toString());
        }
    }
}
