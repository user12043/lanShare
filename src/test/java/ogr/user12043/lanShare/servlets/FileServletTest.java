package ogr.user12043.lanShare.servlets;

import ogr.user12043.lanShare.util.Properties;
import ogr.user12043.lanShare.util.TestConstants;
import ogr.user12043.lanShare.util.TestUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.FluentWait;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * Created on 15.04.2020 - 18:37
 * part of project lanShare
 *
 * @author user12043
 */
public class FileServletTest {
    private final Path testFile1 = Paths.get(TestConstants.TEST_FILES_DIR, TestConstants.TEST_FILE_1_NAME);
    private final Path testFile2 = Paths.get(TestConstants.TEST_FILES_DIR, TestConstants.TEST_FILE_2_NAME);
    private final Path testFile3 = Paths.get(TestConstants.TEST_FILES_DIR, TestConstants.TEST_FILE_3_NAME);

    private final Path testFile1LinkPath = Paths.get(Properties.appFilesLocation(), TestConstants.TEST_FILE_1_NAME);
    private final Path testFile2LinkPath = Paths.get(Properties.appFilesLocation(), TestConstants.TEST_FILE_2_NAME);
    private final Path testFile3LinkPath = Paths.get(Properties.appFilesLocation(), TestConstants.TEST_FILE_3_NAME);

    private final Path testFile1DownloadPath = Paths.get(TestConstants.DOWNLOAD_DIR, TestConstants.TEST_FILE_1_NAME);
    private final Path testFile2DownloadPath = Paths.get(TestConstants.DOWNLOAD_DIR, TestConstants.TEST_FILE_2_NAME);
    private final Path testFile3DownloadPath = Paths.get(TestConstants.DOWNLOAD_DIR, TestConstants.TEST_FILE_3_NAME);

    private WebDriver driver;

    @Before
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("download.default_directory", Paths.get("", "downloads").toAbsolutePath().toString());
        options.setExperimentalOption("prefs", prefs);
        options.addArguments("--incognito");
        driver = new ChromeDriver(options);
    }

    @After
    public void tearDown() throws Exception {
        removeTestFiles();
        Files.deleteIfExists(Paths.get(Properties.appFilesLocation(), ""));
        Files.deleteIfExists(Paths.get(Properties.logFileLocation(), ""));
        Files.deleteIfExists(Paths.get(Properties.appConfigDir(), ""));
        driver.close();
    }

    /**
     * Uploading files test
     *
     * @throws IOException - Error while reading/writing files
     */
    @Test
    public void doPostTest() throws IOException {
        removeTestFiles();
        Path[][] testFiles = new Path[][]{{testFile1, testFile1LinkPath}, {testFile2, testFile2LinkPath}, {testFile3, testFile3LinkPath}};

        for (Path[] testFileSet : testFiles) {
            Path testFile = testFileSet[0];
            Path uploadFile = testFileSet[1];
            System.out.println("Upload test for: " + testFile.getFileName());
            driver.get(TestConstants.APP_URL);
//            driver.findElement(By.id("upFiles")).click();
            driver.findElement(By.id("upFiles")).sendKeys(testFile.toAbsolutePath().toString());
            driver.findElement(By.id("submitButton")).click();

            File targetFile = uploadFile.toFile();

            FluentWait<WebDriver> wait = new FluentWait<>(driver)
                    .withTimeout(Duration.ofSeconds(50)) // 50 seconds max to upload
                    .pollingEvery(Duration.ofMillis(100)); // check every 100 ms
            wait.until(webDriver -> targetFile.exists() && targetFile.length() == testFile.toFile().length());
            Assert.assertTrue("Upload failed for " + testFile.getFileName(), targetFile.exists());
            System.out.println("Test file '" + testFile.getFileName() + "' uploaded successfully");
        }
    }

    /**
     * Download test with three sample files
     *
     * @throws IOException - Error while reading/writing files
     */
    @Test
    public void doGetTest() throws IOException {
        putTestFiles();
        Path[][] testFiles = new Path[][]{{testFile1, testFile1DownloadPath}, {testFile2, testFile2DownloadPath}, {testFile3, testFile3DownloadPath}};

        for (Path[] testFileSet : testFiles) {
            Path testFile = testFileSet[0];
            Path downloadFile = testFileSet[1];
            System.out.println("Download test for: " + testFile.getFileName());
            driver.get(TestConstants.APP_URL);
            driver.findElement(By.linkText(testFile.getFileName().toString())).click();

            // create target file
            File targetFile = downloadFile.toFile();

            // wait until download complete
            FluentWait<WebDriver> wait = new FluentWait<>(driver)
                    .withTimeout(Duration.ofSeconds(30)) // 30 seconds max to download
                    .pollingEvery(Duration.ofMillis(100)); // check every 100 ms
            wait.until(o -> targetFile.exists() && targetFile.length() == testFile.toFile().length());
            Assert.assertTrue("Download failed for " + testFile.getFileName(), targetFile.exists());
            System.out.println("Test file '" + testFile.getFileName() + "' downloaded successfully");
        }
    }

    /**
     * Removes the downloaded and posted sample files if exists
     *
     * @throws IOException - Error while reading/writing files
     */
    private void removeTestFiles() throws IOException {
        Files.deleteIfExists(testFile1LinkPath);
        Files.deleteIfExists(testFile2LinkPath);
        Files.deleteIfExists(testFile3LinkPath);

        Files.deleteIfExists(testFile1DownloadPath);
        Files.deleteIfExists(testFile2DownloadPath);
        Files.deleteIfExists(testFile3DownloadPath);
    }

    private void putTestFiles() throws IOException {
        Assert.assertTrue("Test file 1 does not exists", testFile1.toFile().exists());
        Assert.assertTrue("Test file 2 does not exists", testFile2.toFile().exists());
        Assert.assertTrue("Test file 3 does not exists", testFile3.toFile().exists());

        TestUtils.copyOrLinkFile(testFile1.toAbsolutePath(), testFile1LinkPath);
        TestUtils.copyOrLinkFile(testFile2.toAbsolutePath(), testFile2LinkPath);
        TestUtils.copyOrLinkFile(testFile3.toAbsolutePath(), testFile3LinkPath);
    }
}
