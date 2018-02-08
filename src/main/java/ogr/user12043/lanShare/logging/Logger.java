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

    // To use one instance of writer
    private static Writer getWriter() {
        try {
            if (writer == null) {
                writer = new FileWriter(logFile, true);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return writer;
    }

    // Writing given string value to log file
    private static void write(String value) {
        try {
            getWriter().append(value);
            getWriter().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Logging functions
    public static void info(String value) {
        write(Utils.getTimeAsString() + "[INFO]: " + value + seperator);
    }

    public static void error(String value) {
        write(Utils.getTimeAsString() + "[ERROR]: " + value + seperator);
    }

    // Same functions to can specify the target file
    private static void write(String value, File targetFile) {
        try {
            FileWriter newWriter = new FileWriter(targetFile, true);
            newWriter.append(value);
            newWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void info(String value, File targetFile) {
        write((Utils.getTimeAsString() + "[INFO]: " + value + seperator), targetFile);
    }

    public static void error(String value, File targetFile) {
        write((Utils.getTimeAsString() + "[ERROR]: " + value + seperator), targetFile);
    }
}
