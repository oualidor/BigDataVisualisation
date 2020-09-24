package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("mainWindow.fxml"));
        Scene scene = new Scene(root, 1000, 600);
        final FileChooser fileChooser = new FileChooser();

        //Graph
        //defining the axes
        final CategoryAxis xAxis = new CategoryAxis(); // we are gonna plot against time
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Time/s");
        xAxis.setAnimated(false); // axis animations are removed
        yAxis.setLabel("Value");
        yAxis.setAnimated(false); // axis animations are removed

        //creating the line chart with two axis created above
        final LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Realtime JavaFX Charts");
        lineChart.setAnimated(false); // disable animations


        //defining a series to display data
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Data Series");

        // add series to chart
        lineChart.getData().add(series);

        //Initilizz graphe holder



        //

        Button btn = (Button) root.lookup("#openDataSet");
        btn.setOnAction(event -> {
            File file = fileChooser.showOpenDialog(primaryStage);
            if (file != null) {
                DynamicTable dataSetpreview = new DynamicTable(file.toString());
                Stage previewStage = new Stage();
                dataSetpreview.start(previewStage);
                try {
                    BufferedReader csvReader = new BufferedReader(new FileReader(file.toString()));
                    String row;
                    while ((row = csvReader.readLine()) != null) {
                        String[] data = row.split(",");
                    }
                    csvReader.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });



        primaryStage.setScene(scene);

        Pane graphHolder = (Pane) root.lookup("#graphHolder");
        lineChart.setPrefSize(graphHolder.getPrefWidth(),  graphHolder.getPrefHeight());
        graphHolder.getChildren().add(lineChart);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
