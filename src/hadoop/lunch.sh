#!/bin/bash

JAVA_HOME=/opt/Programing/Libs/openjdk-14.0.2
HADOOP_HOME=/opt/Programing/Libs/hadoop-3.1.4
rm -rf output_dir

rm -r units

$JAVA_HOME/bin/javac -classpath hadoop-core-1.2.1.jar -d units ProcessUnits.java

$JAVA_HOME/bin/jar -cvf units.jar -C units/ .

$HADOOP_HOME/bin/hadoop  jar units.jar hadoop.ProcessUnits input_dir output_dir $1