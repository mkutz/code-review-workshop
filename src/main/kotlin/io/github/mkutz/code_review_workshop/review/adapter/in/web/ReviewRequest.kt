package io.github.mkutz.code_review_workshop.review.adapter.`in`.web

data class ReviewRequest(
    val author: String,
    val rating: Int,
    val comment: String? = null,
)
