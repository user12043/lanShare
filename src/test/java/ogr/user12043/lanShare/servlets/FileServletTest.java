package ogr.user12043.lanShare.servlets;

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
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.AfterClass;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Created on 15.04.2020 - 18:37
 * part of project lanShare
 *
 * @author user12043
 */
@FixMethodOrder(MethodSorters.JVM)
public class FileServletTest {
    private static File[] testFiles;
    private static WebDriver driver;

    @Before
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("download.default_directory", TestConstants.DOWNLOAD_DIR);
        options.setExperimentalOption("prefs", prefs);
        options.addArguments("--incognito");
        try {
            driver = new ChromeDriver(options);
        } catch (IllegalStateException e) {
            System.out.println("Setting chrome web driver");
            // set chrome web driver
            String os = System.getProperty("os.name");
            if (os.contains("win") || os.contains("Win")) {
                System.setProperty("webdriver.chrome.driver", Paths.get("chromeDriver", "chromedriver.exe").toAbsolutePath().toString());
            } else if (os.contains("nux") || os.contains("nix") || os.contains("aix")) {
                System.setProperty("webdriver.chrome.driver", Paths.get("chromeDriver", "chromedriver").toAbsolutePath().toString());
            }

            driver = new ChromeDriver(options);
        }

        File testFilesDir = new File(TestConstants.TEST_FILES_DIR);
        testFiles = testFilesDir.listFiles(File::isFile);
    }

    @After
    public void tearDown() {
        driver.close();
        driver.quit();
    }

    @Test
    public void downloadNonExistentFile() {
        driver.get(TestConstants.APP_URL + "/file?fileName=");
        Assert.assertEquals("Error 404 Not Found", driver.getTitle());

        driver.get(TestConstants.APP_URL + "/file?fileName=IdoNotExist.txt");
        Assert.assertEquals("Error 404 Not Found", driver.getTitle());
    }

    /**
     * Uploading files test
     *
     * @throws IOException - Error while reading/writing files
     */
    @Test
    public void doPostTest() throws IOException {
        removeTestFiles();

        // Test all test files
        for (File testFile : testFiles) {
            System.out.println("Upload test for: " + testFile.getName());

            // Selenium in action
            driver.get(TestConstants.APP_URL);
//            driver.findElement(By.id("upFiles")).click();
            driver.findElement(By.id("upFiles")).sendKeys(testFile.getAbsolutePath());
            driver.findElement(By.id("submitButton")).click();

            File targetFile = TestUtils.getUploadTargetFile(testFile);

            // wait until upload complete
            WebDriverWait wait = new WebDriverWait(driver, 50, 100);
            wait.until(d -> targetFile.exists() && targetFile.length() == testFile.length());

            // Compare the files byte by byte
            System.out.println("Upload completed. Comparing files...");
            FileReader sourceReader = new FileReader(testFile);
            FileReader targetReader = new FileReader(targetFile);

            int sourceRead;
            int targetRead;
            while ((sourceRead = sourceReader.read()) != -1) {
                targetRead = targetReader.read();
                Assert.assertEquals("Upload failed for " + testFile.getName(), sourceRead, targetRead);
            }

            System.out.println("Test file '" + testFile.getName() + "' uploaded successfully");
        }
    }

    /**
     * Download test with three sample files
     *
     * @throws IOException - Error while reading/writing files
     */
    @Test
    public void doGetTest() throws IOException {
        for (File testFile : testFiles) {
            System.out.println("Download test for: " + testFile.getName());

            // Selenium in action
            driver.get(TestConstants.APP_URL);
            driver.findElement(By.linkText(testFile.getName())).click();

            File targetFile = TestUtils.getDownloadTargetFile(testFile);

            // wait until download complete
            WebDriverWait wait = new WebDriverWait(driver, 30, 100);
            wait.until(d -> targetFile.exists() && targetFile.length() == testFile.length());

            // Compare the files byte by byte
            System.out.println("Download completed. Comparing files...");
            FileReader sourceReader = new FileReader(testFile);
            FileReader targetReader = new FileReader(targetFile);

            int sourceRead;
            int targetRead;
            while ((sourceRead = sourceReader.read()) != -1) {
                targetRead = targetReader.read();
                Assert.assertEquals("Download failed for " + testFile.getName(), sourceRead, targetRead);
            }

            System.out.println("Test file '" + testFile.getName() + "' downloaded successfully");
        }
    }

    /**
     * Removes the downloaded and posted sample files if exists
     *
     * @throws IOException - Error while reading/writing files
     */
    private static void removeTestFiles() throws IOException {
        for (File testFile : testFiles) {
            Files.deleteIfExists(TestUtils.getUploadTargetFile(testFile).toPath());
            Files.deleteIfExists(TestUtils.getDownloadTargetFile(testFile).toPath());
        }
    }

    private void putTestFiles() throws IOException {
        for (File testFile : testFiles) {
            Assert.assertTrue("Test file '" + testFile.getName() + "' does not exists!", testFile.exists());
            TestUtils.copyOrLinkFile(testFile.toPath(), TestUtils.getUploadTargetFile(testFile).toPath());
        }
    }
}
