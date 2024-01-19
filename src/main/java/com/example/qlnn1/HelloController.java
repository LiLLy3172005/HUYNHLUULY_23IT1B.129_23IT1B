package com.example.qlnn1;
import java.io.IOException;
import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.RowIdLifetime;


public class HelloController {
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordPasswordField;
    @FXML
    private Button cancelButton;
    @FXML
    public Label loginMessageLabel;

    private Connection connection;

    public void loginButtonOnAction(ActionEvent e) {

        if (usernameTextField.getText().isBlank() == false && passwordPasswordField.getText().isBlank() == false) {
            loginMessageLabel.setText("You try to login");
            validateLogin();
        } else {
            try {
                loginMessageLabel.setText("Please enter username and password");
                connection = LUULY1.getConnection();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public void cancelButtonOnAction(ActionEvent e) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }


    private void validateLogin() {
        try {
            connection = LUULY1.getConnection();

            // Prepare the SQL statement
            String query = "SELECT count(*) FROM IdUser WHERE Username = ? AND Password = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, usernameTextField.getText());
                preparedStatement.setString(2, passwordPasswordField.getText());

                // Execute the query
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    // Check if the user exists
                    if (resultSet.next() && resultSet.getInt(1) == 2) {
                        // User with the provided username and password exists
                        loginMessageLabel.setText("Login successful");
                        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
                 //    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/qlnn1/hello-view.fxml"));
                     //  Parent root = fxmlLoader.load();
                       // fxmlLoader.setController(new ControllMain());
                        Scene newScene = new Scene(fxmlLoader.load(), 1268, 802);

                        // Lấy stage hiện tại và thiết lập cảnh mới
                        Stage currentStage = (Stage) loginMessageLabel.getScene().getWindow();
                        currentStage.setScene(newScene);
                        // Additional logic for a successful login can be added here
                    } else {
                        // No user with the provided username and password
                        loginMessageLabel.setText("Invalid username or password");
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}




