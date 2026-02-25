package io.github.mkutz.code_review_workshop.review.domain.port.out

import io.github.mkutz.code_review_workshop.review.domain.model.Review

interface SaveReviewPort {
    fun saveReview(review: Review): Review
}
