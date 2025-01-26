Feature: Number Total Publisher
  As a service that processes numbers
  I want to receive numbers and publish running totals
  So that consumers can track cumulative sums

  Background:
    Given the service is ready to receive numbers
    And the current total is 0

  Scenario: Process single number
    When number 5 is received
    Then total 5 should be published

  Scenario: Process multiple numbers
    When these numbers are received:
      | number |
      | 3      |
      | 7      |
      | 2      |
    Then these totals should be published:
      | total |
      | 3     |
      | 10    |
      | 12    | 