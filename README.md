# Library System Management

A clean Spring Boot 3 REST API for managing books, users, and the borrow/return workflow. It ships with an H2 in-memory database for quick starts, pagination on book queries, validation with helpful messages, centralized exception handling, and a built‑in HTML page to try the API in your browser — no extra tools needed.

- Tech: Spring Boot 3, Spring Web, Spring Data JPA, H2, ModelMapper, Jakarta Validation
- Java: 17
- Build: Maven
- Demo page: `http://localhost:8080/api.html`
- H2 Console: `http://localhost:8080/h2-console` (JDBC URL: `jdbc:h2:mem:libdb`)

## Features

- Books: create, list with pagination and text filter, get, update, delete
- Users: create and list
- Borrow / Return: record when a user borrows or returns a book
- Validation: request DTO validation with readable errors
- Exceptions: global handler for not-found and conflict scenarios
- Test Data: preloaded users and books via `data.sql`
- API Demo: simple HTML page to call endpoints from the browser

## Quick Start

Prerequisites

- Java 17
- Maven 3.9+

Run locally

- `mvn spring-boot:run`
- Open `http://localhost:8080/api.html` to try the API in your browser
- H2 console: `http://localhost:8080/h2-console`
  - JDBC URL: `jdbc:h2:mem:libdb`
  - User: `sa`, Password: (empty)

Main class

- `src/main/java/org/masumjia/librarymanagementsystem/LibrarySystemManagementApplication.java`

Configuration

- `src/main/resources/application.properties`
  - In-memory H2 database, schema auto-update, SQL logged
  - H2 web console enabled at `/h2-console`

## API Overview

Base path: `/api`

Books

- Create: `POST /api/books`
- List (paginated): `GET /api/books?q={text}&page={0..}&size={1..}`
- Get: `GET /api/books/{id}`
- Update: `PUT /api/books/{id}`
- Delete: `DELETE /api/books/{id}`

Users

- Create: `POST /api/users`
- List: `GET /api/users`

Borrowing

- Borrow: `POST /api/borrow/{bookId}/user/{userId}`
- Return: `POST /api/return/{bookId}/user/{userId}`

### Example cURL

- Create a book:
  - `curl -X POST http://localhost:8080/api/books -H "Content-Type: application/json" -d '{"title":"Clean Architecture","author":"Robert C. Martin","isbn":"ISBN-999"}'`
- List books (page 0, size 10, filter by “Clean”):
  - `curl "http://localhost:8080/api/books?q=Clean&page=0&size=10"`
- Borrow a book:
  - `curl -X POST http://localhost:8080/api/borrow/1/user/1`
- Return a book:
  - `curl -X POST http://localhost:8080/api/return/1/user/1`

## Data and Model

Seed data (`src/main/resources/data.sql`):

- Users: Alice, Bob
- Books: Clean Code, Effective Java, Spring in Action

Entities

- `User(id, name, email)`
- `Book(id, title, author, isbn, available)`
- `BorrowRecord(id, book_id, user_id, borrowed_at, returned_at)`

DTOs and Requests

- `CreateUserRequest`, `UserDto`
- `CreateBookRequest`, `UpdateBookRequest`, `BookDto`

## Controllers

- `BookController` (`/api/books`): CRUD + pagination
- `UserController` (`/api/users`): create + list
- `BorrowController` (`/api`): borrow/return endpoints

Global error handling is implemented via `GlobalExceptionHandler` that returns a structured `ApiError` payload.

## Development

Build

- `mvn clean package`

Run

- `mvn spring-boot:run`

Test

- `mvn test`
- Unit tests:
  - `src/test/java/org/masumjia/librarymanagementsystem/service/BookServiceTest.java`
  - `src/test/java/org/masumjia/librarymanagementsystem/service/BorrowServiceTest.java`

## Project Structure

- `src/main/java/.../controller` — REST controllers
- `src/main/java/.../service` — business logic
- `src/main/java/.../repository` — Spring Data JPA repositories
- `src/main/java/.../entity` — JPA entities
- `src/main/java/.../dto` — request/response models
- `src/main/java/.../exception` — custom exceptions + handler
- `src/main/java/.../util` — helpers (e.g., pagination wrapper)
- `src/main/resources/static/api.html` — in‑browser API tester
- `src/main/resources/data.sql` — seed data

## Notes

- Uses in‑memory H2; data resets on restart. For persistence, switch to a real DB in `application.properties`.
- Pagination defaults: `page=0`, `size=10`, optional `q` filter on title/author.

