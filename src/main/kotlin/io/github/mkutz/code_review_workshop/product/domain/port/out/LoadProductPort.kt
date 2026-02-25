package io.github.mkutz.code_review_workshop.product.domain.port.out

import io.github.mkutz.code_review_workshop.product.domain.model.Product
import java.util.UUID

interface LoadProductPort {

    fun loadProduct(id: UUID): Product?

    fun loadAllProducts(): List<Product>

    fun searchProductsByName(name: String): List<Product>

    fun existsById(id: UUID): Boolean
}
