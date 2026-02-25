package io.github.mkutz.code_review_workshop.customer.adapter.out.persistence

import java.util.UUID
import org.springframework.data.jpa.repository.JpaRepository

interface CustomerJpaRepository : JpaRepository<CustomerEntity, UUID>
