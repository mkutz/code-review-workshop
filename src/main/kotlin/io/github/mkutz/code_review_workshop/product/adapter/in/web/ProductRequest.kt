package io.github.mkutz.code_review_workshop.product.adapter.`in`.web

import java.math.BigDecimal
import java.util.UUID

data class ProductRequest(
    val name: String,
    val description: String? = null,
    val price: BigDecimal,
    val categoryId: UUID? = null,
    val inStock: Boolean? = null,
)
