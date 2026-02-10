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
