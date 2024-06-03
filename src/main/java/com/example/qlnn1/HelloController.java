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
    private Button signup;
    @FXML
    public Label loginMessageLabel;

    private Connection connection;

    public void conectsignup(ActionEvent e) throws IOException {
        Stage stage = (Stage)this.signup.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("SignUp.fxml"));
        Scene Scene = new Scene((Parent)fxmlLoader.load(), 1085.0, 802.0);
        stage.setScene(Scene);
        stage.show();
    }

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
            String query = "SELECT count(*) FROM IdUsers WHERE UserName = ? AND PassWords = ?";
            System.out.println("Query: " + query);
            System.out.println("Username: " + usernameTextField.getText());
            System.out.println("Password: " + passwordPasswordField.getText());
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, usernameTextField.getText());
                preparedStatement.setString(2, passwordPasswordField.getText());

                // Execute the query
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    // Check if the user exists
                    if (resultSet.next() && resultSet.getInt(1) == 1) {
                        // User with the provided username and password exists
                        loginMessageLabel.setText("Login successful");
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Chosen.fxml"));
                        Parent root = fxmlLoader.load();
                        Scene newScene = new Scene(root, 1085, 802);

                        // Get the current stage and set the new scene
                        Stage currentStage = (Stage) loginMessageLabel.getScene().getWindow();
                        currentStage.setScene(newScene);
                    } else {
                        // No user with the provided username and password
                        loginMessageLabel.setText("Invalid username or password");
                    }
                }
            }
        } catch (SQLException | IOException ex) {
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




