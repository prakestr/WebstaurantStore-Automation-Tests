@WorkTableSearch
Feature: Search and Verify Stainless Work Tables

  @VerifySearchResults
  Scenario: Search for stainless work tables and verify product titles across all pages
    Given I am on the WebstaurantStore homepage
    When I search for "stainless work table"
    Then I should see multiple pages in the search results
    And every product in the search results across all pages should contain the word "Table"

  @EmptyCartAfterAddTable
  Scenario: Add the last item from the search results to the cart and then empty the cart
    Given I am on the WebstaurantStore homepage and have searched "stainless work table"
    When I add the last product from the last page to the cart
    And I view the cart
    Then the last product "Table" should be in the cart
    When I remove all items from the cart
    Then the cart should be empty

