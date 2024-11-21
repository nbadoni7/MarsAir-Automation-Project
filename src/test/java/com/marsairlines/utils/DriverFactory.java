package com.marsairlines.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class DriverFactory {
    private static DriverFactory instance;
    private WebDriver driver;

    // Private constructor to enforce Singleton pattern
    private DriverFactory() {}

    // Singleton instance
    public static DriverFactory getInstance() {
        if (instance == null) {
            instance = new DriverFactory();
        }
        return instance;
    }

    // Getter for WebDriver instance
    public WebDriver getDriver() {
        if (driver == null) {
            throw new IllegalStateException("WebDriver is not initialized. Ensure setup is called before using the driver.");
        }
        return driver;
    }

    // Initialize WebDriver
    public void setUpDriver() {
        if (driver == null) {
            driver = new ChromeDriver(); // Replace with desired WebDriver
            driver.manage().window().maximize();
        }
    }

    // Tear down WebDriver
    public void tearDownDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}