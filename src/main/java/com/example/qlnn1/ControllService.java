package com.example.qlnn1;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
public class ControllService implements Initializable{
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
    private TextField IdService;
    @FXML
    private TextField NameService;
    @FXML
    private TextField PriceService;
    @FXML
    private TextField IdDevice;

    @FXML
    private TextField NameDevice;
    @FXML
    private TextField PriceDevice;


    private Connection connection;
    @FXML
    private Button AddService;
    @FXML
    private Button RemoveService;
    @FXML
    private Button EditService;

    @FXML
    private Button AddDevice;
    @FXML
    private Button RemoveDevice;
    @FXML
    private Button EditDevice;
    @FXML
    private Button exit;
    @FXML
    private TableView<Service> Servicetable ;
    @FXML
    private TableColumn<Service, String> Id_Service;
    @FXML
    private TableColumn<Service, String> Name_Service;
    @FXML
    private TableColumn<Service, Integer> Price_Service;

    @FXML
    private TableView<Device> Devicetable ;
    @FXML
    private TableColumn<Device, String> Id_Device;
    @FXML
    private TableColumn<Device, String> Name_Device;
    @FXML
    private TableColumn<Device, Integer> Price_Device;
    public ControllService() {
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        connection = LUULY1.getConnection();
        Id_Service.setCellValueFactory(new PropertyValueFactory<>("ID_Service"));
        Name_Service.setCellValueFactory(new PropertyValueFactory<>("Name_Service"));
        Price_Service.setCellValueFactory(new PropertyValueFactory<>("Price_Service"));
        Id_Device.setCellValueFactory(new PropertyValueFactory<>("ID_Device"));
        Name_Device.setCellValueFactory(new PropertyValueFactory<>("Name_Device"));
        Price_Device.setCellValueFactory(new PropertyValueFactory<>("Price_Device"));
        loadDataFromDatabase();
        loadDataFromDatabase1();
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
    public void cancelExitOnAction(ActionEvent e) {
        Stage stage = (Stage) exit.getScene().getWindow();
        stage.close();
    }

    private void loadDataFromDatabase() {
        try {
            String query = "SELECT * FROM Service";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String idSerive = resultSet.getString("ID_Service");
                String nameService = resultSet.getString("Name_Service");
                Integer priceSerice = resultSet.getInt("Price_Service");


                Servicetable.getItems().add(new Service(idSerive, nameService,priceSerice));
            }

            preparedStatement.close();
            resultSet.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void loadDataFromDatabase1() {
        try {
            String query = "SELECT * FROM Device";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String idDevice = resultSet.getString("ID_Device");
                String nameDevice = resultSet.getString("Name_Device");
                Integer priceDevice = resultSet.getInt("Price_Device");


                Devicetable.getItems().add(new Device(idDevice, nameDevice,priceDevice));
            }

            preparedStatement.close();
            resultSet.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    @FXML
    void AddService(ActionEvent event) {
        String idService = IdService.getText();
        String nameService = NameService.getText();
        int priceService = Integer.parseInt(PriceService.getText());

        Servicetable.getItems().add(new Service(idService, nameService, priceService));
        insertServiceIntoDatabase(idService, nameService, priceService);
    }

    @FXML
    void EditService(ActionEvent event) {
        Service service = Servicetable.getSelectionModel().getSelectedItem();
        if (service != null) {
            String idService = IdService.getText();
            String nameService = NameService.getText();
            int priceService = Integer.parseInt(PriceService.getText());

            service.setID_Service(idService);
            service.setName_Service(nameService);
            service.setPrice_Service(priceService);

            updateServiceInDatabase(idService, nameService, priceService, service.getID_Service());
            Servicetable.refresh();
        }
    }

    @FXML
    void RemoveService(ActionEvent event) {
        ObservableList<Service> selectedItems = Servicetable.getSelectionModel().getSelectedItems();
        if (selectedItems != null) {
            for (Service service : selectedItems) {
                deleteServiceFromDatabase(service.getID_Service());
            }
            Servicetable.getItems().removeAll(selectedItems);
        }
    }

    private void insertServiceIntoDatabase(String idService, String nameService, int priceService) {
        try {
            String query = "INSERT INTO Service (ID_Service, Name_Service, Price_Service) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, idService);
            preparedStatement.setString(2, nameService);
            preparedStatement.setInt(3, priceService);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void updateServiceInDatabase(String idService, String nameService, int priceService, String id) {
        try {
            String query = "UPDATE Service SET ID_Service=?, Name_Service=?, Price_Service=? WHERE ID_Service=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, idService);
            preparedStatement.setString(2, nameService);
            preparedStatement.setInt(3, priceService);
            preparedStatement.setString(4, id);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void deleteServiceFromDatabase(String idService) {
        try {
            String query = "DELETE FROM Service WHERE ID_Service=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, idService);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    @FXML
    void AddDevice(ActionEvent event) {
        String idDevice = IdDevice.getText();
        String nameDevice = NameDevice.getText();
        int priceDevice = Integer.parseInt(PriceDevice.getText());

        Devicetable.getItems().add(new Device(idDevice, nameDevice, priceDevice));
        insertDeviceIntoDatabase(idDevice, nameDevice, priceDevice);
    }

    @FXML
    void EditDevice(ActionEvent event) {
        Device device = Devicetable.getSelectionModel().getSelectedItem();
        if (device != null) {
            String idDevice = IdDevice.getText();
            String nameDevice = NameDevice.getText();
            int priceDevice = Integer.parseInt(PriceDevice.getText());

            device.setID_Device(idDevice);
            device.setName_Device(nameDevice);
            device.setPrice_Device(priceDevice);

            updateDeviceInDatabase(idDevice, nameDevice, priceDevice, device.getID_Device());
            Devicetable.refresh();
        }
    }

    @FXML
    void RemoveDevice(ActionEvent event) {
        ObservableList<Device> selectedItems = Devicetable.getSelectionModel().getSelectedItems();
        if (selectedItems != null) {
            for (Device device : selectedItems) {
                deleteDeviceFromDatabase(device.getID_Device());
            }
            Devicetable.getItems().removeAll(selectedItems);
        }
    }

    private void insertDeviceIntoDatabase(String idDevice, String nameDevice, int priceDevice) {
        try {
            String query = "INSERT INTO Device (ID_Device, Name_Device, Price_Device) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, idDevice);
            preparedStatement.setString(2, nameDevice);
            preparedStatement.setInt(3, priceDevice);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void updateDeviceInDatabase(String idDevice, String nameDevice, int priceDevice, String id) {
        try {
            String query = "UPDATE Device SET ID_Device=?, Name_Device=?, Price_Device=? WHERE ID_Device=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, idDevice);
            preparedStatement.setString(2, nameDevice);
            preparedStatement.setInt(3, priceDevice);
            preparedStatement.setString(4, id);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void deleteDeviceFromDatabase(String idDevice) {
        try {
            String query = "DELETE FROM Device WHERE ID_Device=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, idDevice);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
