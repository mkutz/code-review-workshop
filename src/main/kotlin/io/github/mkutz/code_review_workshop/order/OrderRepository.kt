package io.github.mkutz.code_review_workshop.order

import java.util.UUID
import org.springframework.data.jpa.repository.JpaRepository

interface OrderRepository : JpaRepository<Order, UUID>
