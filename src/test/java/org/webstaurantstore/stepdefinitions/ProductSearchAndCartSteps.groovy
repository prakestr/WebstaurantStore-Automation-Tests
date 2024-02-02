package org.webstaurantstore.stepdefinitions

import io.cucumber.java.After
import io.cucumber.java.Before
import io.cucumber.java.en.Given
import io.cucumber.java.en.When
import io.cucumber.java.en.Then
import org.openqa.selenium.JavascriptExecutor
import org.webstaurantstore.base.BaseTest
import org.webstaurantstore.pageobjects.CartPage
import org.webstaurantstore.pageobjects.HomePage
import org.webstaurantstore.pageobjects.ProductListPage

import static org.junit.Assert.assertTrue

class ProductSearchAndCartSteps extends BaseTest {

    private HomePage homePage
    private ProductListPage productListPage
    private CartPage cartPage

    @Before
    void setup() {
        super.setUp()  // Ensure the base setup is completed, including driver initialization

        // Check if the driver was initialized successfully in the base setup
        if (!driver) {
            throw new IllegalStateException("Driver initialization failed.")
        }

        navigateTo(getHomeUrl())  // Navigate to the homepage at the start of each scenario

        // Initialize page objects only after ensuring the driver is ready and navigated to the homepage
        homePage = new HomePage(driver)
        productListPage = new ProductListPage(driver)
        cartPage = new CartPage(driver)

        // Ensure the homepage is fully loaded before proceeding with the test steps
        homePage.assurePageLoaded()
    }



    private void checkDriverInitialized() {
        if (!driver) {
            throw new IllegalStateException("Driver initialization failed.")
        }
    }

    private void initializePages() {
        println "Initializing HomePage"
        homePage = new HomePage(driver)
        println "HomePage initialized with driver: $driver"
        // Ensure the driver has navigated to the homepage before initializing further
        homePage.assurePageLoaded()
        productListPage = new ProductListPage(driver)
        cartPage = new CartPage(driver)
    }


    @Given("I am on the WebstaurantStore homepage")
    void onHomepage() {
        navigateTo(getHomeUrl())
        // Additional wait to ensure the page is fully loaded if not included in `navigateTo`
        wait.until { wd ->
            ((JavascriptExecutor) wd).executeScript("return document.readyState") == 'complete'
        }
        homePage.assurePageLoaded()
    }


    @When("I search for {string}")
    void search(String searchQuery) {
        homePage.searchForItem(searchQuery)
    }

    @Then("I should see multiple pages in the search results")
    void verifyMultiplePages() {
        assertTrue("Multiple pages not present", productListPage.isPaginationPresent())
    }

    @Then("every product in the search results across all pages should contain the word {string}")
    void validateTitlesAcrossPages(String wordInTitle) {
        assertTrue("Not all product titles contain '$wordInTitle'", productListPage.verifyTextOnAllPages(wordInTitle))
    }

    @Given("I am on the WebstaurantStore homepage and have searched {string}")
    void onHomepageAndSearchPerformed(String searchQuery) {
        onHomepage()
        search(searchQuery)
    }

    @When("I add the last product from the last page to the cart")
    void addLastProductToCart() {
        productListPage.navigateAndAddLastItemToCart()
    }

    @When("I view the cart")
    void openCartView() {
        cartPage.viewCart()
        assertTrue("Cart is not displayed", cartPage.isCartDisplayed())
    }

    @Then("the last product {string} should be in the cart")
    void verifyLastProductInCart(String wordInTitle) {
        assertTrue("Last product not in cart", cartPage.isLastProductInCart(wordInTitle))
    }

    @When("I remove all items from the cart")
    void emptyCartContents() {
        cartPage.removeAllItemsFromCart()
    }

    @Then("the cart should be empty")
    void theCartShouldBeEmpty() {
        assertTrue("Cart is not empty", cartPage.isCartEmpty())
    }

    @After
    void tearDown() {
        quitWebDriver()
    }
}
