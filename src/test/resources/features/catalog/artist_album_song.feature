Feature: Artist album and song catalog

  Background:
    Given the following users are in database
    | id | email           | password | role        |
    | 1  | artist@test.com | password | ROLE_ARTIST |

    And the following artists are in database
    | id | name      | bio              | user_id |
    | 1  | Daft Punk | Electronic music | 1       |

    And the following albums are in database
    | id | title     | artist_id | release_date |
    | 1  | Discovery | 1         | 2001-03-12   |

    And the authenticated user is
    | email           | role        |
    | artist@test.com | ROLE_ARTIST |

  Scenario: Get albums of an artist
    When HTTP request GET /artists/1/albums
    Then HTTP response with status 200 and body contains
    """
    Discovery
    """

  Scenario: Create album for artist
    When HTTP request POST /artists/1/albums with body
    """
    {
    "title": "Random Access Memories",
    "releaseDate": "2013-05-17"
    }
    """
    Then HTTP response with status 200 and body contains
    """
    Random Access Memories
    """

  Scenario: Add song to album
    When HTTP request POST /artists/1/albums/1/songs with body
    """
    {
    "title": "One More Time",
    "genre": "ELECTRO",
    "duration": 320,
    "audioUrl": "https://cdn.example.com/one-more-time.mp3"
    }
    """
    Then HTTP response with status 200 and body contains
    """
    One More Time
    """