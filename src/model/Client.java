/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author KHAIRUNNISA
 */
public class Client {

    public static String getClient_Id() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    private String ClientId;
    private String Name;
    private String Contact_Person;
    private String Phone;
    private String Email;
    private String Payment_Terms;
    private String Credit_Limit;

    public String getClientId() {
        return ClientId;
    }

    public void setClientId(String ClientId) {
        this.ClientId = ClientId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getContact_Person() {
        return Contact_Person;
    }

    public void setContact_Person(String Contact_Person) {
        this.Contact_Person = Contact_Person;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String Phone) {
        this.Phone = Phone;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getPayment_Terms() {
        return Payment_Terms;
    }

    public void setPayment_Terms(String Payment_Terms) {
        this.Payment_Terms = Payment_Terms;
    }

    public String getCredit_Limit() {
        return Credit_Limit;
    }

    public void setCredit_Limit(String Credit_Limit) {
        this.Credit_Limit = Credit_Limit;
    }
}
