package io.github.mkutz.code_review_workshop.product

import java.util.UUID
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

// TODO: add average rating endpoint

@RestController
@RequestMapping("/products/{productId}/reviews")
class ReviewController(private val reviewRepository: ReviewRepository,
                       private val productRepository: ProductRepository) {

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

    // TODO: add pagination
}
