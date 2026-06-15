Feature: Playlist songs management

  Background:
    Given the following users are in database
    | id | email         | password | role      |
    | 1  | user@test.com | password | ROLE_USER |

    And the following artists are in database
    | id | name      | bio              | user_id |
    | 1  | Daft Punk | Electronic music | 1       |

    And the following albums are in database
    | id | title     | artist_id | release_date |
    | 1  | Discovery | 1         | 2001-03-12   |

    And the following songs are in database
    | id | title         | genre   | duration | audio_url                                      | album_id | artist_id |
    | 1  | One More Time | ELECTRO | 320      | https://cdn.example.com/one-more-time.mp3      | 1        | 1         |
    | 2  | Digital Love   | ELECTRO | 300      | https://cdn.example.com/digital-love.mp3        | 1        | 1         |

    And the following playlists are in database
    | id | name        | description       | owner_id |
    | 1  | Electro Mix | Best electro songs | 1        |

    And the authenticated user is
    | email         | role      |
    | user@test.com | ROLE_USER |

  Scenario: Add song to playlist
    When HTTP request POST /playlists/1/songs/1
    Then HTTP response with status 200 and body contains
    """
    One More Time
    """

  Scenario: Remove song from playlist
    Given the following playlist songs are in database
    | playlist_id | song_id | position |
    | 1           | 1       | 1        |
    When HTTP request DELETE /playlists/1/songs/1
    Then HTTP response with status 200 and body contains
    """
    removed
    """

  Scenario: Reorder playlist songs
    Given the following playlist songs are in database
    | playlist_id | song_id | position |
    | 1           | 1       | 1        |
    | 1           | 2       | 2        |
    When HTTP request PUT /playlists/1/songs/reorder with body
    """
    {
    "songIds": [2, 1]
    }
    """
    Then HTTP response with status 200 and body contains
    """
    Digital Love
    """