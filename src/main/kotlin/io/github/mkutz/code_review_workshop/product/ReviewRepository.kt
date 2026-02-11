package io.github.mkutz.code_review_workshop.product

import java.util.Optional
import java.util.UUID
import org.springframework.data.jpa.repository.JpaRepository

interface ReviewRepository : JpaRepository<Review, Long> {

    fun findByProductId(productId: UUID): List<Review>

    fun findByProductIdAndId(productId: UUID, id: Long): Optional<Review>
}
