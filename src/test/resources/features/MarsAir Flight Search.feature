Feature: MarsAir Flight Search

  Objective: Ensure the MarsAir search functionality works as expected with proper validation, field presence, and seat availability messaging.
  @SmokeTest
  Scenario: Verify the presence of departure and return dropdown fields
    Given the user is on the MarsAir flight search page
    Then the "Departing" dropdown should be displayed
    And the "Returning" dropdown should be displayed

  Scenario Outline: Validate departure and return flight search functionality
    Given the user is on the MarsAir flight search page
    When the user selects "<departure_date>" from the "Departing" dropdown
    And the user selects "<return_date>" from the "Returning" dropdown
    And the user initiates the search
    Then the system should display "<expected_message>"

    Examples:
      | departure_date | return_date     | expected_message                                     |
      | July 2024      | December 2024  | Seats available! Call 0800 MARSAIR to book!         |
      | July 2024      | December 2024  | Sorry, there are no more seats available.           |
      | July 2024      | July 2025      | Seats available! Call 0800 MARSAIR to book!         |
      | December 2024  | December 2025  | Seats available! Call 0800 MARSAIR to book!         |

  Scenario Outline: Validate the ability to search for flights within the next two years
    Given the user is on the MarsAir flight search page
    When the user selects "<departure_date>" from the "Departing" dropdown
    And the user selects "<return_date>" from the "Returning" dropdown
    Then the system should allow the search operation to proceed

    Examples:
      | departure_date | return_date     |
      | July 2024      | December 2024  |
      | December 2024  | July 2025      |
      | July 2025      | December 2025  |
      | December 2025  | July 2026      |

  Scenario: Display an error message for unavailable seats
    Given the user is on the MarsAir flight search page
    When the user selects "July 2024" from the "Departing" dropdown
    And the user selects "December 2024" from the "Returning" dropdown
    And there are no seats available for the selected itinerary
    Then the system should display the message "Sorry, there are no more seats available."

  Scenario: Display a success message for available seats
    Given the user is on the MarsAir flight search page
    When the user selects "July 2024" from the "Departing" dropdown
    And the user selects "December 2024" from the "Returning" dropdown
    And seats are available for the selected itinerary
    Then the system should display the message "Seats available! Call 0800 MARSAIR to book!"
