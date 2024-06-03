package com.example.qlnn1;

public class Device {
    private String ID_Device;
    private String Name_Device;
    private int Price_Device;

    public Device(String ID_Device, String Name_Device, int Price_Device) {
        this.ID_Device = ID_Device;
        this.Name_Device = Name_Device;
        this.Price_Device = Price_Device;
    }

    // Getters and setters
    public String getID_Device() {
        return ID_Device;
    }

    public void setID_Device(String ID_Device) {
        this.ID_Device = ID_Device;
    }

    public String getName_Device() {
        return Name_Device;
    }

    public void setName_Device(String Name_Device) {
        this.Name_Device = Name_Device;
    }

    public int getPrice_Device() {
        return Price_Device;
    }

    public void setPrice_Device(int Price_Device) {
        this.Price_Device = Price_Device;
    }
}
