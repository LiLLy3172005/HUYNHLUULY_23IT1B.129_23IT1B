package com.example.qlnn1;

import java.sql.Date;

public class Customer {
    private String ID_Customer;
    private String Name_Customer;
    private String CCCD_Customer;
    private String Phone_Customer;
    private String Email_Customer;
    private String Address_Customer;

    public Customer(String ID_Customer, String Name_Customer, String CCCD_Customer, String Phone_Customer, String Email_Customer, String Address_Customer) {
        this.ID_Customer = ID_Customer;
        this.Name_Customer = Name_Customer;
        this.CCCD_Customer = CCCD_Customer;
        this.Phone_Customer = Phone_Customer;
        this.Email_Customer = Email_Customer;
        this.Address_Customer = Address_Customer;
    }

    // Getters and setters
    public String getID_Customer() {
        return ID_Customer;
    }

    public void setID_Customer(String ID_Customer) {
        this.ID_Customer = ID_Customer;
    }

    public String getName_Customer() {
        return Name_Customer;
    }

    public void setName_Customer(String Name_Customer) {
        this.Name_Customer = Name_Customer;
    }

    public String getCCCD_Customer() {
        return CCCD_Customer;
    }

    public void setCCCD_Customer(String CCCD_Customer) {
        this.CCCD_Customer = CCCD_Customer;
    }

    public String getPhone_Customer() {
        return Phone_Customer;
    }

    public void setPhone_Customer(String Phone_Customer) {
        this.Phone_Customer = Phone_Customer;
    }

    public String getEmail_Customer() {
        return Email_Customer;
    }

    public void setEmail_Customer(String Email_Customer) {
        this.Email_Customer = Email_Customer;
    }

    public String getAddress_Customer() {
        return Address_Customer;
    }

    public void setAddress_Customer(String Address_Customer) {
        this.Address_Customer = Address_Customer;
    }
}
