package io.github.mkutz.code_review_workshop.review.adapter.out.persistence

import java.util.UUID
import org.springframework.data.jpa.repository.JpaRepository

interface ReviewJpaRepository : JpaRepository<ReviewEntity, UUID> {

    fun findByProductId(productId: UUID): List<ReviewEntity>
}
