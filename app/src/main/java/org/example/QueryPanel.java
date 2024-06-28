package org.example;

import javafx.scene.control.TextArea;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class QueryPanel {
    private BorderPane view;
    private TextArea queryArea;
    private Button executeButton;
    private Connection connection;
    private ResultPanel resultPanel;

    public QueryPanel(Connection connection) {
        this.connection = connection;
        view = new BorderPane();
        queryArea = new TextArea();
        executeButton = new Button("Execute");
        executeButton.setOnAction(e -> executeQuery());

        view.setCenter(queryArea);
        view.setBottom(executeButton);
    }

    public BorderPane getView() {
        return view;
    }

    public void setResultPanel(ResultPanel resultPanel) {
        this.resultPanel = resultPanel;
    }

    public void executeQuery() {
        String query = queryArea.getText();
        try {
            Statement statement = connection.createStatement();
            if (query.trim().toUpperCase().startsWith("SELECT")) {
                ResultSet resultSet = statement.executeQuery(query);
                resultPanel.displayResultSet(resultSet);
            } else {
                int rowsAffected = statement.executeUpdate(query);
                resultPanel.displayUpdateResult(rowsAffected);
            }
        } catch (SQLException e) {
            resultPanel.displayError(e.getMessage());
        }
    }

    public void setQuery(String query) {
        queryArea.setText(query);
    }
}
