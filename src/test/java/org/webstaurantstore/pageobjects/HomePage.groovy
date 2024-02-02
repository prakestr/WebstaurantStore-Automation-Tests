package org.webstaurantstore.pageobjects

import org.openqa.selenium.By
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.PageFactory
import org.openqa.selenium.support.ui.ExpectedConditions



class HomePage extends BasePage {

    private WebElement searchInput = driver.findElement(By.id("searchval"))
    private WebElement searchButton = driver.findElement(By.cssSelector("button[type='submit']"))

    HomePage(WebDriver driver) {
        super(driver)
        PageFactory.initElements(driver, this)
    }

    void assurePageLoaded() {
        // Use the correct ID 'searchval' instead of 'searchInput'
        WebElement searchInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("searchval")))
        assert searchInput.displayed : "Search input is not displayed."
    }





    void searchForItem(String item) {
        WebElement searchInput = driver.findElement(By.id("searchval"))
        searchInput.clear()
        searchInput.sendKeys(item)
        searchInput.submit() // Or find the search button and click it
    }

}
