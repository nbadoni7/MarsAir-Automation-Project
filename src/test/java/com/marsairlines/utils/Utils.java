package com.marsairlines.utils;

public class Utils {

    public static Double extractAndConvertToPercentage(String input) {
        // Validate the input format (e.g., "XX9-XXX-999")
        if (input == null || input.length() < 3 || !input.matches("^[A-Z]{2}[0-9]-[A-Z]{3}-[0-9]{3}$")) {
            return null; // Invalid format
        }

        // Extract the 3rd character
        char thirdChar = input.charAt(2);

        // Check if the character is a digit
        if (Character.isDigit(thirdChar)) {
            // Convert the digit to a percentage value
            int number = Character.getNumericValue(thirdChar);
            return number * 10.0; // Convert to percentage (e.g., 3 becomes 30.0)
        }

        // If the 3rd character is not a digit, return null
        return null;
    }
}
