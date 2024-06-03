package com.example.qlnn1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import java.sql.*;

public class ControllSignup implements Initializable {
    @FXML
    private TextField IdUser;
    @FXML
    private TextField UserName;
    @FXML
    private TextField PassWords;
    @FXML
    private TextField Email;
    @FXML
    private TextField PhoneNumber;
    private Connection connection;

    @FXML
    private Button Login;
    @FXML
    private Button SignUp;
    @FXML
    private Button Cancel;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        connection = LUULY1.getConnection();

    }
    public void cancelExitOnAction(ActionEvent e) {
        Stage stage = (Stage) Cancel.getScene().getWindow();
        stage.close();
    }

    public void conectLogin(ActionEvent e) throws IOException {
        Stage stage = (Stage) Login.getScene().getWindow();
        FXMLLoader fxmlLoader  = new FXMLLoader(HelloApplication.class.getResource("login.fxml"));
        Scene Scene = new Scene(fxmlLoader.load(), 1530, 802);
        stage.setScene(Scene);
        stage.show();
    }
    @FXML
    public void handleSignUpButtonAction(ActionEvent event) {
        // Get data from text fields

        String idUser = IdUser.getText();
        String userName = UserName.getText();
        String passWords = PassWords.getText();
        String email = Email.getText();
        String phoneNumber = PhoneNumber.getText();

        insertIdUser(idUser,userName,passWords,email,phoneNumber);


        // Insert data into the database

        // You can optionally navigate to another view after adding the data
        // For example, go back to the Dashboard
        try {
            conectLogin(event);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void insertIdUser(String idUser, String userName, String passWords, String email, String phoneNumber) {
        try {
            String query = "INSERT INTO IdUsers (IdUser, UserName, PassWords, Email, PhoneNumber) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, idUser);
                preparedStatement.setString(2, userName);
                preparedStatement.setString(3, passWords);
                preparedStatement.setString(4, email);
                preparedStatement.setString(5, phoneNumber);


                preparedStatement.executeUpdate();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

}
