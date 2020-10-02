package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Hadop extends Application {
    Button openBtn;
    TextArea commandResultTA;

    @Override
    public void start(Stage primaryStage) throws Exception{
        final FileChooser fileChooser = new FileChooser();
        Parent root = FXMLLoader.load(getClass().getResource("hadopWindow.fxml"));

        initializeComponents(root);

        openBtn.setOnAction(event -> {
            File file = fileChooser.showOpenDialog(primaryStage);
            if (file != null) {
                System.out.println(file.toString());
            }
        });
        String command = "./lunch.sh";
        executeCommand(command);

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {

        launch(args);
    }

    private void initializeComponents(Parent root){
        openBtn = (Button) root.lookup("#openBtn");
        commandResultTA = (TextArea) root.lookup("#commandResultTA");

    }

    private void executeCommand(String command){
        ProcessBuilder pb = new ProcessBuilder("./lunch.sh");
        pb.redirectErrorStream(true);
        pb.redirectOutput(ProcessBuilder.Redirect.INHERIT);
        pb.directory(new File("/mnt/Bibliotheque/Programing/Projects/Java/BigDataVisualisation/src/hadoop"));
        pb.environment().put("JAVA_HOME", "/opt/Programing/Libs/openjdk-14.0.2");
        Map<String, String> env = System.getenv();
        for (String envName : env.keySet()) {
            pb.environment().put(envName, env.get(envName));
        }

        Process p = null;
        try {
            p = pb.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = null;
            while ((line = reader.readLine()) != null)
            {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
