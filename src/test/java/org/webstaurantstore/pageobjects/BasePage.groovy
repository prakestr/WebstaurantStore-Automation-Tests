package org.webstaurantstore.pageobjects

import org.openqa.selenium.By
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.interactions.Actions
import org.openqa.selenium.support.PageFactory
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import org.webstaurantstore.utilities.ConfigLoader

import java.time.Duration

class BasePage {
    protected WebDriver driver
    protected JavascriptExecutor jse
    protected Actions actions
    protected WebDriverWait wait

    private ConfigLoader configLoader = new ConfigLoader()

    BasePage(WebDriver driver) {
        this.driver = driver
        PageFactory.initElements(driver, this)
        wait = new WebDriverWait(driver, Duration.ofSeconds(30))
        jse = driver as JavascriptExecutor
        actions = new Actions(driver)
    }

    protected void clickButton(WebElement button) {
        waitForElementToBeClickable(button).click()
    }

    String getHomeUrl() {
        configLoader.testUrl
    }

    protected void setTextElementText(WebElement textElement, String value) {
        textElement.clear()
        textElement.sendKeys(value)
    }

    WebElement waitForVisibilityOfElement(By locator) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator))
    }

    protected WebElement waitForVisibilityOfElement(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element))
    }

    WebElement waitForElementToBeClickable(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator))
    }

    void waitForElementToBeClickable(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element))
    }

    void waitForUrl(String expectedFragment) {
        wait.until(ExpectedConditions.urlContains(expectedFragment))
    }
}
