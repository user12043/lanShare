package ogr.user12043.lanShare.util;

import java.io.File;

/**
 * Created by user12043 on 1/19/18
 * part of project lanShare
 */

public class Properties {
    private static String userHome = System.getProperty("user.home");

    public static String appConfigDir() {
        String path = userHome + "/.lanShare";

        // Checking directory
        File configDir = new File(path);
        if (!configDir.exists()) {
            if (!configDir.mkdir()) {
                // If cannot crate the directory, using userHome instead.
                System.err.println("Cannot create directory in user home. Files will be in home directory instead!");
                return userHome;
            }
        }

        return path;
    }

    // App's files location
    public static String appFilesLocation() {
        return appConfigDir() + "/files";
    }

    // Logging parameters
    public static String logFileLocation() {
        return appConfigDir() + "/lanShareLog.log";
    }
}
