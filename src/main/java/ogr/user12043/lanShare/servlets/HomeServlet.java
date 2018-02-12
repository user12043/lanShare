package ogr.user12043.lanShare.servlets;

import ogr.user12043.lanShare.logging.Logger;
import ogr.user12043.lanShare.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

/**
 * Created by user12043 on 2/8/18
 * part of project lanShare
 */

@WebServlet(urlPatterns = {"/"})
public class HomeServlet extends HttpServlet {
    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Logger.info("Home page, Client address: \"" + request.getRemoteAddr() + "\", Client user: \"" + request.getRemoteUser() + "\"");
            File file = new File(Properties.appFilesLocation());
            StringBuilder builder = new StringBuilder("");
//            builder.append("Files:\n<ul>\n");
            if (file.exists()) {
                // Get list of files in the directory
                File[] files = file.listFiles();
                request.setAttribute("files", files);

                /*// Create and write html string into response
                if (files != null) {
                    for (File f : files) {
                        if (f.isFile()) {
                            String name = f.getName();
                            builder.append("<li><a href=\"").append(request.getContextPath()).append("/file?fileName=").append(name).append("\">").append(name).append("</a></li>\n");
                        }
                    }
                }*/
            }
            /*builder.append("</ul>\n");
            builder.append("<br><br><br>\n");
            builder.append("<form action=\"").append(request.getContextPath()).append("/file\" method=\"post\" enctype=\"multipart/form-data\">\n");
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
            response.getWriter().print(Utils.buildHtml(builder.toString()));*/
            request.getRequestDispatcher("pages/mainPage.jsp").forward(request, response);
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            Logger.error(errorMessage);
            // Send internal server error
            response.sendError(500, errorMessage);
        }
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
