package org.webstaurantstore.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.webstaurantstore.utilities.ConfigLoader;


import java.time.Duration;

public class BasePage {
    protected WebDriver driver;
    protected JavascriptExecutor jse;
    protected Actions actions;
    protected WebDriverWait wait;

    private ConfigLoader configLoader;


    public BasePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        jse = (JavascriptExecutor) driver;
        actions = new Actions(driver);
        configLoader = new ConfigLoader();
    }

    protected void clickButton(WebElement button) {
        waitForElementToBeClickable(button);
        button.click();
    }

    public String getHomeUrl() {
        // This method will return the URL from the configuration file
        return configLoader.getTestUrl();
    }

    protected void setTextElementText(WebElement textElement, String value) {
        clearText(textElement);
        textElement.sendKeys(value);
    }

    public void clearText(WebElement element) {
        element.clear();
    }


    public WebElement waitForVisibilityOfElement(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected WebElement waitForVisibilityOfElement(WebElement element) {
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    public WebElement waitForElementToBeClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public void waitForElementToBeClickable(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public void waitForUrl(String expectedFragment) {
        wait.until(ExpectedConditions.urlContains(expectedFragment));

    }


}