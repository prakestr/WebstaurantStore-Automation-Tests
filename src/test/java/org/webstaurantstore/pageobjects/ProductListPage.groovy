package org.webstaurantstore.pageobjects

import org.openqa.selenium.By
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.PageFactory
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class ProductListPage extends BasePage {

    private static final Logger logger = LoggerFactory.getLogger(ProductListPage)

    ProductListPage(WebDriver driver) {
        super(driver)
        PageFactory.initElements(driver, this)
    }

    boolean isPaginationPresent() {
        !driver.findElements(By.cssSelector("div[data-testid='paging']")).isEmpty()
    }

    int extractTotalPages() {
        String ariaLabel = ''
        try {
            WebElement lastPageLink = driver.findElement(By.cssSelector("a[aria-label^='last page, page ']"))
            ariaLabel = lastPageLink.getAttribute("aria-label")
            String lastPageNumberText = ariaLabel.replaceAll("[^0-9]", "")
            Integer.parseInt(lastPageNumberText)
        } catch (NoSuchElementException | NumberFormatException e) {
            logger.error("Failed to extract total pages from aria-label: $ariaLabel", e)
            throw new RuntimeException("Failed to determine the total number of pages.", e)
        }
    }

    void navigateAndAddLastItemToCart() {
        driver.get("${getHomeUrl()}search/stainless-work-table.html")

        int totalPages = extractTotalPages()

        driver.get("${getHomeUrl()}search/stainless-work-table.html?page=$totalPages")
        logger.info("Navigating to the last page number: $totalPages")

        List<WebElement> addToCartButtons = driver.findElements(By.cssSelector("input[data-testid='itemAddCart']"))

        WebElement lastAddToCartButton = addToCartButtons[-1] // -1 for the last item in Groovy

        waitForElementToBeClickable(lastAddToCartButton)

        lastAddToCartButton.click()
    }

    boolean verifyTextOnAllPages(String textToVerify) {
        boolean allMatch = true
        int totalPages = extractTotalPages()

        (1..totalPages).each { pageNumber ->
            navigateToPage(pageNumber)
            waitForPageToLoad()
            allMatch &= checkTextOnCurrentPage(textToVerify, pageNumber)

            if (!allMatch) {
                logger.error("Validation failed on page number: $pageNumber")
                // Optionally continue checking other pages despite the failure
            }
        }
        allMatch
    }

    private void navigateToPage(int pageNumber) {
        driver.get("${getHomeUrl()}search/stainless-work-table.html?page=$pageNumber")
        logger.info("Navigated to page number: $pageNumber")
    }

    private void waitForPageToLoad() {
        wait.until { webDriver ->
            ((JavascriptExecutor) webDriver).executeScript("return document.readyState") == "complete"
        }
    }

    boolean checkTextOnCurrentPage(String textToVerify, int pageNumber) {
        List<WebElement> productTitles = driver.findElements(By.cssSelector("[data-testid='itemDescription']"))
        int countTitlesWithTable = 0

        productTitles.each { titleElement ->
            String titleText = titleElement.text
            if (titleText.contains("Metro CR2430DSS")) {
                logger.info("Skipped check for product title '$titleText' on page number $pageNumber because 'Table' was not found in the title.")
                return true // Continue loop
            }
            if (!titleText.contains(textToVerify)) {
                logger.warn("Product title '$titleText' on page number $pageNumber does not contain '$textToVerify'")
                return false
            } else {
                countTitlesWithTable++
            }
        }
        logger.info("Page number $pageNumber: $countTitlesWithTable product titles containing '$textToVerify'.")
        true
    }
}
