package io.github.mkutz.code_review_workshop.product

import io.github.mkutz.code_review_workshop.category.Category
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import java.math.BigDecimal
import java.util.UUID

@Entity
class Product(
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID? = null,
    var name: String = "",
    var description: String? = null,
    var price: BigDecimal = BigDecimal.ZERO,
    @ManyToOne
    @JoinColumn(name = "category_id")
    var category: Category? = null,
    var inStock: Boolean = true,
)
