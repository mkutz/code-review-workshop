package io.github.mkutz.code_review_workshop.category.domain.port.out

import io.github.mkutz.code_review_workshop.category.domain.model.Category
import java.util.UUID

interface SaveCategoryPort {
    fun saveCategory(category: Category): Category
    fun deleteCategory(id: UUID)
}
