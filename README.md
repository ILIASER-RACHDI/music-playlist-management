# Music Playlist Management System

Spring Boot REST API for playlist management with artists, albums, songs, playlist ordering, runtime shuffle strategies, export strategies, recommendations and a thread-safe global player.

## Stack

- Java 21
- Spring Boot 3
- Spring Web
- Spring Data JPA / Hibernate
- PostgreSQL
- Flyway
- Spring Security + JWT
- Swagger / OpenAPI
- JUnit 5 / Mockito
- GitHub Actions
- SonarCloud

## Run PostgreSQL

```bash
docker compose up -d
```

## Run API

```bash
mvn spring-boot:run
```

Swagger:

```text
http://localhost:8080/swagger-ui.html
```

## Authentication

Register:

```http
POST /auth/register
```

Login:

```http
POST /auth/login
```

Use the returned JWT token as:

```text
Authorization: Bearer <token>
```

## Main features

- Artist / Album / Song management
- Playlist CRUD
- Add/remove/reorder songs dynamically
- Runtime shuffle strategy selection
- JSON and M3U export
- Simple recommendations
- Thread-safe global player
