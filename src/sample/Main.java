package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;

public class Main extends Application {
    TextField dataSetPathTF;
    Button btn, clearGraphBtn;
    Pane graphHolder;
    SplitMenuButton countriesSplitBtn;
    LineChart<String, Number> lineChart;

    FileChooser fileChooser = new FileChooser();

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("mainWindow.fxml"));
        Scene scene = new Scene(root);
        initializeComponents(root);

        drawEmptyGraph();

        countriesSplitBtn.getItems().clear();

        btn.setOnAction(event -> {
            File file = fileChooser.showOpenDialog(primaryStage);
            if (file != null) {
                dataSetPathTF.setText(file.getPath());
                ArrayList<String> countries = Apis.getCountries(file.getPath());
                for(int i=1; i< countries.size(); i++){
                    MenuItem item = new MenuItem(countries.get(i));
                    int finalI = i;
                    item.setOnAction(e ->{
                                Apis.runMapReduce(finalI);
                                try {
                                    Thread.sleep(4000);
                                } catch (InterruptedException interruptedException) {
                                    interruptedException.printStackTrace();
                                }

                                displayCountryChart(countries.get(finalI));
                    }
                    );
                    countriesSplitBtn.getItems().add(item);
                }
            }
        });

        clearGraphBtn.setOnAction(event ->{
                    lineChart.getData().clear();
        }
        );

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void initializeComponents(Parent root){
        dataSetPathTF = (TextField) root.lookup("#dataSetPathTF");
        btn = (Button) root.lookup("#openDataSet");
        clearGraphBtn = (Button) root.lookup("#clearGraphBtn");
        graphHolder = (Pane) root.lookup("#graphHolder");
        countriesSplitBtn = (SplitMenuButton) root.lookup("#countriesSplitBtn");
    }

    private void drawEmptyGraph(){
        final CategoryAxis xAxis = new CategoryAxis(); // we are gonna plot against time
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setAnimated(false); // axis animations are removed
        xAxis.setLabel("Days");

        yAxis.setAnimated(false); // axis animations are removed
        yAxis.setLabel("Cases Number");

        //creating the line chart with two axis created above
        lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Realtime JavaFX Charts");
        lineChart.setAnimated(false); // disable animations

        lineChart.setPrefSize(graphHolder.getPrefWidth(),  graphHolder.getPrefHeight());
        graphHolder.getChildren().clear();
        graphHolder.getChildren().add(lineChart);
    }
    private void displayCountryChart(String country){

        ArrayList<String> lines = Apis.fileToLines("src/hadoop/output_dir/part-00000");
        XYChart.Series<String, Number> points = Apis.linesToPoints(lines);
        points.setName(country);

        // add series to chart
        lineChart.getData().add(points);
    }
}
