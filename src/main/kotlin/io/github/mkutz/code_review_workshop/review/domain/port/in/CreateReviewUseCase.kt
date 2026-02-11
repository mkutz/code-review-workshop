package io.github.mkutz.code_review_workshop.review.domain.port.`in`

import io.github.mkutz.code_review_workshop.review.domain.model.Review
import java.util.UUID

interface CreateReviewUseCase {
    fun createReview(productId: UUID, review: Review): Review?
}
