package io.github.mkutz.code_review_workshop.product

import io.github.mkutz.code_review_workshop.category.CategoryRepository
import java.util.UUID
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/products")
class ProductController(
    private val productRepository: ProductRepository,
    private val productSearchRepository: ProductSearchRepository,
    private val categoryRepository: CategoryRepository,
) {

    @GetMapping
    fun findAll(): List<Product> = productRepository.findAll()

    @GetMapping("/search")
    fun search(@RequestParam name: String): List<Product> =
        productSearchRepository.searchByName(name)

    @GetMapping("/{id}")
    fun findById(@PathVariable id: UUID): ResponseEntity<Product> =
        productRepository.findById(id)
            .map { ResponseEntity.ok(it) }
            .orElse(ResponseEntity.notFound().build())

    @PostMapping
    fun create(@RequestBody product: Product): ResponseEntity<Product> {
        val category = product.category?.id?.let { categoryRepository.findById(it).orElse(null) }
            ?: return ResponseEntity.badRequest().build()
        product.category = category
        return ResponseEntity.status(HttpStatus.CREATED).body(productRepository.save(product))
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: UUID, @RequestBody product: Product): ResponseEntity<Product> {
        if (!productRepository.existsById(id)) {
            return ResponseEntity.notFound().build()
        }
        val category = product.category?.id?.let { categoryRepository.findById(it).orElse(null) }
            ?: return ResponseEntity.badRequest().build()
        product.id = id
        product.category = category
        return ResponseEntity.ok(productRepository.save(product))
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: UUID): ResponseEntity<Product> =
        productRepository.findById(id)
            .map { product ->
                productRepository.delete(product)
                ResponseEntity.ok(product)
            }
            .orElse(ResponseEntity.notFound().build())
}
