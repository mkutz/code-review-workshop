package io.github.mkutz.code_review_workshop

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import

@SpringBootTest
@Import(ContainersConfig::class)
class CodeReviewWorkshopApplicationTests {

	@Test
	fun contextLoads() {
	}
}