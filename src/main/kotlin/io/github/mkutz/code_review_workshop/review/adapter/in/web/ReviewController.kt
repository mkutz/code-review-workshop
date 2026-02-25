package io.github.mkutz.code_review_workshop.review.adapter.`in`.web

import io.github.mkutz.code_review_workshop.review.domain.model.Review
import io.github.mkutz.code_review_workshop.review.domain.port.`in`.CreateReviewUseCase
import io.github.mkutz.code_review_workshop.review.domain.port.`in`.GetReviewUseCase
import java.util.UUID
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/products/{productId}/reviews")
class ReviewController(
    private val getReviewUseCase: GetReviewUseCase,
    private val createReviewUseCase: CreateReviewUseCase,
) {

    @GetMapping
    fun findAll(@PathVariable productId: UUID): ResponseEntity<List<ReviewResponse>> {
        val reviews = getReviewUseCase.getReviewsForProduct(productId)
            ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(reviews.map { ReviewResponse.from(it) })
    }

    @PostMapping
    fun create(@PathVariable productId: UUID, @RequestBody request: ReviewRequest): ResponseEntity<ReviewResponse> {
        val review = Review(productId = productId, author = request.author, rating = request.rating, comment = request.comment)
        val created = createReviewUseCase.createReview(productId, review)
            ?: return ResponseEntity.notFound().build()
        return ResponseEntity.status(HttpStatus.CREATED).body(ReviewResponse.from(created))
    }
}
