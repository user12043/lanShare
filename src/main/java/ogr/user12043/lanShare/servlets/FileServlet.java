package ogr.user12043.lanShare.servlets;

import ogr.user12043.lanShare.logging.Logger;
import ogr.user12043.lanShare.util.Properties;
import ogr.user12043.lanShare.util.Utils;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;
import java.net.URLDecoder;
import java.nio.file.Paths;

/**
 * Created by user12043 on 2/7/18
 * part of project lanShare
 */

@WebServlet(urlPatterns = "/file")
@MultipartConfig(location = "./tmp", maxRequestSize = 4294967296L, maxFileSize = 4294967296L, fileSizeThreshold = 104857600)
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
        try {
            for (Part part : request.getParts()) {
                String upFileName = Paths.get(part.getSubmittedFileName()).getFileName().toString();
                Logger.info("Uploading file name: \"" + upFileName + "\", Client address: \"" + request.getRemoteAddr() + "\", Client user: \"" + request.getRemoteUser() + "\"");

                // <editor-fold desc="Write manually from InputStream" defaultstate="collapsed">
                InputStream upFileStream = part.getInputStream();
                File saveFile = new File(Properties.appFilesLocation() + File.separator + upFileName);

                if (!saveFile.exists()) {
                    OutputStream out = new FileOutputStream(saveFile);
                    // Read from InputStream and write to the FileOutputStream
                    while ((read = upFileStream.read(buffer)) != -1) {
                        out.write(buffer, 0, read);
                    }
                    out.close();
                } else if (part.getSize() == 0) {
                    response.sendError(400, "The file part can not empty");
                } else {
                    // Send conflict error
                    response.sendError(409);
                }
                upFileStream.close();
            }
            // Redirect to home
            response.sendRedirect(request.getContextPath());
            // </editor-fold>

            //<editor-fold desc="Writing more simply" defaultstate="collapsed">
            // Not working in Glassfish-5.0 (January 2018 now) because does not support absolute path in this case.
            // Also in Jetty-9.4.8.v20171121
            //upFilePart.write(Properties.appFilesLocation() + File.separator + upFileName);
            //</editor-fold>
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            Logger.error(errorMessage);
            // Send internal server error
            response.sendError(500, errorMessage);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Get file name from encoded parameter and create file object
            String fileName = URLDecoder.decode(request.getParameterMap().get("fileName")[0], "UTF-8");
            // String fileName = request.getParameter("fileName");

            File file = new File(Properties.appFilesLocation() + File.separator + fileName);

            // Check file state
            if (!fileName.isEmpty() && file.exists() && !file.isDirectory()) {
                Logger.info("Downloading file name: \"" + fileName + "\", Client address: \"" + request.getRemoteAddr() + "\", Client user: \"" + request.getRemoteUser() + "\"");
                // Set header to tell browser a file is downloading
                response.setContentType("application/octet-stream");

                // Not setting file name directly because there is any special character in it maybe.
                // Set encoding depending on the browser
                boolean isIE = (request.getHeader("user-agent").contains("MSIE"));
                String sendName = Utils.fixSpecialCharactersInFileName(fileName, isIE);
                response.setHeader("content-disposition", "attachment; filename=\"" + sendName + "\"");

                // Set content length. Browser will know file size.
                response.setContentLengthLong(file.length());

                OutputStream out = response.getOutputStream();
                FileInputStream inputStream = new FileInputStream(file);

                // Write the file to response output
                while ((read = inputStream.read(buffer)) != -1) {
                    out.write(buffer, 0, read);
                }

                inputStream.close();
                out.flush();
            } else {
                // Send not found error
                response.sendError(404);
            }
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
