package io.github.mkutz.code_review_workshop.review

import java.util.UUID
import org.springframework.data.jpa.repository.JpaRepository

interface ReviewRepository : JpaRepository<Review, UUID> {

    fun findByProductId(productId: UUID): List<Review>

    fun findByProductIdAndId(productId: UUID, id: UUID): Review?
}
