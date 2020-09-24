package sample;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.Scene;

import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class DynamicTable extends Application {

    String path = null;
    DynamicTable(String path){
        this.path = "file:///"+path;
    }


    @Override
    public void start(Stage primaryStage) {
        final BorderPane root = new BorderPane();
        final TableView<ObservableList<StringProperty>> table = new TableView<>();
        root.setCenter(table);
        Scene scene = new Scene(root, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();

        populateTable(table, path, true);
    }

    private void populateTable(
            final TableView<ObservableList<StringProperty>> table,
            final String urlSpec, final boolean hasHeader) {
        table.getItems().clear();
        table.getColumns().clear();
        table.setPlaceholder(new Label("Loading..."));
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                BufferedReader in = getReaderFromUrl(urlSpec);
                // Header line
                if (hasHeader) {
                    final String headerLine = in.readLine();
                    final String[] headerValues = headerLine.split("\t");
                    Platform.runLater(() -> {
                        for (int column = 0; column < headerValues.length; column++) {
                            table.getColumns().add(
                                    createColumn(column, headerValues[column]));
                        }
                    });
                }

                // Data:

                String dataLine;
                while ((dataLine = in.readLine()) != null) {
                    final String[] dataValues = dataLine.split("\t");
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            // Add additional columns if necessary:
                            for (int columnIndex = table.getColumns().size(); columnIndex < dataValues.length; columnIndex++) {
                                table.getColumns().add(createColumn(columnIndex, ""));
                            }
                            // Add data to table:
                            ObservableList<StringProperty> data = FXCollections
                                    .observableArrayList();
                            for (String value : dataValues) {
                                data.add(new SimpleStringProperty(value));
                            }
                            table.getItems().add(data);
                        }
                    });
                }
                return null;
            }
        };
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }

    private TableColumn<ObservableList<StringProperty>, String> createColumn(
            final int columnIndex, String columnTitle) {
        TableColumn<ObservableList<StringProperty>, String> column = new TableColumn<>();
        String title;
        if (columnTitle == null || columnTitle.trim().length() == 0) {
            title = "Column " + (columnIndex + 1);
        } else {
            title = columnTitle;
        }
        column.setText(title);
        column
                .setCellValueFactory(cellDataFeatures -> {
                    ObservableList<StringProperty> values = cellDataFeatures.getValue();
                    if (columnIndex >= values.size()) {
                        return new SimpleStringProperty("");
                    } else {
                        return cellDataFeatures.getValue().get(columnIndex);
                    }
                });
        return column;
    }

    private BufferedReader getReaderFromUrl(String urlSpec) throws Exception {
        URL url = new URL(urlSpec);
        URLConnection connection = url.openConnection();
        InputStream in = connection.getInputStream();
        return new BufferedReader(new InputStreamReader(in));
    }
}
