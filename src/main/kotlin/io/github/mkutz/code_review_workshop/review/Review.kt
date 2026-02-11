package io.github.mkutz.code_review_workshop.review

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.util.UUID

@Entity
class Review(
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID? = null,
    var productId: UUID? = null,
    var author: String,
    var rating: Int,
    var comment: String? = null,
)
