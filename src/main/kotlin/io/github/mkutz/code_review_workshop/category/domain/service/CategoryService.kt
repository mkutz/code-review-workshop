package io.github.mkutz.code_review_workshop.category.domain.service

import io.github.mkutz.code_review_workshop.category.domain.model.Category
import io.github.mkutz.code_review_workshop.category.domain.port.`in`.CreateCategoryUseCase
import io.github.mkutz.code_review_workshop.category.domain.port.`in`.DeleteCategoryUseCase
import io.github.mkutz.code_review_workshop.category.domain.port.`in`.GetCategoryUseCase
import io.github.mkutz.code_review_workshop.category.domain.port.`in`.UpdateCategoryUseCase
import io.github.mkutz.code_review_workshop.category.domain.port.out.LoadCategoryPort
import io.github.mkutz.code_review_workshop.category.domain.port.out.SaveCategoryPort
import java.util.UUID
import org.springframework.stereotype.Service

@Service
class CategoryService(private val loadCategoryPort: LoadCategoryPort, private val saveCategoryPort: SaveCategoryPort) : GetCategoryUseCase, CreateCategoryUseCase, UpdateCategoryUseCase, DeleteCategoryUseCase {

    override fun getCategory(id: UUID): Category? = loadCategoryPort.loadCategory(id)

    override fun getAllCategories(): List<Category> = loadCategoryPort.loadAllCategories()

    override fun createCategory(category: Category): Category = saveCategoryPort.saveCategory(category)

    override fun updateCategory(id: UUID, category: Category): Category? {
        if (!loadCategoryPort.existsById(id)) return null
        return saveCategoryPort.saveCategory(Category(id = id, name = category.name, description = category.description))
    }

    override fun deleteCategory(id: UUID): Category? {
        val category = loadCategoryPort.loadCategory(id) ?: return null
        saveCategoryPort.deleteCategory(id)
        return category
    }
}
