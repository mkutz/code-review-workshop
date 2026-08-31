package io.github.mkutz.code_review_workshop.review.domain.model

import java.util.UUID

class Review(
    val id: UUID? = null,
    val productId: UUID,
    val author: String,
    val rating: Int,
    val comment:String? = null,
)
