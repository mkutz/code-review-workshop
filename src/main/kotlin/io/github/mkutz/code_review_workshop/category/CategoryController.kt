package io.github.mkutz.code_review_workshop.category

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
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/categories")
class CategoryController(private val categoryRepository: CategoryRepository) {

    @GetMapping
    fun findAll(): List<Category> = categoryRepository.findAll()

    @GetMapping("/{id}")
    fun findById(@PathVariable id: UUID): ResponseEntity<Category> =
        categoryRepository.findById(id)
            .map { ResponseEntity.ok(it) }
            .orElse(ResponseEntity.notFound().build())

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody category: Category): Category = categoryRepository.save(category)

    @PutMapping("/{id}")
    fun update(@PathVariable id: UUID, @RequestBody category: Category): ResponseEntity<Category> =
        if (categoryRepository.existsById(id)) {
            category.id = id
            ResponseEntity.ok(categoryRepository.save(category))
        } else {
            ResponseEntity.notFound().build()
        }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: UUID): ResponseEntity<Category> =
        categoryRepository.findById(id)
            .map { category ->
                categoryRepository.delete(category)
                ResponseEntity.ok(category)
            }
            .orElse(ResponseEntity.notFound().build())
}
