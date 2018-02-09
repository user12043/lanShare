package ogr.user12043.lanShare.servlets;

import ogr.user12043.lanShare.util.Properties;
import ogr.user12043.lanShare.util.Utils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

/**
 * Created by user12043 on 2/8/18
 * part of project lanShare
 */

public class HomeServlet extends HttpServlet {
    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        File file = new File(Properties.appFilesLocation());
        StringBuilder builder = new StringBuilder("");
        if (file.exists()) {
            String[] files = file.list();

            builder.append("Files:\n<ul>\n");
            if (files != null) {
                for (String s : files) {
                    builder.append("<li><a href=\"file?fileName=").append(s).append("\">").append(s).append("</a></li>\n");
                }
            }
            builder.append("</ul>\n");
            builder.append("<br><br><br>\n");
            builder.append("<form action=\"file\" method=\"post\" enctype=\"multipart/form-data\">\n");
            builder.append("<table border=\"3\">\n");
            builder.append("<tr>\n");
            builder.append("<td><label for=\"upFile\">Select File: </label></td>\n");
            builder.append("<td><input id=\"upFile\" name=\"upFile\" type=\"file\"></td>\n");
            builder.append("</tr>\n");
            builder.append("<tr style=\"text-align: center\">\n");
            builder.append("<td colspan=\"2\"><input type=\"submit\" value=\"Submit\"></td>\n");
            builder.append("</tr>\n");
            builder.append("</table>\n");
            builder.append("</form>\n");
            response.getWriter().print(Utils.buildHtml(builder.toString()));
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
