package com.example.qlnn1;

public class Room {
    private String Room_ID;
    private int Room_Number;
    private String Room_Condition;
    private String Room_Type;
    private String ID_RoomType;
    private String ID_Device;

    public Room(String Room_ID, int Room_Number, String Room_Condition, String Room_Type, String ID_RoomType, String ID_Device) {
        this.Room_ID = Room_ID;
        this.Room_Number = Room_Number;
        this.Room_Condition = Room_Condition;
        this.Room_Type = Room_Type;
        this.ID_RoomType = ID_RoomType;
        this.ID_Device = ID_Device;
    }

    // Getters and setters
    public String getRoom_ID() {
        return Room_ID;
    }

    public void setRoom_ID(String Room_ID) {
        this.Room_ID = Room_ID;
    }

    public int getRoom_Number() {
        return Room_Number;
    }

    public void setRoom_Number(int Room_Number) {
        this.Room_Number = Room_Number;
    }

    public String getRoom_Condition() {
        return Room_Condition;
    }

    public void setRoom_Condition(String Room_Condition) {
        this.Room_Condition = Room_Condition;
    }

    public String getRoom_Type() {
        return Room_Type;
    }

    public void setRoom_Type(String Room_Type) {
        this.Room_Type = Room_Type;
    }

    public String getID_RoomType() {
        return ID_RoomType;
    }

    public void setID_RoomType(String ID_RoomType) {
        this.ID_RoomType = ID_RoomType;
    }

    public String getID_Device() {
        return ID_Device;
    }

    public void setID_Device(String ID_Device) {
        this.ID_Device = ID_Device;
    }
}
