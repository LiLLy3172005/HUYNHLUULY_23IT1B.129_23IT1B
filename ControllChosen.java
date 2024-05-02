package com.example.qlnn1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;

public class ControllChosen {

    @FXML
    private Button Room;
    @FXML
    private Button Employee;
    private Connection connection;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        connection = LUULY1.getConnection();

    }
    public void conectHome(ActionEvent e) throws IOException {
        Stage stage = (Stage)this.Room.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene Scene = new Scene((Parent)fxmlLoader.load(), 1530.0, 802.0);
        stage.setScene(Scene);
        stage.show();
    }


    public void conectEmployee(ActionEvent e) throws IOException {
        Stage stage = (Stage)this.Employee.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Chosen.fxml"));
        Scene Scene = new Scene((Parent)fxmlLoader.load(), 1530.0, 802.0);
        stage.setScene(Scene);
        stage.show();
    }

}
