package ogr.user12043.lanShare.util;

import java.io.File;

/**
 * Created by user12043 on 1/19/18
 * part of project lanShare
 */

public class Properties {
    // User's home folder
    private static String userHome = System.getProperty("user.home");

    public static String appConfigDir() {
        return userHome + File.separator + ".lanShare";
    }

    // App's files location
    public static String appFilesLocation() {
        return appConfigDir() + File.separator + "files";
    }

    // Logging parameters
    public static String logFileLocation() {
        return appConfigDir() + File.separator + "lanShare.log";
    }
}
