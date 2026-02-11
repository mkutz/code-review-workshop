package io.github.mkutz.code_review_workshop.review.adapter.out.persistence

import io.github.mkutz.code_review_workshop.review.domain.model.Review
import io.github.mkutz.code_review_workshop.review.domain.port.out.LoadReviewPort
import io.github.mkutz.code_review_workshop.review.domain.port.out.SaveReviewPort
import java.util.UUID
import org.springframework.stereotype.Component

@Component
class ReviewPersistenceAdapter(
    private val reviewJpaRepository: ReviewJpaRepository,
    private val reviewMapper: ReviewMapper,
) : LoadReviewPort, SaveReviewPort {

    override fun loadReviewsByProductId(productId: UUID): List<Review> =
        reviewJpaRepository.findByProductId(productId).map { reviewMapper.toDomain(it) }

    override fun saveReview(review: Review): Review {
        val entity = reviewMapper.toEntity(review)
        return reviewMapper.toDomain(reviewJpaRepository.save(entity))
    }
}
