package com.marsairlines.pages;

import com.marsairlines.utils.ConfigUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


public class MarsAirHomePage  extends BasePage {
    private static final Logger logger = LogManager.getLogger(MarsAirHomePage.class);

    // Locators
    @FindBy(xpath = "//*[text()='Departing']")
    public WebElement departingDropdownLabel;

    @FindBy(id = "departing")
    public WebElement departingDropdownField;

    @FindBy(xpath = "//*[text()='Returning']")
    public WebElement returningDropdownLabel;

    @FindBy(id = "returning")
    public WebElement returningDropdownField;


    @FindBy(xpath = "//input[@value='Search']")
    public WebElement searchButton;

    @FindBy(xpath = "//*[@id='content']/p[1]")
    public WebElement flightResultMessage;

    @FindBy(xpath = "//*[@id='content']/p[2]")
    public WebElement flightResultSubMessage;

    @FindBy(id="promotional_code")
    private WebElement promoCodeField;

    @FindBy(xpath = "//p[@class='promo_code']")
    private WebElement promoCodeMessage;

    @FindBy(id = "error-message")
    public WebElement errorMessage;

    // Constructor
    public MarsAirHomePage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    // Navigate to the MarsAir homepage
    public void open() {
        navigateTo(ConfigUtils.getProperty("marsair.url"));
    }

    // Select a date from the "Departing" dropdown
    public void selectDepartingDate(String date) {
        selectFromDropdown(departingDropdownField, date);
    }

    // Select a date from the "Returning" dropdown
    public void selectReturningDate(String date) {
        selectFromDropdown(returningDropdownField, date);
    }

    // Click the "Search" button
    public void clickSearchButton() {
        clickElement(searchButton);

    }

    // Retrieve the result message displayed for flight options availability
    public String getFlightResultMessage() {
       return getElementText(flightResultMessage);
    }

    // Retrieve the result sub message displayed for flight options availability
    public String getFlightResultSubMessage() {
        return getElementText(flightResultSubMessage);
    }

    // Retrieve the error message displayed on the page
    public String getErrorMessage() {
        return getElementText(errorMessage);
    }

    // Verify if the "Departing" dropdown is displayed
    public boolean isDepartingDropdownDisplayed() {
        return isElementDisplayed(departingDropdownLabel) && isElementDisplayed(departingDropdownField) ;
    }

    // Verify if the "Returning" dropdown is displayed
    public boolean isReturningDropdownDisplayed() {
        return isElementDisplayed(returningDropdownLabel) && isElementDisplayed(returningDropdownField);
    }

    public void enterPromotionalCode(String promoCode) {
        promoCodeField.sendKeys(promoCode);
    }

    public String getPromotionalCode() {
        return promoCodeMessage.getText();
    }
}
