package sample;

import javafx.scene.chart.XYChart;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
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
            return result;
        }catch (FileNotFoundException e) {
            e.printStackTrace();
            return result;
        }catch (IOException e) {
            e.printStackTrace();
            return result;
        }
    }

    public static XYChart.Series<String, Number> prepareLines(ArrayList<String> lines) {
        String cvsSplitBy = ",";
        int j=0;
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        for (String line: lines) {
            try {
                if( j != 0){
                    String[] lineArray = line.split(cvsSplitBy);
                    double total = 0;
                    for(int i=4; i< lineArray.length; i++){
                        total = total + Double.parseDouble(lineArray[i]);
                        System.out.println(lineArray[i]);
                    }
                    series.getData().add(new XYChart.Data<>(lineArray[1], total));
                }
            }catch (NumberFormatException e){
                System.out.println("////////////////////////////////////////////////");
                System.out.println("Skipped");
            }

            j++;
        }
        return series;
    }

    public static void getTotal(){

    }

    public static void map(LongWritable key, Text value,
                           OutputCollector<Text, IntWritable> output,
                           Reporter reporter) throws IOException {
        String line = value.toString();
        String lasttoken = null;
        StringTokenizer s = new StringTokenizer(line,",");
        String year = s.nextToken();

        while(s.hasMoreTokens()) {
            lasttoken = s.nextToken();
        }

        int avgprice = Integer.parseInt(lasttoken);
        output.collect(new Text(year), new IntWritable(avgprice));
    }
}
