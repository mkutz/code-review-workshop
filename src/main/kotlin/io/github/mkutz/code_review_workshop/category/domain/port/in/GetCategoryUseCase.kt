package io.github.mkutz.code_review_workshop.category.domain.port.`in`

import io.github.mkutz.code_review_workshop.category.domain.model.Category
import java.util.UUID

interface GetCategoryUseCase {
    fun getCategory(id: UUID): Category?
    fun getAllCategories(): List<Category>
}
