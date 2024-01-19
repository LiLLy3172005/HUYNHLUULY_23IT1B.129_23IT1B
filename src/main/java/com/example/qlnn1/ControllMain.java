package com.example.qlnn1;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.Button;

import java.sql.*;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.Scene;
import java.sql.Connection;


public class ControllMain {

    @FXML
    private TableView<IDCustomers> Id;
    @FXML
    private TableColumn<IDCustomers, String> Name;
    @FXML
    private TableColumn<IDCustomers, String> Phone;
    @FXML
    private TableColumn<IDCustomers, String> RoomNumber;
    @FXML
    private TableColumn<IDCustomers, String> RoomType;
    @FXML
    private TableColumn<IDCustomers, String> Bill;
    @FXML
    private TableColumn<IDCustomers, String>  Date;

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

    private Connection connection;
    @FXML
    private Button exit;
    @FXML
    private TextField Find;

@FXML
private Button Search;

    public void initialize() {
        connection = LUULY1.getConnection();
        Name.setCellValueFactory(new PropertyValueFactory<>("Name"));
        Phone.setCellValueFactory(new PropertyValueFactory<>("Phone"));
        RoomNumber.setCellValueFactory(new PropertyValueFactory<>("RoomNumber"));
        RoomType.setCellValueFactory(new PropertyValueFactory<>("RoomType"));
        Bill.setCellValueFactory(new PropertyValueFactory<>("Bill"));
        Date.setCellValueFactory(new  PropertyValueFactory<>("Date"));

        loadDataFromDatabase();
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

    public void conectDashBoard(ActionEvent e) throws IOException {
        Stage stage = (Stage) DashBoard.getScene().getWindow();
        FXMLLoader fxmlLoader  = new FXMLLoader(HelloApplication.class.getResource("DashBoard.fxml"));
        Scene Scene = new Scene(fxmlLoader.load(), 1268, 802);
        stage.setScene(Scene);
        stage.show();
    }

    public void cancelExitOnAction(ActionEvent e) {
        Stage stage = (Stage) exit.getScene().getWindow();
        stage.close();
    }

    public void conectHomeButtonOnAction(ActionEvent e) throws IOException {
        Stage stage = (Stage) Home.getScene().getWindow();
        FXMLLoader fxmlLoader  = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene Scene = new Scene(fxmlLoader.load(), 1268, 802);
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


    @FXML
    public void handleSearch(ActionEvent event) {
        // Get the room number to search
        String roomNumberToSearch = Find.getText().trim();

        // Check if the room number is not empty
        if (!roomNumberToSearch.isEmpty()) {
            // Clear the previous data in the TableView
            Id.getItems().clear();

            try {
                // Check if the connection is closed and open it if necessary
                if (connection == null || connection.isClosed()) {
                    connection = LUULY1.getConnection();
                }

                // Prepare the SELECT query to search by Room Number
                String query = "SELECT * FROM IDCustomers WHERE [Room Number] = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    // Set the parameter in the query
                    preparedStatement.setString(1, roomNumberToSearch);

                    // Execute the query
                    try (ResultSet resultSet = preparedStatement.executeQuery()) {
                        while (resultSet.next()) {
                            String name = resultSet.getString("Name");
                            String phone = resultSet.getString("Phone");
                            String roomNumber = resultSet.getString("Room Number");
                            String roomType = resultSet.getString("Room Type");
                            String bill = resultSet.getString("Bill");
                            String date = resultSet.getString("Date");

                            // Create an IDCustomer object and add it to the TableView
                            IDCustomers idCustomer = new IDCustomers(name, phone, roomNumber, roomType, bill, date);
                            Id.getItems().add(idCustomer);
                        }
                    }
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }




    private void loadDataFromDatabase() {
        try {
            String query = "SELECT * FROM IDCustomers";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query);
                 ResultSet resultSet = preparedStatement.executeQuery()) {


                while (resultSet.next()) {
                    String name = resultSet.getString("Name");
                    String phone = resultSet.getString("Phone");
                    String roomNumber = resultSet.getString("Room Number");
                    String roomType = resultSet.getString("Room Type");
                    String bill = resultSet.getString("Bill");

                    // Retrieve the date as a string (may be null)
                    String date = resultSet.getString("Date");


                    IDCustomers IdCustomer = new IDCustomers(name, phone, roomNumber, roomType, bill, date);


                    // Add the IDCustomer object to the TableView
                    Id.getItems().add(IdCustomer);
                }

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
