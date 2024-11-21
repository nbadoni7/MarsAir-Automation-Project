package com.marsairlines.utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;

public class ScreenshotUtils {

    public static void takeScreenshot(WebDriver driver, String screenshotName) {
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String screenshotPath = "/target/srcscreenshots/" + screenshotName + ".png";
        try {
            FileUtils.copyFile(screenshot, new File(screenshotPath));
            System.out.println("Screenshot saved at: " + screenshotPath);
        } catch (IOException e) {
            System.err.println("Failed to save screenshot: " + e.getMessage());
        }
    }
}

