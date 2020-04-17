package ogr.user12043.lanShare.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created on 4.04.2020 - 23:50
 * part of project lanShare
 *
 * @author user12043
 */
public class Constants {
    public static final String TEMPORARY_FILE_LOCATION = "./tmp";
    public static final long MAX_REQUEST_SIZE = 4294967296L;
    public static final long MAX_FILE_SIZE = 4294967296L;
    public static final int FILE_SIZE_THRESHOLD = 104857600;
    public static final int UPLOAD_BUFFER_SIZE = 4096;
    public static final int DOWNLOAD_BUFFER_SIZE = 8192;
    public static final DateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    public static final String LOG_SEPARATOR = "\n=============================================================\n";
}
