package io.github.mkutz.code_review_workshop

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.testcontainers.context.ImportTestcontainers
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.testcontainers.postgresql.PostgreSQLContainer

@SpringBootTest
@ImportTestcontainers
class CodeReviewWorkshopApplicationTests {

	companion object {
		@ServiceConnection
		val postgres = PostgreSQLContainer("postgres:17")
	}

	@Test
	fun contextLoads() {
	}
}
