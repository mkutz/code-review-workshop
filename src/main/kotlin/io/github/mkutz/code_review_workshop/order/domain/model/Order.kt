package io.github.mkutz.code_review_workshop.order.domain.model

import java.math.BigDecimal
import java.util.UUID

class Order(
    val id: UUID? = null,
    val customerId: UUID,
    val items: List<OrderItem> = emptyList(),
    val status: String = "PENDING",
    val totalPrice: BigDecimal = BigDecimal.ZERO,
)
