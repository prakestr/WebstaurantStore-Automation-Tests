# WebstaurantStore Automation Tests

This Selenium-Java project is designed for automated testing of the WebstaurantStore website. It encompasses a variety of tests to ensure the functionality of the website, focusing on product search and cart management.

## Prerequisites

- Java SDK 20

## Project Setup and Installation

### Environment Setup
Ensure Java SDK 20 is installed on your system. This project uses Java SDK 20 for its features and improvements.

### Driver Configuration
The `WebDriverManager` class handles the driver setup for Chrome, Firefox, and Edge browsers based on the browser's configuration.

### Dependencies
All required dependencies are listed in the `pom.xml` file and include Selenium WebDriver, Cucumber, TestNG, and others for comprehensive testing and reporting.

## Running Tests

### Test Execution
Tests are executed in parallel using the TestNG framework, enhancing the speed and efficiency of the test runs. The scenarios for the tests are defined in Cucumber feature files, and the `TestRunner` class located in the `src/test/java/org/webstaurantstore/runner` directory is configured to trigger these tests.

For instance, the feature file `work-table-search.feature` in the `src/test/resources/features` directory contains scenarios that can be run directly. This file includes scenarios such as:

- Verifying that multiple pages of search results are displayed when searching for "stainless work table".
- Ensuring that every product in the search results contains the word "Table".
- Adding the last item from the search results to the cart and ensuring the cart can be emptied.

These scenarios are annotated with Cucumber tags like `@VerifySearchResults` and `@EmptyCartAfterAddTable`, allowing for selective test execution.

## Project Structure

### Base Classes
Base classes like `BaseTest` and `BasePage` provide foundational functionality for the tests and page objects.

### Page Objects
Located in `src/test/java/org/webstaurantstore/pageobjects`, these classes represent various pages of the WebstaurantStore website, such as `HomePage`, `ProductListPage`, and `CartPage`.

### Utilities and Configurations
The `ConfigLoader` class is used for loading configurations, and logging is configured via `log4j2.xml` in the `src/main/resources` directory.

### Logging
Log4j2 is used for logging, with configurations set in `log4j2.xml`.
