package ogr.user12043.lanShare.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

/**
 * Created on 17.04.2020 - 18:15
 * part of project lanShare
 *
 * @author user12043
 */
public class TestUtils {
    public static void copyOrLinkFile(Path sourcePath, Path targetPath) throws IOException {
        Files.deleteIfExists(targetPath);
        try {
            Files.createSymbolicLink(targetPath, sourcePath);
            System.out.println("Created symlink '" + sourcePath + " -> " + targetPath + "'");
        } catch (UnsupportedOperationException e) {
            System.err.println("Unable to link file: '" + targetPath + "', copying...");
            Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
        }
    }
}
