package io.github.mkutz.code_review_workshop.order.adapter.out.persistence

import java.util.UUID
import org.springframework.data.jpa.repository.JpaRepository

interface OrderJpaRepository : JpaRepository<OrderEntity, UUID>
