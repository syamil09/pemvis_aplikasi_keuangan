/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 * Model class untuk representasi tabel other_receipts
 * Termasuk field tambahan categoryName untuk keperluan UI
 * 
 * @author Leonovo
 * @version 1.0
 */
public class OtherReceipt {
    
    // Field dari database
    private String receiptId;
    private String categoryId;
    private String description;
    private Double amount;
    private String receiptDate;
    private String source;
    private String createdBy;
    private String createdAt;
    
    // Field tambahan untuk UI (tidak disimpan di database)
    private String categoryName;
    private String createdByName; // nama user yang menginput
    
    
    // Getter dan Setter methods
    
    public String getReceiptId() {
        return receiptId;
    }
    
    public void setReceiptId(String receiptId) {
        this.receiptId = receiptId;
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
    
    public String getReceiptDate() {
        return receiptDate;
    }
    
    public void setReceiptDate(String receiptDate) {
        this.receiptDate = receiptDate;
    }
    
    public String getSource() {
        return source;
    }
    
    public void setSource(String source) {
        this.source = source;
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
    
    // Utility methods
    
    /**
     * Format amount ke format currency Indonesia
     * @return String format Rupiah
     */
    public String getFormattedAmount() {
        if (amount == null) return "Rp 0";
        return String.format("Rp %,.2f", amount);
    }
    
    /**
     * Validasi data receipt
     * @return true jika valid, false jika tidak
     */
    public boolean isValid() {
        return categoryId != null && !categoryId.trim().isEmpty()
                && description != null && !description.trim().isEmpty()
                && amount != null && amount > 0
                && receiptDate != null
                && source != null && !source.trim().isEmpty();
    }
    
}
