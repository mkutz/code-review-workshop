package io.github.mkutz.code_review_workshop.product.adapter.`in`.web

import io.github.mkutz.code_review_workshop.product.domain.model.Product
import io.github.mkutz.code_review_workshop.product.domain.port.`in`.*
import java.util.UUID
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/products")
class ProductController(
    private val getProductUseCase: GetProductUseCase,
    private val createProductUseCase: CreateProductUseCase,
    private val updateProductUseCase: UpdateProductUseCase,
    private val deleteProductUseCase: DeleteProductUseCase,
) {

    @GetMapping
    fun findAll(): List<ProductResponse> =
        getProductUseCase.getAllProducts().map { ProductResponse.from(it) }

    @GetMapping("/search")
    fun search(@RequestParam name: String): List<ProductResponse> =
        getProductUseCase.searchProducts(name).map { ProductResponse.from(it) }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: UUID): ResponseEntity<ProductResponse> {
        val product = getProductUseCase.getProduct(id) ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(ProductResponse.from(product))
    }

    @PostMapping
    fun create(@RequestBody request: ProductRequest): ResponseEntity<ProductResponse> {
        val product = Product(
            name = request.name,
            description = request.description,
            price = request.price,
            categoryId = request.categoryId,
        )
        val created = createProductUseCase.createProduct(product)
        return ResponseEntity.status(HttpStatus.CREATED).body(ProductResponse.from(created))
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: UUID, @RequestBody request: ProductRequest): ResponseEntity<ProductResponse> {
        val product = Product(
            name = request.name,
            description = request.description,
            price = request.price,
            categoryId = request.categoryId,
            inStock = request.inStock ?: false,
        )
        val updated = updateProductUseCase.updateProduct(id, product) ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(ProductResponse.from(updated))
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: UUID): ResponseEntity<ProductResponse> {
        val deleted = deleteProductUseCase.deleteProduct(id) ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(ProductResponse.from(deleted))
    }
}
