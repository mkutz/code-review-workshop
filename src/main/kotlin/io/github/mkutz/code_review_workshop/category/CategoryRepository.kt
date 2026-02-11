package io.github.mkutz.code_review_workshop.category

import java.util.UUID
import org.springframework.data.jpa.repository.JpaRepository

interface CategoryRepository : JpaRepository<Category, UUID>
