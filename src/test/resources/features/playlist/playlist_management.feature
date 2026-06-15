Feature: Playlist management

  Background:
    Given the following users are in database
    | id | email         | password | role      |
    | 1  | user@test.com | password | ROLE_USER |

    And the following playlists are in database
    | id | name          | description        | owner_id |
    | 1  | My Playlist   | My favorite songs  | 1        |

    And the authenticated user is
    | email         | role      |
    | user@test.com | ROLE_USER |

  Scenario: Get playlist by id
    When HTTP request GET /playlists/1
    Then HTTP response with status 200 and body contains
    """
    My Playlist
    """

  Scenario: Create playlist
    When HTTP request POST /playlists with body
    """
    {
    "name": "Workout Playlist",
    "description": "Songs for training",
    "ownerId": 1
    }
    """
    Then HTTP response with status 200 and body contains
    """
    Workout Playlist
    """

  Scenario: Update playlist
    When HTTP request PUT /playlists/1 with body
    """
    {
    "name": "Updated Playlist",
    "description": "Updated description",
    "ownerId": 1
    }
    """
    Then HTTP response with status 200 and body contains
    """
    Updated Playlist
    """