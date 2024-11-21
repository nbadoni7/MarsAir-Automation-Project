package com.marsairlines.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SearchResultPage extends BasePage {

    private static final Logger logger = LogManager.getLogger(MarsAirHomePage.class);

    @FindBy(xpath= "//h2[text()='Search Results']")
    public WebElement searchResultsContainer;

    public SearchResultPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public boolean isSearchResultsDisplayed() {
        return isElementDisplayed(searchResultsContainer);
    }
}
