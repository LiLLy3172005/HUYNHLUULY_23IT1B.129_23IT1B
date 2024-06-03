package com.example.qlnn1;

import java.util.Date;

public class Check_out {
    private String ID_Customer;
    private String Room_ID;
    private String Name_Customer;
    private Date Check_in_Date;
    private Date Check_out_Date;
    private int Room_Number;
    private int Totals_day;
    private int Totals_price;
    private String Status;

    public Check_out(String ID_Customer, String room_ID, String name_Customer, Date check_in_Date, Date check_out_Date, int room_Number, String status) {
        this.ID_Customer = ID_Customer;
        Room_ID = room_ID;
        Name_Customer = name_Customer;
        Check_in_Date = check_in_Date;
        Check_out_Date = check_out_Date;
        Room_Number = room_Number;
        Status = status;
    }

    public String getID_Customer() {
        return ID_Customer;
    }

    public void setID_Customer(String ID_Customer) {
        this.ID_Customer = ID_Customer;
    }

    public String getRoom_ID() {
        return Room_ID;
    }

    public void setRoom_ID(String room_ID) {
        Room_ID = room_ID;
    }

    public String getName_Customer() {
        return Name_Customer;
    }

    public void setName_Customer(String name_Customer) {
        Name_Customer = name_Customer;
    }

    public Date getCheck_in_Date() {
        return Check_in_Date;
    }

    public void setCheck_in_Date(Date check_in_Date) {
        Check_in_Date = check_in_Date;
    }

    public Date getCheck_out_Date() {
        return Check_out_Date;
    }

    public void setCheck_out_Date(Date check_out_Date) {
        Check_out_Date = check_out_Date;
    }

    public int getRoom_Number() {
        return Room_Number;
    }

    public void setRoom_Number(int room_Number) {
        Room_Number = room_Number;
    }

    public int getTotals_day() {
        return Totals_day;
    }

    public void setTotals_day(int totals_day) {
        Totals_day = totals_day;
    }

    public int getTotals_price() {
        return Totals_price;
    }

    public void setTotals_price(int totals_price) {
        Totals_price = totals_price;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
