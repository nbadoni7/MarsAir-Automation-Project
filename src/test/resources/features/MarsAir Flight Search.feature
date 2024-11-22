Feature: MarsAir Flight Search

  Objective: Ensure the MarsAir search functionality works as expected with proper validation, field presence, and seat availability messaging.
  @RegTest
  #Automation TC- TS01_TC007, Date: 21-Nov-2024,Author -Neeti
  Scenario: Verify the presence of departure and return dropdown fields
    Given the user is on the MarsAir flight search page
    Then the "Departing" dropdown should be displayed
    And the "Returning" dropdown should be displayed

  @RegTest
  #Automation TC- TS01_TC007, Date: 21-Nov-2024,Author -Neeti
  Scenario: Validate departure and return flight search functionality with Empty values
    Given the user is on the MarsAir flight search page
    When the user selects "" from the "Departing" dropdown
    And the user selects "" from the "Returning" dropdown
    And the user initiates the search
    And the search results page should not be displayed

#  @runTest
#  Scenario Outline: Validate departure and return flight search functionality
#    Given the user is on the MarsAir flight search page
#    When the user selects "<departure_date>" from the "Departing" dropdown
#    And the user selects "<return_date>" from the "Returning" dropdown
#    And the user initiates the search
#    Then the system should display "<expected_message>"
#
#    Examples:
#      | departure_date   | return_date                   | expected_message                            |
#      | July             | July (next year)              | Sorry, there are no more seats available.   |
#      | July             | December (next year)          | Sorry, there are no more seats available.   |
#      | July             | December (two years from now) | Seats available! Call now on 0800 MARSAIR to book! |

#  @runTest
#  Scenario Outline: Validate the ability to search for flights within the next two years
#    Given the user is on the MarsAir flight search page
#    When the user selects "<departure_date>" from the "Departing" dropdown
#    And the user selects "<return_date>" from the "Returning" dropdown
#    And the user initiates the search
#    Then the system should allow the search operation to proceed
#
#    Examples:
#      | departure_date   | return_date                   |
#      | July             | December                      |
#      | December         | July (next year)              |
#      | July (next year) | December (two years from now) |
#      | December         | December (two years from now) |
#
#  Scenario: Display an error message for unavailable seats
#    Given the user is on the MarsAir flight search page
#    When the user selects "July" from the "Departing" dropdown
#    And the user selects "July (next year)" from the "Returning" dropdown
#    And there are no seats available for the selected itinerary
#    Then the system should display the message "Sorry, there are no more seats available."
#
#  Scenario: Display a success message for available seats
#    Given the user is on the MarsAir flight search page
#    When the user selects "July" from the "Departing" dropdown
#    And the user selects "December (two years from now)" from the "Returning" dropdown
#    And seats are available for the selected itinerary
#    Then the system should display the message "Seats available! Call now on 0800 MARSAIR to book!"

  @runTest
  Scenario Outline: Verify Valid/Invalid Promotional Code Application
    Given the user is on the MarsAir flight search page
    When the user selects "<departure_date>" from the "Departing" dropdown
    And the user selects "<return_date>" from the "Returning" dropdown
    And the user enters a valid promotional code "<promotion_code>"
    And the user initiates the search
    Then the system should display the expected message as "<expected_message>" for "<promotion_code>" promotion code

    Examples:
    | departure_date   | return_date                   | promotion_code | expected_message                            |
    | July             | December (two years from now) | AF3-FJK-418    | Promotional code {promo_code} used: {discount_per}% discount!   |
    | July             | December (two years from now) | AF1-FJK-418    | Sorry, code {promo_code} is not valid |
    | July             | December (two years from now) | JJK-OPQ-320    | Sorry, code {promo_code} is not valid |
    | July             | December (two years from now) | JJ5-OPQ-320    | Promotional code {promo_code} used: {discount_per}% discount! |
