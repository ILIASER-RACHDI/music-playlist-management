Feature: Global music player

  Background:
    Given the following users are in database
    | id | email         | password | role      |
    | 1  | user@test.com | password | ROLE_USER |

    And the following songs are in database
    | id | title         | genre   | duration | audio_url                                 | album_id | artist_id |
    | 1  | One More Time | ELECTRO | 320      | https://cdn.example.com/one-more-time.mp3 | 1        | 1         |
    | 2  | Digital Love   | ELECTRO | 300      | https://cdn.example.com/digital-love.mp3  | 1        | 1         |

    And the following playlists are in database
    | id | name        | description       | owner_id |
    | 1  | Electro Mix | Best electro songs | 1        |

    And the following playlist songs are in database
    | playlist_id | song_id | position |
    | 1           | 1       | 1        |
    | 1           | 2       | 2        |

    And the authenticated user is
    | email         | role      |
    | user@test.com | ROLE_USER |

  Scenario: Start playback
    When HTTP request POST /player/play/1
    Then HTTP response with status 200 and body contains
    """
    PLAYING
    """

  Scenario: Pause playback
    Given the player is playing playlist 1
    When HTTP request POST /player/pause
    Then HTTP response with status 200 and body contains
    """
    PAUSED
    """

  Scenario: Move to next song
    Given the player is playing playlist 1
    When HTTP request POST /player/next
    Then HTTP response with status 200 and body contains
    """
    Digital Love
    """