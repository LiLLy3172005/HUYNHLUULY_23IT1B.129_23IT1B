package com.example.qlnn1;

import java.sql.Date;
import java.time.LocalDate;

public class Employee {
    private String ID_Employee;
    private String Name_Employee;
    private String Duty_Employee;
    private LocalDate BirthDay_Employee; // Change type to LocalDate
    private String Email_Employee;

    public Employee(String ID_Employee, String Name_Employee, String Duty_Employee, LocalDate BirthDay_Employee, String Email_Employee) { // Change parameter type to LocalDate
        this.ID_Employee = ID_Employee;
        this.Name_Employee = Name_Employee;
        this.Duty_Employee = Duty_Employee;
        this.BirthDay_Employee = BirthDay_Employee;
        this.Email_Employee = Email_Employee;
    }

    // Getters and setters
    public String getID_Employee() {
        return ID_Employee;
    }

    public void setID_Employee(String ID_Employee) {
        this.ID_Employee = ID_Employee;
    }

    public String getName_Employee() {
        return Name_Employee;
    }

    public void setName_Employee(String Name_Employee) {
        this.Name_Employee = Name_Employee;
    }

    public String getDuty_Employee() {
        return Duty_Employee;
    }

    public void setDuty_Employee(String Duty_Employee) {
        this.Duty_Employee = Duty_Employee;
    }

    public LocalDate getBirthDay_Employee() {
        return BirthDay_Employee;
    }

    public void setBirthDay_Employee(LocalDate BirthDay_Employee) {
        this.BirthDay_Employee = BirthDay_Employee;
    }

    public String getEmail_Employee() {
        return Email_Employee;
    }

    public void setEmail_Employee(String Email_Employee) {
        this.Email_Employee = Email_Employee;
    }
}
