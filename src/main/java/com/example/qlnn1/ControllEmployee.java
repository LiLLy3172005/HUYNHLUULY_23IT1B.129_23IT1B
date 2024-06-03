package com.example.qlnn1;

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

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

public class ControllEmployee {

    @FXML
    private Button Employee;

    private Connection connection;
    @FXML
    private Button Edit;
    @FXML
    private Button Delete;
    @FXML
    private Button exit;
    @FXML
    private  Button add;

    @FXML
    private TableView<Employee> Employee1;
    @FXML
    private TableColumn<Employee, String> ID_Employee;
    @FXML
    private TableColumn<Employee, String> Name_Employee;
    @FXML
    private TableColumn<Employee, String> Duty_Employee;
    @FXML
    private TableColumn<Employee, LocalDate> BirthDay_Employee; // Change type to LocalDate
    @FXML
    private TableColumn<Employee, String> Email_Employee;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        connection = LUULY1.getConnection();
        initialize();
    }

    public void conectChosen(ActionEvent e) throws IOException {
        Stage stage = (Stage) Employee.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Chosen.fxml"));
        Scene Scene = new Scene(fxmlLoader.load(), 1530, 802);
        stage.setScene(Scene);
        stage.show();
    }

    public void initialize() {
        connection = LUULY1.getConnection();
        ID_Employee.setCellValueFactory(new PropertyValueFactory<>("ID_Employee"));
        Name_Employee.setCellValueFactory(new PropertyValueFactory<>("Name_Employee"));
        Duty_Employee.setCellValueFactory(new PropertyValueFactory<>("Duty_Employee"));
        BirthDay_Employee.setCellValueFactory(new PropertyValueFactory<>("BirthDay_Employee")); // Sửa lại
        Email_Employee.setCellValueFactory(new PropertyValueFactory<>("Email_Employee"));

        loadDataFromDatabase(); // Thêm phương thức loadDataFromDatabase() vào initialize()
    }

    private void loadDataFromDatabase() {
        try {
            String query = "SELECT * FROM Employee";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String IdEmployee = resultSet.getString("ID_Employee");
                String NameEmployee = resultSet.getString("Name_Employee");
                String DutyEmployee = resultSet.getString("Duty_Employee");
                Date BirthDayEmployee = resultSet.getDate("BirthDay_Employee");
                String EmailEmployee = resultSet.getString("Email_Employee");
                Employee1.getItems().add(new Employee(IdEmployee, NameEmployee, DutyEmployee, BirthDayEmployee.toLocalDate(), EmailEmployee));
            }

            preparedStatement.close();
            resultSet.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @FXML
    private void handleDeleteButton(ActionEvent event) {
        Employee selectedItem = Employee1.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            String idEmployee = selectedItem.getID_Employee();
            deleteEmployeeData(idEmployee);
            Employee1.getItems().remove(selectedItem);
        }
    }

    private void deleteEmployeeData(String idEmployee) {
        try {
            String query = "DELETE FROM Employee WHERE ID_Employee = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, idEmployee);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleEditButton(ActionEvent event) {
        Employee selectedItem = Employee1.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            showEditDialog(selectedItem);
        }
    }

    private void showEditDialog(Employee item) {
        // Tạo một dialog để chỉnh sửa thông tin
        Dialog<Employee> dialog = new Dialog<>();
        dialog.setTitle("Edit Employee Information");
        dialog.setHeaderText("Edit Employee Information");

        // Thiết lập nút Save và Cancel
        ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        // Tạo một gridpane để đặt các trường thông tin cần chỉnh sửa
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 150, 10, 10));

        // Thêm các trường thông tin cần chỉnh sửa
        TextField nameField = new TextField(item.getName_Employee());
        TextField dutyField = new TextField(item.getDuty_Employee());
        DatePicker birthDayPicker = new DatePicker(item.getBirthDay_Employee());
        TextField emailField = new TextField(item.getEmail_Employee());

        gridPane.add(new Label("Name:"), 0, 0);
        gridPane.add(nameField, 1, 0);
        gridPane.add(new Label("Duty:"), 0, 1);
        gridPane.add(dutyField, 1, 1);
        gridPane.add(new Label("BirthDay:"), 0, 2);
        gridPane.add(birthDayPicker, 1, 2);
        gridPane.add(new Label("Email:"), 0, 3);
        gridPane.add(emailField, 1, 3);

        dialog.getDialogPane().setContent(gridPane);

        // Enable/Disable nút Save tùy thuộc vào việc nhập liệu
        Node saveButton = dialog.getDialogPane().lookupButton(saveButtonType);
        saveButton.setDisable(true);

        // Validation dữ liệu
        nameField.textProperty().addListener((observable, oldValue, newValue) -> {
            saveButton.setDisable(newValue.trim().isEmpty() || dutyField.getText().trim().isEmpty() ||
                    birthDayPicker.getValue() == null || emailField.getText().trim().isEmpty());
        });
        dutyField.textProperty().addListener((observable, oldValue, newValue) -> {
            saveButton.setDisable(newValue.trim().isEmpty() || nameField.getText().trim().isEmpty() ||
                    birthDayPicker.getValue() == null || emailField.getText().trim().isEmpty());
        });
        birthDayPicker.valueProperty().addListener((observable, oldValue, newValue) -> {
            saveButton.setDisable(newValue == null || nameField.getText().trim().isEmpty() ||
                    dutyField.getText().trim().isEmpty() || emailField.getText().trim().isEmpty());
        });
        emailField.textProperty().addListener((observable, oldValue, newValue) -> {
            saveButton.setDisable(newValue.trim().isEmpty() || nameField.getText().trim().isEmpty() ||
                    dutyField.getText().trim().isEmpty() || birthDayPicker.getValue() == null);
        });

        // Xử lý sự kiện khi nhấn Save
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                item.setName_Employee(nameField.getText());
                item.setDuty_Employee(dutyField.getText());
                item.setBirthDay_Employee(birthDayPicker.getValue());
                item.setEmail_Employee(emailField.getText());
                updateEmployeeData(item);
                return item;
            }
            return null;
        });

        Optional<Employee> result = dialog.showAndWait();
        result.ifPresentOrElse(
                employee -> {
                    // Refresh TableView
                    Employee1.refresh();
                },
                () -> System.out.println("No input")
        );
    }

    private void updateEmployeeData(Employee item) {
        try {
            String query = "UPDATE Employee SET Name_Employee=?, Duty_Employee=?, BirthDay_Employee=?, Email_Employee=? WHERE ID_Employee=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, item.getName_Employee());
            preparedStatement.setString(2, item.getDuty_Employee());
            preparedStatement.setDate(3, Date.valueOf(item.getBirthDay_Employee()));
            preparedStatement.setString(4, item.getEmail_Employee());
            preparedStatement.setString(5, item.getID_Employee());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAddButton(ActionEvent event) {
        // Create a dialog for adding a new employee
        Dialog<Employee> dialog = new Dialog<>();
        dialog.setTitle("Add New Employee");
        dialog.setHeaderText("Enter Employee Details");

        // Set the button types
        ButtonType addButtonType = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);

        // Create a GridPane for the form
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 150, 10, 10));

        // Add input fields to the form
        TextField idField = new TextField();
        idField.setPromptText("ID");
        TextField nameField = new TextField();
        nameField.setPromptText("Name");
        TextField dutyField = new TextField();
        dutyField.setPromptText("Duty");
        DatePicker birthDayPicker = new DatePicker();
        TextField emailField = new TextField();
        emailField.setPromptText("Email");

        gridPane.add(new Label("ID:"), 0, 0);
        gridPane.add(idField, 1, 0);
        gridPane.add(new Label("Name:"), 0, 1);
        gridPane.add(nameField, 1, 1);
        gridPane.add(new Label("Duty:"), 0, 2);
        gridPane.add(dutyField, 1, 2);
        gridPane.add(new Label("BirthDay:"), 0, 3);
        gridPane.add(birthDayPicker, 1, 3);
        gridPane.add(new Label("Email:"), 0, 4);
        gridPane.add(emailField, 1, 4);

        dialog.getDialogPane().setContent(gridPane);

        // Enable/Disable the Add button depending on whether the fields are filled
        Node addButton = dialog.getDialogPane().lookupButton(addButtonType);
        addButton.setDisable(true);

        // Add validation
        idField.textProperty().addListener((observable, oldValue, newValue) -> validateInput(idField, nameField, dutyField, birthDayPicker, emailField, addButton));
        nameField.textProperty().addListener((observable, oldValue, newValue) -> validateInput(idField, nameField, dutyField, birthDayPicker, emailField, addButton));
        dutyField.textProperty().addListener((observable, oldValue, newValue) -> validateInput(idField, nameField, dutyField, birthDayPicker, emailField, addButton));
        birthDayPicker.valueProperty().addListener((observable, oldValue, newValue) -> validateInput(idField, nameField, dutyField, birthDayPicker, emailField, addButton));
        emailField.textProperty().addListener((observable, oldValue, newValue) -> validateInput(idField, nameField, dutyField, birthDayPicker, emailField, addButton));

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButtonType) {
                return new Employee(
                        idField.getText(),
                        nameField.getText(),
                        dutyField.getText(),
                        birthDayPicker.getValue(),
                        emailField.getText()
                );
            }
            return null;
        });

        Optional<Employee> result = dialog.showAndWait();
        result.ifPresent(employee -> {
            addEmployeeToDatabase(employee);
            Employee1.getItems().add(employee);
        });
    }

    private void validateInput(TextField idField, TextField nameField, TextField dutyField, DatePicker birthDayPicker, TextField emailField, Node addButton) {
        addButton.setDisable(
                idField.getText().trim().isEmpty() ||
                        nameField.getText().trim().isEmpty() ||
                        dutyField.getText().trim().isEmpty() ||
                        birthDayPicker.getValue() == null ||
                        emailField.getText().trim().isEmpty()
        );
    }

    private void addEmployeeToDatabase(Employee employee) {
        try {
            String query = "INSERT INTO Employee (ID_Employee, Name_Employee, Duty_Employee, BirthDay_Employee, Email_Employee) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, employee.getID_Employee());
            preparedStatement.setString(2, employee.getName_Employee());
            preparedStatement.setString(3, employee.getDuty_Employee());
            preparedStatement.setDate(4, Date.valueOf(employee.getBirthDay_Employee()));
            preparedStatement.setString(5, employee.getEmail_Employee());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
