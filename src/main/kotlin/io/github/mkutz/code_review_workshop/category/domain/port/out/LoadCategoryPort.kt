package io.github.mkutz.code_review_workshop.category.domain.port.out

import io.github.mkutz.code_review_workshop.category.domain.model.Category
import java.util.UUID

interface LoadCategoryPort {
    fun loadCategory(id: UUID): Category?
    fun loadAllCategories(): List<Category>
    fun existsById(id: UUID): Boolean
}
