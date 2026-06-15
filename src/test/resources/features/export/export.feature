Feature: Playlist export

  Background:
    Given the following users are in database
    | id | email         | password | role      |
    | 1  | user@test.com | password | ROLE_USER |

    And the following songs are in database
    | id | title         | genre   | duration | audio_url                                 | album_id | artist_id |
    | 1  | One More Time | ELECTRO | 320      | https://cdn.example.com/one-more-time.mp3 | 1        | 1         |

    And the following playlists are in database
    | id | name        | description       | owner_id |
    | 1  | Electro Mix | Best electro songs | 1        |

    And the following playlist songs are in database
    | playlist_id | song_id | position |
    | 1           | 1       | 1        |

    And the authenticated user is
    | email         | role      |
    | user@test.com | ROLE_USER |

  Scenario: Export playlist as JSON
    When HTTP request GET /playlists/1/export?format=JSON
    Then HTTP response with status 200 and body contains
    """
    One More Time
    """

  Scenario: Export playlist as M3U
    When HTTP request GET /playlists/1/export?format=M3U
    Then HTTP response with status 200 and body contains
    """
    #EXTM3U
    """

  Scenario: Export playlist with unsupported format
    When HTTP request GET /playlists/1/export?format=XML
    Then HTTP response with status 400 and body contains
    """
    Unsupported
    """