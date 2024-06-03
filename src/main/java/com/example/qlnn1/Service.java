package com.example.qlnn1;

public class Service {
    private String ID_Service;
    private String Name_Service;
    private int Price_Service;

    public Service(String ID_Service, String Name_Service, int Price_Service) {
        this.ID_Service = ID_Service;
        this.Name_Service = Name_Service;
        this.Price_Service = Price_Service;
    }

    // Getters and setters
    public String getID_Service() {
        return ID_Service;
    }

    public void setID_Service(String ID_Service) {
        this.ID_Service = ID_Service;
    }

    public String getName_Service() {
        return Name_Service;
    }

    public void setName_Service(String Name_Service) {
        this.Name_Service = Name_Service;
    }

    public int getPrice_Service() {
        return Price_Service;
    }

    public void setPrice_Service(int Price_Service) {
        this.Price_Service = Price_Service;
    }
}
