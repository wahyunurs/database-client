package org.example;

import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class LoginPage {
    private VBox view;
    private TextField hostField;
    private TextField usernameField;
    private PasswordField passwordField;
    private TextField portField;
    private Button connectButton;

    public LoginPage(Stage stage) {
        view = new VBox(10);
        hostField = new TextField();
        hostField.setPromptText("Host");

        usernameField = new TextField();
        usernameField.setPromptText("Username");

        passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        portField = new TextField();
        portField.setPromptText("Port");

        connectButton = new Button("Connect");
        connectButton.setOnAction(e -> connectToDatabase(stage));

        view.getChildren().addAll(hostField, usernameField, passwordField, portField, connectButton);
    }

    public VBox getView() {
        return view;
    }

    private void connectToDatabase(Stage stage) {
        String host = hostField.getText();
        String username = usernameField.getText();
        String password = passwordField.getText();
        String port = portField.getText();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            showError("MySQL JDBC Driver not found.");
            e.printStackTrace();
            return;
        }

        String url = "jdbc:mysql://" + host + ":" + port + "/earlybirddb";

        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            MainPage mainPage = new MainPage(stage, connection);
            stage.setScene(new Scene(mainPage.getView(), 800, 600));
        } catch (SQLException e) {
            showError("Connection failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.show();
    }
}
