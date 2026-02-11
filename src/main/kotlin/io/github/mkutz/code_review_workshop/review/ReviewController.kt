package io.github.mkutz.code_review_workshop.review

import io.github.mkutz.code_review_workshop.product.ProductRepository
import java.util.UUID
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/products/{productId}/reviews")
class ReviewController(
    private val reviewRepository: ReviewRepository,
    private val productRepository: ProductRepository,
) {

    @GetMapping
    fun findAll(@PathVariable productId: UUID): ResponseEntity<List<Review>> {
        if (!productRepository.existsById(productId)) {
            return ResponseEntity.notFound().build()
        }
        return ResponseEntity.ok(reviewRepository.findByProductId(productId))
    }

    @PostMapping
    fun create(@PathVariable productId: UUID, @RequestBody review: Review): ResponseEntity<Review> {
        if (!productRepository.existsById(productId)) {
            return ResponseEntity.notFound().build()
        }
        review.productId = productId
        return ResponseEntity.status(HttpStatus.CREATED).body(reviewRepository.save(review))
    }
}
