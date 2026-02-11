# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

A workshop project for demonstrating and practicing code reviews. Built with Kotlin, Spring Boot 4, and PostgreSQL.

## Build & Test Commands

```bash
./gradlew build          # Build and run all tests
./gradlew test           # Run tests only
./gradlew test --tests "io.github.mkutz.code_review_workshop.SomeTest"  # Run a single test class
./gradlew test --tests "io.github.mkutz.code_review_workshop.SomeTest.someMethod"  # Run a single test method
./gradlew bootRun        # Run the application locally
./gradlew bootJar        # Create executable JAR
./gradlew clean          # Clean build directory
```

## Tech Stack

- **Language:** Kotlin 2.x targeting Java 21
- **Framework:** Spring Boot 4 (Spring MVC, Spring Data JPA)
- **Database:** PostgreSQL
- **Build:** Gradle (Kotlin DSL) with Gradle wrapper
- **Testing:** JUnit 5 with Spring Boot test starters
- **Serialization:** Jackson with Kotlin module

## Architecture

Standard Spring Boot layered architecture under `src/main/kotlin/io/github/mkutz/code_review_workshop/`.

JPA entities use the Kotlin `allOpen` plugin so `@Entity`, `@MappedSuperclass`, and `@Embeddable` classes are automatically made open (required for Hibernate proxying).

Kotlin compiler is configured with `-Xjsr305=strict` for strict JSR-305 nullability checking on Spring annotations.

Tests use Testcontainers for PostgreSQL. The artifact is `org.testcontainers:testcontainers-postgresql` (2.x naming) with the class at `org.testcontainers.postgresql.PostgreSQLContainer`. Container setup is centralized in `ContainersConfig` (`@TestConfiguration` with `@Bean @ServiceConnection`), which test classes pull in via `@Import(ContainersConfig::class)`. The container uses `Wait.forListeningPort()` to ensure port forwarding is ready (required for Rancher Desktop / Lima-based Docker). A test `main()` function in `TestCodeReviewWorkshopApplication.kt` allows running the app locally with Testcontainers via the IDE or `./gradlew bootTestRun`.

## Workshop Purpose

This project serves as a code review workshop with three phases:

### Phase 1: Spotting issues in PRs
Three PRs on `main` that participants review to find non-obvious flaws:
- A subtle security flaw (e.g. missing authorization, IDOR)
- Documentation that becomes outdated due to a change (not in the diff itself)
- Intentional style inconsistencies across PRs (POJOs vs records, commented vs uncommented code) to prompt discussion about what's worth calling out in review

### Phase 2: Large refactoring PR (LGTM problem)
A PR that refactors from layered to hexagonal architecture. Contains:
- A hidden behavioral change in a **separate commit** — reviewers going commit-by-commit can spot it, but those just looking at the full diff likely won't
- Inconsistent formatting (line breaks, spacing) to add noise, demonstrating the value of automated formatters
- A follow-up that introduces **Spotless/ktfmt** to show how formatting noise is eliminated automatically

This demonstrates how large diffs reduce review quality ("looks good to me" reviews).

### Phase 3: Alternatives to code reviews
Discussion and exercise around pair/ensemble programming as a real-time alternative to after-the-fact code reviews. Potentially also a PR decomposition exercise (how would you split the phase 2 PR into reviewable chunks?).
