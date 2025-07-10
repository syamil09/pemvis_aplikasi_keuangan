/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Leonovo
 */


import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import java.math.BigDecimal;

import java.math.BigDecimal;

import java.math.BigDecimal;

/**
 * Model class untuk representasi tabel expenses
 * Termasuk field tambahan projectName dan categoryName untuk keperluan UI
 * 
 * @author Your Name
 * @version 1.0
 */
public class Expense {
    
    // Field dari database
    private String expenseId;
    private String projectId;
    private String categoryId;
    private String description;
    private Double amount;
    private String expenseDate;
    private String receiptNumber;
    private String createdBy;
    private String createdAt;
    
    // Field tambahan untuk UI (tidak disimpan di database)
    private String projectName;
    private String categoryName;
    private String createdByName; // nama user yang menginput
    
    // Status untuk validasi dan approval
    private String status; // draft, approved, rejected
   
    
    // Getter dan Setter methods
    
    public String getExpenseId() {
        return expenseId;
    }
    
    public void setExpenseId(String expenseId) {
        this.expenseId = expenseId;
    }
    
    public String getProjectId() {
        return projectId;
    }
    
    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }
    
    public String getCategoryId() {
        return categoryId;
    }
    
    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public Double getAmount() {
        return amount;
    }
    
    public void setAmount(Double amount) {
        this.amount = amount;
    }
    
    public String getExpenseDate() {
        return expenseDate;
    }
    
    public void setExpenseDate(String expenseDate) {
        this.expenseDate = expenseDate;
    }
    
    public String getReceiptNumber() {
        return receiptNumber;
    }
    
    public void setReceiptNumber(String receiptNumber) {
        this.receiptNumber = receiptNumber;
    }
    
    public String getCreatedBy() {
        return createdBy;
    }
    
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
    
    public String getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
    
    // Getter dan Setter untuk field tambahan
    
    public String getProjectName() {
        return projectName;
    }
    
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
    
    public String getCategoryName() {
        return categoryName;
    }
    
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
    
    public String getCreatedByName() {
        return createdByName;
    }
    
    public void setCreatedByName(String createdByName) {
        this.createdByName = createdByName;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    // Utility methods
    
    /**
     * Cek apakah expense terkait dengan proyek tertentu
     * @return true jika terkait proyek, false jika pengeluaran umum
     */
    public boolean isProjectRelated() {
        return projectId != null && !projectId.trim().isEmpty();
    }
    
    /**
     * Format amount ke format currency Indonesia
     * @return String format Rupiah
     */
    public String getFormattedAmount() {
        if (amount == null) return "Rp 0";
        return String.format("Rp %,.2f", amount);
    }
    
    /**
     * Get display name untuk project (jika null tampilkan "Umum")
     * @return nama proyek atau "Umum"
     */
    public String getProjectDisplayName() {
        if (projectName != null && !projectName.trim().isEmpty()) {
            return projectName;
        }
        return isProjectRelated() ? "Proyek tidak ditemukan" : "Umum";
    }
    
    /**
     * Validasi data expense
     * @return true jika valid, false jika tidak
     */
    public boolean isValid() {
        return categoryId != null && !categoryId.trim().isEmpty()
                && description != null && !description.trim().isEmpty()
                && amount != null && amount > 0
                && expenseDate != null;
    }
    
}