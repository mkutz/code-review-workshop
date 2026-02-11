package io.github.mkutz.code_review_workshop.review.adapter.`in`.web

import io.github.mkutz.code_review_workshop.review.domain.model.Review
import java.util.UUID

data class ReviewResponse(
    val id: UUID?,
    val productId: UUID,
    val author: String,
    val rating: Int,
    val comment: String?
) {
    companion object {
        fun from(review: Review) = ReviewResponse(
            id = review.id,
            productId = review.productId,
            author = review.author,
            rating = review.rating,
            comment = review.comment
        )
    }
}
