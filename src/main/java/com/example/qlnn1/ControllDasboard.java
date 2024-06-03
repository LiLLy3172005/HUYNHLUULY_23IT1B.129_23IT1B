package com.example.qlnn1;
import java.io.IOException;
import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.RowIdLifetime;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class ControllDasboard implements Initializable{
    @FXML
    private Button DashBoard;
    @FXML
    private Button CheckIn;
    @FXML
    private Button CheckOut;
    @FXML
    private Button Service;

    @FXML
    private Button ToEmployee;
@FXML
private Button Bill;

    @FXML
    private Label R001;

    @FXML
    private Label R002;

    @FXML
    private Label R003;

    @FXML
    private Label R004;

    @FXML
    private Label R005;

    @FXML
    private Label R006;

    @FXML
    private Label R007;

    @FXML
    private Label R008;

    @FXML
    private Label R009;

    @FXML
    private Label R010;

    @FXML
    private Label R011;

    @FXML
    private Label R012;

    @FXML
    private Label R013;

    @FXML
    private Label R014;

    @FXML
    private Label R015;
    private Connection connection;
    @FXML
    private Button exit;

    @FXML
    private Button Select;

    @FXML
    private Button SToday;
    @FXML
    private Button SCheckout;

    @FXML
    private DatePicker day;

    private Map<String, Label> roomLabels;






    public void conectServiceButtonOnAction(ActionEvent e) throws IOException {
        Stage stage = (Stage) this.Service.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Edit.fxml"));
        Scene Scene = new Scene((Parent) fxmlLoader.load(), 1530.0, 802.0);
        stage.setScene(Scene);
        stage.show();
    }
    public void conectEmployeeButtonOnAction(ActionEvent e) throws IOException {
        Stage stage = (Stage) this.ToEmployee.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Chosen.fxml"));
        Scene Scene = new Scene((Parent) fxmlLoader.load(), 1530.0, 802.0);
        stage.setScene(Scene);
        stage.show();
    }
    public void cancelExitOnAction(ActionEvent e) {
        Stage stage = (Stage) exit.getScene().getWindow();
        stage.close();
    }
    public void conectCheckOut(ActionEvent e) throws IOException {
        Stage stage = (Stage)this.CheckOut.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Remove.fxml"));
        Scene Scene = new Scene((Parent)fxmlLoader.load(), 1530.0, 802.0);
        stage.setScene(Scene);
        stage.show();
    }
    public void conectCheckIn(ActionEvent e) throws IOException {
        Stage stage = (Stage) CheckIn.getScene().getWindow();
        FXMLLoader fxmlLoader  = new FXMLLoader(HelloApplication.class.getResource("Add.fxml"));
        Scene Scene = new Scene(fxmlLoader.load(), 1530, 802);
        stage.setScene(Scene);
        stage.show();
    }


    public void conectDashBoard(ActionEvent e) throws IOException {
        Stage stage = (Stage) DashBoard.getScene().getWindow();
        FXMLLoader fxmlLoader  = new FXMLLoader(HelloApplication.class.getResource("DashBoard.fxml"));
        Scene Scene = new Scene(fxmlLoader.load(), 1530, 802);
        stage.setScene(Scene);
        stage.show();
    }
    public void conectBillButtonOnAction(ActionEvent e) throws IOException {
        Stage stage = (Stage) Bill.getScene().getWindow();
        FXMLLoader fxmlLoader  = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));

        Scene Scene = new Scene(fxmlLoader.load(), 1530, 802);
        stage.setScene(Scene);
        stage.show();


    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        connection = LUULY1.getConnection();
        roomLabels = new HashMap<>();
        roomLabels.put("R001", R001);
        roomLabels.put("R002", R002);
        roomLabels.put("R003", R003);
        roomLabels.put("R004", R004);
        roomLabels.put("R005", R005);
        roomLabels.put("R006", R006);
        roomLabels.put("R007", R007);
        roomLabels.put("R008", R008);
        roomLabels.put("R009", R009);
        roomLabels.put("R010", R010);
        roomLabels.put("R011", R011);
        roomLabels.put("R012", R012);
        roomLabels.put("R013", R013);
        roomLabels.put("R014", R014);
        roomLabels.put("R015", R015);
        day.setConverter(new StringConverter<LocalDate>() {
            String pattern = "yyyy/MM/dd";
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

            {
                day.setPromptText(pattern.toLowerCase());
            }

            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        });
    }

    @FXML
    public void handleSelect(ActionEvent event) {
        String selectedDate = day.getValue().toString();
        if (selectedDate.isEmpty()) {
            System.out.println("Vui lòng chọn ngày!");
        } else {
            updateLabels(selectedDate);
        }
    }

    private void updateLabels(String selectedDate) {
        Map<String, String> roomCustomerMap = getRoomCustomerMap(selectedDate);
        for (String roomNumber : roomLabels.keySet()) {
            Label label = roomLabels.get(roomNumber);
            String customerName = roomCustomerMap.getOrDefault(roomNumber, "Empty");
            label.setText(customerName);
        }
    }

    private Map<String, String> getRoomCustomerMap(String date) {
        Map<String, String> roomCustomerMap = new HashMap<>();
        // gui len server getRoomCustomer-date
        try {
            String query = "SELECT Name_Customer, Room_ID FROM Check_in WHERE Check_in_Date = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, date);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        String roomID = resultSet.getString("Room_ID");
                        String customerName = resultSet.getString("Name_Customer");
                        roomCustomerMap.put(roomID, customerName);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return roomCustomerMap;
    }
    @FXML
    public void handleSCheckout(ActionEvent event) {
        String selectedDate1 = day.getValue().toString();
        if (selectedDate1.isEmpty()) {
            System.out.println("Vui lòng chọn ngày!");
        } else {
            updateLabelsSCheckout(selectedDate1);
        }
    }

    private void updateLabelsSCheckout(String selectedDate1) {
        Map<String, String> roomCustomerMap = getRoomCustomerMapSCheckout(selectedDate1);
        for (String roomNumber : roomLabels.keySet()) {
            Label label = roomLabels.get(roomNumber);
            String customerName = roomCustomerMap.getOrDefault(roomNumber, "Empty");
            label.setText(customerName);
        }
    }

    private Map<String, String> getRoomCustomerMapSCheckout(String date) {
        Map<String, String> roomCustomerMap = new HashMap<>();
        try {
            String query = "SELECT Name_Customer, Room_ID FROM Check_in WHERE Check_out_Date = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, date);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        String roomID = resultSet.getString("Room_ID");
                        String customerName = resultSet.getString("Name_Customer");
                        roomCustomerMap.put(roomID, customerName);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return roomCustomerMap;
    }

    @FXML
    public void handleSToday(ActionEvent event) {
        LocalDate today = LocalDate.now();
        String selectedDate = today.toString();
        updateLabelsSToday(selectedDate);
    }

    private void updateLabelsSToday(String selectedDate) {
        Map<String, String> roomCustomerMap = getRoomCustomerMapSToday(selectedDate);
        for (String roomNumber : roomLabels.keySet()) {
            Label label = roomLabels.get(roomNumber);
            String customerName = roomCustomerMap.getOrDefault(roomNumber, "Empty");
            label.setText(customerName);
        }
    }

    private Map<String, String> getRoomCustomerMapSToday(String date) {
        Map<String, String> roomCustomerMap = new HashMap<>();
        try {
            String query = "SELECT Name_Customer, Room_ID FROM Check_in WHERE Check_in_Date <= ? AND Check_out_Date >= ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, date);
                preparedStatement.setString(2, date);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        String roomID = resultSet.getString("Room_ID");
                        String customerName = resultSet.getString("Name_Customer");
                        roomCustomerMap.put(roomID, customerName);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return roomCustomerMap;
    }

}






