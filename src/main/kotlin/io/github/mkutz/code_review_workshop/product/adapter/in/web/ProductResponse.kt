package io.github.mkutz.code_review_workshop.product.adapter.`in`.web

import io.github.mkutz.code_review_workshop.product.domain.model.Product
import java.math.BigDecimal
import java.util.UUID

data class ProductResponse(
    val id: UUID?,
    val name: String,
    val description: String?,
    val price: BigDecimal,
    val categoryId: UUID?,
    val inStock: Boolean,
) {
    companion object {
        fun from(product: Product) = ProductResponse(
            id = product.id,
            name = product.name,
            description = product.description,
            price = product.price,
            categoryId = product.categoryId,
            inStock = product.inStock,
        )
    }
}
