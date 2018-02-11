package ogr.user12043.lanShare.servlets;

import ogr.user12043.lanShare.logging.Logger;
import ogr.user12043.lanShare.util.Properties;
import ogr.user12043.lanShare.util.Utils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;
import java.nio.file.Paths;

//import javax.servlet.annotation.MultipartConfig;

/**
 * Created by user12043 on 2/7/18
 * part of project lanShare
 */

//@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 10, maxFileSize = 1024 * 1024 * 50, maxRequestSize = 1024 * 1024 * 100, location = "/")
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
            Part upFilePart = request.getPart("upFile");
            String upFileName = Paths.get(upFilePart.getSubmittedFileName()).getFileName().toString();
            Logger.info("Uploading file name: \"" + upFileName + "\", Client address: \"" + request.getRemoteAddr() + "\", Client user: \"" + request.getRemoteUser() + "\"");
            // <editor-fold desc="Write manually from InputStream" defaultstate="collapsed">
            InputStream upFileStream = upFilePart.getInputStream();
            File saveFile = new File(Properties.appFilesLocation() + File.separator + upFileName);
            boolean success = false;
            OutputStream out = null;

            if (!saveFile.exists()) {
                out = new FileOutputStream(saveFile);
                // Read from InputStream and write to the FileOutputStream
                while ((read = upFileStream.read(buffer)) != -1) {
                    out.write(buffer, 0, read);
                }
                response.sendRedirect(request.getContextPath());
                success = true;
            }
            upFileStream.close();
            if (out != null) {
                out.close();
            }

            if (success) {
                return;
            }

            response.getWriter().print(Utils.buildHtml("<h1 style=\"color:red\">FAILED</h1>\n" +
                    "<a href=\"" + request.getContextPath() + "\">Back to home...</a>"));
            // </editor-fold>

            /*// Writing more simply (Not working now)
            // TODO; Make working
            upFilePart.write(Properties.appFilesLocation() + File.separator + ((upFileName.isEmpty()) ? "unnamed" : upFileName));*/
        } catch (IOException e) {
            Logger.error(e.getMessage());
        } catch (ServletException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Get file name and create file object
            String fileName = request.getParameter("fileName");
            File file = new File(Properties.appFilesLocation() + File.separator + fileName);

            // Check file state
            if (!fileName.isEmpty() && file.exists() && !file.isDirectory()) {
                Logger.info("Downloading file name: \"" + fileName + "\", Client address: \"" + request.getRemoteAddr() + "\", Client user: \"" + request.getRemoteUser() + "\"");
                // Set header to tell browser a file is downloading
                response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
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
                response.setStatus(404);
            }
        } catch (IOException e) {
            Logger.error(e.getMessage());
        }
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
