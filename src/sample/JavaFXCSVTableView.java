package sample;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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

        TableColumn columnF1 = new TableColumn("f1");
        columnF1.setCellValueFactory(
                new PropertyValueFactory<>("f1"));

        TableColumn columnF2 = new TableColumn("f2");
        columnF2.setCellValueFactory(
                new PropertyValueFactory<>("f2"));

        TableColumn columnF3 = new TableColumn("f3");
        columnF3.setCellValueFactory(
                new PropertyValueFactory<>("f3"));

        TableColumn columnF4 = new TableColumn("f4");
        columnF4.setCellValueFactory(
                new PropertyValueFactory<>("f4"));

        TableColumn columnF5 = new TableColumn("f5");
        columnF5.setCellValueFactory(
                new PropertyValueFactory<>("f5"));

        TableColumn columnF6 = new TableColumn("f6");
        columnF6.setCellValueFactory(
                new PropertyValueFactory<>("f6"));

        TableColumn columnF7 = new TableColumn("f7");
        columnF7.setCellValueFactory(
                new PropertyValueFactory<>("f7"));

        TableColumn columnF8 = new TableColumn("f8");
        columnF8.setCellValueFactory(
                new PropertyValueFactory<>("f8"));

        TableColumn columnF9 = new TableColumn("f9");
        columnF9.setCellValueFactory(
                new PropertyValueFactory<>("f9"));

        tableView.setItems(dataList);
        tableView.getColumns().addAll(
                columnF1, columnF2, columnF3, columnF4, columnF5, columnF6, columnF7, columnF8, columnF9);

        VBox vBox = new VBox();
        vBox.setSpacing(10);
        vBox.getChildren().add(tableView);

        root.getChildren().add(vBox);

        primaryStage.setScene(new Scene(root, 720, 250));
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