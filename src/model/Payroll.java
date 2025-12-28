/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 * Model class untuk representasi tabel payrolls
 * POJO untuk data penggajian karyawan
 * 
 * @author Leonovo
 * @version 1.0
 */
public class Payroll {
    
    // Field dari database
    private String payrollId;
    private int periodMonth;
    private int periodYear;
    private String paymentDate;
    private String status; // draft, paid
    private String employeeId;
    private String employeeName;
    
    // Salary components (copied from employees)
    private Double basicSalary;
    private Double allowance;
    private Double taxPph21;
    private Double bpjs;
    private Double otherDeductions;
    
    private Double totalGaji;
    private String notes;
    private String createdAt;
    
    // Constructors
    public Payroll() {
        this.status = "draft"; // default status
        this.basicSalary = 0.0;
        this.allowance = 0.0;
        this.taxPph21 = 0.0;
        this.bpjs = 0.0;
        this.otherDeductions = 0.0;
    }
    
    public Payroll(String employeeId, String employeeName, int periodMonth, int periodYear) {
        this();
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.periodMonth = periodMonth;
        this.periodYear = periodYear;
    }
    
    // Getter dan Setter methods
    
    public String getPayrollId() {
        return payrollId;
    }
    
    public void setPayrollId(String payrollId) {
        this.payrollId = payrollId;
    }
    
    public int getPeriodMonth() {
        return periodMonth;
    }
    
    public void setPeriodMonth(int periodMonth) {
        this.periodMonth = periodMonth;
    }
    
    public int getPeriodYear() {
        return periodYear;
    }
    
    public void setPeriodYear(int periodYear) {
        this.periodYear = periodYear;
    }
    
    public String getPaymentDate() {
        return paymentDate;
    }
    
    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getEmployeeId() {
        return employeeId;
    }
    
    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }
    
    public String getEmployeeName() {
        return employeeName;
    }
    
    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }
    
    public Double getBasicSalary() {
        return basicSalary;
    }
    
    public void setBasicSalary(Double basicSalary) {
        this.basicSalary = basicSalary;
        calculateTotal();
    }
    
    public Double getAllowance() {
        return allowance;
    }
    
    public void setAllowance(Double allowance) {
        this.allowance = allowance;
        calculateTotal();
    }
    
    public Double getTaxPph21() {
        return taxPph21;
    }
    
    public void setTaxPph21(Double taxPph21) {
        this.taxPph21 = taxPph21;
        calculateTotal();
    }
    
    public Double getBpjs() {
        return bpjs;
    }
    
    public void setBpjs(Double bpjs) {
        this.bpjs = bpjs;
        calculateTotal();
    }
    
    public Double getOtherDeductions() {
        return otherDeductions;
    }
    
    public void setOtherDeductions(Double otherDeductions) {
        this.otherDeductions = otherDeductions;
        calculateTotal();
    }
    
    public Double getTotalGaji() {
        return totalGaji;
    }
    
    public void setTotalGaji(Double totalGaji) {
        this.totalGaji = totalGaji;
    }
    
    public String getNotes() {
        return notes;
    }
    
    public void setNotes(String notes) {
        this.notes = notes;
    }
    
    public String getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
    
    // Utility methods
    
    /**
     * Hitung total gaji otomatis
     * Total = Basic Salary + Allowance - (Tax + BPJS + Other Deductions)
     */
    public void calculateTotal() {
        double basic = (basicSalary != null) ? basicSalary : 0.0;
        double allow = (allowance != null) ? allowance : 0.0;
        double tax = (taxPph21 != null) ? taxPph21 : 0.0;
        double bpjsAmt = (bpjs != null) ? bpjs : 0.0;
        double other = (otherDeductions != null) ? otherDeductions : 0.0;
        
        this.totalGaji = basic + allow - (tax + bpjsAmt + other);
    }
    
    /**
     * Format periode ke string (e.g., "Januari 2025")
     * @return String periode terformat
     */
    public String getFormattedPeriod() {
        String[] monthNames = {
            "Januari", "Februari", "Maret", "April", "Mei", "Juni",
            "Juli", "Agustus", "September", "Oktober", "November", "Desember"
        };
        
        if (periodMonth < 1 || periodMonth > 12) {
            return periodMonth + "/" + periodYear;
        }
        
        return monthNames[periodMonth - 1] + " " + periodYear;
    }
    
    /**
     * Get status display text for UI
     * @return String status display
     */
    public String getStatusDisplay() {
        if (status == null) return "Unknown";
        switch (status) {
            case "draft":
                return "Draft";
            case "paid":
                return "Terbayar";
            case "pending":
                return "Pending";
            default:
                return status;
        }
    }
    
    /**
     * Format periode ke string pendek (e.g., "Jan 2025")
     * @return String periode terformat singkat
     */
    public String getShortPeriod() {
        String[] monthShort = {
            "Jan", "Feb", "Mar", "Apr", "Mei", "Jun",
            "Jul", "Ags", "Sep", "Okt", "Nov", "Des"
        };
        
        if (periodMonth < 1 || periodMonth > 12) {
            return periodMonth + "/" + periodYear;
        }
        
        return monthShort[periodMonth - 1] + " " + periodYear;
    }
    
    /**
     * Cek apakah payroll sudah dibayar
     * @return true jika sudah paid, false jika masih draft
     */
    public boolean isPaid() {
        return "paid".equalsIgnoreCase(status);
    }
    
    /**
     * Cek apakah payroll masih draft
     * @return true jika draft, false jika sudah paid
     */
    public boolean isDraft() {
        return "draft".equalsIgnoreCase(status);
    }
    
    /**
     * Format amount ke format currency Indonesia
     * @param amount
     * @return String format Rupiah
     */
    private String formatCurrency(Double amount) {
        if (amount == null) return "Rp 0";
        return String.format("Rp %,.2f", amount);
    }
    
    /**
     * Get formatted basic salary
     * @return String
     */
    public String getFormattedBasicSalary() {
        return formatCurrency(basicSalary);
    }
    
    /**
     * Get formatted allowance
     * @return String
     */
    public String getFormattedAllowance() {
        return formatCurrency(allowance);
    }
    
    /**
     * Get formatted tax PPh21
     * @return String
     */
    public String getFormattedTaxPph21() {
        return formatCurrency(taxPph21);
    }
    
    /**
     * Get formatted BPJS
     * @return String
     */
    public String getFormattedBpjs() {
        return formatCurrency(bpjs);
    }
    
    /**
     * Get formatted other deductions
     * @return String
     */
    public String getFormattedOtherDeductions() {
        return formatCurrency(otherDeductions);
    }
    
    /**
     * Get formatted total gaji
     * @return String
     */
    public String getFormattedTotalGaji() {
        return formatCurrency(totalGaji);
    }
    
    /**
     * Validasi data payroll
     * @return true jika valid, false jika tidak
     */
    public boolean isValid() {
        return employeeId != null && !employeeId.trim().isEmpty()
                && periodMonth >= 1 && periodMonth <= 12
                && periodYear > 2000
                && basicSalary != null && basicSalary >= 0
                && allowance != null && allowance >= 0
                && taxPph21 != null && taxPph21 >= 0
                && bpjs != null && bpjs >= 0
                && otherDeductions != null && otherDeductions >= 0;
    }
    
    @Override
    public String toString() {
        return "Payroll{" +
                "payrollId='" + payrollId + '\'' +
                ", period=" + getFormattedPeriod() +
                ", employeeName='" + employeeName + '\'' +
                ", totalGaji=" + getFormattedTotalGaji() +
                ", status='" + status + '\'' +
                '}';
    }
}
