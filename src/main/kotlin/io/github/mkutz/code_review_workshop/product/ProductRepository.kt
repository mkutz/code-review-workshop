package io.github.mkutz.code_review_workshop.product

import java.util.UUID
import org.springframework.data.jpa.repository.JpaRepository

interface ProductRepository : JpaRepository<Product, UUID>
