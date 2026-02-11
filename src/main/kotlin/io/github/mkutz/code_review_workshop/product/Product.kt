package io.github.mkutz.code_review_workshop.product

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.math.BigDecimal
import java.util.UUID

@Entity
class Product(
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID? = null,
    var name: String,
    var description: String? = null,
    var price: BigDecimal,
    var category: String,
    var inStock: Boolean = true,
)
