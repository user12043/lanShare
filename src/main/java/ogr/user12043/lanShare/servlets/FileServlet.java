package ogr.user12043.lanShare.servlets;

import ogr.user12043.lanShare.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Paths;

/**
 * Created by user12043 on 2/7/18
 * part of project lanShare
 */

@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 10, maxFileSize = 1024 * 1024 * 50, maxRequestSize = 1024 * 1024 * 100, location = "/")

public class FileServlet extends HttpServlet {
    private byte[] buffer;
    private int read;

    @Override
    public void init() throws ServletException {
        super.init();
        buffer = new byte[4096];
        read = 0;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Part upFilePart = request.getPart("upFile");
        String upFileName = Paths.get(upFilePart.getSubmittedFileName()).getFileName().toString();

        // <editor-fold desc="Write manually from InputStream" defaultstate="collapsed">
        /*InputStream upFileStream = upFilePart.getInputStream();
        File saveFile = new File(Properties.appFilesLocation() + "/" + upFileName);
        OutputStream out = new FileOutputStream(saveFile);
        boolean fail = false;

        if (!saveFile.exists()) {

            while ((read = upFileStream.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }

            if (saveFile.createNewFile()) {
                response.sendRedirect("index");
            } else {
                fail = true;
            }
        } else {
            fail = true;
        }

        if (fail) {
            response.getWriter().print(Utils.buildHtml("<h1 style=\"color:red\">FAILED</h1>\n" +
                    "<a href=\"index\">Back to home...</a>"));
        }
        upFileStream.close();
        out.close();*/
        // </editor-fold>

        // Writing more simply
        upFilePart.write(Properties.appFilesLocation() + File.separator + upFileName);
        response.sendRedirect("index");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fileName = request.getParameter("fileName");
        File file = new File(Properties.appFilesLocation() + File.separator + fileName);
        if (!fileName.isEmpty() && file.exists() && !file.isDirectory()) {
            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

            OutputStream out = response.getOutputStream();
            FileInputStream inputStream = new FileInputStream(file);

            while ((read = inputStream.read(buffer)) != -1) {
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
