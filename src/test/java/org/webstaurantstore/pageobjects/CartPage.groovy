package org.webstaurantstore.pageobjects

import org.openqa.selenium.By
import org.openqa.selenium.TimeoutException
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.PageFactory
import org.openqa.selenium.support.ui.ExpectedConditions

class CartPage extends BasePage {

    CartPage(WebDriver driver) {
        super(driver)
        PageFactory.initElements(driver, this)
    }

    private WebElement getCartItemsContainer() {
        driver.findElement(By.cssSelector("div.cartItems"))
    }

    private WebElement getEmptyCartButton() {
        driver.findElement(By.cssSelector("button.emptyCartButton"))
    }

    private WebElement getLastProductDescription() {
        driver.findElement(By.cssSelector("span.itemDescription a"))
    }

    private WebElement getCartHeader() {
        driver.findElement(By.cssSelector("div.cartItemsHeader"))
    }

    private WebElement getEmptyCartMessage() {
        driver.findElement(By.cssSelector("p.header-1"))
    }

    void viewCart() {
        WebElement notification = waitForVisibilityOfElement(By.cssSelector("div[data-role='notification']"))
        WebElement viewCartButton = notification.findElement(By.cssSelector("a[href*='viewcart.cfm']"))
        WebElement checkoutButton = notification.findElement(By.cssSelector("a[href*='viewinfo.cfm']"))

        assert viewCartButton.isDisplayed() : "'View Cart' button is not displayed in the notification"
        assert checkoutButton.isDisplayed() : "'Checkout' button is not displayed in the notification"

        viewCartButton.click()
        // Wait for the URL to contain 'cart', indicating that the cart page is loaded
        waitForUrl("cart")

        // Wait for the cart header to be visible
        WebElement cartHeader = waitForVisibilityOfElement(By.cssSelector("h1.page-header"))

        // Assert that the cart header is displayed and the text equals 'Cart', ignoring case
        assert cartHeader.isDisplayed() : "'Cart' header is not displayed on the cart page"
        assert cartHeader.getText().equalsIgnoreCase("Cart") : "The text of the 'Cart' header does not match 'Cart'"
    }

    boolean isCartDisplayed() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.cartItems")))
        getCartItemsContainer().isDisplayed()
    }

    boolean isLastProductInCart(String productName) {
        WebElement lastProductDescription = getLastProductDescription()
        wait.until(ExpectedConditions.visibilityOf(lastProductDescription))
        lastProductDescription.getText().contains(productName)
    }

    void removeAllItemsFromCart() {
        WebElement emptyCartButton = getEmptyCartButton()
        wait.until(ExpectedConditions.visibilityOf(emptyCartButton))
        if (emptyCartButton.isDisplayed()) {
            emptyCartButton.click()

            WebElement emptyCartTitle = waitForVisibilityOfElement(By.id("empty-cart-title"))
            assert emptyCartTitle.isDisplayed() : "Empty Cart title is not displayed"

            WebElement emptyCartConfirmButton = waitForVisibilityOfElement(By.cssSelector("[data-testid='modal-footer'] > button:nth-child(1)"))
            assert emptyCartConfirmButton.isDisplayed() : "Empty Cart button is not displayed"

            WebElement cancelButton = waitForVisibilityOfElement(By.cssSelector("[data-testid='modal-footer'] > button:nth-child(2)"))
            assert cancelButton.isDisplayed() : "Cancel button is not displayed"

            emptyCartConfirmButton.click()
        } else {
            throw new IllegalStateException("Empty Cart button not found")
        }
    }

    boolean isCartEmpty() {
        try {
            WebElement emptyCartMessageElement = waitForVisibilityOfElement(By.cssSelector("p.header-1"))
            return emptyCartMessageElement.text.contains("Your cart is empty.")
        } catch (TimeoutException e) {
            // Handle the case where the element is not found within the wait period
            return false;
        }
    }


}
