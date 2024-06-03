package com.example.qlnn1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Optional;
import java.util.ResourceBundle;

public class ControllRoom implements Initializable {
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
    private TableView<Room> Room;
    @FXML
    private TableColumn<Room, String> Room_ID;
    @FXML
    private TableColumn<Room, Integer> Room_Number;
    @FXML
    private TableColumn<Room, String> Room_Condition;
    @FXML
    private TableColumn<Room, String> Room_Type;
    @FXML
    private TableColumn<Room, String> ID_RoomType;
    @FXML
    private TableColumn<Room, String> ID_Device;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        connection = LUULY1.getConnection();
        Room_ID.setCellValueFactory(new PropertyValueFactory<>("Room_ID"));
        Room_Number.setCellValueFactory(new PropertyValueFactory<>("Room_Number"));
        Room_Condition.setCellValueFactory(new PropertyValueFactory<>("Room_Condition"));
        Room_Type.setCellValueFactory(new PropertyValueFactory<>("Room_Type"));
        ID_RoomType.setCellValueFactory(new PropertyValueFactory<>("ID_RoomType"));
        ID_Device.setCellValueFactory(new PropertyValueFactory<>("ID_Device"));

        loadDataFromDatabase();
    }

    private void loadDataFromDatabase() {
        try {
            String query = "SELECT * FROM Room";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String roomId = resultSet.getString("Room_ID");
                Integer roomNumber = resultSet.getInt("Room_Number");
                String roomCondition = resultSet.getString("Room_Condition");
                String roomType = resultSet.getString("Room_Type");
                String idRoomType = resultSet.getString("ID_RoomType");
                String idDevice = resultSet.getString("ID_Device");

                Room.getItems().add(new Room(roomId, roomNumber, roomCondition, roomType, idRoomType, idDevice));
            }

            preparedStatement.close();
            resultSet.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @FXML
    private void handleDeleteButton(ActionEvent event) {
        Room selectedItem = Room.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            String roomId = selectedItem.getRoom_ID();
            deleteRoomData(roomId);
            Room.getItems().remove(selectedItem);
        }
    }

    private void deleteRoomData(String roomId) {
        try {
            String query = "DELETE FROM Room WHERE Room_ID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, roomId);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleEditButton(ActionEvent event) {
        Room selectedItem = Room.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            showEditDialog(selectedItem);
        }
    }

    private void showEditDialog(Room item) {
        Dialog<Room> dialog = new Dialog<>();
        dialog.setTitle("Edit Room Information");
        dialog.setHeaderText("Edit Room Information");

        ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 150, 10, 10));

         TextField roomNumberField = new TextField(String.valueOf(item.getRoom_Number()));
        TextField roomConditionField = new TextField(item.getRoom_Condition());
        TextField roomTypeField = new TextField(item.getRoom_Type());
        TextField idRoomTypeField = new TextField(item.getID_RoomType());
        TextField idDeviceField = new TextField(item.getID_Device());

        gridPane.add(new Label("Room Number:"), 0, 0);
        gridPane.add(roomNumberField, 1, 0);
        gridPane.add(new Label("Room Condition:"), 0, 1);
        gridPane.add(roomConditionField, 1, 1);
        gridPane.add(new Label("Room Type:"), 0, 2);
        gridPane.add(roomTypeField, 1, 2);
        gridPane.add(new Label("ID Room Type:"), 0, 3);
        gridPane.add(idRoomTypeField, 1, 3);
        gridPane.add(new Label("ID Device:"), 0, 4);
        gridPane.add(idDeviceField, 1, 4);

        dialog.getDialogPane().setContent(gridPane);

        Node saveButton = dialog.getDialogPane().lookupButton(saveButtonType);
        saveButton.setDisable(true);

        roomNumberField.textProperty().addListener((observable, oldValue, newValue) -> {
            saveButton.setDisable(newValue.trim().isEmpty() || roomConditionField.getText().trim().isEmpty() ||
                    roomTypeField.getText().trim().isEmpty() || idRoomTypeField.getText().trim().isEmpty() ||
                    idDeviceField.getText().trim().isEmpty());
        });
        roomConditionField.textProperty().addListener((observable, oldValue, newValue) -> {
            saveButton.setDisable(newValue.trim().isEmpty() || roomNumberField.getText().trim().isEmpty() ||
                    roomTypeField.getText().trim().isEmpty() || idRoomTypeField.getText().trim().isEmpty() ||
                    idDeviceField.getText().trim().isEmpty());
        });
        roomTypeField.textProperty().addListener((observable, oldValue, newValue) -> {
            saveButton.setDisable(newValue.trim().isEmpty() || roomNumberField.getText().trim().isEmpty() ||
                    roomConditionField.getText().trim().isEmpty() || idRoomTypeField.getText().trim().isEmpty() ||
                    idDeviceField.getText().trim().isEmpty());
        });
        idRoomTypeField.textProperty().addListener((observable, oldValue, newValue) -> {
            saveButton.setDisable(newValue.trim().isEmpty() || roomNumberField.getText().trim().isEmpty() ||
                    roomConditionField.getText().trim().isEmpty() || roomTypeField.getText().trim().isEmpty() ||
                    idDeviceField.getText().trim().isEmpty());
        });
        idDeviceField.textProperty().addListener((observable, oldValue, newValue) -> {
            saveButton.setDisable(newValue.trim().isEmpty() || roomNumberField.getText().trim().isEmpty() ||
                    roomConditionField.getText().trim().isEmpty() || roomTypeField.getText().trim().isEmpty() ||
                    idRoomTypeField.getText().trim().isEmpty());
        });

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                item.setRoom_Number(Integer.parseInt(roomNumberField.getText()));
                item.setRoom_Condition(roomConditionField.getText());
                item.setRoom_Type(roomTypeField.getText());
                item.setID_RoomType(idRoomTypeField.getText());
                item.setID_Device(idDeviceField.getText());
                updateRoomData(item);
                return item;
            }
            return null;
        });

        Optional<Room> result = dialog.showAndWait();
        result.ifPresentOrElse(
                room -> Room.refresh(),
                () -> System.out.println("No input")
        );
    }

    private void updateRoomData(Room item) {
        try {
            String query = "UPDATE Room SET Room_Number=?, Room_Condition=?, Room_Type=?, ID_RoomType=?, ID_Device=? WHERE Room_ID=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, item.getRoom_Number());
            preparedStatement.setString(2, item.getRoom_Condition());
            preparedStatement.setString(3, item.getRoom_Type());
            preparedStatement.setString(4, item.getID_RoomType());
            preparedStatement.setString(5, item.getID_Device());
            preparedStatement.setString(6, item.getRoom_ID());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void conectBillButtonOnAction(ActionEvent e) throws IOException {
        Stage stage = (Stage) Chosen.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1530, 802);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    private void handleAddButton(ActionEvent event) {
        Dialog<Room> dialog = new Dialog<>();
        dialog.setTitle("Add New Room");
        dialog.setHeaderText("Enter Room Details");

        ButtonType addButtonType = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 150, 10, 10));

        TextField roomIdField = new TextField();
        roomIdField.setPromptText("Room ID");
        TextField roomNumberField = new TextField();
        roomNumberField.setPromptText("Room Number");
        TextField roomConditionField = new TextField();
        roomConditionField.setPromptText("Room Condition");
        TextField roomTypeField = new TextField();
        roomTypeField.setPromptText("Room Type");
        TextField idRoomTypeField = new TextField();
        idRoomTypeField.setPromptText("ID Room Type");
        TextField idDeviceField = new TextField();
        idDeviceField.setPromptText("ID Device");

        gridPane.add(new Label("Room ID:"), 0, 0);
        gridPane.add(roomIdField, 1, 0);
        gridPane.add(new Label("Room Number:"), 0, 1);
        gridPane.add(roomNumberField, 1, 1);
        gridPane.add(new Label("Room Condition:"), 0, 2);
        gridPane.add(roomConditionField, 1, 2);
        gridPane.add(new Label("Room Type:"), 0, 3);
        gridPane.add(roomTypeField, 1, 3);
        gridPane.add(new Label("ID Room Type:"), 0, 4);
        gridPane.add(idRoomTypeField, 1, 4);
        gridPane.add(new Label("ID Device:"), 0, 5);
        gridPane.add(idDeviceField, 1, 5);

        dialog.getDialogPane().setContent(gridPane);

        Node addButton = dialog.getDialogPane().lookupButton(addButtonType);
        addButton.setDisable(true);

        roomIdField.textProperty().addListener((observable, oldValue, newValue) ->
                validateInput(roomIdField, roomNumberField, roomConditionField, roomTypeField, idRoomTypeField, idDeviceField, addButton));
        roomNumberField.textProperty().addListener((observable, oldValue, newValue) ->
                validateInput(roomIdField, roomNumberField, roomConditionField, roomTypeField, idRoomTypeField, idDeviceField, addButton));
        roomConditionField.textProperty().addListener((observable, oldValue, newValue) ->
                validateInput(roomIdField, roomNumberField, roomConditionField, roomTypeField, idRoomTypeField, idDeviceField, addButton));
        roomTypeField.textProperty().addListener((observable, oldValue, newValue) ->
                validateInput(roomIdField, roomNumberField, roomConditionField, roomTypeField, idRoomTypeField, idDeviceField, addButton));
        idRoomTypeField.textProperty().addListener((observable, oldValue, newValue) ->
                validateInput(roomIdField, roomNumberField, roomConditionField, roomTypeField, idRoomTypeField, idDeviceField, addButton));
        idDeviceField.textProperty().addListener((observable, oldValue, newValue) ->
                validateInput(roomIdField, roomNumberField, roomConditionField, roomTypeField, idRoomTypeField, idDeviceField, addButton));

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButtonType) {
                return new Room(
                        roomIdField.getText(),
                        Integer.parseInt(roomNumberField.getText()),
                        roomConditionField.getText(),
                        roomTypeField.getText(),
                        idRoomTypeField.getText(),
                        idDeviceField.getText()
                );
            }
            return null;
        });

        Optional<Room> result = dialog.showAndWait();
        result.ifPresent(room -> {
            addRoomToDatabase(room);
            Room.getItems().add(room);
        });
    }

    private void validateInput(TextField roomIdField, TextField roomNumberField, TextField roomConditionField, TextField roomTypeField, TextField idRoomTypeField, TextField idDeviceField, Node addButton) {
        addButton.setDisable(
                roomIdField.getText().trim().isEmpty() ||
                        roomNumberField.getText().trim().isEmpty() ||
                        roomConditionField.getText().trim().isEmpty() ||
                        roomTypeField.getText().trim().isEmpty() ||
                        idRoomTypeField.getText().trim().isEmpty() ||
                        idDeviceField.getText().trim().isEmpty()
        );
    }
    private void addRoomToDatabase(Room room) {
        try {
            String query = "INSERT INTO Room (Room_ID, Room_Number, Room_Condition, Room_Type, ID_RoomType, ID_Device) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, room.getRoom_ID());
            preparedStatement.setInt(2, room.getRoom_Number());
            preparedStatement.setString(3, room.getRoom_Condition());
            preparedStatement.setString(4, room.getRoom_Type());
            preparedStatement.setString(5, room.getID_RoomType());
            preparedStatement.setString(6, room.getID_Device());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
