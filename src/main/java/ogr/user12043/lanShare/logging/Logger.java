package ogr.user12043.lanShare.logging;

import ogr.user12043.lanShare.util.Properties;
import ogr.user12043.lanShare.util.Utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

/**
 * Created by user12043 on 1/19/18
 * part of project lanShare
 */

public class Logger {
    private static File logFile = new File(Properties.logFileLocation());
    private static Writer writer;
    private static String seperator = "\n=============================================================\n";

    private static void write(String value) {
        try {
            if (writer == null) {
                writer = new FileWriter(logFile, true);
            }

            writer.append(value);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void info(String value) {
        write(Utils.getTimeAsString() + "[INFO]: " + value + seperator);
    }

    public static void error(String value) {
        write(Utils.getTimeAsString() + "[ERROR]: " + value + seperator);
    }
}
