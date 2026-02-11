package io.github.mkutz.code_review_workshop.review.domain.port.out

import io.github.mkutz.code_review_workshop.review.domain.model.Review
import java.util.UUID

interface LoadReviewPort {
    fun loadReviewsByProductId(productId: UUID): List<Review>
}
