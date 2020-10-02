package sample;

import java.io.File;

public interface Config {
    String PROJECT_DIRECTORY = new File("").getAbsolutePath();
    String JAVA_HOME = "/opt/Programing/Libs/openjdk-14.0.2";
    String MAP_REDUCE_SCRIPT = "./lunch.sh";
    String HADOP_CLASS_LOCATION  = PROJECT_DIRECTORY + "/src/hadoop";
}
