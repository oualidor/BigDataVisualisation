package sample;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class JavaFXCSVTableView extends Application {
    String path = null;
    JavaFXCSVTableView(String path){
        this.path = path;
    }

    private final TableView<Record> tableView = new TableView<>();

    private final ObservableList<Record> dataList = FXCollections.observableArrayList();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("DataSet preview");

        Group root = new Group();

        tableView.setItems(dataList);

        ArrayList<TableColumn> columnsList = new ArrayList<>();
        int rowsNumber = 9;
        for(int i=1; i<=rowsNumber; i++){
            TableColumn column = new TableColumn(("f"+i));
            column.setCellValueFactory(
                    new PropertyValueFactory<>(("f"+i)));
            tableView.getColumns().add(column);
            columnsList.add(column);
        }


        VBox vBox = new VBox();
        vBox.setSpacing(10);
        vBox.getChildren().add(tableView);
        root.getChildren().add(vBox);


        primaryStage.setScene(new Scene(root, vBox.getMaxWidth(), vBox.getHeight()));
        primaryStage.show();

        readCSV();
    }

    private void readCSV() {
        String FieldDelimiter = ",";

        BufferedReader br;

        try {
            br = new BufferedReader(new FileReader(this.path));

            String line;
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(FieldDelimiter, -1);

                for(int i=0; i<fields.length; i++){


                }
                Record record = new Record(fields[0], fields[1], fields[2],
                        fields[3], fields[4], fields[5], fields[6], fields[7], fields[8]);
                dataList.add(record);


            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(JavaFXCSVTableView.class.getName())
                    .log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(JavaFXCSVTableView.class.getName())
                    .log(Level.SEVERE, null, ex);
        }

    }


}