package io.github.mkutz.code_review_workshop.review.domain.port.`in`

import io.github.mkutz.code_review_workshop.review.domain.model.Review
import java.util.UUID

interface GetReviewUseCase {
    fun getReviewsForProduct(productId: UUID): List<Review>?
}
