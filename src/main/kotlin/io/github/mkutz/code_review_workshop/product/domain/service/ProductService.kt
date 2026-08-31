package io.github.mkutz.code_review_workshop.product.domain.service

import io.github.mkutz.code_review_workshop.product.domain.model.Product
import io.github.mkutz.code_review_workshop.product.domain.port.`in`.CreateProductUseCase
import io.github.mkutz.code_review_workshop.product.domain.port.`in`.DeleteProductUseCase
import io.github.mkutz.code_review_workshop.product.domain.port.`in`.GetProductUseCase
import io.github.mkutz.code_review_workshop.product.domain.port.`in`.UpdateProductUseCase
import io.github.mkutz.code_review_workshop.product.domain.port.out.LoadProductPort
import io.github.mkutz.code_review_workshop.product.domain.port.out.SaveProductPort
import java.util.UUID
import org.springframework.stereotype.Service

@Service
class ProductService(
    private val loadProductPort: LoadProductPort,
    private val saveProductPort: SaveProductPort,
) : GetProductUseCase, CreateProductUseCase, UpdateProductUseCase, DeleteProductUseCase {

    override fun getProduct(id: UUID): Product? = loadProductPort.loadProduct(id)
    override fun getAllProducts(): List<Product> = loadProductPort.loadAllProducts()
    override fun searchProducts(name: String): List<Product> = loadProductPort.searchProductsByName(name)
    override fun createProduct(product: Product): Product = saveProductPort.saveProduct(product)
    override fun updateProduct(id: UUID, product: Product): Product? {
        if (!loadProductPort.existsById(id)) return null
        return saveProductPort.saveProduct(
            Product(
                id = id,
                name = product.name,
                description = product.description,
                price = product.price,
                categoryId = product.categoryId,
                inStock = product.inStock,
            )
        )
    }
    override fun deleteProduct(id: UUID): Product? {
        val product = loadProductPort.loadProduct(id) ?: return null
        saveProductPort.deleteProduct(id)
        return product
    }
}
