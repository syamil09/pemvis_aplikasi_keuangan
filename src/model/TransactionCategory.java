/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Leonovo
 */
public class TransactionCategory {
    private String categoryId;
    private String nama;
    private String debitAccountId;
    private String debitAccountName;
    private String creditAccountId;
    private String creditAccountName;
    private String description;

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


    public String getCreditAccountId() {
        return creditAccountId;
    }

    public void setCreditAccountId(String creditAccountId) {
        this.creditAccountId = creditAccountId;
    }
    
    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getDebitAccountId() {
        return debitAccountId;
    }

    public void setDebitAccountId(String debitAccountId) {
        this.debitAccountId = debitAccountId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

  
}
