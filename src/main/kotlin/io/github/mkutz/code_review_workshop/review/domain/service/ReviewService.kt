package io.github.mkutz.code_review_workshop.review.domain.service

import io.github.mkutz.code_review_workshop.product.domain.port.out.LoadProductPort
import io.github.mkutz.code_review_workshop.review.domain.model.Review
import io.github.mkutz.code_review_workshop.review.domain.port.`in`.CreateReviewUseCase
import io.github.mkutz.code_review_workshop.review.domain.port.`in`.GetReviewUseCase
import io.github.mkutz.code_review_workshop.review.domain.port.out.LoadReviewPort
import io.github.mkutz.code_review_workshop.review.domain.port.out.SaveReviewPort
import java.util.UUID
import org.springframework.stereotype.Service

@Service
class ReviewService(
    private val loadReviewPort: LoadReviewPort,
    private val saveReviewPort: SaveReviewPort,
    private val loadProductPort:LoadProductPort,
) : GetReviewUseCase, CreateReviewUseCase {

    override fun getReviewsForProduct(productId: UUID): List<Review>? {
        if (!loadProductPort.existsById(productId)) return null
        return loadReviewPort.loadReviewsByProductId(productId)
    }
    override fun createReview(productId: UUID, review: Review): Review? {
        if (!loadProductPort.existsById(productId)) return null
        val reviewToSave = Review(
            productId = productId,
            author = review.author,
            rating = review.rating,
            comment = review.comment,
        )
        return saveReviewPort.saveReview(reviewToSave)
    }
}
