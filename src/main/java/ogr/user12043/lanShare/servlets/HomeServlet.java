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
            for (String s : files) {
                builder.append("<li><a href=\"file?fileName=" + s + "\">" + s + "</a></li>\n");
            }
            builder.append("</ul>\n");
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
