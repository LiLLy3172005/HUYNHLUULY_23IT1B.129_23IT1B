package com.example.qlnn1;
import java.io.IOException;
import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.RowIdLifetime;
import java.util.ResourceBundle;

public class ControllDasboard implements Initializable{
    @FXML
    private Button DashBoard;
    @FXML
    private Button AddCustomer;
    @FXML
    private Button RemoveCustomer;
    @FXML
    private Button EditCustomer;
@FXML
private Button Home;
    @FXML
    private Label totalroom;
    @FXML
    private Label totalearning;
    @FXML
    private Label bookedroom;
    @FXML
    private Label availableroom;
    @FXML
    private Label pendingpayment;
    private Connection connection;
    @FXML
    private Button exit;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        connection = LUULY1.getConnection();
        updateLabels();

    }
    public void conectEditButtonOnAction(ActionEvent e) throws IOException {
        Stage stage = (Stage) this.EditCustomer.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Edit.fxml"));
        Scene Scene = new Scene((Parent) fxmlLoader.load(), 1268.0, 802.0);
        stage.setScene(Scene);
        stage.show();
    }
    public void cancelExitOnAction(ActionEvent e) {
        Stage stage = (Stage) exit.getScene().getWindow();
        stage.close();
    }
    public void conectRemove(ActionEvent e) throws IOException {
        Stage stage = (Stage)this.RemoveCustomer.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Remove.fxml"));
        Scene Scene = new Scene((Parent)fxmlLoader.load(), 1268.0, 802.0);
        stage.setScene(Scene);
        stage.show();
    }
    public void conectAdd(ActionEvent e) throws IOException {
        Stage stage = (Stage) AddCustomer.getScene().getWindow();
        FXMLLoader fxmlLoader  = new FXMLLoader(HelloApplication.class.getResource("Add.fxml"));
        Scene Scene = new Scene(fxmlLoader.load(), 1268, 802);
        stage.setScene(Scene);
        stage.show();
    }


    public void conectDashBoard(ActionEvent e) throws IOException {
        Stage stage = (Stage) DashBoard.getScene().getWindow();
        FXMLLoader fxmlLoader  = new FXMLLoader(HelloApplication.class.getResource("DashBoard.fxml"));
        Scene Scene = new Scene(fxmlLoader.load(), 1268, 802);
        stage.setScene(Scene);
        stage.show();
    }
    public void conectHomeButtonOnAction(ActionEvent e) throws IOException {
        Stage stage = (Stage) Home.getScene().getWindow();
        FXMLLoader fxmlLoader  = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));

        Scene Scene = new Scene(fxmlLoader.load(), 1268, 802);
        stage.setScene(Scene);
        stage.show();


    }

    private void updateLabels() {
        int pendingPayment = calculatePendingPayment();
        int sumOfNumberOfRooms = calculateSumOfNumberOfRooms();

        pendingpayment.setText(String.valueOf(pendingPayment));
        bookedroom.setText(String.valueOf(sumOfNumberOfRooms));
        availableroom.setText(String.valueOf(100 - sumOfNumberOfRooms));
        totalroom.setText("100");
        totalearning.setText(String.valueOf((int) (0.9 * pendingPayment)));
    }

    private int calculatePendingPayment() {
        int totalBill = 0;
        try {
            String query = "SELECT SUM(Bill) AS TotalBill FROM IDCustomers";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query);
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                if (resultSet.next()) {
                    totalBill = resultSet.getInt("TotalBill");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totalBill;
    }

    private int calculateSumOfNumberOfRooms() {
        int sumOfNumberOfRooms = 0;
        try {
            String query = "SELECT SUM(NumberOfRooms) AS SumOfNumberOfRooms FROM (SELECT [Room Number], COUNT(*) AS NumberOfRooms FROM IDCustomers GROUP BY [Room Number]) AS RoomCounts";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query);
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                if (resultSet.next()) {
                    sumOfNumberOfRooms = resultSet.getInt("SumOfNumberOfRooms");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sumOfNumberOfRooms;
    }

}


