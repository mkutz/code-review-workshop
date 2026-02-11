package io.github.mkutz.code_review_workshop.review.adapter.out.persistence

import io.github.mkutz.code_review_workshop.review.domain.model.Review
import org.springframework.stereotype.Component

@Component
class ReviewMapper {

    fun toDomain(entity: ReviewEntity): Review =
        Review(
            id =entity.id,
            productId = entity.productId!!,
            author = entity.author,
            rating = entity.rating,
            comment = entity.comment,
        )

    fun toEntity(review: Review): ReviewEntity =
        ReviewEntity(
            id = review.id,
            productId = review.productId,
            author = review.author,
            rating = review.rating,
            comment = review.comment,
        )
}
