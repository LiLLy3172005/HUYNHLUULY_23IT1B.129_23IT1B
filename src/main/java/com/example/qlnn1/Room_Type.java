package com.example.qlnn1;

public class Room_Type {
    private String ID_RoomType;
    private String Room_ID;
    private String Name_RoomType;
    private int Price_RoomType;
    private int NumberofPeople_RoomType;

    public Room_Type(String ID_RoomType, String Room_ID, String Name_RoomType, int Price_RoomType, int NumberofPeople_RoomType) {
        this.ID_RoomType = ID_RoomType;
        this.Room_ID = Room_ID;
        this.Name_RoomType = Name_RoomType;
        this.Price_RoomType = Price_RoomType;
        this.NumberofPeople_RoomType = NumberofPeople_RoomType;
    }

    // Getters and setters
    public String getID_RoomType() {
        return ID_RoomType;
    }

    public void setID_RoomType(String ID_RoomType) {
        this.ID_RoomType = ID_RoomType;
    }

    public String getRoom_ID() {
        return Room_ID;
    }

    public void setRoom_ID(String Room_ID) {
        this.Room_ID = Room_ID;
    }

    public String getName_RoomType() {
        return Name_RoomType;
    }

    public void setName_RoomType(String Name_RoomType) {
        this.Name_RoomType = Name_RoomType;
    }

    public int getPrice_RoomType() {
        return Price_RoomType;
    }

    public void setPrice_RoomType(int Price_RoomType) {
        this.Price_RoomType = Price_RoomType;
    }

    public int getNumberofPeople_RoomType() {
        return NumberofPeople_RoomType;
    }

    public void setNumberofPeople_RoomType(int NumberofPeople_RoomType) {
        this.NumberofPeople_RoomType = NumberofPeople_RoomType;
    }
}
