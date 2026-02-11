package io.github.mkutz.code_review_workshop.product.adapter.out.persistence

import io.github.mkutz.code_review_workshop.category.Category
import io.github.mkutz.code_review_workshop.product.domain.model.Product
import org.springframework.stereotype.Component

@Component
class ProductMapper {

    fun toDomain(entity: ProductEntity): Product =
        Product(
            id = entity.id,
            name = entity.name,
            description = entity.description,
            price = entity.price,
            categoryId = entity.category?.id,
            inStock = entity.inStock,
        )

    fun toEntity(product: Product, category: Category?): ProductEntity =
        ProductEntity(
            id = product.id,
            name = product.name,
            description = product.description,
            price = product.price,
            category = category,
            inStock = product.inStock,
        )
}
