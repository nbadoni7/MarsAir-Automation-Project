package com.marsairlines.StepDefinitions;

import com.marsairlines.pages.MarsAirHomePage;
import com.marsairlines.pages.SearchResultPage;
import com.marsairlines.utils.DriverFactory;

import com.marsairlines.utils.Utils;
import io.cucumber.java.en.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class MarsAirSteps {

    private static final Logger logger = LogManager.getLogger(MarsAirSteps.class);
    private MarsAirHomePage marsAirHomePage;
    private SearchResultPage searchResultPage;

    private WebDriver driver;

    // Initialize page objects using the DriverFactory
    public MarsAirSteps() {
        this.driver = DriverFactory.getDriver();

        // Initialize MarsAirHomePage with the WebDriver instance
        this.marsAirHomePage = new MarsAirHomePage(driver);
        this.searchResultPage = new SearchResultPage(driver);
    }

    @Given("the user is on the MarsAir flight search page")
    public void theUserIsOnTheMarsAirFlightSearchPage() {
        logger.info("Navigating to the MarsAir homepage...");
        marsAirHomePage.open();
        logger.info("Successfully navigated to the MarsAir homepage.");
    }

    @Then("the {string} dropdown should be displayed")
    public void theDropdownShouldBeDisplayed(String dropdownName) {
        // Test for Label
        boolean isLabelDisplayed = dropdownName.equalsIgnoreCase("Departing")
                ? marsAirHomePage.isDepartingDropdownDisplayed()
                : marsAirHomePage.isReturningDropdownDisplayed();

        assertTrue(isLabelDisplayed, dropdownName + " dropdown label is not displayed.");
        logger.info("{} dropdown label is displayed.", dropdownName);
    }

    @When("the user selects {string} from the {string} dropdown")
    public void theUserSelectsFromTheDropdown(String date, String dropdownName) {
        if (dropdownName.equalsIgnoreCase("Departing")) {
            marsAirHomePage.selectDepartingDate(date);
        } else if (dropdownName.equalsIgnoreCase("Returning")) {
            marsAirHomePage.selectReturningDate(date);
        }
        logger.info("Selected '{}' from '{}' dropdown.", date, dropdownName);
    }

    @And("the user initiates the search")
    public void theUserInitiatesTheSearch() {
        marsAirHomePage.clickSearchButton();
        logger.info("Search button clicked.");
    }

    @Then("the system should display {string}")
    public void theSystemShouldDisplay(String expectedMessage) {
        String resultMessage = marsAirHomePage.getFlightResultMessage();
        String resultSubMessage = marsAirHomePage.getFlightResultSubMessage();

        // Check if additional result sub message is available
        if(resultSubMessage != null
                && !resultSubMessage.equalsIgnoreCase("Back")){
            resultMessage = resultMessage.concat(" ").concat(resultSubMessage);
        }

        logger.info("Actual message: {}", resultMessage);
        Assert.assertEquals(expectedMessage, resultMessage);
        logger.info("Verified message: {}", expectedMessage);
    }

    @Then("the system should display the message {string}")
    public void theSystemShouldDisplayTheMessage(String expectedErrorMessage) {
        String actualErrorMessage = marsAirHomePage.getErrorMessage();
        Assert.assertEquals(expectedErrorMessage, actualErrorMessage);
        logger.info("Verified error message: {}", expectedErrorMessage);
    }

    @And("there are no seats available for the selected itinerary")
    public void thereAreNoSeatsAvailableForTheSelectedItinerary() {
        String actualMessage = marsAirHomePage.getFlightResultMessage();
        Assert.assertEquals("Sorry, there are no more seats available.", actualMessage);
        logger.info("Verified message: No seats available.");
    }

    @And("seats are available for the selected itinerary")
    public void seatsAreAvailableForTheSelectedItinerary() {
        String actualMessage = marsAirHomePage.getFlightResultMessage();
        Assert.assertEquals("Seats available! Call 0800 MARSAIR to book!", actualMessage);
        logger.info("Verified message: Seats available.");
    }

    @Then("the system should allow the search operation to proceed")
    public void theSystemShouldAllowTheSearchOperationToProceed() {
        String flightOptionsMessage = marsAirHomePage.getFlightResultMessage();
        Assert.assertNotNull(flightOptionsMessage, "The search operation did not proceed as expected.");
        logger.info("Search operation proceeded successfully.");
    }

    @And("the search results page should not be displayed")
    public void theSearchResultsPageShouldNotBeDisplayed() {
        boolean isDisplayed = searchResultPage.isSearchResultsDisplayed();
        assertFalse(isDisplayed, "The search results page should not be displayed.");
    }

    @When("the user enters a valid promotional code {string}")
    public void theUserEntersAValidPromotionalCode(String promoCode) {
        marsAirHomePage.enterPromotionalCode(promoCode);
    }

    @Then("the system should display the expected message as {string} for {string} promotion code")
    public void theSystemShouldDisplayTheExpectedPromotionalCode(String expectedMessage, String promotion_code) {
        String promotion_code_message = marsAirHomePage.getPromotionalCode();

        if (promotion_code_message.contains("Sorry")) {
            Assert.assertEquals(promotion_code_message, expectedMessage.replace("{promo_code}", promotion_code), "The promotion code is valid.");
        } else {
            //Try to fetch the discount percentage form the entered promotional code
            Double discount_percentage_value = Utils.extractAndConvertToPercentage(promotion_code);
            String generate_expectedMessage = null;

            //Generate the discount message
            if (discount_percentage_value != null) {
                generate_expectedMessage = expectedMessage
                        .replace("{promo_code}", promotion_code)
                        .replace("{discount_per}", String.valueOf(discount_percentage_value.intValue()));
            }

            //Assert the generated message
            Assert.assertEquals(promotion_code_message, generate_expectedMessage, "The discount code applied is invalid.");
        }
    }
}
