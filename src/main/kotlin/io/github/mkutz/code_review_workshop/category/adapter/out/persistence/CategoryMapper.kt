package io.github.mkutz.code_review_workshop.category.adapter.out.persistence

import io.github.mkutz.code_review_workshop.category.domain.model.Category
import org.springframework.stereotype.Component

@Component
class CategoryMapper {
    fun toDomain(entity: CategoryEntity): Category = Category(id = entity.id, name = entity.name, description = entity.description)
    fun toEntity(category: Category): CategoryEntity = CategoryEntity(id = category.id, name = category.name, description = category.description)
}
