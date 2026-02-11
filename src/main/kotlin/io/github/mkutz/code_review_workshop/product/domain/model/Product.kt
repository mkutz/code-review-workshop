package io.github.mkutz.code_review_workshop.product.domain.model

import java.math.BigDecimal
import java.util.UUID

class Product(
    val id: UUID? = null,
    val name: String,
    val description: String? = null,
    val price: BigDecimal,
    val categoryId: UUID? = null,
    val inStock: Boolean = false,
)
