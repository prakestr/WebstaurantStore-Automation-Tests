package org.webstaurantstore.base;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.interactions.Actions;
import org.webstaurantstore.utilities.ConfigLoader;
import org.webstaurantstore.manager.WebDriverManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class BaseTest {

    protected WebDriver driver;
    protected WebDriverWait wait;
    protected Actions actions;
    private ConfigLoader configLoader;

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    public void setUp() {
        configLoader = new ConfigLoader();
        String browser = configLoader.getBrowser();
        driver = new WebDriverManager().getDriver(browser);
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        actions = new Actions(driver);
    }

    public String getHomeUrl() {
        return configLoader.getTestUrl();
    }


    protected void navigateTo(String url) {
        driver.get(url);
    }


    // This method is used by the step definitions to quit the WebDriver
    public void quitWebDriver() {
        WebDriverManager.quitDriver();
    }


}
