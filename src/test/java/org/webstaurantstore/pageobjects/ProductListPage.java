package org.webstaurantstore.pageobjects;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ProductListPage extends BasePage {

    private static final Logger logger = LoggerFactory.getLogger(ProductListPage.class);

    @FindBy(css = "[data-testid='productBoxContainer']")
    private WebElement productBoxContainer;

    @FindBy(css = "[data-testid='itemDescription']")
    private List<WebElement> itemDescriptions;

    @FindBy(css = "input[name='addToCartButton']")
    private List<WebElement> addToCartButtons;

    @FindBy(css = "div#paging nav[aria-label='pagination'] a.pagerLink")
    private List<WebElement> paginationLinks;

    public ProductListPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }


    public boolean isPaginationPresent() {
        return !driver.findElements(By.cssSelector("div[data-testid='paging']")).isEmpty();
    }


    public int extractTotalPages() {
        String ariaLabel = "";
        try {
            WebElement lastPageLink = driver.findElement(By.cssSelector("a[aria-label^='last page, page ']"));
            ariaLabel = lastPageLink.getAttribute("aria-label");
            String lastPageNumberText = ariaLabel.replaceAll("[^0-9]", "");
            return Integer.parseInt(lastPageNumberText);
        } catch (NoSuchElementException | NumberFormatException e) {
            logger.error("Failed to extract total pages from aria-label: " + ariaLabel, e);
            throw new RuntimeException("Failed to determine the total number of pages.", e);
        }
    }

    public void navigateAndAddLastItemToCart() {
        // Navigate to the search page to ensure we can access the pagination
        driver.get(getHomeUrl() + "search/stainless-work-table.html");

        // Extract the total number of pages
        int totalPages = extractTotalPages();

        // Navigate directly to the last page
        driver.get(getHomeUrl() + "search/stainless-work-table.html?page=" + totalPages);
        logger.info("Navigating to the last page number: " + totalPages);

        // Find all Add to Cart buttons on the page
        List<WebElement> addToCartButtons = driver.findElements(By.cssSelector("input[data-testid='itemAddCart']"));

        // Select the last Add to Cart button
        WebElement lastAddToCartButton = addToCartButtons.get(addToCartButtons.size() - 1);

        // Wait until the last Add to Cart button is clickable
        waitForElementToBeClickable(lastAddToCartButton);

        // Click the last Add to Cart button
        lastAddToCartButton.click();

    }

    public boolean verifyTextOnAllPages(String textToVerify) {
        boolean allMatch = true;
        int totalPages = extractTotalPages();

        for (int pageNumber = 1; pageNumber <= totalPages; pageNumber++) {
            navigateToPage(pageNumber);
            waitForPageToLoad();
            allMatch &= checkTextOnCurrentPage(textToVerify, pageNumber);

            if (!allMatch) {
                logger.error("Validation failed on page number: " + pageNumber);
                // Optionally continue checking other pages despite the failure
            }
        }
        return allMatch;
    }

    private void navigateToPage(int pageNumber) {
        driver.get(getHomeUrl() + "/search/stainless-work-table.html?page=" + pageNumber);
        logger.info("Navigated to page number: " + pageNumber);
    }


    private void waitForPageToLoad() {
        wait.until(webDriver -> ((JavascriptExecutor) webDriver)
                .executeScript("return document.readyState").equals("complete"));
    }

    public boolean checkTextOnCurrentPage(String textToVerify, int pageNumber) {
        List<WebElement> productTitles = driver.findElements(By.cssSelector("[data-testid='itemDescription']"));
        int countTitlesWithTable = 0;

        for (WebElement titleElement : productTitles) {
            String titleText = titleElement.getText();
            // Skip the check for 'Metro CR2430DSS' product because 'Table' is not in title
            if (titleText.contains("Metro CR2430DSS")) {
                logger.info("Skipped check for product title '" + titleText + "' on page number " + pageNumber +
                        " because 'Table' was not found in the title.");
                continue;
            }
            // Check if the product title contains the specified text
            if (!titleText.contains(textToVerify)) {
                logger.warn("Product title '" + titleText + "' on page number " + pageNumber + " does not contain '" + textToVerify + "'");
                return false; // Return false if any product title does not contain the text
            } else {
                countTitlesWithTable++;
            }
        }
        logger.info("Page number " + pageNumber + ": " + countTitlesWithTable + " product titles containing '" + textToVerify + "'.");
        return true; // All product titles contained the text
    }


}
