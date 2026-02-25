package io.github.mkutz.code_review_workshop.product.adapter.out.persistence

import io.github.mkutz.code_review_workshop.category.adapter.out.persistence.CategoryJpaRepository
import io.github.mkutz.code_review_workshop.product.domain.model.Product
import io.github.mkutz.code_review_workshop.product.domain.port.out.LoadProductPort
import io.github.mkutz.code_review_workshop.product.domain.port.out.SaveProductPort
import java.util.UUID
import org.springframework.stereotype.Component

@Component
class ProductPersistenceAdapter(
    private val productJpaRepository: ProductJpaRepository,
    private val productSearchJpaRepository: ProductSearchJpaRepository,
    private val categoryJpaRepository: CategoryJpaRepository,
    private val productMapper: ProductMapper,
) : LoadProductPort, SaveProductPort {

    override fun loadProduct(id: UUID): Product? =
        productJpaRepository.findById(id)
            .map { productMapper.toDomain(it) }
            .orElse(null)

    override fun loadAllProducts(): List<Product> =
        productJpaRepository.findAll().map { productMapper.toDomain(it) }

    override fun searchProductsByName(name: String): List<Product> =
        productSearchJpaRepository.searchByName(name)

    override fun existsById(id: UUID): Boolean = productJpaRepository.existsById(id)

    override fun saveProduct(product: Product): Product {
        val category = product.categoryId?.let { categoryJpaRepository.findById(it).orElse(null) }
        val entity = productMapper.toEntity(product, category)
        return productMapper.toDomain(productJpaRepository.save(entity))
    }

    override fun deleteProduct(id: UUID) = productJpaRepository.deleteById(id)
}
