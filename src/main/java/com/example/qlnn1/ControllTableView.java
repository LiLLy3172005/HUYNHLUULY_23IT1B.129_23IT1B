package com.example.qlnn1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Optional;
import java.util.ResourceBundle;

public class ControllTableView implements Initializable {
    @FXML
    private Button Edit;
    @FXML
    private Button Home;
    @FXML
    private Button Delete;
    @FXML
    private Button Service;
    @FXML
    private Button Bill;

    private Connection connection;
    @FXML
    private Button OK;
    @FXML
    private Button Table_View;
    @FXML
    private Button exit;

    @FXML
    private TableView<Check_in> Check_in;
    @FXML
    private TableColumn<Check_in, String> Id_Customer;
    @FXML
    private TableColumn<Check_in, Date> Checkindate;
    @FXML
    private TableColumn<Check_in, Date> Checkoutdate;
    @FXML
    private TableColumn<Check_in, String> Name;
    @FXML
    private TableColumn<Check_in, String> CCCD;
    @FXML
    private TableColumn<Check_in, String> Phone;

    @FXML
    private TableColumn<Check_in, String> Email;
    @FXML
    private TableColumn<Check_in, String> Address;
    @FXML
    private TableColumn<Check_in, String> RoomID;
    @FXML
    private TableColumn<Check_in, String> IdService;
    @FXML
    private TableColumn<Check_in, String> IdEmployee;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        connection = LUULY1.getConnection();

    }


    public void conectHome(ActionEvent e) throws IOException {
        Stage stage = (Stage) Home.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Add.fxml"));
        Scene Scene = new Scene(fxmlLoader.load(), 1530, 802);
        stage.setScene(Scene);
        stage.show();
    }







    public void initialize() {
        connection = LUULY1.getConnection();
        Id_Customer.setCellValueFactory(new PropertyValueFactory<>("ID_Customer"));
        Checkindate.setCellValueFactory(new PropertyValueFactory<>("Check_in_Date"));
        Checkoutdate.setCellValueFactory(new PropertyValueFactory<>("Check_out_Date"));
        Name.setCellValueFactory(new PropertyValueFactory<>("Name_Customer"));
        CCCD.setCellValueFactory(new PropertyValueFactory<>("CCCD_Customer"));
        Phone.setCellValueFactory(new PropertyValueFactory<>("Phone_Customer"));
        Email.setCellValueFactory(new PropertyValueFactory<>("Email_Customer"));
        Address.setCellValueFactory(new PropertyValueFactory<>("Address_Customer"));
        RoomID.setCellValueFactory(new PropertyValueFactory<>("Room_ID"));
        IdService.setCellValueFactory(new PropertyValueFactory<>("ID_Services"));
        IdEmployee.setCellValueFactory(new PropertyValueFactory<>("ID_Employee"));

        loadDataFromDatabase();
    }

    private void loadDataFromDatabase() {
        try {
            String query = "SELECT * FROM Check_in";
            PreparedStatement preparedStatement = connection.prepareStatement(query);   // thuc thi nhieu lan
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String idCustomer = resultSet.getString("ID_Customer");
                Date checkinDate = resultSet.getDate("Check_in_Date");
                Date checkoutDate = resultSet.getDate("Check_out_Date");
                String nameCustomer = resultSet.getString("Name_Customer");
                String cccdCustomer = resultSet.getString("CCCD_Customer");
                String phoneCustomer = resultSet.getString("Phone_Customer");
                String emailCustomer = resultSet.getString("Email_Customer");
                String addressCustomer = resultSet.getString("Address_Customer");
                String roomID = resultSet.getString("Room_ID");
                String idServices = resultSet.getString("ID_Services");
                String idEmployee = resultSet.getString("ID_Employee");

                Check_in.getItems().add(new Check_in(idCustomer, checkinDate, checkoutDate, nameCustomer, cccdCustomer, phoneCustomer, emailCustomer, addressCustomer, roomID, idServices, idEmployee));
            }

            preparedStatement.close();
            resultSet.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    @FXML
    private void handleDeleteButton(ActionEvent event) {
        Check_in selectedItem = Check_in.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            String idCustomer = selectedItem.getID_Customer();
            deleteCheckInData(idCustomer);
            Check_in.getItems().remove(selectedItem);
        }
    }

    private void deleteCheckInData(String idCustomer) {
        try {
            String query = "DELETE FROM Check_in WHERE ID_Customer = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, idCustomer);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void handleEditButton(ActionEvent event) {
        Check_in selectedItem = Check_in.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            showEditDialog(selectedItem);
        }
    }

    private void showEditDialog(Check_in item) {
        // Tạo một dialog để chỉnh sửa thông tin
        Dialog<Check_in> dialog = new Dialog<>();
        dialog.setTitle("Edit Customer Information");
        dialog.setHeaderText("Edit Customer Information");

        // Thiết lập nút Save và Cancel
        ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        // Tạo một gridpane để đặt các trường thông tin cần chỉnh sửa
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 150, 10, 10));

        // Thêm các trường thông tin cần chỉnh sửa
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

        // Enable/Disable nút Save tùy thuộc vào việc nhập liệu
        Node saveButton = dialog.getDialogPane().lookupButton(saveButtonType);
        saveButton.setDisable(true);

        // Validation dữ liệu
        nameField.textProperty().addListener((observable, oldValue, newValue) -> {
            saveButton.setDisable(newValue.trim().isEmpty() || cccdField.getText().trim().isEmpty() ||
                    phoneField.getText().trim().isEmpty() || emailField.getText().trim().isEmpty() ||
                    addressField.getText().trim().isEmpty());
        });
        cccdField.textProperty().addListener((observable, oldValue, newValue) -> {
            saveButton.setDisable(newValue.trim().isEmpty() || nameField.getText().trim().isEmpty() ||
                    phoneField.getText().trim().isEmpty() || emailField.getText().trim().isEmpty() ||
                    addressField.getText().trim().isEmpty());
        });
        phoneField.textProperty().addListener((observable, oldValue, newValue) -> {
            saveButton.setDisable(newValue.trim().isEmpty() || nameField.getText().trim().isEmpty() ||
                    cccdField.getText().trim().isEmpty() || emailField.getText().trim().isEmpty() ||
                    addressField.getText().trim().isEmpty());
        });
        emailField.textProperty().addListener((observable, oldValue, newValue) -> {
            saveButton.setDisable(newValue.trim().isEmpty() || nameField.getText().trim().isEmpty() ||
                    cccdField.getText().trim().isEmpty() || phoneField.getText().trim().isEmpty() ||
                    addressField.getText().trim().isEmpty());
        });
        addressField.textProperty().addListener((observable, oldValue, newValue) -> {
            saveButton.setDisable(newValue.trim().isEmpty() || nameField.getText().trim().isEmpty() ||
                    cccdField.getText().trim().isEmpty() || phoneField.getText().trim().isEmpty() ||
                    emailField.getText().trim().isEmpty());
        });

        // Xử lý sự kiện khi nhấn Save
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

        Optional<Check_in> result = dialog.showAndWait();
        result.ifPresentOrElse(
                customer -> {
                    // Refresh TableView
                    Check_in.refresh();
                },
                () -> System.out.println("No input")
        );
    }

    private void updateCustomerData(Check_in item) {
        try {
            String query = "UPDATE Check_in SET Name_Customer=?, CCCD_Customer=?, Phone_Customer=?, Email_Customer=?, Address_Customer=? WHERE ID_Customer=?";
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

}
