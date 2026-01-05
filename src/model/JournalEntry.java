/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 * Model class untuk representasi tabel journal_entries
 * 
 * @author Leonovo
 * @version 1.0
 */
public class JournalEntry {
    
    // Field dari database
    private String entryId;
    private String entryNumber;
    private String transactionType; // invoice, expense, receipt, adjustment, payment, payroll
    private String transactionId;
    private String categoryId;
    private String entryDate;
    private String description;
    private String debitAccountId;
    private String creditAccountId;
    private Double amount;
    private String referenceNumber;
    private String createdBy;
    private String createdAt;
    private String updatedAt;
    
    // Field tambahan untuk UI (dari JOIN)
    private String debitAccountName;
    private String creditAccountName;
    private String categoryName;
    private String createdByName;
    
    // Transaction type constants
    public static final String TYPE_INVOICE = "invoice";
    public static final String TYPE_EXPENSE = "expense";
    public static final String TYPE_RECEIPT = "receipt";
    public static final String TYPE_ADJUSTMENT = "adjustment";
    public static final String TYPE_PAYMENT = "payment";
    public static final String TYPE_PAYROLL = "payroll";
    
    // Getter dan Setter methods
    
    public String getEntryId() {
        return entryId;
    }
    
    public void setEntryId(String entryId) {
        this.entryId = entryId;
    }
    
    public String getEntryNumber() {
        return entryNumber;
    }
    
    public void setEntryNumber(String entryNumber) {
        this.entryNumber = entryNumber;
    }
    
    public String getTransactionType() {
        return transactionType;
    }
    
    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }
    
    public String getTransactionId() {
        return transactionId;
    }
    
    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }
    
    public String getCategoryId() {
        return categoryId;
    }
    
    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }
    
    public String getEntryDate() {
        return entryDate;
    }
    
    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getDebitAccountId() {
        return debitAccountId;
    }
    
    public void setDebitAccountId(String debitAccountId) {
        this.debitAccountId = debitAccountId;
    }
    
    public String getCreditAccountId() {
        return creditAccountId;
    }
    
    public void setCreditAccountId(String creditAccountId) {
        this.creditAccountId = creditAccountId;
    }
    
    public Double getAmount() {
        return amount;
    }
    
    public void setAmount(Double amount) {
        this.amount = amount;
    }
    
    public String getReferenceNumber() {
        return referenceNumber;
    }
    
    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
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
    
    public String getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    // UI field getters/setters
    
    public String getDebitAccountName() {
        return debitAccountName;
    }
    
    public void setDebitAccountName(String debitAccountName) {
        this.debitAccountName = debitAccountName;
    }
    
    public String getCreditAccountName() {
        return creditAccountName;
    }
    
    public void setCreditAccountName(String creditAccountName) {
        this.creditAccountName = creditAccountName;
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
     * Validasi data journal entry
     * @return true jika valid, false jika tidak
     */
    public boolean isValid() {
        return transactionType != null && !transactionType.trim().isEmpty()
                && entryDate != null && !entryDate.trim().isEmpty()
                && debitAccountId != null && !debitAccountId.trim().isEmpty()
                && creditAccountId != null && !creditAccountId.trim().isEmpty()
                && amount != null && amount > 0;
    }
    
    /**
     * Get display text for transaction type
     * @return String type label in Indonesian
     */
    public String getTransactionTypeLabel() {
        if (transactionType == null) return "-";
        switch (transactionType) {
            case TYPE_INVOICE: return "Faktur";
            case TYPE_EXPENSE: return "Pengeluaran";
            case TYPE_RECEIPT: return "Penerimaan";
            case TYPE_ADJUSTMENT: return "Penyesuaian";
            case TYPE_PAYMENT: return "Pembayaran";
            case TYPE_PAYROLL: return "Penggajian";
            default: return transactionType;
        }
    }
}
