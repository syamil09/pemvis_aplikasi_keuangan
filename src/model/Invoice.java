/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Leonovo
 */

import java.sql.Timestamp;
import java.time.LocalDate;

/**
 * Model class untuk tabel invoices
 * Mapping langsung dengan struktur database
 */
public class Invoice {
    
    // Method untuk format tanggal display
    public String getFormattedInvoiceDate() {
        return formatDateForDisplay(invoiceDate);
    }
    
    public String getFormattedDueDate() {
        return formatDateForDisplay(dueDate);
    }
    
    // Helper method untuk format tanggal
    private String formatDateForDisplay(String dateString) {
        if (dateString == null || dateString.isEmpty()) {
            return "";
        }
        try {
            // Convert dari format "YYYY-MM-DD" ke "DD/MM/YYYY"
            String[] parts = dateString.split("-");
            if (parts.length == 3) {
                return parts[2] + "/" + parts[1] + "/" + parts[0];
            }
        } catch (Exception e) {
            // Jika parsing gagal, return string asli
        }
        return dateString;
    }
    
    // Method untuk validasi format tanggal
    public boolean isValidDateFormat(String dateString) {
        if (dateString == null || dateString.isEmpty()) {
            return false;
        }
        try {
            // Check format "YYYY-MM-DD"
            String[] parts = dateString.split("-");
            if (parts.length != 3) return false;
            
            int year = Integer.parseInt(parts[0]);
            int month = Integer.parseInt(parts[1]);
            int day = Integer.parseInt(parts[2]);
            
            return year >= 1900 && year <= 2100 && 
                   month >= 1 && month <= 12 && 
                   day >= 1 && day <= 31;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    // Database fields berdasarkan struktur tabel invoices
    private String invoiceId;
    private String projectId;
    private String clientId;
    private String invoiceNumber;
    private String invoiceDate;
    private String dueDate;
    private double subtotal;
    private double taxAmount;
    private double totalAmount;
    private double paidAmount;
    private String status;
    private String createdBy;
    private Timestamp createdAt;
    
    // Constructor default
    public Invoice() {
        this.subtotal = 0.0;
        this.taxAmount = 0.0;
        this.totalAmount = 0.0;
        this.paidAmount = 0.0;
        this.status = "draft";
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }
    
    // Constructor dengan parameter utama
    public Invoice(String invoiceId, String projectId, String clientId, String invoiceNumber) {
        this();
        this.invoiceId = invoiceId;
        this.projectId = projectId;
        this.clientId = clientId;
        this.invoiceNumber = invoiceNumber;
    }
    
    // Getter dan Setter untuk invoiceId
    public String getInvoiceId() {
        return invoiceId;
    }
    
    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }
    
    // Getter dan Setter untuk projectId
    public String getProjectId() {
        return projectId;
    }
    
    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }
    
    // Getter dan Setter untuk clientId
    public String getClientId() {
        return clientId;
    }
    
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
    
    // Getter dan Setter untuk invoiceNumber
    public String getInvoiceNumber() {
        return invoiceNumber;
    }
    
    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }
    
    // Getter dan Setter untuk invoiceDate
    public String getInvoiceDate() {
        return invoiceDate;
    }
    
    public void setInvoiceDate(String invoiceDate) {
        this.invoiceDate = invoiceDate;
    }
    
    // Getter dan Setter untuk dueDate
    public String getDueDate() {
        return dueDate;
    }
    
    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }
    
    // Getter dan Setter untuk subtotal
    public double getSubtotal() {
        return subtotal;
    }
    
    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }
    
    // Getter dan Setter untuk taxAmount
    public double getTaxAmount() {
        return taxAmount;
    }
    
    public void setTaxAmount(double taxAmount) {
        this.taxAmount = taxAmount;
    }
    
    // Getter dan Setter untuk totalAmount
    public double getTotalAmount() {
        return totalAmount;
    }
    
    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }
    
    // Getter dan Setter untuk paidAmount
    public double getPaidAmount() {
        return paidAmount;
    }
    
    public void setPaidAmount(double paidAmount) {
        this.paidAmount = paidAmount;
    }
    
    // Getter dan Setter untuk status
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    // Getter dan Setter untuk createdBy
    public String getCreatedBy() {
        return createdBy;
    }
    
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
    
    // Getter dan Setter untuk createdAt
    public Timestamp getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
    
    // Method untuk menghitung sisa tagihan
    public double getRemainingAmount() {
        return this.totalAmount - this.paidAmount;
    }
    
    // Method untuk cek apakah sudah lunas
    public boolean isFullyPaid() {
        return this.paidAmount >= this.totalAmount;
    }
    
    // Method untuk cek apakah overdue
    public boolean isOverdue() {
        if (this.dueDate == null || this.dueDate.isEmpty()) {
            return false;
        }
        try {
            // Parse string date format "YYYY-MM-DD"
            String currentDate = java.time.LocalDate.now().toString();
            return this.dueDate.compareTo(currentDate) < 0 && !isFullyPaid();
        } catch (Exception e) {
            return false;
        }
    }
    
    // Method untuk format status display
    public String getStatusDisplay() {
        switch (status.toLowerCase()) {
            case "draft": return "Draft";
            case "sent": return "Terkirim";
            case "paid": return "Lunas";
            case "overdue": return "Jatuh Tempo";
            case "cancelled": return "Dibatalkan";
            default: return "Draft";
        }
    }
    
    // Method untuk format currency
    public String getFormattedSubtotal() {
        return String.format("Rp %,.2f", subtotal);
    }
    
    public String getFormattedTaxAmount() {
        return String.format("Rp %,.2f", taxAmount);
    }
    
    public String getFormattedTotalAmount() {
        return String.format("Rp %,.2f", totalAmount);
    }
    
    public String getFormattedPaidAmount() {
        return String.format("Rp %,.2f", paidAmount);
    }
    
    public String getFormattedRemainingAmount() {
        return String.format("Rp %,.2f", getRemainingAmount());
    }
    
    @Override
    public String toString() {
        return String.format("Invoice{id='%s', number='%s', total=%.2f, status='%s'}", 
                invoiceId, invoiceNumber, totalAmount, status);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Invoice invoice = (Invoice) obj;
        return invoiceId != null ? invoiceId.equals(invoice.invoiceId) : invoice.invoiceId == null;
    }
    
    @Override
    public int hashCode() {
        return invoiceId != null ? invoiceId.hashCode() : 0;
    }
}