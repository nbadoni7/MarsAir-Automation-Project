package com.marsairlines.hooks;

import com.marsairlines.utils.DriverFactory;
import com.marsairlines.utils.ScreenshotUtils;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Hooks {
    private static final Logger logger = LogManager.getLogger(Hooks.class);

    @Before
    public void setUp() {
        logger.info("Initializing WebDriver...");
        DriverFactory.setUpDriver();
        logger.info("WebDriver initialized successfully.");
    }

    @After
    public void tearDown(Scenario scenario) {
        logger.info("Closing WebDriver...");

        if (scenario.isFailed()) {
            // Take a screenshot and attach it to the report
            ScreenshotUtils.takeScreenshotAndAttachToReport(DriverFactory.getDriver(), scenario);
        }

        DriverFactory.tearDownDriver();
        logger.info("WebDriver closed successfully.");
    }
}