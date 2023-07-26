@web
Feature: Order basket
  Background:
    Given basket icon is visible on the navigation bar

  Scenario: Test case number 1: navigation to an empty basket page
    When user clicks on the basket icon
    And a basket dropdown appears
    And user clicks on "Go to the basket" button
    Then user navigates to the basket page

  Scenario Outline: Test case number = <num>: adding product(-s) with discount = <has_discount> to the basket where already products in the cart initial_num=<initial_num>
#    And if initial_num=<initial_num> > 0, this number of products with discount = <initial_has_discount> and count = <initial_count> added to the basket
    When initial_num=<initial_num> product added to the basket and user adds product(-s) with discount=<has_discount> with Number of products=<product_num> and quantity of each of the products Product count=<product_count> to the basket
    And on the nav bar, the Product count=<product_count> is displayed next to the basket icon
    When user clicks on the basket icon
    And a basket dropdown appears
    And user clicks on "Go to the basket" button
    Then user navigates to the basket page

    Examples:
      |num| initial_num|initial_has_discount|initial_count|product_num | product_count | has_discount |
      |4  |1           | yes                |1            |8           | 1             | all          |



