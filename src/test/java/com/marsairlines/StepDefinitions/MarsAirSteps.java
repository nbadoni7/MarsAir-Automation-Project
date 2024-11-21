package com.marsairlines.StepDefinitions;

import com.marsairlines.pages.MarsAirHomePage;
import com.marsairlines.utils.DriverFactory;

import io.cucumber.java.en.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

public class MarsAirSteps {

    private static final Logger logger = LogManager.getLogger(MarsAirSteps.class);
    private MarsAirHomePage marsAirHomePage;

    private WebDriver driver;

    // Initialize page objects using the DriverFactory
    public MarsAirSteps() {
        this.driver = DriverFactory.getInstance().getDriver();

        // Initialize MarsAirHomePage with the WebDriver instance
        this.marsAirHomePage = new MarsAirHomePage(driver);
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
        String actualMessage = marsAirHomePage.getFlightOptionsMessage();
        assertEquals(expectedMessage, actualMessage, "The displayed message does not match the expected value.");
        logger.info("Verified message: {}", expectedMessage);
    }

    @Then("the system should display the message {string}")
    public void theSystemShouldDisplayTheMessage(String expectedErrorMessage) {
        String actualErrorMessage = marsAirHomePage.getErrorMessage();
        assertEquals(expectedErrorMessage, actualErrorMessage, "The error message does not match the expected value.");
        logger.info("Verified error message: {}", expectedErrorMessage);
    }

    @And("there are no seats available for the selected itinerary")
    public void thereAreNoSeatsAvailableForTheSelectedItinerary() {
        String actualMessage = marsAirHomePage.getFlightOptionsMessage();
        assertEquals("Sorry, there are no more seats available.", actualMessage, "The message does not indicate unavailability of seats.");
        logger.info("Verified message: No seats available.");
    }

    @And("seats are available for the selected itinerary")
    public void seatsAreAvailableForTheSelectedItinerary() {
        String actualMessage = marsAirHomePage.getFlightOptionsMessage();
        assertEquals("Seats available! Call 0800 MARSAIR to book!", actualMessage, "The message does not indicate seat availability.");
        logger.info("Verified message: Seats available.");
    }

    @Then("the system should allow the search operation to proceed")
    public void theSystemShouldAllowTheSearchOperationToProceed() {
        String flightOptionsMessage = marsAirHomePage.getFlightOptionsMessage();
        assertNotNull(flightOptionsMessage, "The search operation did not proceed as expected.");
        logger.info("Search operation proceeded successfully.");
    }
}
