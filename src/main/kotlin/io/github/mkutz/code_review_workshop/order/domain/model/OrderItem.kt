package io.github.mkutz.code_review_workshop.order.domain.model

import java.math.BigDecimal
import java.util.UUID

class OrderItem(
    val id: UUID? = null,
    val productId: UUID,
    val quantity: Int,
    val unitPrice: BigDecimal,
)
