package io.github.mkutz.code_review_workshop.product

import jakarta.persistence.EntityManager
import org.springframework.stereotype.Repository

@Repository
class ProductSearchRepository(private val entityManager: EntityManager) {

    fun searchByName(name: String): List<Product> =
        entityManager.createQuery(
            "SELECT p FROM Product p WHERE LOWER(p.name) LIKE LOWER(:name)",
            Product::class.java,
        ).setParameter("name", "%$name%").resultList
}
