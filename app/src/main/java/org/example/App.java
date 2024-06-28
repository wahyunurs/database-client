package org.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage primaryStage) {
        LoginPage loginPage = new LoginPage(primaryStage);
        Scene scene = new Scene(loginPage.getView(), 400, 300);
        primaryStage.setTitle("Early Bird Database Client");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
