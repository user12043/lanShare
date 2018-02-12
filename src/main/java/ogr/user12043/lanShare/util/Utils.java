package ogr.user12043.lanShare.util;

import java.io.UnsupportedEncodingException;
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

    // Build and html body and return
    public static String buildHtml(String body) {

        return "<html>\n" +
                "<head>" +
                "<meta charset=\"UTF-8\">" +
                "</head>" +
                "<body>" +
                body +
                "</body>" +
                "</html>\n";
    }

    // Encode given string for special characters in downloading file names
    public static String fixSpecialCharactersInFileName(String fileName, boolean isIE) throws UnsupportedEncodingException {
        byte[] nameBytes = fileName.getBytes((isIE) ? "WINDOWS-1250" : "UTF-8");
        StringBuilder sendName = new StringBuilder();
        for (byte b : nameBytes) {
            sendName.append((char) (b & 0xff));
        }
        return sendName.toString();
    }
}
