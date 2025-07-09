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

    private String clientId;
    private String name;
    private String contactPerson;
    private String phone;
    private String email;
    private String paymentTerms;
    private Double creditLimit;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String ClientId) {
        this.clientId = ClientId;
    }

    public String getName() {
        return name;
    }

    public void setName(String Name) {
        this.name = Name;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String Contact_Person) {
        this.contactPerson = Contact_Person;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String Phone) {
        this.phone = Phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String Email) {
        this.email = Email;
    }

    public String getPaymentTerms() {
        return paymentTerms;
    }

    public void setPaymentTerms(String Payment_Terms) {
        this.paymentTerms = Payment_Terms;
    }

    public Double getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(Double Credit_Limit) {
        this.creditLimit = Credit_Limit;
    }
}
