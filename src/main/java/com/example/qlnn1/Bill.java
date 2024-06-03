package com.example.qlnn1;

import java.io.File;

import java.io.IOException;
import java.util.Date;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;





import java.awt.Desktop;





public class Bill {
    private String ID_Bill;
    private String ID_Customer;
    private String ID_Service;
    private String Room_ID;

    private Date Check_in_Date;
    private Date Check_out_Date;

    private int Total;


    public Bill(String ID_Bill, String ID_Customer, String ID_Service, String room_ID, Date check_in_Date, Date check_out_Date, int total) {
        this.ID_Bill = ID_Bill;
        this.ID_Customer = ID_Customer;
        this.ID_Service = ID_Service;
        Room_ID = room_ID;

        Check_in_Date = check_in_Date;
        Check_out_Date = check_out_Date;
        Total = total;
    }

    public String getID_Bill() {
        return ID_Bill;
    }

    public void setID_Bill(String ID_Bill) {
        this.ID_Bill = ID_Bill;
    }

    public String getID_Customer() {
        return ID_Customer;
    }

    public void setID_Customer(String ID_Customer) {
        this.ID_Customer = ID_Customer;
    }

    public String getID_Service() {
        return ID_Service;
    }

    public void setID_Service(String ID_Service) {
        this.ID_Service = ID_Service;
    }

    public String getRoom_ID() {
        return Room_ID;
    }

    public void setRoom_ID(String room_ID) {
        Room_ID = room_ID;
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

    public Integer getTotal() {
        return Total;
    }

    public void setTotal(Integer total) {
        Total = total;
    }



    public void exportToPDF() {
        String dest = "bill_" + ID_Bill + ".pdf";
        try {
            PdfWriter writer = new PdfWriter(dest);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);
            document.add(new Paragraph("BILL HOIANNA"));
            document.add(new Paragraph("ID Bill: " + ID_Bill));
            document.add(new Paragraph("ID Customer: " + ID_Customer));
            document.add(new Paragraph("Room ID: " + Room_ID));
            document.add(new Paragraph("ID Services: " + ID_Service));
            document.add(new Paragraph("Check-in Date: " + Check_in_Date));
            document.add(new Paragraph("Checkout Date: " + Check_out_Date));
            document.add(new Paragraph("Total: " + Total));

            document.close();

            openPDF(dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openPDF(String filePath) {
        try {
            File file = new File(filePath);
            if (file.exists()) {
                Desktop.getDesktop().open(file);
            } else {
                System.out.println("File doesn't exist");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

