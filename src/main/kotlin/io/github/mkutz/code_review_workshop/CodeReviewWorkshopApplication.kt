package io.github.mkutz.code_review_workshop

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CodeReviewWorkshopApplication

fun main(args: Array<String>) {
	runApplication<CodeReviewWorkshopApplication>(*args)
}
