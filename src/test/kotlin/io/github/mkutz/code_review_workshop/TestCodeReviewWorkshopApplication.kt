package io.github.mkutz.code_review_workshop

import org.springframework.boot.fromApplication
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.context.annotation.Bean
import org.testcontainers.containers.wait.strategy.Wait
import org.testcontainers.postgresql.PostgreSQLContainer

@TestConfiguration(proxyBeanMethods = false)
class ContainersConfig {

	@Bean
	@ServiceConnection
	fun postgresContainer(): PostgreSQLContainer =
		PostgreSQLContainer("postgres:latest")
			.waitingFor(Wait.forListeningPort())
}

fun main(args: Array<String>) {
	fromApplication<CodeReviewWorkshopApplication>()
		.with(ContainersConfig::class.java)
		.run(*args)
}