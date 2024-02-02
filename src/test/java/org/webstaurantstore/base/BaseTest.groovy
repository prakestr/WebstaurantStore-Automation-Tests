package org.webstaurantstore.base

import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.WebDriverWait
import org.openqa.selenium.interactions.Actions
import org.webstaurantstore.utilities.ConfigLoader
import org.webstaurantstore.manager.WebDriverManager
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import java.time.Duration

class BaseTest {

    protected WebDriver driver
    protected WebDriverWait wait
    protected Actions actions
    protected ConfigLoader configLoader

    protected Logger logger = LoggerFactory.getLogger(getClass())

    void setUp() {
        configLoader = new ConfigLoader()
        String browser = configLoader.browser
        driver = new WebDriverManager().getDriver(browser)
        wait = new WebDriverWait(driver, Duration.ofSeconds(30))
        actions = new Actions(driver)
    }

    String getHomeUrl() {
        configLoader.testUrl
    }

    protected void navigateTo(String url) {
        driver.get(url)
    }

    void quitWebDriver() {
        WebDriverManager.quitDriver()
    }
}
