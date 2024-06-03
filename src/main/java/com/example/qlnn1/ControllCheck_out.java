package com.example.qlnn1;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ControllCheck_out implements Initializable {

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
    private TableView<Check_out> Check_out_Table;
    @FXML
    private TableColumn<Check_out, String> Id_Customer;
    @FXML
    private TableColumn<Check_out, Date> Checkindate;
    @FXML
    private TableColumn<Check_out, Date> Checkoutdate;
    @FXML
    private TableColumn<Check_out, String> Name;
    @FXML
    private TableColumn<Check_out, String> RoomID;
    @FXML
    private TableColumn<Check_out, Integer> Room_Number;
    @FXML
    private TableColumn<Check_out, Integer> Totalday;
    @FXML
    private TableColumn<Check_out, Integer> Totalprice;
    @FXML
    private TableColumn<Check_out, String> Status;
    @FXML
    private DatePicker Today;
    @FXML
    private Button Select;
    @FXML
    private Button Delete;
    @FXML
    private Button exit;

    private Connection connection;

    public ControllCheck_out() {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        connection = LUULY1.getConnection();
        Id_Customer.setCellValueFactory(new PropertyValueFactory<>("ID_Customer"));
        Checkindate.setCellValueFactory(new PropertyValueFactory<>("Check_in_Date"));
        Checkoutdate.setCellValueFactory(new PropertyValueFactory<>("Check_out_Date"));
        Name.setCellValueFactory(new PropertyValueFactory<>("Name_Customer"));
        Room_Number.setCellValueFactory(new PropertyValueFactory<>("Room_Number"));
        Status.setCellValueFactory(new PropertyValueFactory<>("Status"));
        RoomID.setCellValueFactory(new PropertyValueFactory<>("Room_ID"));

        loadDataFromDatabase();
    }

    public void conectCheckOut(ActionEvent e) throws IOException {
        Stage stage = (Stage) this.CheckOut.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Remove.fxml"));
        Scene Scene = new Scene((Parent) fxmlLoader.load(), 1530.0, 802.0);
        stage.setScene(Scene);
        stage.show();
    }

    public void conectCheckIn(ActionEvent e) throws IOException {
        Stage stage = (Stage) CheckIn.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Add.fxml"));
        Scene Scene = new Scene(fxmlLoader.load(), 1530, 802);
        stage.setScene(Scene);
        stage.show();
    }

    public void conectDashBoard(ActionEvent e) throws IOException {
        Stage stage = (Stage) this.DashBoard.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("DashBoard.fxml"));
        Scene Scene = new Scene((Parent) fxmlLoader.load(), 1530.0, 802.0);
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
        Stage stage = (Stage) this.Bill.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene Scene = new Scene((Parent) fxmlLoader.load(), 1530.0, 802.0);
        stage.setScene(Scene);
        stage.show();
    }

    public void cancelExitOnAction(ActionEvent e) {
        Stage stage = (Stage) exit.getScene().getWindow();
        stage.close();
    }
    @FXML
    void selectAction(ActionEvent event) {
        Date selectedDate = Date.valueOf(Today.getValue());
        Check_out_Table.getItems().clear();
        loadDataFromDatebase(selectedDate);
    }
    private void loadDataFromDatabase() {
        try {
            String query = "SELECT * FROM Check_out";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String idCustomer = resultSet.getString("ID_Customer");
                Date checkinDate = resultSet.getDate("Check_in_Date");
                Date checkoutDate = resultSet.getDate("Check_out_Date");
                String nameCustomer = resultSet.getString("Name_Customer");
                String roomID = resultSet.getString("Room_ID");
                int roomNumber = resultSet.getInt("Room_Number");

                String status = resultSet.getString("Status");

                Check_out_Table.getItems().add(new Check_out( idCustomer, roomID, nameCustomer, checkinDate, checkoutDate, roomNumber, status));
            }

            preparedStatement.close();
            resultSet.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void loadDataFromDatebase(Date selectedDate) {
        try {
            String query = "SELECT * FROM Check_out WHERE Check_out_Date = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setDate(1, selectedDate);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String idCustomer = resultSet.getString("ID_Customer");
                Date checkinDate = resultSet.getDate("Check_in_Date");
                Date checkoutDate = resultSet.getDate("Check_out_Date");
                String nameCustomer = resultSet.getString("Name_Customer");
                String roomID = resultSet.getString("Room_ID");
                int roomNumber = resultSet.getInt("Room_Number");

                String status = resultSet.getString("Status");

                Check_out_Table.getItems().add(new Check_out(idCustomer, roomID, nameCustomer, checkinDate, checkoutDate, roomNumber, status));
            }

            preparedStatement.close();
            resultSet.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @FXML
    void handleDelete(ActionEvent event) {
        Check_out selectedItem = Check_out_Table.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            try {
                String query = "DELETE FROM Check_out WHERE ID_Customer=?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, selectedItem.getID_Customer());
                preparedStatement.executeUpdate();
                preparedStatement.close();
                Check_out_Table.getItems().remove(selectedItem);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
