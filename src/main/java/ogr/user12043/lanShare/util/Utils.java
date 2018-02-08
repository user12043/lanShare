package ogr.user12043.lanShare.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by user12043 on 1/19/18
 * part of project lanShare
 */

public class Utils {
    private static DateFormat format = new SimpleDateFormat("(dd/MM/yyyy) HH:mm:ss");

    public static String getTimeAsString() {
        Date date = new Date();
        return format.format(date);
    }

    public static String buildHtml(String body) {

        return "<html>\n" +
                "<head>" +
                "<meta charset=\"utf-8\">" +
                "</head>" +
                "<body>" +
                body +
                "</body>" +
                "</html>\n";
    }
}
