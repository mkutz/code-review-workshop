# code-review-workshop

A workshop to demonstrate and practice code reviews and alternatives.

## The Application

A simple product catalog REST API built with Kotlin, Spring Boot 4, and PostgreSQL.

### Domain

The application manages **categories**, **products**, **reviews**, **customers**, and **orders**.

**Category**

| Field       | Type   | Required | Default |
|-------------|--------|----------|---------|
| id          | UUID   | auto     | —       |
| name        | String | yes      | —       |
| description | String | no       | null    |

**Product**

| Field       | Type        | Required | Default |
|-------------|-------------|----------|---------|
| id          | UUID        | auto     | —       |
| name        | String      | yes      | —       |
| description | String      | no       | null    |
| price       | BigDecimal  | yes      | —       |
| category    | Category    | yes      | —       |
| inStock     | Boolean     | no       | true    |

**Customer**

| Field   | Type   | Required | Default |
|---------|--------|----------|---------|
| id      | UUID   | auto     | —       |
| name    | String | yes      | —       |
| email   | String | yes      | —       |
| address | String | no       | null    |

**Order**

| Field      | Type           | Required | Default   |
|------------|----------------|----------|-----------|
| id         | UUID           | auto     | —         |
| customer   | Customer       | yes      | —         |
| items      | List<OrderItem>| yes      | —         |
| status     | String         | no       | "PENDING" |
| totalPrice | BigDecimal     | auto     | 0         |

**OrderItem**

| Field     | Type       | Required | Default |
|-----------|------------|----------|---------|
| id        | UUID       | auto     | —       |
| product   | Product    | yes      | —       |
| quantity  | Int        | yes      | —       |
| unitPrice | BigDecimal | auto     | —       |

**Review**

| Field     | Type   | Required | Default |
|-----------|--------|----------|---------|
| id        | UUID   | auto     | —       |
| productId | UUID   | yes      | —       |
| author    | String | yes      | —       |
| rating    | Int    | yes      | —       |
| comment   | String | no       | null    |

### API Endpoints

Endpoints for categories:

| Method | Path               | Status  | Description                |
|--------|--------------------|---------|----------------------------|
| GET    | `/categories`      | 200     | List all categories        |
| GET    | `/categories/{id}` | 200/404 | Get a category by ID       |
| POST   | `/categories`      | 201     | Create a new category      |
| PUT    | `/categories/{id}` | 200/404 | Update an existing category|
| DELETE | `/categories/{id}` | 200/404 | Delete a category          |

Endpoints for products:

| Method | Path             | Status  | Description              |
|--------|------------------|---------|--------------------------|
| GET    | `/products`        | 200     | List all products          |
| GET    | `/products/search` | 200     | Search products by name    |
| GET    | `/products/{id}`   | 200/404 | Get a product by ID        |
| POST   | `/products`        | 201     | Create a new product       |
| PUT    | `/products/{id}`   | 200/404 | Update an existing product |
| DELETE | `/products/{id}`   | 200/404 | Delete a product           |

Endpoints for customers:

| Method | Path               | Status  | Description                |
|--------|--------------------|---------|----------------------------|
| GET    | `/customers`       | 200     | List all customers         |
| GET    | `/customers/{id}`  | 200/404 | Get a customer by ID       |
| POST   | `/customers`       | 201     | Create a new customer      |
| PUT    | `/customers/{id}`  | 200/404 | Update an existing customer|
| DELETE | `/customers/{id}`  | 200/404 | Delete a customer          |

Endpoints for orders:

| Method | Path                  | Status  | Description                |
|--------|-----------------------|---------|----------------------------|
| POST   | `/orders`             | 201/404 | Create a new order         |
| GET    | `/orders`             | 200     | List all orders            |
| GET    | `/orders/{id}`        | 200/404 | Get an order by ID         |
| PUT    | `/orders/{id}/status` | 200/404 | Update an order's status   |

Reviews are nested under products:

| Method | Path                              | Status  | Description                    |
|--------|-----------------------------------|---------|--------------------------------|
| GET    | `/products/{id}/reviews`          | 200/404 | List reviews for a product     |
| POST   | `/products/{id}/reviews`          | 201/404 | Create a review for a product  |

### Architecture

A two-layer setup: `ProductController` (REST) talks directly to `ProductRepository` (Spring Data JPA). There is no service layer. Hibernate manages the schema automatically (`ddl-auto: update`).

## Workshop

### Phase 1: Reviewing Pull Requests

There are three open pull requests on this repository, each adding a small feature. Your task is to review them as you would in a real project.

- [PR #9: Add product search endpoint](https://github.com/mkutz/code-review-workshop/pull/9)
- [PR #10: Return deleted product in response](https://github.com/mkutz/code-review-workshop/pull/10)
- [PR #11: Add product reviews](https://github.com/mkutz/code-review-workshop/pull/11)

For each PR:

1. Open the PR on GitHub and read through the diff.
2. Try to understand what the change does and whether it does it well.
3. Leave review comments on anything you find — bugs, security issues, style problems, missing things, or anything else worth discussing.

Don't just look at the code that changed. Think about what the change means for the project as a whole: does it fit with existing conventions? Does it affect anything outside the diff? Is the documentation still accurate?

After you've reviewed all three PRs, we'll discuss what people found and what they missed.

**Things to think about while reviewing:**

- Is the code correct and safe?
- Does it follow the patterns already established in the codebase?
- Are there any side effects outside the changed files?
- What would you flag in a real review, and what would you let slide?

### Phase 2: The Big Diff Problem

There is a [large refactoring PR](https://github.com/mkutz/code-review-workshop/pull/14) that restructures the entire codebase from layered architecture to hexagonal architecture. It touches almost every file in the project.

**Exercise:**

1. Open [PR #14](https://github.com/mkutz/code-review-workshop/pull/14) and try to review the full diff.
2. Then switch to reviewing it commit-by-commit (use the "Commits" tab on GitHub).
3. Compare the two approaches: What did you notice commit-by-commit that you missed in the full diff?

**Things to think about while reviewing:**

- Is this purely a structural refactoring, or did any behavior change?
- How confident are you in your review of the full diff?
- Would you approve this PR as-is?
- What could the author have done to make this easier to review?

### Phase 3: Alternatives to Code Reviews

_Details will be shared during the workshop._

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
