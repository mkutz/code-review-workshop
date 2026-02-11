package io.github.mkutz.code_review_workshop.product

import java.util.UUID
import jakarta.persistence.*

@Entity
data class Review(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    var productId: UUID? = null,
    var author: String,
    var rating: Int,
    var comment: String? = null
    // TODO: add createdAt timestamp
)
