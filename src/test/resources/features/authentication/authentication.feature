Feature: Authentication

  Background:
    Given the following users are in database
      | id | email             | password | role        |
      | 1  | user@test.com     | password | ROLE_USER   |
      | 2  | artist@test.com   | password | ROLE_ARTIST |
      | 3  | admin@test.com    | password | ROLE_ADMIN  |

  Scenario: Login with valid credentials
    When HTTP request POST /auth/login with body
      """
      {
        "email": "user@test.com",
        "password": "password"
      }
      """
    Then HTTP response with status 200 and body contains
      """
      token
      """

  Scenario: Login with wrong password
    When HTTP request POST /auth/login with body
      """
      {
        "email": "user@test.com",
        "password": "wrong-password"
      }
      """
    Then HTTP response with status 401 and body contains
      """
      Unauthorized
      """

  Scenario: Register a new user
    When HTTP request POST /auth/register with body
      """
      {
        "email": "new-user@test.com",
        "password": "password",
        "role": "USER"
      }
      """
    Then HTTP response with status 200 and body contains
      """
      token
      """