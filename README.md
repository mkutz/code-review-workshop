# code-review-workshop

A workshop to demonstrate and practice code reviews and alternatives.

## The Application

A simple product catalog REST API built with Kotlin, Spring Boot 4, and PostgreSQL.

### Domain

The application manages **products** with the following attributes:

| Field       | Type        | Required | Default |
|-------------|-------------|----------|---------|
| id          | UUID        | auto     | —       |
| name        | String      | yes      | —       |
| description | String      | no       | null    |
| price       | BigDecimal  | yes      | —       |
| category    | String      | yes      | —       |
| inStock     | Boolean     | no       | true    |

### API Endpoints

All endpoints are under `/products`:

| Method | Path             | Status  | Description              |
|--------|------------------|---------|--------------------------|
| GET    | `/products`      | 200     | List all products        |
| GET    | `/products/{id}` | 200/404 | Get a product by ID      |
| POST   | `/products`      | 201     | Create a new product     |
| PUT    | `/products/{id}` | 200/404 | Update an existing product |
| DELETE | `/products/{id}` | 204     | Delete a product         |

### Architecture

A two-layer setup: `ProductController` (REST) talks directly to `ProductRepository` (Spring Data JPA). There is no service layer. Hibernate manages the schema automatically (`ddl-auto: update`).

## Getting Started

### Prerequisites

- Java 21
- Docker (or Rancher Desktop, Colima, etc.)

### Run Tests

```bash
./gradlew test
```

Tests use [Testcontainers](https://testcontainers.com/) to start a real PostgreSQL instance automatically — no local database setup needed.

### Run Locally

You can start the application from the test source set, which uses Testcontainers to provide the database:

```bash
./gradlew bootTestRun
```

Or run `TestCodeReviewWorkshopApplication.kt`'s `main()` function from your IDE.
