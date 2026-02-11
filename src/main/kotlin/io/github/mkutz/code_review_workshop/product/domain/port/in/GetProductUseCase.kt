package io.github.mkutz.code_review_workshop.product.domain.port.`in`

import io.github.mkutz.code_review_workshop.product.domain.model.Product
import java.util.UUID

interface GetProductUseCase {

    fun getProduct(id: UUID): Product?

    fun getAllProducts(): List<Product>

    fun searchProducts(name: String): List<Product>
}
