package com.marsairlines.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class DriverFactory {

    // ThreadLocal to ensure each thread gets its own WebDriver instance
    private static ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    // Private constructor to prevent instantiation
    private DriverFactory() {}

    // Method to get the WebDriver instance for the current thread
    public static WebDriver getDriver() {
        if (driverThreadLocal.get() == null) {
            setUpDriver(); // Initialize the WebDriver if not already initialized
        }
        return driverThreadLocal.get();
    }

    // Method to initialize the WebDriver for the current thread
    public static void setUpDriver() {
        if (driverThreadLocal.get() == null) {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--user-data-dir=/tmp/chrome-profile-" + Thread.currentThread().getId());
            options.addArguments("--remote-debugging-port=" + (9222 + Thread.currentThread().getId()));

            // Initialize ChromeDriver with the options
            WebDriver driver = new ChromeDriver(options);
            driver.manage().window().maximize();
            driverThreadLocal.set(driver);
        }
    }

    // Method to tear down the WebDriver for the current thread
    public static void tearDownDriver() {
        WebDriver driver = driverThreadLocal.get();
        if (driver != null) {
            driver.quit();
            driverThreadLocal.remove(); // Remove the WebDriver instance for the current thread
        }
    }
}