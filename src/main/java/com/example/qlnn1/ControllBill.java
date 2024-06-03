package com.example.qlnn1;
import java.io.IOException;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.chart.AreaChart;
import javafx.scene.control.*;
import javafx.scene.control.Button;

import java.sql.*;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.Scene;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javafx.scene.chart.XYChart;

public class ControllBill {
    @FXML
    private AreaChart<String, Integer> areaChart;
    @FXML
    private TableView<Bill> BillTable;
    @FXML
    private TableColumn<Bill, String> ID_Customer;
    @FXML
    private TableColumn<Bill, Date> Check_in_Date;
    @FXML
    private TableColumn<Bill, Date> Check_out_Date;
    @FXML
    private TableColumn<Bill, String> Room_ID;
    @FXML
    private TableColumn<Bill, String> ID_Service;
    @FXML
    private TableColumn<Bill, String> ID_Bill;
    @FXML
    private TableColumn<Bill, Integer> Total;
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
    private DatePicker day;
    private Connection connection;
    @FXML
    private Button exit;
    @FXML
    private Button EBill;
    @FXML
    private Button Chart;
    @FXML
    private Button Room;
    @FXML
    private Button Customer;

@FXML
private Button Search;

    public void initialize() {
        connection = LUULY1.getConnection();
        ID_Customer.setCellValueFactory(new PropertyValueFactory<>("ID_Customer"));
        Check_in_Date.setCellValueFactory(new PropertyValueFactory<>("Check_in_Date"));
        Check_out_Date.setCellValueFactory(new PropertyValueFactory<>("Check_out_Date"));
        Room_ID.setCellValueFactory(new PropertyValueFactory<>("Room_ID"));
        ID_Service.setCellValueFactory(new PropertyValueFactory<>("ID_Service"));
        ID_Bill.setCellValueFactory(new PropertyValueFactory<>("ID_Bill"));
        Total.setCellValueFactory(new PropertyValueFactory<>("Total"));
        loadDataFromDatabase();
        drawChart();
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

    public void conectChart(ActionEvent e) throws IOException {
        Stage stage = (Stage) Chart.getScene().getWindow();
        FXMLLoader fxmlLoader  = new FXMLLoader(HelloApplication.class.getResource("ChartBill.fxml"));
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
    public void conectRoom(ActionEvent e) throws IOException {
        Stage stage = (Stage) Room.getScene().getWindow();
        FXMLLoader fxmlLoader  = new FXMLLoader(HelloApplication.class.getResource("Room.fxml"));
        Scene Scene = new Scene(fxmlLoader.load(), 1530, 802);
        stage.setScene(Scene);
        stage.show();
    }
    public void conectCustomer(ActionEvent e) throws IOException {
        Stage stage = (Stage) Customer.getScene().getWindow();
        FXMLLoader fxmlLoader  = new FXMLLoader(HelloApplication.class.getResource("Customer.fxml"));
        Scene Scene = new Scene(fxmlLoader.load(), 1530, 802);
        stage.setScene(Scene);
        stage.show();
    }


    private void loadDataFromDatabase() {

        // gui lenh len server loadData
        try {
            String query = "SELECT * FROM Bill";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String idCustomer = resultSet.getString("ID_Customer");
                Date checkinDate = resultSet.getDate("Check_in_Date");
                Date checkoutDate = resultSet.getDate("Check_out_Date");

                String roomID = resultSet.getString("Room_ID");
                String idServices = resultSet.getString("ID_Service");
                String idBill = resultSet.getString("ID_Bill");
                Integer total =  calculateTotal(checkinDate, checkoutDate, roomID, idServices);
                BillTable.getItems().add(new Bill(idBill, idCustomer, roomID, idServices,  checkinDate, checkoutDate, total));
            }

            preparedStatement.close();
            resultSet.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private int calculateTotal(Date checkinDate, Date checkoutDate, String roomID, String idServices) {
        int totalRoom = 0;
        int totalServices = 0;

        try {
            // Tính tổng tiền phòng
            String queryRoom = "SELECT Price_RoomType FROM Room_Type JOIN Room ON Room.ID_RoomType = Room_Type.ID_RoomType WHERE Room.Room_ID = ?";
            PreparedStatement preparedStatementRoom = connection.prepareStatement(queryRoom);
            preparedStatementRoom.setString(1, roomID);
            ResultSet resultSetRoom = preparedStatementRoom.executeQuery();
            if (resultSetRoom.next()) {
                totalRoom = resultSetRoom.getInt("Price_RoomType");
            }
            preparedStatementRoom.close();
            resultSetRoom.close();

            // Tính tổng tiền dịch vụ
            String queryService = "SELECT Price_Service FROM Service WHERE ID_Service = ?";
            PreparedStatement preparedStatementService = connection.prepareStatement(queryService);
            preparedStatementService.setString(1, idServices);
            ResultSet resultSetService = preparedStatementService.executeQuery();
            if (resultSetService.next()) {
                totalServices = resultSetService.getInt("Price_Service");
            }
            preparedStatementService.close();
            resultSetService.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        // Tính số ngày lưu trú
        long diffInMillies = Math.abs(checkoutDate.getTime() - checkinDate.getTime());
        long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

        return totalRoom * (int)diff + totalServices;
    }
    @FXML
    void exportBillToPDF() {
        Bill selectedBill = BillTable.getSelectionModel().getSelectedItem();
        if (selectedBill != null) {
            selectedBill.exportToPDF();
        } else {
            System.out.println("No bill selected");
        }
    }
    @FXML
    void handleEBillButtonAction() {
        exportBillToPDF();
    }


    @FXML
    void searchBillByCheckoutDate() {
        // Lấy ngày được chọn từ DatePicker
        LocalDate selectedDate = day.getValue();

        // Chuyển LocalDate sang java.sql.Date
        Date selectedSqlDate = Date.valueOf(selectedDate);

        // Xóa dữ liệu hiện tại trong bảng
        BillTable.getItems().clear();

        // Truy vấn để lấy danh sách ID Bill có ngày Check out date tương ứng
        String query = "SELECT * FROM Bill WHERE Check_out_Date = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setDate(1, selectedSqlDate);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String idCustomer = resultSet.getString("ID_Customer");
                Date checkinDate = resultSet.getDate("Check_in_Date");
                Date checkoutDate = resultSet.getDate("Check_out_Date");
                String roomID = resultSet.getString("Room_ID");
                String idServices = resultSet.getString("ID_Service");
                String idBill = resultSet.getString("ID_Bill");
                Integer total =  calculateTotal(checkinDate, checkoutDate, roomID, idServices);
                BillTable.getItems().add(new Bill(idBill, idCustomer, roomID, idServices,  checkinDate, checkoutDate, total));
            }

            preparedStatement.close();
            resultSet.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void drawChart() {
        XYChart.Series<String, Integer> series = new XYChart.Series<>();

        for (Bill bill : BillTable.getItems()) {
            series.getData().add(new XYChart.Data<>(bill.getCheck_out_Date().toString(), bill.getTotal()));
        }

        areaChart.getData().add(series);
    }

}

