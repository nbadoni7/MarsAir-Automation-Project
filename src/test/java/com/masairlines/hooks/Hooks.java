package com.masairlines.hooks;

import com.masairlines.utils.DriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Hooks {
    private static final Logger logger = LogManager.getLogger(Hooks.class);

    @Before
    public void setUp() {
        logger.info("Initializing WebDriver...");
        DriverFactory.getInstance().setUpDriver();
        logger.info("WebDriver initialized successfully.");
    }

    @After
    public void tearDown() {
        logger.info("Closing WebDriver...");
        DriverFactory.getInstance().tearDownDriver();
        logger.info("WebDriver closed successfully.");
    }
}