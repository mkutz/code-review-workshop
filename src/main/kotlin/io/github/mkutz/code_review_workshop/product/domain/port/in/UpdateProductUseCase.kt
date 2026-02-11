package io.github.mkutz.code_review_workshop.product.domain.port.`in`

import io.github.mkutz.code_review_workshop.product.domain.model.Product
import java.util.UUID

interface UpdateProductUseCase {

    fun updateProduct(id: UUID, product: Product): Product?
}
