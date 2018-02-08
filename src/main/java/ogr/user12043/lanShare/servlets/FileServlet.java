package ogr.user12043.lanShare.servlets;

import ogr.user12043.lanShare.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by user12043 on 2/7/18
 * part of project lanShare
 */

public class FileServlet extends HttpServlet {
    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fileName = request.getParameter("fileName");
        File file = new File(Properties.appFilesLocation() + "/" + fileName);
        if (!fileName.isEmpty() && file.exists() && !file.isDirectory()) {
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName);

            OutputStream out = response.getOutputStream();
            FileInputStream inputStream = new FileInputStream(file);
            byte[] buffer = new byte[4096];
            int length;

            while ((length = inputStream.read(buffer)) > -1) {
                out.write(buffer, 0, length);
            }

            inputStream.close();
            out.flush();
        } else {
            response.setStatus(404);
        }
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
