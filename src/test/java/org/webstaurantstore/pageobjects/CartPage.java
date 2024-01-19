package org.webstaurantstore.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static org.testng.Assert.assertTrue;

public class CartPage extends BasePage {

    @FindBy(css = "div.cartItems")
    private WebElement cartItemsContainer;

    @FindBy(css = "button.emptyCartButton")
    private WebElement emptyCartButton;

    @FindBy(css = "span.itemDescription a")
    private WebElement lastProductDescription;

    @FindBy(css = "div.cartItemsHeader")
    private WebElement cartHeader;

    @FindBy(css = "p.header-1")
    private WebElement emptyCartMessage;

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public void viewCart() {
        // Wait for the notification pop-up to appear
        WebElement notification = waitForVisibilityOfElement(By.cssSelector("div[data-role='notification']"));
        WebElement viewCartButton = notification.findElement(By.cssSelector("a[href*='viewcart.cfm']"));
        WebElement checkoutButton = notification.findElement(By.cssSelector("a[href*='viewinfo.cfm']"));

        // Verify 'View Cart' button is displayed in the notification
        assertTrue(viewCartButton.isDisplayed(), "'View Cart' button is not displayed in the notification");

        // Verify that 'Checkout' button is displayed in the notification
        assertTrue(checkoutButton.isDisplayed(), "'Checkout' button is not displayed in the notification");

        // Click the 'View Cart' button to navigate to the cart page
        viewCartButton.click();

        // Wait for the cart page URL to be confirmed
        waitForUrl("cart");

        // Additional verification to check for the page header 'Cart' being visible on the cart page
        WebElement cartHeader = waitForVisibilityOfElement(By.cssSelector("h1.page-header"));
        assertTrue(cartHeader.isDisplayed() && cartHeader.getText().equals("Cart"), "'Cart' header is not displayed on the cart page");
    }


    public boolean isCartDisplayed() {
        // Check if the cart items container is displayed
        return cartItemsContainer.isDisplayed();
    }

    public boolean isLastProductInCart(String productName) {
        // Check if the last product in the cart matches the provided product name
        return waitForVisibilityOfElement(lastProductDescription).getText().contains(productName);
    }

    public void removeAllItemsFromCart() {
        // Click the empty cart button if it's visible
        WebElement emptyCartButton = waitForVisibilityOfElement(By.cssSelector("button.emptyCartButton"));
        if (emptyCartButton.isDisplayed()) {
            emptyCartButton.click();

            // Verify the Empty Cart modal title is displayed
            WebElement emptyCartTitle = waitForVisibilityOfElement(By.id("empty-cart-title"));
            assertTrue(emptyCartTitle.isDisplayed(), "Empty Cart title is not displayed");

            // Verify 'Empty Cart' button is displayed
            WebElement emptyCartConfirmButton = waitForVisibilityOfElement(By.cssSelector("[data-testid=\"modal-footer\"] > button:nth-child(1)\n"));
            assertTrue(emptyCartConfirmButton.isDisplayed(), "Empty Cart button is not displayed");

            // Verify 'Cancel' button is displayed
            WebElement cancelButton = waitForVisibilityOfElement(By.cssSelector("[data-testid=\"modal-footer\"] > button:nth-child(2)\n"));
            assertTrue(cancelButton.isDisplayed(), "Cancel button is not displayed");

            // Click 'Empty Cart' button to confirm emptying the cart
            emptyCartConfirmButton.click();
        } else {
            throw new IllegalStateException("Empty Cart button not found");
        }
    }

    public boolean isCartEmpty() {
        // Check if the cart empty message is displayed
        return waitForVisibilityOfElement(emptyCartMessage).getText().contains("Your cart is empty.");
    }
}
