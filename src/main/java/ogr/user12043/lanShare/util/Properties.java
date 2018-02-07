package ogr.user12043.lanShare.util;

import java.io.File;

/**
 * Created by user12043 on 1/19/18
 * part of project lanShare
 */

public class Properties {
    private static String userHome = System.getProperty("user.home");

    public static String appConfigDir() {
        return userHome + "/.lanShare";
    }

    // Logging parameters
    public static String logFileLocation() {
        File configDir = new File(appConfigDir());
        if (!configDir.exists()) {
            if (!configDir.mkdir()) {
                System.err.println("Cannot create directory in user home. Log file will be in home directory instead!");
                return userHome + "/lanShareLog.log";
            }
        }
        return appConfigDir() + "/lanShareLog.log";
    }
}
