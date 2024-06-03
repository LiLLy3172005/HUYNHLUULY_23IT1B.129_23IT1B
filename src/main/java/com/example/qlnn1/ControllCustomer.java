package com.example.qlnn1;

import com.itextpdf.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class ControllCustomer {
    @FXML
private Button add;
    @FXML
    private Button Edit;
    @FXML
    private Button Delete;
    @FXML
    private Button Chosen;

    private Connection connection;

    @FXML
    private TableView<Customer> Customer;
    @FXML
    private TableColumn<Customer, String> ID_Customer;
    @FXML
    private TableColumn<Customer, String> Name_Customer;
    @FXML
    private TableColumn<Customer, String> CCCD_Customer;
    @FXML
    private TableColumn<Customer, String> Phone_Customer;
    @FXML
    private TableColumn<Customer, String>  Email_Customer;
    @FXML
    private TableColumn<Customer, String>  Address_Customer;

    @FXML
    public void initialize() {
        connection = LUULY1.getConnection();
        ID_Customer.setCellValueFactory(new PropertyValueFactory<>("ID_Customer"));
        Name_Customer.setCellValueFactory(new PropertyValueFactory<>("Name_Customer"));
        CCCD_Customer.setCellValueFactory(new PropertyValueFactory<>("CCCD_Customer"));
        Phone_Customer.setCellValueFactory(new PropertyValueFactory<>("Phone_Customer"));
        Email_Customer.setCellValueFactory(new PropertyValueFactory<>("Email_Customer"));
        Address_Customer.setCellValueFactory(new PropertyValueFactory<>("Address_Customer"));

        loadDataFromDatabase();
    }

    private void loadDataFromDatabase() {
        try {
            String query = "SELECT * FROM Customer";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String customerId = resultSet.getString("ID_Customer");
                String name = resultSet.getString("Name_Customer");
                String cccd = resultSet.getString("CCCD_Customer");
                String phone = resultSet.getString("Phone_Customer");
                String email = resultSet.getString("Email_Customer");
                String address = resultSet.getString("Address_Customer");

                Customer.getItems().add(new Customer(customerId, name, cccd, phone, email, address));
            }

            preparedStatement.close();
            resultSet.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @FXML
    private void handleDeleteButton(ActionEvent event) {
        Customer selectedItem = Customer.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            String customerId = selectedItem.getID_Customer();
            deleteCustomerData(customerId);
            Customer.getItems().remove(selectedItem);
        }
    }

    private void deleteCustomerData(String customerId) {
        try {
            String query = "DELETE FROM Customer WHERE ID_Customer = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, customerId);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleEditButton(ActionEvent event) {
        Customer selectedItem = Customer.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            showEditDialog(selectedItem);
        }
    }

    private void showEditDialog(Customer item) {
        Dialog<Customer> dialog = new Dialog<>();
        dialog.setTitle("Edit Customer Information");
        dialog.setHeaderText("Edit Customer Information");

        ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 150, 10, 10));

        TextField nameField = new TextField(item.getName_Customer());
        TextField cccdField = new TextField(item.getCCCD_Customer());
        TextField phoneField = new TextField(item.getPhone_Customer());
        TextField emailField = new TextField(item.getEmail_Customer());
        TextField addressField = new TextField(item.getAddress_Customer());

        gridPane.add(new Label("Name:"), 0, 0);
        gridPane.add(nameField, 1, 0);
        gridPane.add(new Label("CCCD:"), 0, 1);
        gridPane.add(cccdField, 1, 1);
        gridPane.add(new Label("Phone:"), 0, 2);
        gridPane.add(phoneField, 1, 2);
        gridPane.add(new Label("Email:"), 0, 3);
        gridPane.add(emailField, 1, 3);
        gridPane.add(new Label("Address:"), 0, 4);
        gridPane.add(addressField, 1, 4);

        dialog.getDialogPane().setContent(gridPane);

        Node saveButton = dialog.getDialogPane().lookupButton(saveButtonType);
        saveButton.setDisable(true);

        nameField.textProperty().addListener((observable, oldValue, newValue) ->
                saveButton.setDisable(newValue.trim().isEmpty() || cccdField.getText().trim().isEmpty() ||
                        phoneField.getText().trim().isEmpty() || emailField.getText().trim().isEmpty() ||
                        addressField.getText().trim().isEmpty()));
        cccdField.textProperty().addListener((observable, oldValue, newValue) ->
                saveButton.setDisable(newValue.trim().isEmpty() || nameField.getText().trim().isEmpty() ||
                        phoneField.getText().trim().isEmpty() || emailField.getText().trim().isEmpty() ||
                        addressField.getText().trim().isEmpty()));
        phoneField.textProperty().addListener((observable, oldValue, newValue) ->
                saveButton.setDisable(newValue.trim().isEmpty() || nameField.getText().trim().isEmpty() ||
                        cccdField.getText().trim().isEmpty() || emailField.getText().trim().isEmpty() ||
                        addressField.getText().trim().isEmpty()));
        emailField.textProperty().addListener((observable, oldValue, newValue) ->
                saveButton.setDisable(newValue.trim().isEmpty() || nameField.getText().trim().isEmpty() ||
                        cccdField.getText().trim().isEmpty() || phoneField.getText().trim().isEmpty() ||
                        addressField.getText().trim().isEmpty()));
        addressField.textProperty().addListener((observable, oldValue, newValue) ->
                saveButton.setDisable(newValue.trim().isEmpty() || nameField.getText().trim().isEmpty() ||
                        cccdField.getText().trim().isEmpty() || phoneField.getText().trim().isEmpty() ||
                        emailField.getText().trim().isEmpty()));

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                item.setName_Customer(nameField.getText());
                item.setCCCD_Customer(cccdField.getText());
                item.setPhone_Customer(phoneField.getText());
                item.setEmail_Customer(emailField.getText());
                item.setAddress_Customer(addressField.getText());
                updateCustomerData(item);
                return item;
            }
            return null;
        });

        Optional<Customer> result = dialog.showAndWait();
        result.ifPresentOrElse(
                customer -> Customer.refresh(),
                () -> System.out.println("No input")
        );
    }

    private void updateCustomerData(Customer item) {
        try {

            String query = "UPDATE Customer SET Name_Customer=?, CCCD_Customer=?, Phone_Customer=?, Email_Customer=?, Address_Customer=? WHERE ID_Customer=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, item.getName_Customer());
            preparedStatement.setString(2, item.getCCCD_Customer());
            preparedStatement.setString(3, item.getPhone_Customer());
            preparedStatement.setString(4, item.getEmail_Customer());
            preparedStatement.setString(5, item.getAddress_Customer());
            preparedStatement.setString(6, item.getID_Customer());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void conectBillButtonOnAction(ActionEvent e) throws IOException, java.io.IOException {
        Stage stage = (Stage) Chosen.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1530, 802);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handleAddButton(ActionEvent event) {
        Dialog<Customer> dialog = new Dialog<>();
        dialog.setTitle("Add New Customer");
        dialog.setHeaderText("Enter Customer Details");

        ButtonType addButtonType = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 150, 10, 10));

        TextField nameField = new TextField();
        nameField.setPromptText("Name");
        TextField cccdField = new TextField();
        cccdField.setPromptText("CCCD");
        TextField phoneField = new TextField();
        phoneField.setPromptText("Phone");
        TextField emailField = new TextField();
        emailField.setPromptText("Email");
        TextField addressField = new TextField();
        addressField.setPromptText("Address");

        gridPane.add(new Label("Name:"), 0, 0);
        gridPane.add(nameField, 1, 0);
        gridPane.add(new Label("CCCD:"), 0, 1);
        gridPane.add(cccdField, 1, 1);
        gridPane.add(new Label("Phone:"), 0, 2);
        gridPane.add(phoneField, 1, 2);
        gridPane.add(new Label("Email:"), 0, 3);
        gridPane.add(emailField, 1, 3);
        gridPane.add(new Label("Address:"), 0, 4);
        gridPane.add(addressField, 1, 4);

        dialog.getDialogPane().setContent(gridPane);

        Node addButton = dialog.getDialogPane().lookupButton(addButtonType);
        addButton.setDisable(true);

        nameField.textProperty().addListener((observable, oldValue, newValue) ->
                addButton.setDisable(newValue.trim().isEmpty() || cccdField.getText().trim().isEmpty() ||
                        phoneField.getText().trim().isEmpty() || emailField.getText().trim().isEmpty() ||
                        addressField.getText().trim().isEmpty()));
        cccdField.textProperty().addListener((observable, oldValue, newValue) ->
                addButton.setDisable(newValue.trim().isEmpty() || nameField.getText().trim().isEmpty() ||
                        phoneField.getText().trim().isEmpty() || emailField.getText().trim().isEmpty() ||
                        addressField.getText().trim().isEmpty()));
        phoneField.textProperty().addListener((observable, oldValue, newValue) ->
                addButton.setDisable(newValue.trim().isEmpty() || nameField.getText().trim().isEmpty() ||
                        cccdField.getText().trim().isEmpty() || emailField.getText().trim().isEmpty() ||
                        addressField.getText().trim().isEmpty()));
        emailField.textProperty().addListener((observable, oldValue, newValue) ->
                addButton.setDisable(newValue.trim().isEmpty() || nameField.getText().trim().isEmpty() ||
                        cccdField.getText().trim().isEmpty() || phoneField.getText().trim().isEmpty() ||
                        addressField.getText().trim().isEmpty()));
        addressField.textProperty().addListener((observable, oldValue, newValue) ->
                addButton.setDisable(newValue.trim().isEmpty() || nameField.getText().trim().isEmpty() ||
                        cccdField.getText().trim().isEmpty() || phoneField.getText().trim().isEmpty() ||
                        emailField.getText().trim().isEmpty()));

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButtonType) {
                return new Customer(
                        "",
                        nameField.getText(),
                        cccdField.getText(),
                        phoneField.getText(),
                        emailField.getText(),
                        addressField.getText()
                );
            }
            return null;
        });

        Optional<Customer> result = dialog.showAndWait();
        result.ifPresent(customer -> {
            addCustomerToDatabase(customer);
            Customer.getItems().add(customer);
        });
    }

    private void addCustomerToDatabase(Customer customer) {
        try {
            String query = "INSERT INTO Customer (Name_Customer, CCCD_Customer, Phone_Customer, Email_Customer, Address_Customer) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, customer.getName_Customer());
            preparedStatement.setString(2, customer.getCCCD_Customer());
            preparedStatement.setString(3, customer.getPhone_Customer());
            preparedStatement.setString(4, customer.getEmail_Customer());
            preparedStatement.setString(5, customer.getAddress_Customer());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


