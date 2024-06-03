package com.example.qlnn1;

import java.util.Date;

public class Check_in {
    private String ID_Customer;
    private Date Check_in_Date;
    private Date Check_out_Date;
    private String Name_Customer;
    private String CCCD_Customer;
    private String Phone_Customer;
    private String Email_Customer;
    private String Address_Customer;
    private String Room_ID;
    private String ID_Services;
    private String ID_Employee;

    public Check_in(String ID_Customer, Date Check_in_Date, Date Check_out_Date, String Name_Customer, String CCCD_Customer, String Phone_Customer, String Email_Customer, String Address_Customer, String Room_ID, String ID_Services, String ID_Employee) {
        this.ID_Customer = ID_Customer;
        this.Check_in_Date = Check_in_Date;
        this.Check_out_Date = Check_out_Date;
        this.Name_Customer = Name_Customer;
        this.CCCD_Customer = CCCD_Customer;
        this.Phone_Customer = Phone_Customer;
        this.Email_Customer = Email_Customer;
        this.Address_Customer = Address_Customer;
        this.Room_ID = Room_ID;
        this.ID_Services = ID_Services;
        this.ID_Employee = ID_Employee;
    }

    // Getters and setters
    public String getID_Customer() {
        return ID_Customer;
    }

    public void setID_Customer(String ID_Customer) {
        this.ID_Customer = ID_Customer;
    }

    public Date getCheck_in_Date() {
        return Check_in_Date;
    }

    public void setCheck_in_Date(Date Check_in_Date) {
        this.Check_in_Date = Check_in_Date;
    }

    public Date getCheck_out_Date() {
        return Check_out_Date;
    }

    public void setCheck_out_Date(Date Check_out_Date) {
        this.Check_out_Date = Check_out_Date;
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

    public String getRoom_ID() {
        return Room_ID;
    }

    public void setRoom_ID(String Room_ID) {
        this.Room_ID = Room_ID;
    }

    public String getID_Services() {
        return ID_Services;
    }

    public void setID_Services(String ID_Services) {
        this.ID_Services = ID_Services;
    }

    public String getID_Employee() {
        return ID_Employee;
    }

    public void setID_Employee(String ID_Employee) {
        this.ID_Employee = ID_Employee;
    }
}
