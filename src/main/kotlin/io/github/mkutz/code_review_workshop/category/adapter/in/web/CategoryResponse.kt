package io.github.mkutz.code_review_workshop.category.adapter.`in`.web

import io.github.mkutz.code_review_workshop.category.domain.model.Category
import java.util.UUID

data class CategoryResponse(val id: UUID?, val name: String, val description: String?) {
    companion object {
        fun from(category: Category) = CategoryResponse(id = category.id, name = category.name, description = category.description)
    }
}
