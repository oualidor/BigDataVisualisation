package sample;

import javafx.scene.chart.XYChart;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;

import java.io.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.StringTokenizer;

public interface Apis {


    public static ArrayList<String> fileToLines(String path) {
        ArrayList<String> result = new ArrayList<>();
        BufferedReader br = null;
        String line = "";

        try{
            br = new BufferedReader(new FileReader(path));
            while ((line = br.readLine()) != null) {
                result.add(line);
            }
            br.close();
            return result;
        }catch (FileNotFoundException e) {
            e.printStackTrace();
            return result;
        }catch (IOException e) {
            e.printStackTrace();
            return result;
        }
    }

    public static XYChart.Series<String, Number> linesToPoints(ArrayList<String> lines) {
        String SplitBy = "\t";
        int j=0;
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        for (String line: lines) {
            try {
                if( j != 0){
                    String[] lineArray = line.split(SplitBy);
                    series.getData().add(new XYChart.Data<>(lineArray[0], Double.valueOf(lineArray[1])));
                }
            }catch (NumberFormatException e){
                System.out.println("////////////////////////////////////////////////");
                System.out.println("Skipped");
            }

            j++;
        }
        return series;
    }

    static ArrayList<String>  getCountries (String path){
        ArrayList<String> result = new ArrayList<>();
        BufferedReader br = null;


        try{
            br = new BufferedReader(new FileReader(path));
            String line = br.readLine();
            String[] countries = line.split(",");
            for(int i=1; i< countries.length; i++){
                result.add(countries[i]);
            }
            return result;
        }catch (FileNotFoundException e) {
            e.printStackTrace();
            return result;
        }catch (IOException e) {
            e.printStackTrace();
            return result;
        }

    }

    static void runMapReduce(int index){
        String[] c = {"./lunch.sh", String.valueOf(index)};
        ProcessBuilder pb = new ProcessBuilder(c);
        pb.redirectErrorStream(true);
        pb.redirectOutput(ProcessBuilder.Redirect.INHERIT);
        pb.directory(new File(Config.HADOP_CLASS_LOCATION));
        pb.environment().put("JAVA_HOME", Config.JAVA_HOME);
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
