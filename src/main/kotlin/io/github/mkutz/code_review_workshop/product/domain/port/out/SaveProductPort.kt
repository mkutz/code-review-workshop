package io.github.mkutz.code_review_workshop.product.domain.port.out

import io.github.mkutz.code_review_workshop.product.domain.model.Product
import java.util.UUID

interface SaveProductPort {

    fun saveProduct(product: Product): Product

    fun deleteProduct(id: UUID)
}
