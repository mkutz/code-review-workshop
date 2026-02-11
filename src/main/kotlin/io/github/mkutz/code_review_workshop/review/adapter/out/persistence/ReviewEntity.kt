package io.github.mkutz.code_review_workshop.review.adapter.out.persistence

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(name = "review")
class ReviewEntity(
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID? = null,
    var productId: UUID? = null,
    var author: String = "",
    var rating: Int = 0,
    var comment: String? = null,
)
