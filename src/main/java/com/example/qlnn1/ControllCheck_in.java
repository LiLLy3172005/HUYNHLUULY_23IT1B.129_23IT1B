package com.example.qlnn1;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

public class ControllCheck_in implements Initializable{
    @FXML
    private Button DashBoard;
    @FXML
    private Button CheckIn;
    @FXML
    private Button CheckOut;
    @FXML
    private Button Service;
    @FXML
    private Button Bill;

    @FXML
    private ChoiceBox<String> roomTypeChoiceBox;
    @FXML
    private ChoiceBox<String> roomNumberChoiceBox;

    @FXML
    private DatePicker Checkin;

    @FXML
    private DatePicker Checkout;




    @FXML
    private TextField Room_ID;
    @FXML
    private TextField ID_Employee;
    @FXML
    private TextField ID_Service;
    @FXML
    private TextField ID_Customer;

    @FXML
    private TextField Name_Customer;

    @FXML
    private TextField CCCD_Customer;
    @FXML
    private TextField Email_Customer;
    @FXML
    private TextField Address_Customer;
    @FXML
    private TextField Phone_Customer;
    private Connection connection;
    @FXML
    private Button OK;
    @FXML
    private Button Table_View;
    @FXML
    private Button exit;
    @FXML
    private ScrollBar scrollBar;


    public ControllCheck_in(){

    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        connection = LUULY1.getConnection();
        roomTypeChoiceBox.getItems().addAll("Standard", "Deluxe", "Suite");
        roomNumberChoiceBox.getItems().addAll("101", "102", "103", "104", "105", "201", "202", "203", "204", "205", "301", "302", "303", "304", "305");


    }
    public void cancelExitOnAction(ActionEvent e) {
        Stage stage = (Stage) exit.getScene().getWindow();
        stage.close();
    }
    public void conectCheckIn(ActionEvent e) throws IOException {
        Stage stage = (Stage) CheckIn.getScene().getWindow();
        FXMLLoader fxmlLoader  = new FXMLLoader(HelloApplication.class.getResource("Add.fxml"));
        Scene Scene = new Scene(fxmlLoader.load(), 1530, 802);
        stage.setScene(Scene);
        stage.show();
    }
    public void conectDashBoard(ActionEvent e) throws IOException {
        Stage stage = (Stage)this.DashBoard.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("DashBoard.fxml"));
        Scene Scene = new Scene((Parent)fxmlLoader.load(), 1530.0, 802.0);
        stage.setScene(Scene);
        stage.show();
    }
    public void conectCheckOut(ActionEvent e) throws IOException {
        Stage stage = (Stage)this.CheckOut.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Remove.fxml"));
        Scene Scene = new Scene((Parent)fxmlLoader.load(), 1530.0, 802.0);
        stage.setScene(Scene);
        stage.show();
    }
    public void conectServiceButtonOnAction(ActionEvent e) throws IOException {
        Stage stage = (Stage) this.Service.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Edit.fxml"));
        Scene Scene = new Scene((Parent) fxmlLoader.load(), 1530.0, 802.0);
        stage.setScene(Scene);
        stage.show();
    }

    public void conectBillButtonOnAction(ActionEvent e) throws IOException {
        Stage stage = (Stage)this.Bill.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene Scene = new Scene((Parent)fxmlLoader.load(), 1530.0, 802.0);
        stage.setScene(Scene);
        stage.show();
    }

    public void conectTableView(ActionEvent e) throws IOException {
        Stage stage = (Stage) Table_View.getScene().getWindow();
        FXMLLoader fxmlLoader  = new FXMLLoader(HelloApplication.class.getResource("TableChekin.fxml"));
        Scene Scene = new Scene(fxmlLoader.load(), 1530, 802);
        stage.setScene(Scene);
        stage.show();
    }
    @FXML
    public void handleOKButtonAction(ActionEvent event) {
        // Get data from text fields
        String roomNumber = roomNumberChoiceBox.getValue(); // Lấy giá trị đã chọn từ ChoiceBox
        String roomType = roomTypeChoiceBox.getValue(); // Lấy giá trị đã chọn từ ChoiceBox
        LocalDate checkInDate = Checkin.getValue(); // Lấy ngày check-in từ DatePicker
        LocalDate checkOutDate = Checkout.getValue(); // Lấy ngày check-out từ DatePicker
        String roomId = Room_ID.getText();
        String idEmployee = ID_Employee.getText();
        String idService = ID_Service.getText();
        String idCustomer = ID_Customer.getText();
        String nameCustomer = Name_Customer.getText();
        String cccdCustomer = CCCD_Customer.getText();
        String emailCustomer = Email_Customer.getText();
        String addressCustomer = Address_Customer.getText();
        String phoneCustomer = Phone_Customer.getText();
        String status = "Paid";
        int totalday = 4;
        int todatprice = 500;

        // Sinh số ngẫu nhiên có 3 chữ số
        Random random = new Random();
        int randomNumber = random.nextInt(900) + 100;
        String idbill = "B" + randomNumber;

        // Insert data into the database
        insertCustomerData(idCustomer, nameCustomer, cccdCustomer, phoneCustomer, emailCustomer, addressCustomer);
        insertCheckInData(idCustomer, checkInDate, checkOutDate, nameCustomer, cccdCustomer, phoneCustomer, emailCustomer, addressCustomer, roomId, idService, idEmployee);
        insertCheckOutData(idCustomer, checkInDate, checkOutDate, nameCustomer, roomId, roomNumber, status ,totalday,todatprice);
        insertBillData(idCustomer,checkInDate,checkOutDate,roomId,idService,idbill,todatprice,totalday,status);
        // You can optionally navigate to another view after adding the data
        // For example, go back to the Dashboard
        try {
            conectDashBoard(event);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void insertCustomerData(String idCustomer, String nameCustomer, String cccdCustomer, String phoneCustomer, String emailCustomer, String addressCustomer) {
        try {
            String query = "INSERT INTO Customer (ID_Customer, Name_Customer, CCCD_Customer, Phone_Customer, Email_Customer, Address_Customer) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, idCustomer);
                preparedStatement.setString(2, nameCustomer);
                preparedStatement.setString(3, cccdCustomer);
                preparedStatement.setString(4, phoneCustomer);
                preparedStatement.setString(5, emailCustomer);
                preparedStatement.setString(6, addressCustomer);

                preparedStatement.executeUpdate();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void insertCheckInData(String idCustomer, LocalDate checkInDate, LocalDate checkOutDate, String nameCustomer, String cccdCustomer, String phoneCustomer, String emailCustomer, String addressCustomer, String roomID, String idService, String idEmployee) {
        try {
            String query = "INSERT INTO Check_in (ID_Customer, Check_in_Date, Check_out_Date, Name_Customer, CCCD_Customer, Phone_Customer, Email_Customer, Address_Customer, Room_ID, ID_Services, ID_Employee) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, idCustomer);
                preparedStatement.setDate(2, Date.valueOf(checkInDate));
                preparedStatement.setDate(3, Date.valueOf(checkOutDate));
                preparedStatement.setString(4, nameCustomer);
                preparedStatement.setString(5, cccdCustomer);
                preparedStatement.setString(6, phoneCustomer);
                preparedStatement.setString(7, emailCustomer);
                preparedStatement.setString(8, addressCustomer);
                preparedStatement.setString(9, roomID);
                preparedStatement.setString(10, idService);
                preparedStatement.setString(11, idEmployee);

                preparedStatement.executeUpdate();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    private void insertCheckOutData(String idCustomer, LocalDate checkInDate, LocalDate checkOutDate, String nameCustomer,  String roomID, String roomNumber, String status , int totalday, int totalprice) {
        try {
            String query = "INSERT INTO Check_out (ID_Customer, Check_in_Date, Check_out_Date, Name_Customer,  Room_ID, Room_Number, Status, Totals_Day, Totals_Price) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, idCustomer);
                preparedStatement.setDate(2, Date.valueOf(checkInDate));
                preparedStatement.setDate(3, Date.valueOf(checkOutDate));
                preparedStatement.setString(4, nameCustomer);
                preparedStatement.setString(5, roomID);
                preparedStatement.setString(6, roomNumber);
                preparedStatement.setString(7, status);
                preparedStatement.setInt(8, totalday);
                preparedStatement.setInt(9, totalprice);

                preparedStatement.executeUpdate();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    private void insertBillData(String idCustomer, LocalDate checkInDate, LocalDate checkOutDate, String roomID, String idService, String idbill, int totalprice, int totalday, String status) {
        try {
            String query = "INSERT INTO Bill (ID_Bill, ID_Customer, ID_Service, Room_ID, Day_Bill, Check_in_Date, Check_out_Date, Totals_Day, Totals_Price, Status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, idbill);
                preparedStatement.setString(2, idCustomer);
                preparedStatement.setString(3, idService);
                preparedStatement.setString(4, roomID);
                preparedStatement.setDate(5, Date.valueOf(checkOutDate));
                preparedStatement.setDate(6, Date.valueOf(checkInDate));
                preparedStatement.setDate(7, Date.valueOf(checkOutDate));
                preparedStatement.setInt(8, totalday);
                preparedStatement.setInt(9, totalprice);
                preparedStatement.setString(10, status);

                preparedStatement.executeUpdate();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }



}