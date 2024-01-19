package org.webstaurantstore.manager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class WebDriverManager {
    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    public WebDriver getDriver(String browser) {
        if (driverThreadLocal.get() == null) {
            System.out.println("Instantiating a new driver instance for browser: " + browser);
            WebDriver driver;
            switch (browser.toLowerCase()) {
                case "firefox" -> {
                    io.github.bonigarcia.wdm.WebDriverManager.firefoxdriver().setup();
                    driver = new FirefoxDriver();
                }
                case "edge" -> {
                    io.github.bonigarcia.wdm.WebDriverManager.edgedriver().setup();
                    driver = new EdgeDriver();
                }
                case "chrome" -> {
                    io.github.bonigarcia.wdm.WebDriverManager.chromedriver().setup();
                    ChromeOptions options = new ChromeOptions();
                    if (Boolean.parseBoolean(System.getProperty("headless", "false"))) {
                        options.addArguments("--headless");
                    }
                    driver = new ChromeDriver(options);
                }
                default -> throw new IllegalArgumentException("The browser " + browser + " is not supported.");
            }
            driver.manage().window().maximize();
            driverThreadLocal.set(driver);
        }
        return driverThreadLocal.get();
    }

    public static void quitDriver() {
        if (driverThreadLocal.get() != null) {
            driverThreadLocal.get().quit();
            driverThreadLocal.remove();
        }
    }
}
