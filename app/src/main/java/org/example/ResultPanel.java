package org.example;

import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import javafx.util.Callback;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.beans.value.ObservableValue;
import javafx.beans.property.SimpleStringProperty;
import java.util.HashMap;
import java.util.Map;

public class ResultPanel {
    private BorderPane view;
    private TableView<Map<String, String>> tableView;

    public ResultPanel() {
        view = new BorderPane();
        tableView = new TableView<>();
        view.setCenter(tableView);
    }

    public BorderPane getView() {
        return view;
    }

    public void displayResultSet(ResultSet resultSet) {
        tableView.getItems().clear();
        tableView.getColumns().clear();

        try {
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            // Membuat kolom tabel berdasarkan metadata dari ResultSet
            for (int i = 1; i <= columnCount; i++) {
                String columnName = metaData.getColumnName(i);
                TableColumn<Map<String, String>, String> column = new TableColumn<>(columnName);

                // Custom CellValueFactory
                column.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
                        return new SimpleStringProperty(data.getValue().get(columnName));
                    }
                });

                tableView.getColumns().add(column);
            }

            // Mengisi data ke dalam TableView
            ObservableList<Map<String, String>> data = FXCollections.observableArrayList();
            while (resultSet.next()) {
                Map<String, String> row = new HashMap<>();
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnName(i);
                    row.put(columnName, resultSet.getString(i));
                }
                data.add(row);
            }
            tableView.setItems(data);

        } catch (SQLException e) {
            showError("Failed to display results: " + e.getMessage());
        }
    }

    public void displayUpdateResult(int rowsAffected) {
        tableView.getItems().clear();
        tableView.getColumns().clear();

        Label resultLabel = new Label(rowsAffected + " row(s) affected.");
        tableView.setPlaceholder(resultLabel);
    }

    public void displayError(String message) {
        tableView.getItems().clear();
        tableView.getColumns().clear();

        Label errorLabel = new Label("Error: " + message);
        tableView.setPlaceholder(errorLabel);
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.show();
    }
}
