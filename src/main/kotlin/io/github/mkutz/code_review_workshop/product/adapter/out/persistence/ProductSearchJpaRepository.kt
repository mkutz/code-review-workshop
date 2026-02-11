package io.github.mkutz.code_review_workshop.product.adapter.out.persistence

import io.github.mkutz.code_review_workshop.product.domain.model.Product
import jakarta.persistence.EntityManager
import org.springframework.stereotype.Repository

@Repository
class ProductSearchJpaRepository(
    private val entityManager: EntityManager,
    private val productMapper: ProductMapper,
) {

    fun searchByName(name: String): List<Product> =
        entityManager.createQuery(
            "SELECT p FROM ProductEntity p WHERE LOWER(p.name) LIKE LOWER(:name)",
            ProductEntity::class.java,
        ).setParameter("name", "%$name%")
            .resultList
            .map { productMapper.toDomain(it) }
}
