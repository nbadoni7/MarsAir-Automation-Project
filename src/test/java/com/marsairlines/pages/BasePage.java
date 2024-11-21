package com.marsairlines.pages;

import com.marsairlines.utils.ScreenshotUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class BasePage {
    protected  WebDriver driver;
    // Shared across all pages
    private static final Logger logger = LogManager.getLogger(BasePage.class);

    public BasePage(WebDriver driver) {
        this.driver = driver;
    }

    // Set the driver instance
    public void setDriver(WebDriver driverInstance) {
        driver = driverInstance;
    }

    // Navigate to a URL
    public void navigateTo(String url) {
        try {
            driver.get(url);
            logger.info("Navigated to URL: {}", url);
            ScreenshotUtils.takeScreenshot(driver, "NavigateToURL");
        } catch (Exception e) {
            logger.error("Failed to navigate to URL '{}': {}", url, e.getMessage());
        }
    }

    // Find an element
    public WebElement findElement(By locator) {
        try {
            WebElement element = driver.findElement(locator);
            logger.info("Element located: {}", locator);
            return element;
        } catch (Exception e) {
            logger.error("Failed to locate element '{}': {}", locator, e.getMessage());
            throw new RuntimeException("Element not found: " + locator);
        }
    }

    // Click on an element
    public void clickElement(WebElement element) {
        try {
            element.click();
            logger.info("Clicked on element: {}", element);
            ScreenshotUtils.takeScreenshot(driver, "ClickElement");
        } catch (Exception e) {
            logger.error("Failed to click on element '{}': {}", element, e.getMessage());
        }
    }

    // Enter text into an element
    public void enterText(WebElement element, String text) {
        try {
            element.clear();
            element.sendKeys(text);
            logger.info("Entered text '{}' into element: {}", text, element);
            ScreenshotUtils.takeScreenshot(driver, "EnterText");
        } catch (Exception e) {
            logger.error("Failed to enter text '{}' into element '{}': {}", text, element, e.getMessage());
        }
    }

    // Select a value from a dropdown by visible text
    public void selectFromDropdown(WebElement element, String visibleText) {
        try {

            Select dropdown = new Select(element);
            dropdown.selectByVisibleText(visibleText);
            logger.info("Selected '{}' from dropdown: {}", visibleText, element);
            ScreenshotUtils.takeScreenshot(driver, "SelectFromDropdown");
        } catch (Exception e) {
            logger.error("Failed to select '{}' from dropdown '{}': {}", visibleText, element, e.getMessage());
        }
    }

    // Get text from an element
    public String getElementText(WebElement element) {
        try {
            String text = element.getText();
            logger.info("Retrieved text '{}' from element: {}", text, element);
            return text;
        } catch (Exception e) {
            logger.error("Failed to retrieve text from element '{}': {}", element, e.getMessage());
            return null;
        }
    }

    // Verify if an element is displayed
    public boolean isElementDisplayed(WebElement element) {
        try {
            boolean isDisplayed = element.isDisplayed();
            logger.info("Element '{}' is displayed: {}", element, isDisplayed);
            return isDisplayed;
        } catch (Exception e) {
            logger.error("Failed to verify if element '{}' is displayed: {}", element, e.getMessage());
            return false;
        }
    }
}

