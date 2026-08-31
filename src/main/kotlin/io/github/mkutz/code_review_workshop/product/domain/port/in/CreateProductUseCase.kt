package io.github.mkutz.code_review_workshop.product.domain.port.`in`

import io.github.mkutz.code_review_workshop.product.domain.model.Product

interface CreateProductUseCase {

    fun createProduct(product: Product): Product
}
