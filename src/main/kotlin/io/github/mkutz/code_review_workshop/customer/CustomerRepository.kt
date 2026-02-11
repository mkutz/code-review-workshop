package io.github.mkutz.code_review_workshop.customer

import java.util.UUID
import org.springframework.data.jpa.repository.JpaRepository

interface CustomerRepository : JpaRepository<Customer, UUID>
