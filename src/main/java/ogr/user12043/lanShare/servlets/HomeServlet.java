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
//            builder.append("Files:\n<ul>\n");
            if (file.exists()) {
                // Get list of files in the directory
                File[] files = file.listFiles();
                request.setAttribute("files", files);
            }
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
