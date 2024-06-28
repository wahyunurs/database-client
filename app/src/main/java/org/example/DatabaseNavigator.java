package org.example;

import javafx.scene.control.TreeView;
import javafx.scene.input.MouseButton;
import javafx.scene.control.TreeItem;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseNavigator {
    private BorderPane view;
    private TreeView<String> databaseTree;
    private Connection connection;
    private QueryPanel queryPanel;
    private TreeItem lastTable;

    public DatabaseNavigator(Connection connection) {

        this.connection = connection;
        view = new BorderPane();
        databaseTree = new TreeView<>();
        databaseTree.setOnMouseClicked((event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
                TreeItem<String> selectedItem = databaseTree.getSelectionModel().getSelectedItem();
                // header clicked
                if (getDepth(selectedItem) == 0) {
                    loadDatabases();
                }
                // database clicked
                if (getDepth(selectedItem) == 1) {
                    if(this.lastTable != null){
                        this.lastTable.getChildren().clear();
                    }
                    loadTables(selectedItem, selectedItem.getValue());
                    this.lastTable = selectedItem;
                }
                // table clicked
                if (getDepth(selectedItem) == 2) {
                    queryPanel
                            .setQuery("SELECT * FROM " + selectedItem.getParent().getValue() + "."
                                    + selectedItem.getValue() + " LIMIT 100");
                    queryPanel.executeQuery();
                }
            }
        });
        view.setCenter(databaseTree);
        loadDatabases();

    }

    private int getDepth(TreeItem<String> item) {
        int depth = 0;
        TreeItem<String> parent = item;
        while ((parent = parent.getParent()) != null) {
            depth++;
        }
        return depth;
    }

    public BorderPane getView() {
        return view;
    }

    public void setQueryPanel(QueryPanel queryPanel) {
        this.queryPanel = queryPanel;
    }

    private void loadDatabases() {
        try {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet databases = metaData.getCatalogs();

            TreeItem<String> rootItem = new TreeItem<>("Databases");
            databaseTree.setRoot(rootItem);

            while (databases.next()) {
                String dbName = databases.getString(1);
                TreeItem<String> dbItem = new TreeItem<>(dbName);
                rootItem.getChildren().add(dbItem);
            }
            rootItem.setExpanded(true);
        } catch (SQLException e) {
            showError("Failed to load databases: " + e.getMessage());
        }
    }

    private void loadTables(TreeItem<String> dbItem, String dbName) {
        try {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet tables = metaData.getTables(dbName, null, "%", new String[] { "TABLE" });

            dbItem.getChildren().clear();
            while (tables.next()) {
                String tableName = tables.getString(3);
                TreeItem<String> tableItem = new TreeItem<>(tableName);
                dbItem.getChildren().add(tableItem);

                tableItem.addEventHandler(TreeItem.branchExpandedEvent(), ev -> {
                    queryPanel.setQuery("SELECT * FROM " + dbName + "." + tableName + " LIMIT 100");
                    queryPanel.executeQuery();
                });
            }
            dbItem.setExpanded(true);
        } catch (SQLException ex) {
            showError("Failed to load tables: " + ex.getMessage());
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.show();
    }
}
