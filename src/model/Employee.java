/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 * Model class untuk representasi tabel employees
 * POJO untuk data karyawan dalam sistem
 * 
 * @author Leonovo
 * @version 1.0
 */
public class Employee {
    
    // Field dari database
    private String employeeId;
    private String nik;
    private String fullname;
    private String position;
    private String contact; // Phone/Email
    private String status; // active, inactive
    
    // Salary fields (Option A)
    private Double basicSalary;
    private Double allowance;
    private Double taxPph21;
    private Double bpjs;
    private Double otherDeductions;
    
    private String bankName;
    private String bankAccountNumber;
    private String createdAt;
    
    // Constructors
    public Employee() {
    }
    
    public Employee(String employeeId, String nik, String fullname) {
        this.employeeId = employeeId;
        this.nik = nik;
        this.fullname = fullname;
        this.status = "active"; // default status
        this.basicSalary = 0.0;
        this.allowance = 0.0;
        this.taxPph21 = 0.0;
        this.bpjs = 0.0;
        this.otherDeductions = 0.0;
    }
    
    // Getter dan Setter methods
    
    public String getEmployeeId() {
        return employeeId;
    }
    
    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }
    
    public String getNik() {
        return nik;
    }
    
    public void setNik(String nik) {
        this.nik = nik;
    }
    
    public String getFullname() {
        return fullname;
    }
    
    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
    
    public String getPosition() {
        return position;
    }
    
    public void setPosition(String position) {
        this.position = position;
    }
    
    public String getContact() {
        return contact;
    }
    
    public void setContact(String contact) {
        this.contact = contact;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getBankName() {
        return bankName;
    }
    
    public void setBankName(String bankName) {
        this.bankName = bankName;
    }
    
    public String getBankAccountNumber() {
        return bankAccountNumber;
    }
    
    public void setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }
    
    public String getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
    
    // Salary getters and setters
    
    public Double getBasicSalary() {
        return basicSalary;
    }
    
    public void setBasicSalary(Double basicSalary) {
        this.basicSalary = basicSalary;
    }
    
    public Double getAllowance() {
        return allowance;
    }
    
    public void setAllowance(Double allowance) {
        this.allowance = allowance;
    }
    
    public Double getTaxPph21() {
        return taxPph21;
    }
    
    public void setTaxPph21(Double taxPph21) {
        this.taxPph21 = taxPph21;
    }
    
    public Double getBpjs() {
        return bpjs;
    }
    
    public void setBpjs(Double bpjs) {
        this.bpjs = bpjs;
    }
    
    public Double getOtherDeductions() {
        return otherDeductions;
    }
    
    public void setOtherDeductions(Double otherDeductions) {
        this.otherDeductions = otherDeductions;
    }
    
    // Utility methods
    
    /**
     * Cek apakah karyawan berstatus aktif
     * @return true jika aktif, false jika tidak
     */
    public boolean isActive() {
        return "active".equalsIgnoreCase(status);
    }
    
    /**
     * Format NIK untuk display dengan mask (hanya tampilkan 4 digit terakhir)
     * @return String NIK terformat
     */
    public String getFormattedNik() {
        if (nik == null || nik.length() <= 4) {
            return nik;
        }
        return "****" + nik.substring(nik.length() - 4);
    }
    
    /**
     * Get display name dengan status
     * @return nama lengkap dengan badge status
     */
    public String getDisplayName() {
        return fullname + (isActive() ? "" : " [INACTIVE]");
    }
    
    /**
     * Get informasi bank lengkap
     * @return String info bank
     */
    public String getBankInfo() {
        if (bankName == null && bankAccountNumber == null) {
            return "Belum ada data bank";
        }
        return (bankName != null ? bankName : "-") + " - " + 
               (bankAccountNumber != null ? bankAccountNumber : "-");
    }
    
    /**
     * Calculate total gross salary (basic + allowance)
     * @return Double total gross
     */
    public Double getGrossSalary() {
        double basic = (basicSalary != null) ? basicSalary : 0.0;
        double allow = (allowance != null) ? allowance : 0.0;
        return basic + allow;
    }
    
    /**
     * Calculate total deductions (tax + bpjs + other)
     * @return Double total deductions
     */
    public Double getTotalDeductions() {
        double tax = (taxPph21 != null) ? taxPph21 : 0.0;
        double bpjsAmt = (bpjs != null) ? bpjs : 0.0;
        double other = (otherDeductions != null) ? otherDeductions : 0.0;
        return tax + bpjsAmt + other;
    }
    
    /**
     * Calculate net salary (gross - deductions)
     * @return Double net salary
     */
    public Double getNetSalary() {
        return getGrossSalary() - getTotalDeductions();
    }
    
    /**
     * Validasi data employee
     * @return true jika valid, false jika tidak
     */
    public boolean isValid() {
        return nik != null && !nik.trim().isEmpty()
                && fullname != null && !fullname.trim().isEmpty()
                && status != null && (status.equals("active") || status.equals("inactive"));
    }
    
    @Override
    public String toString() {
        return "Employee{" +
                "employeeId='" + employeeId + '\'' +
                ", nik='" + nik + '\'' +
                ", fullname='" + fullname + '\'' +
                ", position='" + position + '\'' +
                ", contact='" + contact + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
