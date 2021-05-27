Feature: get weather forecast
  As a authenticated user I should be able to get weather forescast

  Background: set the base url and accesskey
    Given User sets current weather api endpoint "http://api.openweathermap.org/data/2.5"
    And User authenticate the API request with "928e25597c4c07c0c25cce875d8c4987"

  
  Scenario Outline: A happy holidaymaker
    Given I like to holiday in "<city_name>" "<country>"
    And I only like to holiday on "<day>"
    When I look up the weather forecast
    Then I receive the weather forecast
    And the temperature is warmer than 10 degrees

    Examples: 
      | city_name | day      | country |
      | sydney    | Thursday | AU      |
