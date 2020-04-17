package ogr.user12043.lanShare.util;

import ogr.user12043.lanShare.logging.Logger;

import java.io.File;

/**
 * Created by user12043 on 1/19/18
 * part of project lanShare
 */

public class Properties {
    public static String appConfigDir() {
        final String userHome = System.getProperty("user.home");
        String path = userHome + File.separator + ".lanShare";
        File dir = new File(path);
        if (!dir.exists() || !dir.isDirectory()) {
            if (!dir.mkdir()) {
                Logger.error("Cannot create default config directory");
                return userHome;
            }
        }
        return path;
    }

    // App's files location
    public static String appFilesLocation() {
        String path = appConfigDir() + File.separator + "files";
        File dir = new File(path);
        if (!dir.exists() || !dir.isDirectory()) {
            if (!dir.mkdir()) {
                Logger.error("Cannot create default files directory");
                String alPath = "files";
                File alDir = new File(alPath);
                if (!dir.exists()) {
                    if (dir.mkdir()) {
                        Logger.info("Alternate files directory created.");
                        return alPath;
                    }
                }
            }
        }
        return path;
    }

    // Logging parameters
    public static String logFileLocation() {
        return appConfigDir() + File.separator + "lanShare.log";
    }
}
