package io.github.mkutz.code_review_workshop.category.domain.port.`in`

import io.github.mkutz.code_review_workshop.category.domain.model.Category

interface CreateCategoryUseCase {
    fun createCategory(category: Category): Category
}
