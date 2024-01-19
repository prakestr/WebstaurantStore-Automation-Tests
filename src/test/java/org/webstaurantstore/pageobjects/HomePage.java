package org.webstaurantstore.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage {

    @FindBy(id = "searchval")
    private WebElement searchInput;

    @FindBy(css = "button[type='submit']")
    private WebElement searchButton;


    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void assurePageLoaded() {
        waitForVisibilityOfElement(searchInput);
        waitForVisibilityOfElement(searchButton);
    }

    public void searchForItem(String item) {
        assurePageLoaded();
        setTextElementText(searchInput, item);
        clickButton(searchButton);
    }


}
