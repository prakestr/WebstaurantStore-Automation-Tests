package org.webstaurantstore.stepdefinitions;


import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.webstaurantstore.base.BaseTest;
import org.webstaurantstore.pageobjects.CartPage;
import org.webstaurantstore.pageobjects.HomePage;
import org.webstaurantstore.pageobjects.ProductListPage;

import static org.junit.Assert.assertTrue;

public class ProductSearchAndCartSteps extends BaseTest {


    private HomePage homePage;
    private ProductListPage productListPage;
    private CartPage cartPage;

    @Before
    public void setup() {
        try {
            super.setUp();
            checkDriverInitialized();
            initializePages();
        } catch (Exception e) {
            logger.error("Setup failed", e);
        }
    }

    private void checkDriverInitialized() {
        if (driver == null) {
            throw new IllegalStateException("Driver initialization failed.");
        }
    }

    private void initializePages() {
        homePage = new HomePage(driver);
        productListPage = new ProductListPage(driver);
        cartPage = new CartPage(driver);
    }

    @Given("I am on the WebstaurantStore homepage")
    public void onHomepage() {
        navigateTo(getHomeUrl());
        homePage.assurePageLoaded();
    }


    @When("I search for {string}")
    public void search(String searchQuery) {
        homePage.searchForItem(searchQuery);
    }

    @Then("I should see multiple pages in the search results")
    public void verifyMultiplePages() {
        assertTrue("Multiple pages not present", productListPage.isPaginationPresent());
    }

    @Then("every product in the search results across all pages should contain the word {string}")
    public void validateTitlesAcrossPages(String wordInTitle) {
        boolean allTitlesContainWord = productListPage.verifyTextOnAllPages(wordInTitle);
        assertTrue("Not all product titles contain '" + wordInTitle + "'", allTitlesContainWord);

    }


    @Given("I am on the WebstaurantStore homepage and have searched {string}")
    public void onHomepageAndSearchPerformed(String searchQuery) {
        onHomepage();
        search(searchQuery);
    }

    @When("I add the last product from the last page to the cart")
    public void addLastProductToCart() {
        productListPage.navigateAndAddLastItemToCart();
    }

    @When("I view the cart")
    public void openCartView() {
        cartPage.viewCart();
        assertTrue("Cart is not displayed", cartPage.isCartDisplayed());
    }

    @Then("the last product {string} should be in the cart")
    public void verifyLastProductInCart(String wordInTitle) {
        assertTrue("Last product not in cart", cartPage.isLastProductInCart(wordInTitle));
    }

    @When("I remove all items from the cart")
    public void emptyCartContents() {
        cartPage.removeAllItemsFromCart();
    }

    @Then("the cart should be empty")
    public void theCartShouldBeEmpty() {
        assertTrue("Cart is not empty", cartPage.isCartEmpty());
    }


    @After
    public void tearDown() {
        quitWebDriver();
    }

}

