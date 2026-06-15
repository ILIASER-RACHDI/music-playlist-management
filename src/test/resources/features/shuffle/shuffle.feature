Feature: Playlist shuffle

  Background:
    Given the following users are in database
    | id | email         | password | role      |
    | 1  | user@test.com | password | ROLE_USER |

    And the following songs are in database
    | id | title         | genre   | duration | audio_url                                 | album_id | artist_id |
    | 1  | One More Time | ELECTRO | 320      | https://cdn.example.com/one-more-time.mp3 | 1        | 1         |
    | 2  | Digital Love   | ELECTRO | 300      | https://cdn.example.com/digital-love.mp3  | 1        | 1         |
    | 3  | Around World   | POP     | 270      | https://cdn.example.com/around-world.mp3  | 1        | 1         |

    And the following playlists are in database
    | id | name        | description       | owner_id |
    | 1  | Electro Mix | Best electro songs | 1        |

    And the following playlist songs are in database
    | playlist_id | song_id | position |
    | 1           | 1       | 1        |
    | 1           | 2       | 2        |
    | 1           | 3       | 3        |

    And the authenticated user is
    | email         | role      |
    | user@test.com | ROLE_USER |

  Scenario: Shuffle playlist with random strategy
    When HTTP request POST /playlists/1/shuffle?strategy=RANDOM
    Then HTTP response with status 200 and body contains
    """
    Electro Mix
    """

  Scenario: Shuffle playlist with smart strategy
    When HTTP request POST /playlists/1/shuffle?strategy=SMART
    Then HTTP response with status 200 and body contains
    """
    songs
    """

  Scenario: Shuffle playlist with genre balanced strategy
    When HTTP request POST /playlists/1/shuffle?strategy=GENRE_BALANCED
    Then HTTP response with status 200 and body contains
    """
    songs
    """