package io.github.mkutz.code_review_workshop.category.adapter.out.persistence

import io.github.mkutz.code_review_workshop.category.domain.model.Category
import io.github.mkutz.code_review_workshop.category.domain.port.out.LoadCategoryPort
import io.github.mkutz.code_review_workshop.category.domain.port.out.SaveCategoryPort
import java.util.UUID
import org.springframework.stereotype.Component

@Component
class CategoryPersistenceAdapter(private val categoryJpaRepository: CategoryJpaRepository, private val categoryMapper: CategoryMapper) : LoadCategoryPort, SaveCategoryPort {

    override fun loadCategory(id: UUID): Category? =
        categoryJpaRepository.findById(id).map { categoryMapper.toDomain(it) }.orElse(null)

    override fun loadAllCategories(): List<Category> =
        categoryJpaRepository.findAll().map { categoryMapper.toDomain(it) }

    override fun existsById(id: UUID): Boolean = categoryJpaRepository.existsById(id)

    override fun saveCategory(category: Category): Category {
        val entity = categoryMapper.toEntity(category)
        return categoryMapper.toDomain(categoryJpaRepository.save(entity))
    }

    override fun deleteCategory(id: UUID) = categoryJpaRepository.deleteById(id)
}
