package io.github.mkutz.code_review_workshop.category.domain.port.`in`

import io.github.mkutz.code_review_workshop.category.domain.model.Category
import java.util.UUID

interface UpdateCategoryUseCase {
    fun updateCategory(id: UUID, category: Category): Category?
}
