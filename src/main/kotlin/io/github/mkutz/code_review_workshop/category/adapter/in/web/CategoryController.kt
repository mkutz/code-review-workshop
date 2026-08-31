package io.github.mkutz.code_review_workshop.category.adapter.`in`.web

import io.github.mkutz.code_review_workshop.category.domain.model.Category
import io.github.mkutz.code_review_workshop.category.domain.port.`in`.CreateCategoryUseCase
import io.github.mkutz.code_review_workshop.category.domain.port.`in`.DeleteCategoryUseCase
import io.github.mkutz.code_review_workshop.category.domain.port.`in`.GetCategoryUseCase
import io.github.mkutz.code_review_workshop.category.domain.port.`in`.UpdateCategoryUseCase
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
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/categories")
class CategoryController(
    private val getCategoryUseCase: GetCategoryUseCase,
    private val createCategoryUseCase: CreateCategoryUseCase,
    private val updateCategoryUseCase: UpdateCategoryUseCase,
    private val deleteCategoryUseCase: DeleteCategoryUseCase
) {

    @GetMapping
    fun findAll(): List<CategoryResponse> =
        getCategoryUseCase.getAllCategories().map { CategoryResponse.from(it) }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: UUID): ResponseEntity<CategoryResponse> {
        val category = getCategoryUseCase.getCategory(id) ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(CategoryResponse.from(category))
    }

    @PostMapping
    fun create(@RequestBody request: CategoryRequest): ResponseEntity<CategoryResponse> {
        val category = Category(name = request.name, description = request.description)
        val created = createCategoryUseCase.createCategory(category)
        return ResponseEntity.status(HttpStatus.CREATED).body(CategoryResponse.from(created))
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: UUID, @RequestBody request: CategoryRequest): ResponseEntity<CategoryResponse> {
        val category = Category(name = request.name, description = request.description)
        val updated = updateCategoryUseCase.updateCategory(id, category) ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(CategoryResponse.from(updated))
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: UUID): ResponseEntity<CategoryResponse> {
        val deleted = deleteCategoryUseCase.deleteCategory(id) ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(CategoryResponse.from(deleted))
    }
}
