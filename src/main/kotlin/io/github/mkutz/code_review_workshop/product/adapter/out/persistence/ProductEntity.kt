package io.github.mkutz.code_review_workshop.product.adapter.out.persistence

import io.github.mkutz.code_review_workshop.category.adapter.out.persistence.CategoryEntity
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.math.BigDecimal
import java.util.UUID

@Entity
@Table(name = "product")
class ProductEntity(
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID? = null,
    var name: String = "",
    var description: String? = null,
    var price: BigDecimal = BigDecimal.ZERO,
    @ManyToOne
    @JoinColumn(name = "category_id")
    var category: CategoryEntity? = null,
    var inStock: Boolean = true,
)
