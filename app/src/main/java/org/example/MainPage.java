package org.example;

import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.sql.Connection;

public class MainPage {
    private BorderPane view;

    public MainPage(Stage stage, Connection connection) {
        view = new BorderPane();

        DatabaseNavigator databaseNavigator = new DatabaseNavigator(connection);
        QueryPanel queryPanel = new QueryPanel(connection);
        ResultPanel resultPanel = new ResultPanel();

        queryPanel.setResultPanel(resultPanel);
        databaseNavigator.setQueryPanel(queryPanel);

        view.setLeft(databaseNavigator.getView());
        view.setCenter(queryPanel.getView());
        view.setBottom(resultPanel.getView());
    }

    public BorderPane getView() {
        return view;
    }
}
