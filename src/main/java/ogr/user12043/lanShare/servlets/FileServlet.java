package ogr.user12043.lanShare.servlets;

import ogr.user12043.lanShare.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;
import java.nio.file.Paths;

/**
 * Created by user12043 on 2/7/18
 * part of project lanShare
 */

@MultipartConfig
public class FileServlet extends HttpServlet {
    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("/file post");
        Part upFilePart = request.getPart("upFile");
        String upFileName = Paths.get(upFilePart.getSubmittedFileName()).getFileName().toString();
        InputStream upFileStream = upFilePart.getInputStream();
        File saveFile = new File(Properties.appFilesLocation() + "/" + upFileName);
        OutputStream out = new FileOutputStream(saveFile);
        if (!saveFile.exists()) {
            byte[] buffer = new byte[4096];
            int read = 0;

            while ((read = upFileStream.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }

            saveFile.createNewFile();
        }

        upFileStream.close();
        out.close();
        response.sendRedirect("index");
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
            int read = 0;

            while ((read = inputStream.read(buffer)) > -1) {
                out.write(buffer, 0, read);
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
