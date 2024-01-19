package com.example.qlnn1;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ControllAdd implements Initializable{



    @FXML
    private Button Home;
    @FXML
    private Button DashBoard;
    @FXML
    private Button AddCustomer;
    @FXML
    private Button RemoveCustomer;
    @FXML
    private Button EditCustomer;


    @FXML
    private TextField Name;
    @FXML
    private TextField Phone;
    @FXML
    private TextField RoomNumber;
    @FXML
    private TextField RoomType;
    @FXML
    private TextField Bill;
    @FXML
    private TextField Date;
    private Connection connection;
    @FXML
    private Button AddButton;
    @FXML
    private Button exit;


public ControllAdd(){

}

    public void initialize(URL url, ResourceBundle resourceBundle) {
        connection = LUULY1.getConnection();

    }
    public void cancelExitOnAction(ActionEvent e) {
        Stage stage = (Stage) exit.getScene().getWindow();
        stage.close();
    }
    public void conectAdd(ActionEvent e) throws IOException {
        Stage stage = (Stage) AddCustomer.getScene().getWindow();
        FXMLLoader fxmlLoader  = new FXMLLoader(HelloApplication.class.getResource("Add.fxml"));
        Scene Scene = new Scene(fxmlLoader.load(), 1268, 802);
        stage.setScene(Scene);
        stage.show();
    }
    public void conectDashBoard(ActionEvent e) throws IOException {
        Stage stage = (Stage)this.DashBoard.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("DashBoard.fxml"));
        Scene Scene = new Scene((Parent)fxmlLoader.load(), 1268.0, 802.0);
        stage.setScene(Scene);
        stage.show();
    }
    public void conectRemove(ActionEvent e) throws IOException {
        Stage stage = (Stage)this.RemoveCustomer.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Remove.fxml"));
        Scene Scene = new Scene((Parent)fxmlLoader.load(), 1268.0, 802.0);
        stage.setScene(Scene);
        stage.show();
    }
    public void conectEditButtonOnAction(ActionEvent e) throws IOException {
        Stage stage = (Stage) this.EditCustomer.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Edit.fxml"));
        Scene Scene = new Scene((Parent) fxmlLoader.load(), 1268.0, 802.0);
        stage.setScene(Scene);
        stage.show();
    }

    public void conectHomeButtonOnAction(ActionEvent e) throws IOException {
        Stage stage = (Stage)this.Home.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene Scene = new Scene((Parent)fxmlLoader.load(), 1268.0, 802.0);
        stage.setScene(Scene);
        stage.show();
    }
    @FXML
    public void handleAddButtonAction(ActionEvent event) {
        // Get data from text fields
        String name = Name.getText();
        String phone = Phone.getText();
        String roomNumber = RoomNumber.getText();
        String roomType = RoomType.getText();
        String bill = Bill.getText();
        String date = Date.getText();

        // Insert data into the database
        insertData(name, phone, roomNumber, roomType, bill, date);

        // You can optionally navigate to another view after adding the data
        // For example, go back to the Dashboard
        try {
            conectDashBoard(event);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void insertData(String name, String phone, String roomNumber, String roomType, String bill, String date) {
        try {
            String query = "INSERT INTO IDCustomers (Name, Phone, [Room Number], [Room Type], Bill, Date) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, phone);
                preparedStatement.setString(3, roomNumber);
                preparedStatement.setString(4, roomType);
                preparedStatement.setString(5, bill);
                preparedStatement.setString(6, date);

                preparedStatement.executeUpdate();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }


}

