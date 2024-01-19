# WebstaurantStore Automation Tests

This Selenium-Java project is designed for automated testing of the WebstaurantStore website. It encompasses a variety of tests to ensure the functionality of the website, focusing on product search and cart management.

## Project Setup and Installation

### Environment Setup
Ensure Java and Maven are installed on your system.

### Driver Configuration
The `WebDriverManager` class handles the driver setup for Chrome, Firefox, and Edge browsers based on the browser's configuration.

### Dependencies
All required dependencies are listed in the `pom.xml` file and include Selenium WebDriver, Cucumber, TestNG, and others for comprehensive testing and reporting.

## Running Tests

### Test Execution
Tests are run using the TestNG framework, with scenarios defined in Cucumber feature files. The `TestRunner` class in `src/test/java/org/webstaurantstore/runner` directory is used to initiate the tests.

### Feature Files
Scenarios are described in the feature files, like `work-table-search.feature` in the `src/test/resources/features` directory. These feature files outline the steps for various test cases, such as searching for products and verifying cart functionalities.

## Project Structure

### Base Classes
Base classes like `BaseTest` and `BasePage` provide foundational functionality for the tests and page objects.

### Page Objects
Located in `src/test/java/org/webstaurantstore/pageobjects`, these classes represent various pages of the WebstaurantStore website, such as `HomePage`, `ProductListPage`, and `CartPage`.

### Utilities and Configurations
The `ConfigLoader` class is used for loading configurations, and logging is configured via `log4j2.xml` in the `src/main/resources` directory.

## Logging and Reports

### Logging
Log4j2 is used for logging, with configurations set in `log4j2.xml`.

### Reporting
Integration with Allure is set up for generating detailed test reports.
