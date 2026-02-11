package io.github.mkutz.code_review_workshop.category

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.util.UUID

@Entity
class Category(
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID? = null,
    var name: String,
    var description: String? = null,
)
