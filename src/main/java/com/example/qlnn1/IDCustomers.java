package com.example.qlnn1;

public class IDCustomers {
    private String name;
    private String phone;
    private String roomNumber;
    private String roomType;
    private String bill;
    private String date;

    public IDCustomers(String name, String phone, String roomNumber, String roomType, String bill, String date) {
        this.name = name;
        this.phone = phone;
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.bill = bill;
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public String getBill() {
        return bill;
    }

    public void setBill(String bill) {
        this.bill = bill;
    }
}
