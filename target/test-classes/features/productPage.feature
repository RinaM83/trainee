@web
Feature: Order basket
  Background:
    Given product basket is empty
    And basket icon is visible on the navigation bar

  Scenario: check that the user can navigate to an empty basket dropdown
    When user clicks on the basket icon
    And a basket dropdown appears
