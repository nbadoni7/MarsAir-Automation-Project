package com.marsairlines.utils;

import io.cucumber.java.Scenario;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class ScreenshotUtils {

    private static final String SCREENSHOTS_DIR = "target/screenshots/";

    /**
     * Takes a screenshot and saves it to the target/screenshots directory.
     *
     * @param driver        WebDriver instance
     * @param screenshotName Name of the screenshot file
     * @return Path of the saved screenshot
     */
    public static String takeScreenshot(WebDriver driver, String screenshotName) {
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String screenshotPath = Paths.get(SCREENSHOTS_DIR, screenshotName + ".png").toString();

        try {
            // Create the screenshots directory if it does not exist
            File screenshotsDir = new File(SCREENSHOTS_DIR);
            if (!screenshotsDir.exists() && !screenshotsDir.mkdirs()) {
                throw new IOException("Failed to create directory: " + SCREENSHOTS_DIR);
            }

            // Save the screenshot to the specified path
            FileUtils.copyFile(screenshot, new File(screenshotPath));
            System.out.println("Screenshot saved at: " + screenshotPath);
        } catch (IOException e) {
            System.err.println("Failed to save screenshot: " + e.getMessage());
            screenshotPath = null;
        }

        return screenshotPath;
    }

    /**
     * Takes a screenshot and attaches it to the Cucumber scenario report.
     *
     * @param driver    WebDriver instance
     * @param scenario  Cucumber scenario to attach the screenshot to
     */
    public static void takeScreenshotAndAttachToReport(WebDriver driver, Scenario scenario) {
        byte[] screenshotBytes = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);

        // Attach the screenshot to the Cucumber report
        scenario.attach(screenshotBytes, "image/png", "Screenshot");

        // Optionally save the screenshot to the file system
        String screenshotName = scenario.getName().replaceAll("[^a-zA-Z0-9]", "_");
        takeScreenshot(driver, screenshotName);
    }
}